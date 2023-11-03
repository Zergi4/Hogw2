package org.example.service;

import org.example.entity.Student;
import org.example.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @InjectMocks
    StudentService out;
    @Mock
    private StudentRepository repository;

    @Test
    void createStudent() {
        Student newStudent = new Student(1L, "new", 20);
        when(repository.save(newStudent)).thenReturn(newStudent);
        assertEquals(newStudent, out.createStudent(newStudent));
    }

    @Test
    void findStudentTrue() {
        Student newStudent = new Student(2L, "second", 20);
        when(repository.findById(2L)).thenReturn(Optional.of(newStudent));
        assertEquals(newStudent, out.findStudent(2));

    }

    @Test
    void findStudentFalse() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> out.findStudent(2));
    }

    @Test
    void editStudent() {
        Student newStudent = new Student(2L, "2nd", 20);
        when(repository.save(newStudent)).thenReturn(newStudent);
        assertEquals(newStudent, out.editStudent(newStudent));
    }


    @Test
    void getStudentsByAgeTrue() {
        List<Student> list = new ArrayList<>(List.of(new Student(1L, "first", 20),
                new Student(2L, "second", 20)));
        when(repository.findByAge(20)).thenReturn(list);
        assertEquals(list, out.getStudentsByAge(20));
    }

    @Test
    void getStudentsByColorFalse() {
        List<Student> list = new ArrayList<>();
        when(repository.findByAge(21)).thenReturn(list);
        assertEquals(list, out.getStudentsByAge(21));
    }
}