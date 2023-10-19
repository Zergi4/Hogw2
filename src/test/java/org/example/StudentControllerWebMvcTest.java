package org.example;

import org.example.controller.StudentController;
import org.example.entity.Faculty;
import org.example.entity.Student;
import org.example.repository.AvatarRepository;
import org.example.repository.FacultyRepository;
import org.example.repository.StudentRepository;
import org.example.service.AvatarService;
import org.example.service.FacultyService;
import org.example.service.StudentService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private AvatarService avatarService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void getStudentTest() throws Exception {
        Student student = new Student(1L, "Oleg", 20);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", student.getName());
        studentObject.put("age", student.getAge());


        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + student.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));

    }

    @Test
    public void getStudentByAgeTest() throws Exception {
        Student student1 = new Student(1L, "Oleg", 20);
        Student student2 = new Student(2L, "Ne Oleg", 20);

        List<Student> list = List.of(student1, student2);

        when(studentRepository.findByAge(any(int.class))).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(student1.getId()))
                .andExpect(jsonPath("$.[0].name").value(student1.getName()))
                .andExpect(jsonPath("$.[0].age").value(student1.getAge()))
                .andExpect(jsonPath("$.[1].id").value(student2.getId()))
                .andExpect(jsonPath("$.[1].name").value(student2.getName()))
                .andExpect(jsonPath("$.[1].age").value(student2.getAge()));

    }

    @Test
    public void createStudentTest() throws Exception {
        Student student = new Student(1L, "Oleg", 20);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", student.getName());
        studentObject.put("age", student.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));

    }

    @Test
    public void editStudentTest() throws Exception {
        Student student = new Student(1L, "Oleg", 20);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", student.getName());
        studentObject.put("age", student.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));

    }

    @Test
    public void deleteStudentTest() throws Exception {
        doNothing().when(studentRepository).deleteById(any(Long.class));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getStudentByAgeBetweenTest() throws Exception {
        Student student1 = new Student(1L, "Oleg", 20);
        Student student2 = new Student(2L, "Ne Oleg", 20);

        List<Student> list = List.of(student1, student2);

        when(studentRepository.findByAgeBetween(any(int.class), any(int.class))).thenReturn(list);
//http://localhost:8080/student/age?min=10&max=21
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age?min=" + 10 + "&max=" + 21)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(student1.getId()))
                .andExpect(jsonPath("$.[0].name").value(student1.getName()))
                .andExpect(jsonPath("$.[0].age").value(student1.getAge()))
                .andExpect(jsonPath("$.[1].id").value(student2.getId()))
                .andExpect(jsonPath("$.[1].name").value(student2.getName()))
                .andExpect(jsonPath("$.[1].age").value(student2.getAge()));

    }

    @Test
    public void getStudentFacultyTest() throws Exception {
        Student student = new Student(1L, "Oleg", 20);
        student.setFaculty(new Faculty("name","color"));

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", student.getName());
        studentObject.put("age", student.getAge());


        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty/" + student.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getFaculty().getId()))
                .andExpect(jsonPath("$.name").value(student.getFaculty().getName()))
                .andExpect(jsonPath("$.color").value(student.getFaculty().getColor()));

    }
}