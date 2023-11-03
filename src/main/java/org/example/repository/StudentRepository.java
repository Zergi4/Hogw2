package org.example.repository;


import org.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.*;
import java.util.Collection;
import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findAllByFaculty_id(long id);

    @Query(value = "select count(*) from student",nativeQuery = true)
    int getStudentCount();

    @Query(value ="select avg(age) from student" , nativeQuery = true)
    int getStudentAvgAge();

    @Query(value = "select * from student order by id desc limit 5",nativeQuery = true)
    Collection<Student> getLast5Students();
}