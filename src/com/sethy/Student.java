package com.sethy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class Student extends User {


    private ArrayList<Assignment> assignments;
    private ArrayList<SubmittedAssignment> submittedAssignments = new ArrayList<SubmittedAssignment>();

    public Student(RandomGenerator g) {
        super(g);
    }

    public Student(User rUser) {
        this.first_name = rUser.first_name;
        this.last_name = rUser.last_name;
        this.address = rUser.address;
        this.city = rUser.city;
        this.state = rUser.state;
        this.postalcode = rUser.postalcode;
        this.phone = rUser.phone;
        this.user_id = rUser.user_id;
    }

    public void setAssignments(ArrayList assignments) {
        this.assignments = assignments;
    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return new Date(calendar.getTime().getTime());
    }
    public void genSubmittedAssignments(RandomGenerator generator){

        for (int i = 0; i < assignments.size()-1; i++) {
            submittedAssignments.add(new SubmittedAssignment(assignments.get(i)));
            submittedAssignments.get(i).setScore(assignments.get(i).getPoints()-generator.getNextInt(assignments.get(i).getPoints()/5));
            submittedAssignments.get(i).setSubmission_date(addHoursToJavaUtilDate(assignments.get(i).getDue_date(),-generator.getNextInt(7*24)));

        }
        submittedAssignments.forEach(x->x.setUser_id(user_id));
        submittedAssignments.forEach(x->x.submit());

    }



    public void print(){
        super.print();
        submittedAssignments.forEach(x ->System.out.println(x.getName()+":"+x.getDue_date()+":"+x.getPoints()+":"+x.getScore()+":"+x.getSubmission_date()));
    }

    public Student(){

    }
}
