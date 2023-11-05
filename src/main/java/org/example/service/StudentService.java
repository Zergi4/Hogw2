package org.example.service;


import org.example.entity.Faculty;
import org.example.entity.Student;
import org.example.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;


@Service
public class StudentService {
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("A method createStudent was used");
        return studentRepository.save(student);
    }


    public Student findStudent(long id) {
        logger.info("A method findStudent was used");
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.info("A method editStudent was used");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("A method deleteStudent was used");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        logger.info("A method getStudentsByAge was used");
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getStudentsByAgeBetween(int min, int max) {
        logger.info("A method getStudentsByAgeBetween was used");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getStudentFaculty(long id) {
        logger.info("A method getStudentFaculty was used");
        if (studentRepository.findById(id).isPresent()) {
            return studentRepository.findById(id).get().getFaculty();
        }
        return null;
    }

    public int getStudentCount() {
        logger.info("A method getStudentCount was used");
        return studentRepository.getStudentCount();
    }

    public int getStudentAvgAge() {
        logger.info("A method getStudentAvgAge was used");
        return studentRepository.getStudentAvgAge();
    }

    public Collection<Student> getLast5Student() {
        logger.info("A method getLast5Student was used");
        return studentRepository.getLast5Students();
    }

    public Collection<Student> findByNameIsStartingWithA() {
        return studentRepository.findAll().stream()
                .filter(student -> student.getName()
                        .startsWith("A"))
                .toList();
    }


    public double getAverageAge() {

        return studentRepository.findAll().stream().mapToInt(Student::getAge).average().getAsDouble();
    }

    public int getSum() {

        return Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);// .parallel() уменьшает скорость обработки


    }
    public void printAll() {
        List<Student> students = studentRepository.findAll();
        printStudent(students.get(0));
        printStudent(students.get(1));
        Thread thread1 = new Thread(() -> {
            printStudent(students.get(2));
            printStudent(students.get(3));
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        });
        thread2.start();
        System.out.println();


    }


    public void printAllSynchronized() {
        List<Student> students = studentRepository.findAll();
        printStudentSync(students.get(0));
        printStudentSync(students.get(1));
        Thread thread1 = new Thread(() -> {
            printStudentSync(students.get(2));
            printStudentSync(students.get(3));
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            printStudentSync(students.get(4));
            printStudentSync(students.get(5));
        });
        thread2.start();
        System.out.println();


    }


    private void printStudent(Student student) {
        System.out.println(Thread.currentThread().getName() + " " + student.getName());
    }
    private synchronized void printStudentSync(Student student) {
        printStudent(student);
    }
}
