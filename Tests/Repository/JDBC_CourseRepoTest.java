package Repository;

import com.company.Model.Course;
import com.company.Model.Student;
import com.company.Repository.JDBC_CourseRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JDBC_CourseRepoTest {
    JDBC_CourseRepo course = new JDBC_CourseRepo();
    ArrayList<Student> studentList;

    @BeforeEach
    void init(){
        studentList=new ArrayList<Student>(0);
    }

    @Test
    void findOne() {
        assertEquals("LP", course.findOne(12).getTitle());
    }

    @Test
    void findAll() {
        assertEquals(3, course.findAll().size());

    }

    @Test
    void save() {
        int length = course.findAll().size();
        assertEquals(length, course.findAll().size());
        Course course1=new Course(14,"RC",6,29,30,studentList);
        course.save(course1);
        assertEquals(length+1, course.findAll().size());

    }

    @Test
    void delete() {
        int length = course.findAll().size();
        assertEquals(length, course.findAll().size());
        course.delete(14);
        assertEquals(length-1, course.findAll().size());

    }

    @Test
    void update() {
        Course course1=new Course(11,"LP2",6,22,30,studentList);
        course.update(course1);
        assertEquals("LP2", course.findOne(11).getTitle());
    }
}