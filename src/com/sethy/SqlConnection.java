package com.sethy;


import java.sql.*;


public final class SqlConnection {
    /*This is a singleton style class
    * Singletons are a class that init only once
    * and can then be shared amongst other classes.
    * This singleton class is being uses as a driver
    * for the database as to keep the connections down to one
    * and to help avoid race conditions on the database.
    * To read up on singletons please refer to this link:
    * https://en.wikipedia.org/wiki/Singleton_pattern
    *  */
    private static volatile SqlConnection connection = null;

    //JDBC does not work well with Windows auth. Please use sql login instead
    private String dbURL = "jdbc:sqlserver://127.0.0.1:1434";
    private String user = "root"; // <- you will need to add this user to the ssms database
    private String pass = "letmein";// <- use this password for the database pls

    Connection conn = null;

    /*This is fired only once with anything from this class is called.
    * THis is using a JIT method to load this class there for we will
    * only connect to the database when it is time to.*/

    public void connect(){

    }

    private SqlConnection(){

        //Connection conn = null;
        System.out.println("Connecting to database");

        String sql = "SELECT * FROM users";

        try {
            conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                System.out.println("Connected");
                DatabaseMetaData dm = conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }else{
                System.err.println("No Connection! Please check settings.");
                System.exit(-222);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        /*
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {

            DatabaseMetaData meta = conn.getMetaData();

            String catalog = null, schemaPattern = null, tableNamePattern = null;
            String[] types = {"TABLE"};

            ResultSet rsTables = meta.getTables(catalog, schemaPattern, tableNamePattern, types);


            while (rsTables.next()) {
                String tableName = rsTables.getString(3);
                System.out.println("\n=== TABLE: " + tableName);

                String columnNamePattern = null;
                ResultSet rsColumns = meta.getColumns(catalog, schemaPattern, tableName, columnNamePattern);

                ResultSet rsPK = meta.getPrimaryKeys(catalog, schemaPattern, tableName);

                while (rsColumns.next()) {
                    String columnName = rsColumns.getString("COLUMN_NAME");
                    String columnType = rsColumns.getString("TYPE_NAME");
                    int columnSize = rsColumns.getInt("COLUMN_SIZE");
                    System.out.println("\t" + columnName + " - " + columnType + "(" + columnSize + ")");
                }

                while (rsPK.next()) {
                    String primaryKeyColumn = rsPK.getString("COLUMN_NAME");
                    System.out.println("\tPrimary Key Column: " + primaryKeyColumn);
                }

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }*/
    }

    public Connection getConn(){
        return conn;
    }

    public Statement statement() throws SQLException {
        return conn.createStatement();
    }

    public boolean isConnected(){
        if (conn != null) {
            //System.out.println("Connected");
            return true;
        }else{
            return false;
        }
    }

    // not being called yet
    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized SqlConnection getInstance(){
        if(connection == null){
            synchronized(SqlConnection.class){
                if(connection == null){
                    connection = new SqlConnection();
                }
            }
        }
        return connection;
    }

}
