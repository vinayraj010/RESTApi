package com.spring.RESTApi.repository;

import com.spring.RESTApi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}