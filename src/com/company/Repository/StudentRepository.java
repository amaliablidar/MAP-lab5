//package com.company.Repository;
//
//
//import com.company.Model.Course;
//import com.company.Model.Student;
//
//import java.io.*;
//import java.nio.file.*;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class StudentRepository extends InFileRepo<Student> {
//
//    /**
//     * initializeaza lista cu valorile gasite in fisier
//     * @param repoList o lista de studenti
//     * @throws FileNotFoundException daca fisierul nu a fost gasit
//     */
//    public StudentRepository(ArrayList<Student> repoList) throws FileNotFoundException {
//        super(repoList);
//
//    }
//
//    /**
//     * @return lista de studenti
//     */
//    public List<Student> getStudentList() {
//        return repoList;
//    }
//    /**
//     *
//     * @param newStudent - salveaza un student nou in lista de studenti
//     * @return studentul daca acesta a fost salvat, null in caz contrar
//     */
//    @Override
//    public Student save(Student newStudent)
//    {
//        for (Student student:repoList)
//        {
//            if(student.getID().equals(newStudent.getID()))
//                return student;
//        }
//        repoList.add(newStudent);
//        return null;
//    }
//
//    /**
//     *
//     * @param studentID id-ul studentului pe care il cautam
//     * @return studentul daca exista sau null daca nu
//     */
//    @Override
//    public Student findOne(Long studentID)
//    {
//
//        for(Student student:repoList){
//            if (student.getID().equals(studentID))
//                return student;
//        }
//        return null;
//    }
//    /**
//     *
//     * @return toti studentii
//     */
//    @Override
//    public ArrayList<Student> findAll()
//    {
//        return repoList;
//    }
//
//    /**
//     *
//     * @param id id-ul studentului ce trebuie sters
//     * @return studentul daca a fost sters, null in caz contrar
//     */
//    @Override
//    public Student delete(Long id)
//    {
//        Student student=findOne(id);
//        if (student!= null)
//        {
//            repoList.remove(student);
//            return student;
//        }
//        return null;
//    }
//
//
//
//    /**
//     *
//     * @param newStudent noile valori pentru studentul ce trebuie modificat
//     * @return null daca studentul a fost modifical, studentul in caz contrar
//     */
//    @Override
//    public Student update (Student newStudent)
//    {
//        for(Student student:repoList)
//        {
//            if (student.getID().equals(newStudent.getID()))
//            {
//                repoList.remove(student);
//                repoList.add(newStudent);
//                return null;
//            }
//        }
//        return newStudent;
//    }
//    /**
//     * citeste studentii dintr-un fisier
//     * @return o lista de studenti
//     */
//    @Override
//    public List<Student> readDataFromFile() throws FileNotFoundException {
//        List<Student> studentList = new ArrayList<>();
//        String separator=",";
//        String line;
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\blida\\development\\students.csv"));
//            while ((line = reader.readLine()) != null) {
//                String[] student = line.split(separator);
//                String firstName = student[0].replace("\"","");
//                String lastName = student[1].replace("\"","");
//                Long studentID = Long.parseLong(student[2]);
//                int credits = Integer.parseInt(student[3]);
//                student[4] = student[4].replace("]", "");
//                student[4] = student[4].replace("[", "");
//                String[] courseList = student[4].split(";");
//                List<Course> coursesOfStudent = new ArrayList<>();
//                //if course has no students enrolled
//                if (!courseList[0].equals(""))
//                {    for (String course:courseList)
//                {
//                    course= course.replace("\"","");
//                    Long courseID = Long.parseLong(course);
//                    Course newCourse = new Course();
//                    newCourse.setId(courseID);
//                    coursesOfStudent.add(newCourse);
//
//                }
//                }
//
//                Student newStudent = new Student(firstName,lastName,studentID,credits,coursesOfStudent);
//                //System.out.println(Arrays.toString(studensEnrolled.toArray()));
//                studentList.add(newStudent);
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return studentList;
//    }
//
//    /**
//     * face update la fisier
//     * @throws IOException daca fisierul nu a fost gasit
//     */
//    @Override
//    public void writeToFile() throws IOException {
//        List<Student> studentList = repoList;
//        new PrintWriter("C:\\Users\\blida\\development\\students.csv").close();
//        for (Student student:studentList) {
//            String courseIDs="";
//            String data= "";
//            for (Course course : student.getEnrolledCourses()) {
//                courseIDs += (String.valueOf(course.getId())) + ';';
//            }
//            //StringBuffer stringBuffer = new StringBuffer(courseIDs);
//            if (courseIDs.length()>0){
//                courseIDs.substring(0, courseIDs.length() - 1);
//            }
//            String list="["+courseIDs+"]";
//            data = student.getFirstName()+","+student.getLastName()+","+student.getID()+","+student.getTotalCredits()+","+list+"\n";
//            Files.write(Paths.get("C:\\Users\\blida\\development\\students.csv"),data.getBytes(), StandardOpenOption.APPEND);
//        }
//
//
//    }
//}