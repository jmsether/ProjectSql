package com.sethy;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Course implements Table {

    private int class_id;
    private int class_num;
    private String class_type;

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Date date = new Date(System.currentTimeMillis());

    private String sqlLastId = "SELECT TOP 1 class_id FROM classes ORDER BY class_id DESC";

    private ArrayList<Student> students = new ArrayList<Student>();

    private boolean active;

    private ArrayList<Assignment> assignments = new ArrayList<Assignment>();

    public Course(RandomGenerator generator){
        rGen(generator);
    }

    public void rGen(RandomGenerator generator){
        setLastId();
        class_type = generator.getRClassType();
        class_num = generator.getRClassNum();
        genAssignments(generator);
        for (int i = 0; i < 20; i++) {
            Student x = new Student(generator.getRUser());
            x.print();
            if(!students.contains(x)){
                students.add(x);
                students.get(students.size()-1).setAssignments(assignments);
                students.get(students.size()-1).genSubmittedAssignments(generator);
                students.get(students.size()-1).print();
            }else{
                i--;
            }
        }


    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return new Date(calendar.getTime().getTime());
    }

    //Assignment(String name, int points, Date due_date, int assignment_id)
    public void genAssignments(RandomGenerator generator){
        for(int i = 0;i < generator.getNextInt(9); i++){
            assignments.add(new Assignment("Assignment "+i,generator.getNextInt(9)*10,addHoursToJavaUtilDate(date,(generator.getNextInt(20)+i*2)*24),0));
        }
        for(int i = 0;i < generator.getNextInt(9); i++){
            assignments.add(new Assignment("Reading  "+i,generator.getNextInt(9)*10,addHoursToJavaUtilDate(date,(generator.getNextInt(20)+i*2)*24),0));
        }
        String x = generator.getRAssignmentName();
        for(int i = 0;i < generator.getNextInt(9); i++){
            assignments.add(new Assignment("x  "+i,generator.getNextInt(9)*10,addHoursToJavaUtilDate(date,(generator.getNextInt(20)+i*2)*24),0));
        }
    }

    //TODO move all instances of this function up to the sqlConnection class
    private void setLastId() {

        try{


            Statement statement = connection.getConn().createStatement();
            ResultSet result = statement.executeQuery(sqlLastId);


            if(result.next()){
                class_id = result.getInt(1)+1;
            }else{
                System.err.println("CHECK YOUR PERMISSIONS ON SQL DATABASE!!!");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public boolean submit() {
        return false;
    }
}
