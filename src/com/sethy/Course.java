package com.sethy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Course implements Table {

    private int class_id;
    private int class_num;
    private String class_type;

    private String sqlLastId = "SELECT TOP 1 user_id FROM classes ORDER BY user_id DESC";

    private User[] students;

    private boolean active;

    private Assignment[] assignments;

    public Course(RandomGenerator generator){
        rGen(generator);
    }

    public void rGen(RandomGenerator generator){
        setLastId();
        class_type = generator.getRClassType();
        class_num = generator.getRClassNum();
    }

    //TODO move all instances of this function up to the sqlConnection class
    private void setLastId() {

        try{


            Statement statement = connection.getConn().createStatement();
            ResultSet result = statement.executeQuery(sqlLastId);


            if(result.next()){
                class_id = result.getInt(1)+1;
            }else{
                System.err.println("CHECK YOUR PERMISSIONS ON SQL DATABASE!!!");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public boolean submit() {
        return false;
    }
}
