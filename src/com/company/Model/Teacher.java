package com.company.Model;

import java.util.List;
import java.util.Objects;


public class Teacher extends Person {
    private int personId;
    private List<Course>courses;

    public Teacher (int personId,String firstName,String lastName,int teacherID,List<Course>courses)
    {
        this.personId = personId;
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setPersonalId(teacherID);
        this.courses=courses;
    }
    public Teacher (){};

    public int getId() {
        return personId;
    }

    public void setId(int personId) {
        this.personId = personId;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}