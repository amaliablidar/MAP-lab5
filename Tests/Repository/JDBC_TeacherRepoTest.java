package Repository;

import com.company.Model.Course;
import com.company.Model.Teacher;
import com.company.Repository.JDBC_TeacherRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JDBC_TeacherRepoTest {
    JDBC_TeacherRepo teacher = new JDBC_TeacherRepo();
    ArrayList<Course> courses;
    Teacher teacher1;

    @BeforeEach
    void init(){
        courses = new ArrayList<>(0);
        teacher1=new Teacher(5,"andrei","pop",5,courses);

    }

    @Test
    void findOne() {
        assertEquals("pop",teacher.findOne(21).getLastName());
    }

    @Test
    void findAll() {
        assertEquals(7,teacher.findAll().size());
    }

    @Test
    void save() {

        Teacher newTeacher=new Teacher(100,"anamaria","rot",55,courses);
        teacher.save(newTeacher);
        assertEquals("anamaria", teacher.findOne(100).getFirstName());


    }

    @Test
    void delete() {
        int length = teacher.findAll().size();
        teacher.delete(100);
        assertEquals(length-1,teacher.findAll().size());

    }

    @Test
    void update() {

        Teacher teacher2 = new Teacher(5,"madalina","blidar",5,courses);
        teacher.update(teacher2);
        assertEquals("madalina",teacher.findOne(5).getFirstName());

    }
}