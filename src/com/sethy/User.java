package com.sethy;

public class User {
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



    public boolean rGen(){



        return true;
    }



}
