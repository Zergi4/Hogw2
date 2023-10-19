package org.example;

import org.example.controller.FacultyController;

import org.example.entity.Faculty;

import org.assertj.core.api.Assertions;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTestRestTemplate {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(facultyController).isNotNull();
    }


    @Test
    public void get() {
        Faculty faculty = new Faculty("Get", "green3");

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty?id=1", Faculty.class))
                .isNotNull();
    }


    @Test
    void getByColor() {
        Faculty faculty = new Faculty("Get", "Red");
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/by-color?color=red", Faculty.class))
                .isNotNull()
                .isInstanceOf(Faculty.class);
    }

    @Test
    void getByColorOrName() {
        Faculty faculty = new Faculty("Get", "Red");
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/color/" + faculty.getColor(), Faculty.class))
                .isNotNull();
    }

    @Test
    void delete() {
        //Подготовка входных данных
        Faculty facultyForDelete = new Faculty("testFac", "testColor");

        //Начало теста
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyForDelete, Faculty.class);

        this.restTemplate.delete("http://localhost:" + port + "/faculty/" + postedFaculty.getId());
        //проверяем, что такого факультета после удаления нет в базе
        Optional<Faculty> facultyOpt = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + postedFaculty.getId(), Optional.class);
        String url = "http://localhost:" + port + "/faculty/" + postedFaculty.getId();
        ResponseEntity<Void> resp = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test // GET http://localhost:8080/faculty/1/students

    void getStudentsByFacultyId() {
        Faculty facultyForGet = new Faculty("testFac", "testColor");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port +"/faculty/"+ facultyForGet.getId() + "/students", Collection.class))
                .isNotNull();

    }
}