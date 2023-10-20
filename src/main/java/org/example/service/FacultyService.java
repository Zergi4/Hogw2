package org.example.service;

import org.example.entity.Faculty;
import org.example.entity.Student;
import org.example.repository.FacultyRepository;
import org.example.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> getFacultiesByColorOrName(String colorOrName) {
        return facultyRepository.findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(colorOrName, colorOrName);
    }

    public Collection<Student> getFacultyStudents(long id) {

        return studentRepository.findAllByFaculty_id(id);
    }
}