package com.sethy;

import com.sethy.Table;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Assignment implements Table {

    protected int assignment_id;
    private Date due_date;

    private int points;
    private String name;

    protected int class_id;

    private String sqlSubmitAssignment = "insert into assignments(assignment_id, class_id, name, due_date, points)" +
            "values(?, ?, ?, ? ,?)";

    public Assignment(String name,int class_id, int points, Date due_date, int assignment_id){
        this.name = name;
        this.class_id = class_id;
        this.points = points;
        this.due_date = due_date;
        this.assignment_id = assignment_id;
    }

    public Assignment(Assignment assignment) {
        this.name = assignment.name;
        this.points = assignment.points;
        this.class_id = assignment.class_id;
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
        try {

            PreparedStatement statement = connection.getConn().prepareStatement(sqlSubmitAssignment);
            connection.getConn().setAutoCommit(false);
            statement.setInt(1,assignment_id);
            statement.setInt(2,class_id);
            statement.setString(3,name);
            statement.setDate(4,due_date);
            statement.setInt(5,points);


            int rowsInserted = statement.executeUpdate();

            connection.getConn().setAutoCommit(true);
            if (rowsInserted > 0) {
                System.out.println("A new assignment was inserted successfully!");
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
