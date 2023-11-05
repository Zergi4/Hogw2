package org.example;

import org.assertj.core.api.Assertions;
import org.example.controller.StudentController;
import org.example.entity.Faculty;
import org.example.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Collection;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        //http://localhost:8080/student/age/20
    void getByAge() throws Exception {
        Student studentForCreate = new Student("Иван", 20);

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/age/20", Collection.class)).isNotNull();
    }

    @Test
        // http://localhost:8080/student/age?min=19&max=22

    void findByAgeBetween() throws Exception {
        Student studentForCreate = new Student("Иван", 22);
        Student studentForCreate1 = new Student("Марья", 25);
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/age?min=19&max=22", String.class)).isNotNull().contains("Иван");
    }


    @Test
    void delete() {
        //Подготовка входных данных
        Student studentForDelete = new Student("Иван", 20);

        //Начало теста
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForDelete, Student.class);
        //проверяем, что такого студента после удаления нет в базе
/*
        Optional<Student> studentOpt = this.restTemplate.getForObject("http://localhost:" + port + "/student/"+ postedStudent.getId(), Optional.class);
*/
        String url = "http://localhost:" + port + "/student/" + postedStudent.getId();
        ResponseEntity<Void> resp = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    public void get() {
        Student student = new Student("Get", 24);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }


    @Test //http://localhost:8080/student/faculty/1


    public void getFacultyByStudentId() {
        Student student = new Student("GetFaculty", 24);
        this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/faculty/" + student.getId(), Faculty.class))                ;
    }

}