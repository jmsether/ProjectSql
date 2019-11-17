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

    //Used heavily in this class to generate random ints
    private Random r =  new Random();


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
