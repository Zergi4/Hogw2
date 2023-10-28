package org.example.service;

import org.example.entity.Faculty;
import org.example.entity.Student;
import org.example.repository.FacultyRepository;
import org.example.repository.StudentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("A method createFaculty was used");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.info("A method findFacultie was used");

        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {

        logger.info("A method editFaculty was used");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("A method deleteFaculty was used");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.info("A method getFacultiesByColor was used");
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> getFacultiesByColorOrName(String colorOrName) {
        logger.info("A method getFacultiesByColorOrName was used");
        return facultyRepository.findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(colorOrName, colorOrName);
    }

    public Collection<Student> getFacultyStudents(long id) {
        logger.info("A method getFacultyStudents was used");

        return studentRepository.findAllByFaculty_id(id);
    }
}