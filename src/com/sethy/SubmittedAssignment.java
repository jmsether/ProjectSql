package com.sethy;

import java.sql.Date;

public class SubmittedAssignment extends Assignment {

    private Date submission_date;

    private int score;


    public SubmittedAssignment(String name, int points, Date due_date, int assignment_id) {
        super(name, points, due_date, assignment_id);
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
}
