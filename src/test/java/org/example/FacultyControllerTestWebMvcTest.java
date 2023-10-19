package org.example;
import org.example.controller.FacultyController;
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
class FacultyControllerTest {
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
    private FacultyController facultyController;

    @Test
    public void getFacultyTest() throws Exception {
        Faculty faculty = new Faculty("first faculty", "red");

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", faculty.getName());
        facultyObject.put("color", faculty.getColor());


        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }

    @Test
    public void getFacultyByColorTest() throws Exception {
        Faculty faculty1 = new Faculty("first faculty", "red");
        Faculty faculty2 = new Faculty("second faculty", "red");

        List<Faculty> list = List.of(faculty1, faculty2);

        when(facultyRepository.findByColorIgnoreCase(any(String.class))).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/color/red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(faculty1.getId()))
                .andExpect(jsonPath("$.[0].name").value(faculty1.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty1.getColor()))
                .andExpect(jsonPath("$.[1].id").value(faculty2.getId()))
                .andExpect(jsonPath("$.[1].name").value(faculty2.getName()))
                .andExpect(jsonPath("$.[1].color").value(faculty2.getColor()));

    }

    @Test
    public void createFacultyTest() throws Exception {
        Faculty faculty = new Faculty("first faculty", "red");

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", faculty.getName());
        facultyObject.put("color", faculty.getColor());

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }

    @Test
    public void editFacultyTest() throws Exception {
        Faculty faculty = new Faculty("first faculty", "red");

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", faculty.getName());
        facultyObject.put("color", faculty.getColor());

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }

    @Test
    public void deleteFacultyTest() throws Exception {
        doNothing().when(facultyRepository).deleteById(any(Long.class));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getFacultyByColorOrNameTest() throws Exception {
        Faculty faculty1 = new Faculty("first faculty", "red");
        Faculty faculty2 = new Faculty("second faculty", "red");

        List<Faculty> list = List.of(faculty1, faculty2);

        when(facultyRepository.findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(any(String.class), any(String.class))).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/color-or-name/red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(faculty1.getId()))
                .andExpect(jsonPath("$.[0].name").value(faculty1.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty1.getColor()))
                .andExpect(jsonPath("$.[1].id").value(faculty2.getId()))
                .andExpect(jsonPath("$.[1].name").value(faculty2.getName()))
                .andExpect(jsonPath("$.[1].color").value(faculty2.getColor()));

    }

    @Test
    public void getStudentByFacultyTest() throws Exception {
        Student student1 = new Student(1L, "Oleg", 20);
        Student student2 = new Student(2L, "Ne Oleg", 20);

        List<Student> list = List.of(student1, student2);

        when(studentRepository.findAllByFaculty_id(any(Long.class))).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1/students")
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
}