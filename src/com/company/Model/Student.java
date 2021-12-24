package com.company.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Student extends Person {
    private int studentId;
    private int totalCredits;
    private ArrayList<Course>enrolledCourses;


    public Student (int studentId,String firstname,String name ,int personalId,int totalCredits,ArrayList<Course>enrolledCourses)
    {
        this.studentId=studentId;
        super.setLastName(name);
        super.setFirstName(firstname);
        super.setPersonalId(personalId);
        this.totalCredits=totalCredits;
        this.enrolledCourses=enrolledCourses;
    }

    public Student (){};

    public void addCourse(Course newCourse)
    {
        enrolledCourses.add(newCourse);
        int newTotalCredits=totalCredits+newCourse.getCredits();
        setTotalCredits(newTotalCredits);
    }

    public void delete(Course course)
    {
        ArrayList<Course>newCourseList=new ArrayList<>();
        for (Course course1:enrolledCourses)
        {
            if (course.getId()!=course1.getId())
            {
                newCourseList.add(course1);
            }
        }
        setEnrolledCourses(newCourseList);
    }

    public int getId() {
        return studentId;
    }

    public void setId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCredits, enrolledCourses);
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public ArrayList<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(ArrayList<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }


}