package com.sethy;

import com.sethy.Table;

import java.sql.Date;

public class Assignment implements Table {

    private int assignment_id;
    private Date due_date;

    private int points;
    private String name;

    public Assignment(String name, int points, Date due_date, int assignment_id){
        this.name = name;
        this.points = points;
        this.due_date = due_date;
        this.assignment_id = assignment_id;
    }

    public Assignment(Assignment assignment) {
        this.name = assignment.name;
        this.points = assignment.points;
        this.due_date = assignment.due_date;
        this.assignment_id = assignment.assignment_id;
    }

    public int getPoints() {
        return points;
    }

    public Date getDue_date(){
        return due_date;
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean submit() {
        return false;
    }
}
