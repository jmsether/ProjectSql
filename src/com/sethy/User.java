package com.sethy;

public class User extends Table {
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

    private int user_id;
    private String w_num;
    private String first_name;
    private String last_name;
    private String address;
    private String city;
    private String state;
    private String postalcode;
    private String phone;

    public User(String first_name, String last_name){
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public User(RandomGenerator g){
        rGen(g);
    }

    public void print(){
        System.out.println("-----------------");
        System.out.println(first_name + " " + last_name);
        System.out.println("W: " + w_num);
        System.out.println("P: " + phone);
        System.out.println(address);
        System.out.println(city + ", " + state + " " + postalcode);
        //connection.isConnected();
        System.out.println("-----------------");

    }

    public boolean rGen(RandomGenerator generator){
        first_name = generator.getRName(false);
        last_name = generator.getRName(true);
        address = generator.getRAddress();
        city = generator.getCity();
        state = generator.getState();
        postalcode = generator.getZipCode();
        phone = generator.getPhoneNum();
        w_num = "W"+ generator.getRNum(12);


        return true;
    }



}
