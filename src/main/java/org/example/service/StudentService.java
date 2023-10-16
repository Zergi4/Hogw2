package org.example.service;

import org.example.entity.Faculty;
import org.example.entity.Student;
import org.example.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(String name, int age) {
        Student newStudent = new Student(name, age);
        newStudent = studentRepository.save(newStudent);
        return newStudent;
    }

    public Optional<Student> get(long id) {
        return studentRepository.findById(id);
    }

    public Student update(long id, String name, int age) {
        Student studentForUpdate = studentRepository.findById(id).get();
        studentForUpdate.setName(name);
        studentForUpdate.setAge(age);
        return studentRepository.save(studentForUpdate);
    }

    public Student delete(long id) {
        Student studentForDelete = studentRepository.findById(id).get();
        studentRepository.deleteById(id);
        return studentForDelete;
    }

    public List<Student> getByAge(int age) {
        return studentRepository.findAll().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public List<Student> getByAgeBetween(int min, int max) {
        return studentRepository.findAllByAgeBetween(min, max);
    }

    public Faculty getFacultyByStudentId(Long id) {
        return studentRepository.findById(id).get().getFaculty();
    }

    public List<Student> getByFacultyId(Long facultyId) {
        return studentRepository.findByFacultyId(facultyId);
    }
}
