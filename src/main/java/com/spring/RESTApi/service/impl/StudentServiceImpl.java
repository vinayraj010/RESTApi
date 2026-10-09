package com.spring.RESTApi.service.impl;

import com.spring.RESTApi.dto.AddStudentRequestDto;
import com.spring.RESTApi.dto.StudentDto;
import com.spring.RESTApi.entity.Student;
import com.spring.RESTApi.exception.ResourceNotFoundException;
import com.spring.RESTApi.repository.StudentRepository;
import com.spring.RESTApi.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public StudentServiceImpl(
            StudentRepository studentRepository,
            ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found: " + id
                        )
                );

        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public StudentDto createNewStudent(AddStudentRequestDto dto) {
        Student student = modelMapper.map(dto, Student.class);

        Student savedStudent = studentRepository.save(student);

        return modelMapper.map(savedStudent, StudentDto.class);
    }

    @Override
    public StudentDto updateStudent(
            Long id,
            AddStudentRequestDto dto) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found: " + id
                        )
                );

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());

        Student updatedStudent = studentRepository.save(student);

        return modelMapper.map(updatedStudent, StudentDto.class);
    }

    @Override
    public StudentDto updatePartialStudent(
            Long id,
            Map<String, Object> updates) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found: " + id
                        )
                );

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    student.setName((String) value);
                    break;

                case "email":
                    student.setEmail((String) value);
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Unknown field: " + key
                    );
            }
        });

        Student updatedStudent = studentRepository.save(student);

        return modelMapper.map(updatedStudent, StudentDto.class);
    }

    @Override
    public void deleteStudentById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Student not found: " + id
            );
        }

        studentRepository.deleteById(id);
    }
}