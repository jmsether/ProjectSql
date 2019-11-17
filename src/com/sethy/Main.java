package com.sethy;
import com.microsoft.sqlserver.jdbc.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;


public class Main {

    public static void main(String[] args) {
	// write your code here
        String url ="jdbc:sqlserver://localhost;user=root;password=root;";
        /*try{
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:1433/Higby_Park_Starks","root","root");
            //here sonoo is database name, root is username and password
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from users");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
            con.close();
        }catch(Exception e){ System.out.println(e);}*/
        String dbURL = "jdbc:sqlserver://127.0.0.1:1434";
        String user = "root";
        String pass = "letmein";
        //Connection conn = null;
        System.out.println("Connecting to database");

        String sql = "SELECT * FROM users";
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
        NameGenerator g = null;
        try {
            g = new NameGenerator();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 10; i++){
            System.out.println(g.getRName(false) + " " + g.getRName(true));
        }
    }
}

