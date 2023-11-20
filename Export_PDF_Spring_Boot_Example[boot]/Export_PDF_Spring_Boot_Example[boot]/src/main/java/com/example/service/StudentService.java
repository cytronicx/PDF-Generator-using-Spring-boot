package com.example.service;
import java.util.List;
import com.example.entity.Student;

public interface StudentService 
{

void addStudent(Student student);

List<Student> getStudentList();

Student getStudentById(int userId);
}