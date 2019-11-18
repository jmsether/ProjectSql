package com.sethy;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class RandomGenerator {

    //TODO
    /*Someone please re-arrange these functions so they are a bit more readable
    * or at least make sense on the arrangement
    */

    //These can be changed or added to from the files in the names folder.
    //DO NOT RENAME FILES
    private ArrayList<String> fNames = new ArrayList<String>();
    private ArrayList<String> lNames = new ArrayList<String>();
    private ArrayList<String> places = new ArrayList<String>();
    private ArrayList<String> states = new ArrayList<String>();

    private ArrayList<User> user = new ArrayList<User>();

    //This can be added to or changed without breaking anything.
    private String[] streetType = {
            "Road",
            "Street",
            "Drive",
            "Circle",
            "Avenue",
            "Place",
            "Lane",
            "Grove",
            "Gardens",
            "Way",
            "Square",
            "Row",
            "Rise",
            "Vale"
    };

    private String classType[] = {
            "Accounting & Finance",
            "Aeronautical & Manufacturing Engineering",
            "Agriculture & Forestry",
            "American Studies",
            "Anatomy & Physiology",
            "Anthropology",
            "Archaeology",
            "Architecture",
            "Art & Design",
            "Aural & Oral Sciences",
            "Biological Sciences",
            "Building",
            "Business & Management Studies",
            "Celtic Studies",
            "Chemical Engineering",
            "Chemistry",
            "Civil Engineering",
            "Classics & Ancient History",
            "Communication & Media Studies",
            "Complementary Medicine",
            "Computer Science",
            "Counselling",
            "Creative Writing",
            "Criminology",
            "Dentistry",
            "Drama, Dance & Cinematics",
            "East & South Asian Studies",
            "Economics",
            "Education",
            "Electrical & Electronic Engineering",
            "English",
            "Fashion",
            "Film Making",
            "Food Science",
            "Forensic Science",
            "French",
            "Geography & Environmental Sciences",
            "Geology",
            "General Engineering",
            "German",
            "History",
            "History of Art, Architecture & Design",
            "Hospitality, Leisure, Recreation & Tourism",
            "Iberian Languages/Hispanic Studies",
            "Italian",
            "Land & Property Management",
            "Law",
            "Librarianship & Information Management",
            "Linguistics",
            "Marketing",
            "Materials Technology",
            "Mathematics",
            "Mechanical Engineering",
            "Medical Technology",
            "Medicine",
            "Middle Eastern & African Studies",
            "Music",
            "Nursing",
            "Occupational Therapy",
            "Optometry, Ophthalmology & Orthoptics",
            "Pharmacology & Pharmacy",
            "Philosophy",
            "Physics and Astronomy",
            "Physiotherapy",
            "Politics",
            "Psychology",
            "Robotics",
            "Russian & East European Languages",
            "Social Policy",
            "Social Work",
            "Sociology",
            "Sports Science",
            "Theology & Religious Studies",
            "Town & Country Planning and Landscape Design",
            "Veterinary Medicine",
            "Youth Work",

    };

    private String assignmentType[] = {
            "Project",
            "Lab",
            "Homework"
    };

    //Used heavily in this class to generate random ints
    private Random r =  new Random();

    public int getNextInt(int x){
        if(x<1){
            x = 1;
        }
        return r.nextInt(x);
    }

    //Loads an arraylist from a giving file on disk
    private void loadArrayList(ArrayList list, Scanner file){
        while(file.hasNextLine()){
            String x = file.nextLine();
            if(list != null){
                list.add(x);
            }
        }
        file.close();
    }

    public RandomGenerator() throws IOException, URISyntaxException {

        Scanner f = new Scanner(new File(getClass().getResource("names/fNames.txt").toURI()));

        loadArrayList(fNames,new Scanner(new File(getClass().getResource("names/fNames.txt").toURI())));
        loadArrayList(lNames,new Scanner(new File(getClass().getResource("names/lNames.txt").toURI())));
        loadArrayList(places,new Scanner(new File(getClass().getResource("names/places.txt").toURI())));
        loadArrayList(states,new Scanner(new File(getClass().getResource("names/states.txt").toURI())));


        //places.forEach((x) -> System.out.println("Added: "+ x));
        System.out.println("Generator loaded");

        /*
        while(f.hasNextLine()){
            String x = f.nextLine().toString();
            if(x != null){
                fNames.add(x);
            }
        }
        f.close();
        //fNames.forEach((x) -> System.out.println("Added: "+ x));


        while(l.hasNextLine()){
            String x = l.nextLine().toString();
            if(l != null){
                fNames.add(x);
            }
        }
        f.close();

         */
    }

    //return a random assignment name
    public String getRAssignmentName(){
        return assignmentType[r.nextInt(assignmentType.length)];
    }

    //return a random class type
    public String getRClassType(){
        return classType[r.nextInt(classType.length)];
    }

    //return a random course number
    public int getRClassNum(){
        int classLevel = r.nextInt(2)+1;
        return Integer.valueOf(getRNum(classLevel))*10;
    }

    //Adds a user to random generation to do a hat draw thing
    public void addUser(User user){
        this.user.add(user);
    }

    //does the hat draw
    public User getRUser(){
        return user.get(r.nextInt(user.size()));
    }

    //Returns a probable street name from file.
    private String streetT(){
        return streetType[r.nextInt(streetType.length)];
    }

    //Decides if an address is an apartment or not
    //returns apartment number if address should be an apartment
    private String apt(){
        if(r.nextInt(4) == 0){
            return " #" + r.nextInt(999);
        }else{
            return "";
        }
    }

    //returns a randomly generated house and street name from file
    //Can randomly generate whether an address is an apartment
    public String getRAddress(){
        return  "" +
                r.nextInt(9999) + " " +
                places.get(r.nextInt(places.size())) + " " +
                streetT() +
                apt();

    }

    //returns a random (Probable) City name from file.
    public String getCity(){
        return places.get(r.nextInt(places.size()));
    }

    //returns the last user added to the hat
    public User getLastUser(){
        return user.get(user.size()-1);
    }

    //returns a random state
    public String getState(){
        return states.get(r.nextInt(states.size()));
    }

    //Returns a random zipcode
    public String getZipCode(){
        return getFRNum(5);
    }

    //returns a random generated number formatted
    public String getPhoneNum(){
        return  ""+
                getFRNum(3)+
                ""+
                getFRNum(3)+
                ""+
                getFRNum(4);
    }

    //Generates a number with a fixed length
    public String getFRNum(int limit){
        int x = 1+r.nextInt(9);
        for(int i = 0; i < limit-1;i++){
            x = (x*10) + r.nextInt(9);
        }
        return String.valueOf(x);
    }

    //Generates a number with desired length
    public String getRNum(int limit){
        int x = 9;
        for(int i = 0; i < limit; i++){
            x=(x*10)+9;
        }

        return String.valueOf(r.nextInt(x));
    }

    //Returns ether a random first or last name from file.
    public String getRName(Boolean sirName){
        if(sirName){
            return lNames.get(r.nextInt(lNames.size()));
        }else{
            return fNames.get(r.nextInt(fNames.size()));
        }
    }
}
