package com.sethy;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubmittedAssignment extends Assignment {

    private Date submission_date;

    private int score;

    private int user_id;

    private String sqlSubmitAssignment = "insert into assignments_users(assignment_id, user_id, submission_date, score)" +
            "values(?, ?, ?, ?)";


    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public SubmittedAssignment(String name, int class_id, int points, Date due_date, int assignment_id) {
        super(name, class_id,points, due_date, assignment_id);
    }

    public SubmittedAssignment(Assignment assignment) {
        super(assignment);
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return score;
    }

    public Date getSubmission_date(){
        return submission_date;
    }

    public void setSubmission_date(Date date){
        submission_date = date;
    }

    @Override
    public boolean submit() {
        try {

            PreparedStatement statement = connection.getConn().prepareStatement(sqlSubmitAssignment);
            connection.getConn().setAutoCommit(false);
            statement.setInt(1,assignment_id);
            statement.setInt(2,user_id);
            statement.setDate(3,submission_date);
            statement.setInt(4,score);


            int rowsInserted = statement.executeUpdate();

            connection.getConn().setAutoCommit(true);
            if (rowsInserted > 0) {
                System.out.println("A new score was inserted successfully!");
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
