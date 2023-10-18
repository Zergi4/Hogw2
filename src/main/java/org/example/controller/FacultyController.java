package org.example.controller;


import org.example.entity.Faculty;
import org.example.entity.Student;
import org.example.service.FacultyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;
    Logger logger = LoggerFactory.getLogger(FacultyController.class);


    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            logger.error("There is not faculty with id = " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<Collection<Faculty>> getFacultyByColor(@PathVariable String color) {
        Collection<Faculty> result = facultyService.getFacultiesByColor(color);
        if (result.size() == 0) {
            logger.error("There is not faculty with color = " + color);
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            logger.error("There is not faculty with id = " + faculty.getId());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color-or-name/{colorOrName}")
    public ResponseEntity<Collection<Faculty>> getFacultyByColorOrName(@PathVariable String colorOrName) {
        Collection<Faculty> result = facultyService.getFacultiesByColorOrName(colorOrName);
        if (result.size() == 0) {
            logger.error("There is not faculty with color or name = " + colorOrName);
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> getStudentsByFaculty(@PathVariable long id) {
        Collection<Student> result = facultyService.getFacultyStudents(id);
        if (result.size() == 0) {
            logger.error("There is not students with faculty id = " + id);
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/longest-name")
    public String getTheLongestFacultyName(){
        return facultyService.getTheLongestName();
    }
}