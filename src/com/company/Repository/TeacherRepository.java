//package com.company.Repository;
//
//import com.company.Model.Course;
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
//public class TeacherRepository extends InFileRepo<Teacher> {
//
//    /**
//     * initializeaza lista cu valorile gasite in fisier
//     * @param repoList o lista de profesori
//     * @throws FileNotFoundException daca fisierul nu a fost gasit
//     */
//    public TeacherRepository(ArrayList<Teacher> repoList) throws FileNotFoundException {
//        super(repoList);
//    }
//
//    /**
//     *
//     * @param newTeacher - salveaza un profesor nou in lista de profesori
//     * @return profesorul daca acesta a fost salvat, null in caz contrar
//     */
//    @Override
//    public Teacher save(Teacher newTeacher)
//    {
//        for (Teacher teacher:repoList)
//        {
//            if(teacher.equals(newTeacher))
//                return teacher;
//        }
//        repoList.add(newTeacher);
//        return null;
//    }
//
//    /**
//     *
//     * @param teacherID id-ul profesorului pe care il cautam
//     * @return profesorul daca exista sau null daca nu
//     */
//    @Override
//    public Teacher findOne(Long teacherID)
//    {
//
//        for(Teacher teacher:repoList){
//            if (teacher.getID().equals(teacherID))
//                return teacher;
//        }
//        return null;
//    }
//    /**
//     *
//     * @return toti profesorii
//     */
//    @Override
//    public ArrayList<Teacher> findAll()
//    {
//        return repoList;
//    }
//
//    /**
//     *
//     * @param id id-ul profesorului ce trebuie sters
//     * @return profesorul daca a fost sters, null in caz contrar
//     */
//    @Override
//    public Teacher delete(Long id)
//    {
//        Teacher teacher=findOne(id);
//        if (teacher!= null)
//        {
//            repoList.remove(teacher);
//            return teacher;
//        }
//        return null;
//    }
//
//
//    /**
//     *
//     * @param newTeacher noile valori pentru profesorul ce trebuie modificat
//     * @return null daca profesorul a fost modifical, profesorul in caz contrar
//     */
//    @Override
//    public Teacher update (Teacher newTeacher)
//    {
//        for(Teacher teacher:repoList)
//        {
//            if (teacher.getID().equals(newTeacher.getID()))
//            {
//                repoList.remove(teacher);
//                repoList.add(newTeacher);
//                return null;
//            }
//        }
//        return newTeacher;
//    }
//    /**
//     * citeste profesorii dintr-un fisier
//     * @return o lista de profesori
//     */
//    @Override
//    public List<Teacher> readDataFromFile() throws FileNotFoundException {
//        List<Teacher> teacherList = new ArrayList<>();
//        String separator=",";
//        String line;
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\blida\\development\\teachers.csv"));
//            while ((line = reader.readLine()) != null) {
//
//                String[] teacher = line.split(separator);
//                Long teacherID = Long.parseLong(teacher[2]);
//                String firstName = teacher[0].replace("\"","");
//                String lastName = teacher[0].replace("\"","");
//                teacher[3] = teacher[3].replace("]", "");
//                teacher[3] = teacher[3].replace("[", "");
//                String[] courseID = teacher[3].split(";");
//                List<Course> courseList = new ArrayList<>();
//                if (!courseID[0].equals(""))
//                {    for (String course:courseID)
//                {
//                    course= course.replace("\"","");
//                    Long courseIDLong = Long.parseLong(course);
//                    Course newCourse = new Course();
//                    newCourse.setId(courseIDLong);
//                    courseList.add(newCourse);
//
//                }
//                }
//
//                Teacher newTeacher = new Teacher(firstName,lastName, teacherID,courseList);
//                teacherList.add(newTeacher);
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return teacherList;
//    }
//    /**
//     * face update la fisier
//     * @throws IOException daca fisierul nu a fost gasit
//     */
//    @Override
//    public void writeToFile() throws IOException {
//        List<Teacher> teacherList = repoList;
//        new PrintWriter("C:\\Users\\blida\\development\\teachers.csv").close();
//        for (Teacher teacher:teacherList) {
//            String courseIDs="";
//            String data= "";
//            for (Course course : teacher.getCourses()) {
//                courseIDs += (String.valueOf(course.getId())) + ';';
//            }
//            StringBuffer stringBuffer = new StringBuffer(courseIDs);
//            if (courseIDs.length()>0){
//                stringBuffer.deleteCharAt(courseIDs.length() - 1);
//            }
//            data = teacher.getFirstName()+","+teacher.getLastName()+","+teacher.getID()+","+",["+stringBuffer+"]"+"\n";
//            System.out.println(data);
//            Files.write(Paths.get("C:\\Users\\blida\\development\\teachers.csv"),data.getBytes(), StandardOpenOption.APPEND);
//        }
//    }
//}