package org.example.service;


import org.example.entity.Faculty;
import org.example.entity.Student;
import org.example.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.Collection;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }


    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getStudentsByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getStudentFaculty(long id) {
        if (studentRepository.findById(id).isPresent()) {
            return studentRepository.findById(id).get().getFaculty();
        }
        return null;
    }

    public int getStudentCount() {
        return studentRepository.getStudentCount();
    }

    public int getStudentAvgAge() {
        return studentRepository.getStudentAvgAge();
    }

    public Collection<Student> getLast5Student() {
        return studentRepository.getLast5Students();
    }

    public Collection<String> getNameList() {
        return studentRepository.findAll().stream()
                .map(s -> s.getName().toUpperCase())
                .filter(n -> n.startsWith("A"))
                .sorted().collect(Collectors.toList());
    }

    public double getStudentAvgAgeByStream() {
        return studentRepository.findAll().stream().mapToInt(Student::getAge).average().getAsDouble();
    }

    public int getNumber() {

        return Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);//распараллеливание стрима только замедляет скорость выполнения, поэтому оставил все так


    }

    public void studentThread() {
        List<Student> studentList = studentRepository.findAll();
        System.out.println("************************");

        System.out.println(studentList.get(0));
        System.out.println(studentList.get(1));

        new Thread(() -> {
            System.out.println(studentList.get(2));
            System.out.println(studentList.get(3));
        }).start();
        new Thread(() -> {
            System.out.println(studentList.get(4));
            System.out.println(studentList.get(5));
        }).start();
    }
    private static final Object flag = new Object();
    public void synchronizedStudentThread() throws InterruptedException {
        List<Student> studentList = studentRepository.findAll();
        printStudent(studentList.get(0));
        printStudent(studentList.get(1));

        var t1 = new Thread(() -> {
            printStudent(studentList.get(2));
            printStudent(studentList.get(3));
        });
        var t2 = new Thread(() -> {
            printStudent(studentList.get(4));
            printStudent(studentList.get(5));
        });
        t1.start();
        t1.join();
        t2.start();
        t2.join();
    }

    private static void printStudent(Student student){
        synchronized (flag) {
            System.out.println(student);
        }
    }
}