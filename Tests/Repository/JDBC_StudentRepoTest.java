package Repository;

import com.company.Model.Course;
import com.company.Model.Student;
import com.company.Repository.JDBC_StudentRepo;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JDBC_StudentRepoTest {
    JDBC_StudentRepo student = new JDBC_StudentRepo();
    ArrayList<Course> courses;

    @BeforeEach
    void init(){
         courses = new ArrayList<>(0);
    }


    @org.junit.jupiter.api.Test
    void findOne() {
        assertEquals("amalia",student.findOne(1).getFirstName());
    }

    @org.junit.jupiter.api.Test
    void findAll() {
        assertEquals(2,student.findAll().size());
    }

    @org.junit.jupiter.api.Test
    void save() {
        Student studentTest=new Student(4,"amalia","pop",3,33,courses);
        student.save(studentTest);
        assertEquals("bianca",student.findAll().get(1).getFirstName());
    }

    @org.junit.jupiter.api.Test
    void delete() {

        int numberOfStudents = student.findAll().size();
        student.delete(4);
        assertEquals(numberOfStudents-1,student.findAll().size());
    }

    @org.junit.jupiter.api.Test
    void update() {

        Student studentTest2=new Student(1,"amalia","pop",1,30,courses);
        student.update(studentTest2);
        assertEquals(30, student.findOne(1).getTotalCredits());

    }


}