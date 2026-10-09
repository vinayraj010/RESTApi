package com.spring.RESTApi.controller;

import com.spring.RESTApi.dto.AddStudentRequestDto;
import com.spring.RESTApi.dto.StudentDto;
import com.spring.RESTApi.response.ApiResponse;
import com.spring.RESTApi.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDto>>> getAllStudents() {

        List<StudentDto> students = studentService.getAllStudents();

        ApiResponse<List<StudentDto>> response =
                new ApiResponse<>(
                        true,
                        "Students fetched successfully",
                        students
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentById(
            @PathVariable Long id) {

        StudentDto student = studentService.getStudentById(id);

        ApiResponse<StudentDto> response =
                new ApiResponse<>(
                        true,
                        "Student fetched successfully",
                        student
                );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentDto>> createNewStudent(
            @Valid @RequestBody AddStudentRequestDto dto) {

        StudentDto student = studentService.createNewStudent(dto);

        ApiResponse<StudentDto> response =
                new ApiResponse<>(
                        true,
                        "Student created successfully",
                        student
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody AddStudentRequestDto dto) {

        StudentDto student =
                studentService.updateStudent(id, dto);

        ApiResponse<StudentDto> response =
                new ApiResponse<>(
                        true,
                        "Student updated successfully",
                        student
                );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> updatePartialStudent(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        StudentDto student =
                studentService.updatePartialStudent(id, updates);

        ApiResponse<StudentDto> response =
                new ApiResponse<>(
                        true,
                        "Student partially updated successfully",
                        student
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(
            @PathVariable Long id) {

        studentService.deleteStudentById(id);

        ApiResponse<Void> response =
                new ApiResponse<>(
                        true,
                        "Student deleted successfully",
                        null
                );

        return ResponseEntity.ok(response);
    }
}