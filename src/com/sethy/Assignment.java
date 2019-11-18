package com.sethy;

import com.sethy.Table;

import java.sql.Date;

public class Assignment implements Table {

    private int assignment_id;
    private Date submission_date;

    @Override
    public boolean submit() {
        return false;
    }
}
