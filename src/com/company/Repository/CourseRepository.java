//package com.company.Repository;
//
//import com.company.Model.Course;
//import com.company.Model.Person;
//import com.company.Model.Student;
//import com.company.Model.Teacher;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//public class CourseRepository extends InFileRepo<Course> {
//
//    /**
//     * initializeaza lista cu valorile gasite in fisier
//     * @param repoList o lista de cursuri
//     * @throws FileNotFoundException daca fisierul nu a fost gasit
//     */
//    public CourseRepository(List<Course> repoList) throws FileNotFoundException {
//        super(repoList);
//    }
//
//    /**
//     *
//     * @param newCourse - salveaza un curs nou in lista de cursuri
//     * @return cursul daca acesta a fost salvat, null in caz contrar
//     */
//    @Override
//    public Course save(Course newCourse)
//    {
//        for (Course course:repoList)
//        {
//            if (findOne(newCourse.getId())!=null)
//            {
//                return newCourse;
//            }
//        }
//        repoList.add(newCourse);
//        return null;
//    }
//
//    /**
//     *
//     * @param courseID id-ul cursului pe care il cautam
//     * @return cursul daca exista sau null daca nu
//     */
//
//    @Override
//    public Course findOne(Long courseID)
//    {
//
//        for(Course course:repoList){
//            if (course.getId().equals(courseID))
//                return course;
//        }
//        return null;
//    }
//
//    /**
//     *
//     * @return toate cursurile
//     */
//    @Override
//    public ArrayList<Course> findAll()
//    {
//        return repoList;
//    }
//
//
//    /**
//     *
//     * @param id id-ul cursului ce trebuie sters
//     * @return cursul daca a fost sters, null in caz contrar
//     */
//    @Override
//    public Course delete(Long id)
//    {
//        Course course=findOne(id);
//        if (course!= null)
//        {
//            repoList.remove(course);
//            return course;
//        }
//        return null;
//    }
//
//    /**
//     *
//     * @param newCourse noile valori pentru cursul ce trebuie modificat
//     * @return null daca cursul a fost modifical, cursul in caz contrar
//     */
//    @Override
//    public Course update (Course newCourse)
//    {
//        for(Course course:repoList)
//        {
//            if (course.getId().equals(newCourse.getId()))
//            {
//                repoList.remove(course);
//                repoList.add(newCourse);
//                return null;
//            }
//        }
//        return newCourse;
//    }
//
//    /**
//     * citeste cursurile dintr-un fisier
//     * @return o lista de cursuri
//     */
//    @Override
//    public List<Course> readDataFromFile() {
//        List<Course> courses = new ArrayList<>();
//        String separator=",";
//        String line;
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\blida\\development\\courses.csv"));
//            while ((line = reader.readLine()) != null) {
//                String[] course = line.split(separator);
//                Long courseID = Long.parseLong(course[1]);
//                String courseName = course[0].replace("\"","");
//                int maxEnrollment = Integer.parseInt(course[2]);
//                int credits = Integer.parseInt(course[3]);
//                int teacherID = Integer.parseInt(course[4]);
//                Teacher teacher = new Teacher();
//                teacher.setID(teacherID);
//                course[5] = course[5].replace("]", "");
//                course[5] = course[5].replace("[", "");
//                String[] studentList = course[5].split(";");
//                ArrayList<Student> studentsEnrolled = new ArrayList<>();
//                //if course has no students enrolled
//                if (!studentList[0].equals(""))
//                {    for (String studentID:studentList)
//                {
//                    studentID= studentID.replace("\"","");
//                    int studentIDLong = Integer.parseInt(studentID);
//                    Student student = new Student();
//                    student.setID(studentIDLong);
//                    studentsEnrolled.add(student);
//
//                }
//                }
//
//                Course newCourse = new Course(courseName,credits,teacher,maxEnrollment,studentsEnrolled,courseID);
//                courses.add(newCourse);
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return courses;
//    }
//
//    /**
//     * face update la fisier
//     * @throws IOException daca fisierul nu a fost gasit
//     */
//    @Override
//    public void writeToFile() throws IOException {
//        List<Course> courseList = repoList;
//        new PrintWriter("C:\\Users\\blida\\development\\courses.csv").close();
//        for (Course course:courseList) {
//            String studentIDs="";
//            String data= "";
//            String credits = String.valueOf(course.getCredits());
//            for (Student student : course.getStudentsList()) {
//                studentIDs += (String.valueOf(student.getID())) + ';';
//            }
//            StringBuffer stringBuffer = new StringBuffer(studentIDs);
//            if (studentIDs.length()>0){
//                stringBuffer.deleteCharAt(studentIDs.length() - 1);
//            }
//            data = course.getTitle() + "," + course.getId() + ',' + course.getMaxStudents() + ',' + course.getCredits() + ',' +
//                    course.getTeacher().getID()+"," + "[" + stringBuffer + "]"+"\n";
//
//            Files.write(Paths.get("C:\\Users\\blida\\development\\courses.csv"),data.getBytes(), StandardOpenOption.APPEND);
//        }
//    }
//
//}