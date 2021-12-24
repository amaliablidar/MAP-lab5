package com.company;

import com.company.Controller.RegistrationSystemController;
import com.company.Exception.InvalidInputException;
import com.company.Model.Course;
import com.company.Model.Student;
import com.company.Model.Teacher;
import com.company.Repository.JDBC_CourseRepo;
import com.company.Repository.JDBC_StudentRepo;
import com.company.Repository.JDBC_TeacherRepo;
import com.company.Repository.JDBC_repository;
import com.company.View.View;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Main {

    public static void main(String[] args) throws InputMismatchException, InvalidInputException,java.io.IOException {
        JDBC_TeacherRepo teacher = new JDBC_TeacherRepo();
        JDBC_StudentRepo student = new JDBC_StudentRepo();
        JDBC_CourseRepo course = new JDBC_CourseRepo();
        RegistrationSystemController controller=new RegistrationSystemController(student,teacher,course);
        View view = new View(controller);
        view.mainMenu();



    }
}
