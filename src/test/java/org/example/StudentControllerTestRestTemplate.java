package org.example;

import org.assertj.core.api.Assertions;
import org.example.controller.StudentController;
 import org.example.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;


import java.util.Collection;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    void create_success() {
        //Подготовка входных данных
        Student studentForCreate = new Student("Иван", 20);

        //Подготовка ожидаемого результата
        Student expectedStudent = new Student("Иван", 20);

        //Начало теста
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForCreate, Student.class);
        assertThat(postedStudent).isNotNull();
        assertEquals(expectedStudent.getName(), postedStudent.getName());
        assertEquals(expectedStudent.getAge(), postedStudent.getAge());
    }

    @Test
    void getByAge() throws Exception {
        Student studentForCreate = new Student("Иван", 20);

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/by-age?age=20", Collection.class)).isNotNull();
    }

    @Test
    void findByAgeBetween() throws Exception {
        Student studentForCreate = new Student("Иван", 22);
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/by-age-between?min=20&max=23", String.class)).isNotNull().contains("Иван").contains("22");
    }


    @Test
    void delete() {
        //Подготовка входных данных
        Student studentForDelete = new Student("Иван", 20);

        //Начало теста
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForDelete, Student.class);
        this.restTemplate.delete("http://localhost:" + port + "/student" + "?id=" + postedStudent.getId());
        //проверяем, что такого студента после удаления нет в базе
        Optional<Student> studentOpt = this.restTemplate.getForObject("http://localhost:" + port + "/student" + "?id=" + postedStudent.getId(), Optional.class);
        assertTrue(studentOpt.isEmpty());
    }

    @Test
    public void get() {
        Student student = new Student("Get", 24);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }




   @Test //http://localhost:8080/student/faculty-by-student-id?id=2

   public void getFacultyByStudentId() {
       Student student = new Student("GetFaculty", 24);

       Assertions
               .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/faculty-by-student-id"+"?id=2", String.class))
               .contains("studentList");
    }

}