package com.sethy;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class NameGenerator {

    private ArrayList<String> fNames = new ArrayList<String>();
    private ArrayList<String> lNames = new ArrayList<String>();
    private ArrayList<String> places = new ArrayList<String>();
    private ArrayList<String> states = new ArrayList<String>();

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

    private Random r =  new Random();



    private void loadArrayList(ArrayList list, Scanner file){
        while(file.hasNextLine()){
            String x = file.nextLine();
            if(list != null){
                list.add(x);
            }
        }
        file.close();
    }

    public NameGenerator() throws IOException, URISyntaxException {

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

    private String streetT(){
        return streetType[r.nextInt(streetType.length)];
    }

    private String apt(){
        if(r.nextInt(1) == 1){
            return "#" + r.nextInt(999);
        }else{
            return "";
        }
    }

    public String getRAddress(){
        String x = "" +
                r.nextInt(999) +
                places.get(r.nextInt(places.size())) +
                streetT() +
                apt();

        return x;

    }

    public String getCity(){
        return places.get(r.nextInt(places.size()));
    }

    public String getState(){
        return states.get(r.nextInt(states.size()));
    }

    public String getRName(Boolean sirName){
        if(sirName){
            return lNames.get(r.nextInt(lNames.size()));
        }else{
            return fNames.get(r.nextInt(fNames.size()));
        }
    }
}
