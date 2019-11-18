package com.sethy;

import java.io.IOException;
import java.net.URISyntaxException;

public class Student extends User {


    private Assignment[] assignments;
    private SubmittedAssignment[] submittedAssignments;

    public Student(RandomGenerator g) {
        super(g);
    }

    public Student(){

    }
}
