package com.sethy;

import java.sql.*;

public class User implements Table {
    /*
    === TABLE: users
	user_id - int(10)
	w_num - nvarchar(60)
	first_name - nvarchar(150)
	last_name - nvarchar(150)
	address - nvarchar(200)
	city - nvarchar(200)
	state - nvarchar(60)
	postalcode - nvarchar(60)
	phone - nvarchar(10)
	Primary Key Column: user_id
    */

    private int user_id = 0;
    private String w_num;
    private String first_name;
    private String last_name;
    private String address;
    private String city;
    private String state;
    private String postalcode;
    private String phone;

    private String sqlSubmitUser = "insert into users(user_id, w_num, first_name, last_name, address, city, state, postalcode, phone)" +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private String sqlLastId = "SELECT TOP 1 user_id FROM users ORDER BY user_id DESC";
    private String sqlGetW_num = "SELECT user_id FROM users where w_num = ?";

    public User(){}
    private void setLastId() {

        try{


            Statement statement = connection.getConn().createStatement();
            ResultSet result = statement.executeQuery(sqlLastId);


            if(result.next()){
                user_id = result.getInt(1)+1;
            }else{
                System.err.println("CHECK YOUR PERMISSIONS ON SQL DATABASE!!!");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }


    public User(String first_name, String last_name){
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public User(RandomGenerator g){
        if(connection.isConnected()){
            rGen(g);
        }
    }



    public void print(){
        System.out.println("-----------------");
        System.out.println(first_name + " " + last_name);
        System.out.println("U: " + user_id);
        System.out.println("W: " + w_num);
        System.out.println("P: " + phone);
        System.out.println(address);
        System.out.println(city + ", " + state + " " + postalcode);
        //connection.isConnected();
        System.out.println("-----------------");

    }

    private int checkWnum(int x){
        try{

            PreparedStatement statement = connection.getConn().prepareStatement(sqlGetW_num);
            statement.setString(1,"W"+x);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                System.err.println("W number already exists trying next number up");
                return checkWnum(x+1);
            }else{
                return x;
            }





        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return x;
    }

    public boolean rGen(RandomGenerator generator){
        first_name = generator.getRName(false);
        last_name = generator.getRName(true);
        address = generator.getRAddress();
        city = generator.getCity();
        state = generator.getState();
        postalcode = generator.getZipCode();
        phone = generator.getPhoneNum();
        w_num = "W"+ checkWnum(Integer.valueOf(generator.getRNum(12)));

        if(user_id == 0){
            setLastId();
        }

        return true;
    }

    //(user_id, w_num, first_name, last_name, address, city, state, postalcode, phone)
    @Override
    public boolean submit() {
        try {

            PreparedStatement statement = connection.getConn().prepareStatement(sqlSubmitUser);
            connection.getConn().setAutoCommit(false);
            statement.setInt(1,user_id);
            statement.setString(2,w_num);
            statement.setString(3,first_name);
            statement.setString(4,last_name);
            statement.setString(5,address);
            statement.setString(6,city);
            statement.setString(7,state);
            statement.setString(8,postalcode);
            statement.setString(9,phone);

            int rowsInserted = statement.executeUpdate();

            connection.getConn().setAutoCommit(true);
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }


            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection.getConn() != null) {
                try {

                    connection.getConn().rollback();

                    System.out.println("Rolled back.");
                } catch (SQLException exrb) {
                    exrb.printStackTrace();
                }
            }
            return false;
        }
    }
}
