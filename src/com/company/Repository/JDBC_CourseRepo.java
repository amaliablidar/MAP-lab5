package com.company.Repository;
import java.sql.Statement;
import java.util.ArrayList;

import com.company.Model.Course;
import com.company.Model.Person;
import com.company.Model.Student;
import com.company.Model.Teacher;
import com.company.Repository.JDBC_repository;

import java.util.List;
import java.sql.*;


public class JDBC_CourseRepo extends JDBC_repository<Course> {

    /**
     *
     * @param courseID cursul care trebuie gasit
     * @return cursul daca a fost gasit, null in caz contrar
     */
    @Override
    public Course findOne(int courseID){
        ArrayList<Student> studentsList = new ArrayList<>();
        Course course=new Course();
        Teacher teacher=new Teacher();

        String courseQuery="select c.CourseId, c.title, c.credits, c.teacherId, c.maxStudents, t.teacherId, p.firstName, p.lastName from course c join teacher t on c.teacherId = t.teacherId join person p on p.id = t.personalDataId where c.CourseId ="+courseID;
        String studentsListQuery="select s.studentId, s.totalCredits,p.id, p.firstName, p.lastName from student s join person p on p.id = s.personalDataId where s.CourseId ="+courseID;

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(courseQuery);

            if (result.next()) {
                course.setId(result.getInt("CourseId"));
                course.setTitle(result.getString("title"));
                course.setCredits(result.getInt("credits"));
                course.setTeacherId(result.getInt("teacherID"));
                course.setMaxStudents(result.getInt("maxStudents"));
                teacher.setPersonalId(result.getInt("teacherId"));
                teacher.setFirstName(result.getString("firstName"));
                teacher.setLastName(result.getString("lastName"));
            }
            ResultSet resultStudentsList = statement.executeQuery(studentsListQuery);
            while (resultStudentsList.next()) {
                Student student= new Student();
                Person person = new Person();
                student.setId(resultStudentsList.getInt("studentId"));
                student.setTotalCredits(resultStudentsList.getInt("totalCredits"));
                student.setPersonalId(resultStudentsList.getInt("personalDataId"));
                student.setFirstName(resultStudentsList.getString("firstName"));
                student.setLastName(result.getString("lastName"));

                studentsList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        course.setStudentsList(studentsList);
        if (course.getId() == courseID)
            return course;
        else
            return null;
    }

    /**
     *
     * @return intreaga lista de cursuri
     */
    @Override
    public ArrayList<Course> findAll()
    {
        ArrayList<Course>courseList=new ArrayList<>();
        String courseQuery="select c.CourseId, c.title, c.credits, c.teacherId, c.maxStudents, t.teacherId, p.firstName, p.lastName from course c join teacher t on c.teacherId = t.teacherId join person p on p.id = t.personalDataId";

        try {
            Connection connection=connectDB();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(courseQuery);


            while (result.next()) {
                Course course=new Course();
                Teacher teacher=new Teacher();
                course.setId(result.getInt("CourseId"));
                course.setTitle(result.getString("courseTitle"));
                course.setCredits(result.getInt("credits"));
                course.setTeacherId(result.getInt("teacherId"));
                course.setMaxStudents(result.getInt("maxStudents"));
                teacher.setPersonalId(result.getInt("id"));
                teacher.setFirstName(result.getString("firstName"));
                teacher.setLastName(result.getString("lastName"));


                String studentsListQuery="select s.studentId, s.totalCredits,p.id, p.firstName, p.lastName from student s join person p on p.id = s.studentId where s.CourseId ="+course.getId();
                ArrayList<Student> studentsList = new ArrayList<>();
                Statement statement1 = connection.createStatement();
                ResultSet resultStudentList = statement1.executeQuery(studentsListQuery);

                while (resultStudentList.next()) {
                    Student student= new Student();
                    student.setId(resultStudentList.getInt("studentId"));
                    student.setTotalCredits(resultStudentList.getInt("totalCredits"));
                    student.setPersonalId(resultStudentList.getInt("personalDataId"));
                    student.setFirstName(resultStudentList.getString("firstName"));
                    student.setLastName(resultStudentList.getString("lastName"));
                    studentsList.add(student);
                }
                course.setStudentsList(studentsList);
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    /**
     *
     * @param course cursul care trebuie salvat
     * @return cursul daca acesta exista deja, null daca a fost salvat
     */
    @Override
    public Course save(Course course) {
        Course courseFound=findOne(course.getId());
        if (courseFound !=null)
        {
            return courseFound;
        }
        else {
            try{
                String query="select t.teacherId, p.firstName, p.lastName from teacher join person p on p.id = t.personalDataId where t.teacherId =" + course.getTeacherId();
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query);



                try (
                        PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO course(CourseId,title,credits,maxEnrollment) values (?,?,?,?)")

                ) {
                    prepStatement.setInt(1,course.getId());
                    prepStatement.setString(2, course.getTitle());
                    prepStatement.setInt(3,course.getCredits());
                    prepStatement.setInt(4,course.getMaxStudents());
                    prepStatement.execute();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


            return null;
        }}

    /**
     *
     * @param courseId id-ul cursului care trebuie sters
     * @return cursul daca acesta a fost sters, null in caz contrar
     */
    @Override
    public Course delete(int courseId)  {
        Course courseFound=findOne(courseId);
        if (courseFound !=null)
        {
            String deleteCourseQuery = "delete from course where CourseId="+courseId;
            String deleteEnrolmentQuery = "delete from enrolment where CourseId=" + courseId;


            try {
                Statement statement = connection.createStatement();
                Statement statement2 = connection.createStatement();
                statement.executeUpdate(deleteCourseQuery);
                statement2.executeUpdate(deleteEnrolmentQuery);


            } catch (SQLException e) {
                e.printStackTrace();
            }
            return courseFound;
        }
        else
            return null;
    }

    /**
     *
     * @param course cursul cu proprietatile noi
     * @return null daca cursul nu a fost updatat
     */
    @Override
    public Course update (Course course){
        Course courseFound=findOne(course.getId());
        if (courseFound ==null)
        {
            return null;
        }
        else {
            try (
                    PreparedStatement prepStatement = connection.prepareStatement("update course dset CourseId=?,title=?,credits=?,maxStudents=? WHERE ID="+course.getId());

            ) {
                prepStatement.setLong(1,course.getId());
                prepStatement.setString(2, course.getTitle());
                prepStatement.setInt(3,course.getCredits());
                prepStatement.setInt(4,course.getMaxStudents());
                prepStatement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return null;

    }




}