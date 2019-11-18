package com.sethy;

import com.sethy.Table;

import java.sql.Date;

public class Assignment implements Table {

    private int assignment_id;
    private Date due_date;

    private int points;
    private String name;

    @Override
    public boolean submit() {
        return false;
    }
}
