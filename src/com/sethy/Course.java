package com.sethy;

import java.sql.*;
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

    private String sqlSubmitClass = "insert into classes(class_id, class_num, class_type)" +
            "values(?, ?, ?)";


    private String sqlSubmitClassList = "insert into classes_users(class_id, user_id, deleted)" +
            "values(?, ?, ?)";

    private String sqlGet_num = "SELECT assignment_id FROM assignments where assignment_id = ?";
    private String sqlGet_num2 = "SELECT class_num FROM classes where class_num = ?";

    private ArrayList<Student> students = new ArrayList<Student>();

    private boolean active;

    private ArrayList<Assignment> assignments = new ArrayList<Assignment>();

    public Course(RandomGenerator generator){
        rGen(generator);
    }

    public void rGen(RandomGenerator generator){
        setLastId();
        class_type = generator.getRClassType();
        class_num = checkWnum(generator.getRClassNum());
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


    private int checkRnum(int x){
        try{

            PreparedStatement statement = connection.getConn().prepareStatement(sqlGet_num2);
            statement.setInt(1,x);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                System.err.println("number already exists trying next number up");
                return checkWnum(x+1);
            }else{
                return x;
            }





        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return x;
    }

    private int checkWnum(int x){
        try{

            PreparedStatement statement = connection.getConn().prepareStatement(sqlGet_num);
            statement.setInt(1,x);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                System.err.println("number already exists trying next number up");
                return checkWnum(x+1);
            }else{
                return x;
            }





        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return x;
    }

    //Assignment(String name, int points, Date due_date, int assignment_id)
    public void genAssignments(RandomGenerator generator){
        for(int i = 1;i < generator.getNextInt(9); i++){
            assignments.add(new Assignment("Assignment "+i,generator.getNextInt(9)*10,class_id,addHoursToJavaUtilDate(date,(generator.getNextInt(20)+i*2)*24),checkWnum(generator.getNextInt(999999))));
        }
        for(int i = 1;i < generator.getNextInt(9); i++){
            assignments.add(new Assignment("Reading  "+i,generator.getNextInt(9)*10,class_id,addHoursToJavaUtilDate(date,(generator.getNextInt(20)+i*2)*24),checkWnum(generator.getNextInt(999999))));
        }
        String x = generator.getRAssignmentName();
        for(int i = 1;i < generator.getNextInt(9); i++){
            assignments.add(new Assignment(x+" "+i,generator.getNextInt(9)*10,class_id,addHoursToJavaUtilDate(date,(generator.getNextInt(20)+i*2)*24),checkWnum(generator.getNextInt(999999))));
        }
        assignments.forEach(y -> y.submit());
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
        try {

            PreparedStatement statement = connection.getConn().prepareStatement(sqlSubmitClass);
            connection.getConn().setAutoCommit(false);
            statement.setInt(1,class_id);
            statement.setInt(2,class_num);
            statement.setString(3,class_type);




            int rowsInserted = statement.executeUpdate();

            connection.getConn().setAutoCommit(true);
            if (rowsInserted > 0) {
                System.out.println("A new class was inserted successfully!");
                students.forEach(x->{
                    try {

                        PreparedStatement statementList = connection.getConn().prepareStatement(sqlSubmitClassList);
                        connection.getConn().setAutoCommit(false);
                        statementList.setInt(1,class_id);
                        statementList.setInt(2,x.user_id);
                        statementList.setInt(3,0);


                        int rowsInserted2 = statementList.executeUpdate();

                        connection.getConn().setAutoCommit(true);
                        if (rowsInserted2 > 0) {
                            System.out.println("A new class-list was inserted successfully!");
                        }


                        //return true;
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
                        //return false;
                    }
                });
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
