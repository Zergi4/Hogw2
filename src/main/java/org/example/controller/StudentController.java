package org.example.controller;




import org.example.entity.Faculty;
import org.example.entity.Student;
import org.example.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;
    Logger logger = LoggerFactory.getLogger(StudentController.class);

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<Collection<Student>> getStudentByAge(@PathVariable int age) {
        Collection<Student> result = studentService.getStudentsByAge(age);
        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> getStudentByAgeBetween(@RequestParam int min, @RequestParam int max) {
        Collection<Student> result = studentService.getStudentsByAgeBetween(min, max);
        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity facultyByStudent(@PathVariable long id) {
        Faculty foundFaculty = studentService.getStudentFaculty(id);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @GetMapping("/count")
    public int getStudentCount() {
        return studentService.getStudentCount();
    }

    @GetMapping("/avg-age")
    public int getStudentAvgAge() {
        return studentService.getStudentAvgAge();
    }

    @GetMapping("/last5")
    public Collection<Student> getLast5Students() {
        return studentService.getLast5Student();
    }
    @GetMapping("/list/letter/a")
    public Collection<Student> findByNameIsStartingWithA() {
        return studentService.findByNameIsStartingWithA();
    }
    @GetMapping("/average-age")
    public double getAverageAgeByStream() {
        return studentService.getAverageAge();
    }

}
