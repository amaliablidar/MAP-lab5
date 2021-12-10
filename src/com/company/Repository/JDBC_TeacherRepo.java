package com.company.Repository;

import com.company.Model.Course;
import com.company.Model.Person;
import com.company.Model.Teacher;
import com.company.Model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JDBC_TeacherRepo extends JDBC_repository<Teacher> {

    /**
     *
     * @param teacherID profesorul care trebuie gasit
     * @return profesorul daca acesta a fost gasit, null in caz contrar
     */
    @Override
    public Teacher findOne(int teacherID) {

        String teacherQuery="select t.teacherId,t.personalDataId,p.firstName, p.lastName FROM teacher t join person p on p.id = t.teacherId where t.ID="+teacherID;
        String coursesListQuery = " select c.CourseId, c.title, c.credits,c.teacherId,c.maxStudents from course c where c.teacherId="+teacherID;
        Teacher teacher=new Teacher();
        List<Course> coursesList = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(teacherQuery);

            if (result.next()) {
                teacher.setId(result.getInt("teacherId"));
                teacher.setPersonalId(result.getInt("personalDataId"));
                teacher.setFirstName(result.getString("firstName"));
                teacher.setLastName(result.getString("lastName"));
            }

            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery(coursesListQuery);

            while (result2.next()) {
                Course course = new Course();
                Teacher newTeacher=new Teacher();
                newTeacher.setId(teacher.getId());
                newTeacher.setFirstName(teacher.getFirstName());
                newTeacher.setLastName(teacher.getLastName());
                course.setId(result2.getInt("ID"));
                course.setTitle(result2.getString("courseTitle"));
                course.setCredits(result2.getInt("credits"));
                course.setMaxStudents(result2.getInt("maxStudents"));
                coursesList.add(course);
            }
            teacher.setCourses(coursesList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (teacher.getId()== teacherID)
            return teacher;
        else
            return null;
    }

    /**
     *
     * @return lista de profesori
     */
    @Override
    public ArrayList<Teacher> findAll()
    {
        ArrayList<Teacher>teacherList=new ArrayList<>();
        String sqlQuery=" select t.teacherId,t.personalDataId, p.firstName, p.lastName FROM teacher t join person p on p.id=t.personalDataId ";


        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);

            while (result.next()) {
                Teacher teacher=new Teacher();
                teacher.setId(result.getInt("teacherId"));
                teacher.setPersonalId(result.getInt("personalDataId"));
                teacher.setFirstName(result.getString("firstName"));
                teacher.setLastName(result.getString("lastName"));

                List<Course> teacherCourseList = new ArrayList<>();
                String coursesListQuery = " select c.CourseId, c.title, c.credits,c.maxStudents from course c where c.teacherId="+teacher.getId();
                Statement statement2 = connection.createStatement();
                ResultSet result2 = statement2.executeQuery(coursesListQuery);

                while (result2.next()) {
                    Course course = new Course();
                    Teacher newTeacher=new Teacher();
                    newTeacher.setId(teacher.getId());
                    newTeacher.setFirstName(teacher.getFirstName());
                    newTeacher.setLastName(teacher.getLastName());
                    course.setId(result2.getInt("CourseId"));
                    course.setTitle(result2.getString("title"));
                    course.setCredits(result2.getInt("credits"));
                    course.setTeacherId(newTeacher.getId());
                    course.setMaxStudents(result2.getInt("maxStudents"));
                    teacherCourseList.add(course);
                }
                teacher.setCourses(teacherCourseList);
                teacherList.add(teacher);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherList;
    }

    /**
     *
     * @param newTeacher profesorul care trebuie salvat
     * @return profesorul daca acesta exista deja, null daca a fost salvat
     */
    @Override
    public Teacher save(Teacher newTeacher) {
        Teacher foundTeacher=findOne(newTeacher.getId());
        if (foundTeacher !=null)
        {
            return foundTeacher;
        }
        else {
            try (
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO teacher(teacherId, personalDataId) values (?,?)")

            ) {
                ps.setInt(1,newTeacher.getId());
                ps.setInt(2,newTeacher.getPersonalId());
                ps.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try (
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO person(firstName, lastName, id) values (?,?,?)")

            ) {
                ps.setString(1,newTeacher.getFirstName());
                ps.setString(2,newTeacher.getLastName());
                ps.setInt(3,newTeacher.getId());
                ps.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return null;
    }

    /**
     *
     * @param id id-ul profesorului care trebuie sters
     * @return profesorul daca acesta a fost sters, null in caz contrar
     */
    @Override
    public Teacher delete(int id) {
        Teacher teacherFound=findOne(id);
        String deleteTeacherQuery = "delete from teacher Where teacherId="+id;

        if (teacherFound !=null)
        {
            try
            {
                Statement statement = connection.createStatement();
                statement.executeUpdate(deleteTeacherQuery);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return teacherFound;
        }
        return null;
    }


    /**
     *
      * @param teacher profesorul cu noile proprietati
     * @return null daca acesta nu a fost updatat
     */
    @Override
    public Teacher update (Teacher teacher){
        Teacher teacherFound=findOne(teacher.getId());
        if (teacherFound ==null)
        {
            return null;
        }
        else {

            try (
                    PreparedStatement ps = connection.prepareStatement("UPDATE teacher SET teacherId=?, personalDataId=? WHERE teacherId="+teacher.getId())

            ) {
                ps.setInt(1,teacher.getId());
                ps.setInt(2,teacher.getPersonalId());
                ps.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try (
                    PreparedStatement ps = connection.prepareStatement("UPDATE person SET id=?, firstName=?, lastName=? WHERE id="+teacher.getPersonalId())

            ) {
                ps.setInt(1,teacher.getPersonalId());
                ps.setString(2,teacher.getFirstName());
                ps.setString(3,teacher.getLastName());
                ps.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return null;
    }


}