package com.spring.RESTApi.service;

import com.spring.RESTApi.dto.AddStudentRequestDto;
import com.spring.RESTApi.dto.StudentDto;

import java.util.List;
import java.util.Map;

public interface StudentService {

    List<StudentDto> getAllStudents();

    StudentDto getStudentById(Long id);

    StudentDto createNewStudent(
            AddStudentRequestDto addStudentRequestDto
    );

    StudentDto updateStudent(
            Long id,
            AddStudentRequestDto addStudentRequestDto
    );

    StudentDto updatePartialStudent(
            Long id,
            Map<String, Object> updates
    );

    void deleteStudentById(Long id);
}