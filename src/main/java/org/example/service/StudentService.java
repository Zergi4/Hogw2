package org.example.service;


import org.example.entity.Faculty;
import org.example.entity.Student;
import org.example.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.Collection;



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
    logger.info("A method getStudentListByLetterA was used");
    return studentRepository.findByNameIsStartingWith("A");
}

}