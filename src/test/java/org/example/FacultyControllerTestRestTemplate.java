package org.example;

import org.example.controller.FacultyController;

import org.example.entity.Faculty;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

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
    void delete() {
        //Подготовка входных данных
        Faculty facultyForDelete = new Faculty("testFac", "testColor");

        //Начало теста
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyForDelete, Faculty.class);

        this.restTemplate.delete("http://localhost:" + port + "/faculty/" + postedFaculty.getId());
        //проверяем, что такого факультета после удаления нет в базе
        Optional<Faculty> facultyOpt = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + postedFaculty.getId(), Optional.class);
        assertTrue(facultyOpt.isEmpty());
    }

    @Test
    void getByColor() {
        Faculty faculty = new Faculty("Get", "Red");
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/by-color?color=red", Faculty.class))
                .isNotNull();
    }

    @Test
    void getByColorOrNameIgnoreCase() {
    }

    @Test
    void getStudentsByFacultyId() {
    }
}