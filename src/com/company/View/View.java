package com.company.View;

import com.company.Controller.RegistrationSystemController;
import com.company.Exception.InputMismatchException;
import com.company.Exception.InvalidInputException;
import com.company.Model.Course;
import com.company.Model.Student;
import com.company.Model.Teacher;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class View {

    private RegistrationSystemController controller;

    public View(RegistrationSystemController controller) {
        this.controller = controller;
    }

    public void printCoursesWithFreePlaces(ArrayList<Course> courseList){
        if (courseList.size()>0)
        {
            System.out.println("Lista de cursuri: ");
            for (Course course : courseList) {
                System.out.println(course.getTitle() + ",ID:" + course.getId()+" numarul de locuri disponibile "+(course.getMaxStudents()-course.getStudentsList().size()+1));
            }
        }
        else
            System.out.println("Nu exista cursuri cu locuri libere");

    }

    public void printOptionInvalid(String option) {
        while (!option.equals("8") && !option.equals("7") && !option.equals("6") && !option.equals("5") && !option.equals("4")
                && !option.equals("3") && !option.equals("2") && !option.equals("1")) {
            System.out.println("Va rugam introduceti o optiune valida");
        }
    }

    public void printStudentsWithCreditNumber(){
        System.out.println("Introduceti numarul de credite:");
        int creditNr=checkIfInputIsInt();
        ArrayList<Student>allStudents=(ArrayList<Student>)controller.getStudentRepository().findAll();
        ArrayList<Student> filteredList =controller.studentsFilteredByCredits(creditNr,allStudents);
        if (filteredList.size()>0)
        {
            printStudents(filteredList);}
        else
            System.out.println("Nu exista studenti cu acest numar de credite");

    }

    public void printCoursesWithCreditNumber(){
        System.out.println("Introduceti numarul de credite:");
        int creditNr=checkIfInputIsInt();
        ArrayList<Course> allCourses=(ArrayList<Course>)controller.getCourseRepository().findAll();
        ArrayList<Course> filteredList = controller.coursesFilteredByCredits(creditNr,allCourses);
        if (filteredList.size()>0)
        {
            printCourse(filteredList);}
        else
            System.out.println("Nu exista niciun curs cu acest numar de credite");

    }


    public Course readCourseId(){
        Scanner in = new Scanner(System.in);
        System.out.println("Introduceti id-ul cursului:");
        int courseID =in.nextInt();
        Course course=controller.getCourseRepository().findOne(courseID);
        try{
            if (course==null)
                throw  new InvalidInputException("Nu exista niciun curs cu ID-ul dat");
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
        return course;
    }

    public void printSortedStudentList(){
        System.out.println("Lista de studenti: ");
        ArrayList<Student>allStudents=(ArrayList<Student>)controller.getStudentRepository().findAll();
        ArrayList<Student> studentList =controller.studentsSortedAlphabetically(allStudents);
        for (Student student : studentList){
            System.out.println(student.getLastName() + " " + student.getFirstName() + ",ID:" + student.getId());
        }
    }

    public Student readStudentId(){
        System.out.println("Introduceti id-ul de student:");
        Scanner in = new Scanner(System.in);
        int studentID =in.nextInt();
        Student student = controller.getStudentRepository().findOne(studentID);
        try{
            if (student==null)
                throw  new InvalidInputException("Id-ul nu exista");
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
        return student;
    }

    public void printStudentsFromCourse(Course course){

        System.out.println("Lista de studenti: ");
        List<Student> studentList =controller.retrieveStudentsEnrolledForACourse(course);
        if (studentList.size()>0) {
            for(Student student : studentList){
                System.out.println("ID: "+student.getId());
                System.out.println("Prenume: " + student.getFirstName());
                System.out.println( "Nume de familie: " + student.getLastName());
                System.out.println();
            }
        }
        else
            System.out.println("Nu exista niciun student inscris la acest curs");

    }


    public  void mainMenu() throws InvalidInputException, IOException {
        System.out.println("1. Student");
        System.out.println("2. Profesor");
        System.out.println("3. Exit");
        System.out.println("Optiunea aleasa:");
        String option=validateInput();
        while (!option.equals("3"))
        {
            if (option.equals("1"))
            {
                showMenuStudent();
            }
            if (option.equals("2"))
            {
                showMenuTeacher();
            }
            else while  (Integer.parseInt(option)>3)
            {
                System.out.println("Optiunea aleasa");
                option=validateInput();
            }
            System.out.println("1. Student");
            System.out.println("2. Profesor");
            System.out.println("3. Exit");
            System.out.println("Optiunea aleasa:");
            option=validateInput();
        }

    }


    public void showMenuTeacher() throws InvalidInputException {
        Scanner in = new Scanner(System.in);
        System.out.println("ID-ul profesorului:");
        int teacherID=in.nextInt();
        printMenu("teacher");
        String option=validateInput();
        while (!option.equals("9")){
            if (option.equals("1"))
            {
                Teacher teacher=controller.getTeacherRepository().findOne(teacherID);
                for (Course course : teacher.getCourses())
                    for (Course course1:controller.getAllCourses()) {
                        if (course.getId() == (course1.getId()))
                        {
                            System.out.println(course1.getTitle() + ",ID:" + course.getId());
                        }
                    }
                System.out.println("ID-ul cursului:");
                int courseID=in.nextInt();

                List<Student> allStudents = (List<Student>) controller.getStudentRepository().findAll();
                List<Student> allStudentsInTheGivenCourse = new ArrayList<>();
                List<Course> courses=(List<Course>) controller.getCourseRepository().findAll();
                for (Student student : allStudents)
                    for (Course course : student.getEnrolledCourses())
                        for (Course course1:courses){
                            if (course1.getId()==(course.getId()))
                            {
                                course.setTitle(course1.getTitle());
                                course.setCredits(course1.getCredits());
                                course.setMaxStudents(course1.getMaxStudents());
                                course.setTeacher(course1.getTeacher());

                            }
                        }
                Course foundCourse=controller.getCourseRepository().findOne(courseID);
                for (Student student : allStudents)
                {
                    for (Course c:student.getEnrolledCourses())
                    {
                        if (c.getId()==(foundCourse.getId()))
                        {
                            allStudentsInTheGivenCourse.add(student);
                        }
                    }
                }
                Course course= controller.deleteCourseByTeacher(foundCourse,teacher,allStudentsInTheGivenCourse);
                if (course != null)
                {
                    System.out.println("Cursul "+course.getTitle()+" a fost sters");
                }
                else {
                    throw  new InvalidInputException("Valoare incorecta");
                }

                printMenu("teacher");
                option=validateInput();

            }
            if (option.equals("2")) {

                ArrayList<Course>courseList= controller.retrieveCoursesWithFreePlaces();
                printCoursesWithFreePlaces(courseList);
                printMenu("teacher");
                option=validateInput();

            }


            if (option.equals("3")) {

                printCourse((ArrayList<Course>) controller.getAllCourses());
                Course course = readCourseId();
                printStudentsFromCourse(course);
                printMenu("teacher");
                option=validateInput();}
            if (option.equals("4")) {

                printSortedStudentList();
                printMenu("teacher");
                option=validateInput();

            }

            if (option.equals("5")) {

                ArrayList<Course> allCourses=(ArrayList<Course>)controller.getCourseRepository().findAll();
                ArrayList<Course> sortedList = controller.CoursesSortedAlphabetically(allCourses);
                printCourse(sortedList);
                printMenu("teacher");
                option=validateInput();

            }
            if (option.equals("6")) {

                printStudentsWithCreditNumber();
                printMenu("teacher");
                option=validateInput();

            }
            if (option.equals("7")) {

                printCoursesWithCreditNumber();
                printMenu("teacher");
                option=validateInput();

            }

            if (option.equals("8"))
            {
                return;
            }
            else {
                printOptionInvalid(option);
                printMenu("teacher");
                option=validateInput();

            }

        }

    }

    public  String validateInput()

    {
        Scanner in = new Scanner(System.in);
        String option ;
        while (true) {
            option = in.next();
            try {
                int intInputValue = Integer.parseInt(option);
                break;
            } catch (NumberFormatException ne) {
                System.out.println("Va rugam introduceti o optiune valida");
            }}
        return  option;
    }

    public  int checkIfInputIsInt()

    {
        Scanner in = new Scanner(System.in);
        String number ;
        while (true) {
            number = in.next();
            try {
                int intInputValue = Integer.parseInt(number);
                break;
            } catch (InputMismatchException exception) {
                System.out.println("Va rugam introduceti un numar valid");
            }}
        return Integer.parseInt(number);
    }



    public void showMenuStudent() throws IOException {
        Scanner in = new Scanner(System.in);
        printMenu("student");
        String option;
        option=validateInput();

        while(!option.equals("9")) {
            if (option.equals("1")) {

                Student student = readStudentId();
                printCourse((ArrayList<Course>) controller.getAllCourses());
                Course enteredCourse = readCourseId();

                boolean registered = controller.register(student, enteredCourse);
                if (registered)
                    System.out.println("Ati fost inscris cu succes");
                else
                    System.out.println("Din pacate nu am putut sa va inscriem");



                printMenu("student");
                option=validateInput();
            }
            if (option.equals("2")) {

                ArrayList<Course>courseList= controller.retrieveCoursesWithFreePlaces();
                printCoursesWithFreePlaces(courseList);
                printMenu("student");
                option=validateInput();

            }
            if (option.equals("3")) {

                Course course = readCourseId();
                printStudentsFromCourse(course);
                printMenu("student");
                option=validateInput();

            }
            if (option.equals("4")) {

                printSortedStudentList();
                printMenu("student");
                option=validateInput();

            }

            if (option.equals("5")) {

                ArrayList<Course>allCurses=(ArrayList<Course>)controller.getCourseRepository().findAll();
                ArrayList<Course> sortedList =controller.CoursesSortedAlphabetically(allCurses);
                printCourse(sortedList);
                printMenu("student");
                option=validateInput();

            }
            if (option.equals("6")) {

                printStudentsWithCreditNumber();
                printMenu("student");
                option=validateInput();

            }
            if (option.equals("7")) {

                printCoursesWithCreditNumber();
                printMenu("student");
                option=validateInput();

            }

            if (option.equals("8"))
            {
                return;
            }
            else while  (!option.equals("8") && !option.equals("7") && !option.equals("6") && !option.equals("5") && !option.equals("4")
                    && !option.equals("3")&& !option.equals("2") && !option.equals("1"))
            {
                System.out.println("Inputul nu este valid. Va rugam reincercati!");
                printMenu("student");
                option=validateInput();

            }

        }

    }
    void printMenu(String menuType) {
        String firstOption;
        if (menuType.equals("student"))
            firstOption = "1. Inregistrati-va la un curs" + "\n";
        else
            firstOption = "1. Stergeti un curs" + "\n";
        System.out.println(firstOption + "2. Afisati cursurile cu numar disponibil de locuri");
        System.out.println("3. Afisati studentii inscrisi la un anumit curs");
        System.out.println("4. Afisati studentii ordonati alfabetic");
        System.out.println("5. Afisati cursurile ordonate alfabetic");
        System.out.println("6. Filtrati studentii dupa numarul de credite");
        System.out.println("7. Filtrati cursurile dupa numarul de credite");
        System.out.println("8. Inapoi");
        System.out.println("Alegeti una dintre optiuni: ");
    }


    void printCourse(ArrayList<Course>courseList){
        System.out.println("Lista de cursuri:");
        for (int i=0;i< courseList.size();i++) {
            System.out.println(courseList.get(i).getTitle() + ",ID:" + courseList.get(i).getId());
        }

    }

    void printStudents(List<Student>studentList)
    {
        System.out.println("List of Students:");
        for (Student student : studentList){
            System.out.println(student.getFirstName() + " " + student.getLastName() + ",ID:" + student.getId());
        }
    }

}