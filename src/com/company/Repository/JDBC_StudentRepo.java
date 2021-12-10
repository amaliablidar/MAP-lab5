package com.company.Repository;

import com.company.Model.Course;
import com.company.Model.Person;
import com.company.Model.Teacher;
import com.company.Model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBC_StudentRepo extends JDBC_repository<Student> {


    /**
     *
     * @param studentID id-ul studentului care trebuie gasit
     * @return student daca acesta a fost gasit, null in caz contrar
     */
    @Override
    public Student findOne(int studentID) {

        String studentQuery=" select s.studentId,s.totalCredits, p.id, p.firstName, p.lastName from student s join person p on p.id = s.personalDataId where s.studentId="+studentID;
        Student student=new Student();
        Person person = new Person();
        ArrayList<Course> studentCourses = new ArrayList<>();


        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(studentQuery);

            if (result.next()) {
                student.setId(result.getInt("studentId"));
                student.setTotalCredits(result.getInt("totalCredits"));
                student.setFirstName(result.getString("firstName"));
                student.setLastName(result.getString("lastName"));
            }

            String courseListQuery = "select c.CourseId, c.title, c.credits, c.teacherId, c.maxStudents,p.firstName, p.lastName from course c join enrolment e on e.CourseId = c.CourseId join student s on s.studentId = e.StudentId join person p on p.id = s.personalDataId where s.studentId="+studentID;
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery(courseListQuery);

            while (result2.next()) {
                Course course=new Course();
                Teacher teacher=new Teacher();
                course.setId(result2.getInt("CourseId"));
                course.setTitle(result2.getString("title"));
                course.setCredits(result2.getInt("credits"));
                int teacherId = result2.getInt("teacherID");
                course.setTeacherId(teacherId);
                course.setMaxStudents(result2.getInt("maxEnrollment"));
                teacher.setPersonalId(teacherId);
                teacher.setFirstName(result2.getString("firstName"));
                teacher.setLastName(result2.getString("lastName"));
                studentCourses.add(course);
            }
            student.setEnrolledCourses(studentCourses);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (student.getId() == studentID)
            return student;
        else
            return null;

    }


    /**
     *
     * @return lista de studenti
     */
    @Override
    public ArrayList<Student> findAll()
    {
        String studentQuery=" select s.studentId, s.totalCredits,p.id, p.firstName, p.lastName from student s join person p on p.id = s.personalDataId ";
        // s join enrolled_students se on s.ID=se.studentID";
        ArrayList<Student>studentList=new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(studentQuery);


            while (result.next()) {
                Student student = new Student();
                student.setId(result.getInt("studentId"));
                student.setTotalCredits(result.getInt("totalCredits"));
                student.setPersonalId(result.getInt("personalDataId"));
                student.setFirstName(result.getString("firstName"));
                student.setLastName(result.getString("lastName"));

                String courseListQuery = "select c.CourseId, c.title, c.credits, c.teacherId, c.maxStudents,p.firstName, p.lastName from course c join enrolment e on e.CourseId = c.CourseId join student s on s.studentId = e.StudentId join person p on p.id = s.personalDataId where s.studentId="+student.getId();
                ArrayList<Course> coursesStudent = new ArrayList<>();
                Statement statement2 = connection.createStatement();
                ResultSet result2 = statement2.executeQuery(courseListQuery);

                while (result2.next()) {
                    Course course=new Course();
                    Teacher teacher=new Teacher();
                    course.setId(result2.getInt("CourseId"));
                    course.setTitle(result2.getString("title"));
                    int teacherId = result2.getInt("teacherID");
                    teacher.setPersonalId(teacherId);
                    teacher.setFirstName(result2.getString("firstName"));
                    teacher.setLastName(result2.getString("lastName"));
                    course.setCredits(result2.getInt("credits"));
                    course.setMaxStudents(result2.getInt("maxEnrollment"));
                    course.setTeacherId(teacherId);
                    coursesStudent.add(course);
                }
                student.setEnrolledCourses(coursesStudent);
                studentList.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;



    }

    /**
     *
     * @param newStudent studentul ce trebuie salvat
     * @return studentul daca acesta exista deja, null daca a fost salvat
     */
    @Override
    public Student save(Student newStudent)  {
        Student studentFound=findOne(newStudent.getId());
        if (studentFound !=null)
        {
            return studentFound;
        }
        else {

            try (
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO student(ID, totalCredits, personalDataId) values (?,?,?)");

            ) {
                ps.setInt(1,newStudent.getId());
                ps.setInt(2, newStudent.getTotalCredits());
                ps.setInt(3, newStudent.getPersonalId());
                ps.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return null;
    }

    /**
     *
     * @param id id-ul studentului ce trebuie sters
     * @return studentul daca acesta a fost sters, null in caz contrar
     */
    @Override
    public Student delete(int id)  {
        Student foundStudent=findOne(id);
        String deleteStudentQuery = "delete from student where studentId="+id;
        String deleteStudentFromEnrolment = "delete from enrolment where StudentId=" + id;
        if (foundStudent !=null)
        {

            try{
                Statement statement = connection.createStatement();
                int result=statement.executeUpdate(deleteStudentFromEnrolment);
                if(result!=0){
                    try {
                        Statement statement2 = connection.createStatement();
                        statement2.executeUpdate(deleteStudentQuery);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return foundStudent;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (foundStudent !=null)
        {
            try
            {
                Statement statement2 = connection.createStatement();
                statement2.executeUpdate(deleteStudentQuery);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return foundStudent;
        }
        return null;
    }


    /**
     *
     * @param newStudent studentul cu proprietatile noi
     * @return null daca acesta nu a fost updatat
     */
    @Override
    public Student update (Student newStudent){
        Student foundStudent=findOne(newStudent.getId());
        if (foundStudent ==null)
        {
            return null;
        }
        else {
            try (
                    PreparedStatement ps = connection.prepareStatement("update student set studentId=?,totalCredits=?,personalDataId=? WHERE studentId="+newStudent.getId());

            ) {
                ps.setLong(1,newStudent.getId());
                ps.setInt(2,newStudent.getTotalCredits());
                ps.setInt(3,newStudent.getPersonalId());
                ps.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return null;
    }

    /**
     * metoda salveaza studentul si cursul la care s-a inscris
     * @param studentId id-ul studentului
     * @param courseId id-ul cursului
     */
    public void saveEnrolment(int studentId, int courseId){
        try (
                PreparedStatement ps = connection.prepareStatement("insert into enrolment(StudentId,CourseId) values (?,?)")

        ) {
            ps.setLong(1, studentId);
            ps.setLong(2, courseId);
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}