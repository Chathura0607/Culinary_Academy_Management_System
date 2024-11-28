package com.assignment.bo.custom;

import com.assignment.bo.SuperBo;
import com.assignment.dto.StudentDTO;
import com.assignment.entity.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface StudentBo extends SuperBo {
    public boolean saveStudent(StudentDTO dto) throws Exception;
    public boolean updateStudent(StudentDTO dto) throws Exception;
    public boolean deleteStudent(String ID) throws Exception;
    public List<StudentDTO> getAllStudent() throws SQLException, ClassNotFoundException;
    public String generateNewStudentID() throws SQLException, ClassNotFoundException, IOException;
    public boolean StudentIdExists(String studentId) throws SQLException, ClassNotFoundException;
    public List<String> getAllStudentIds() throws SQLException, ClassNotFoundException;
    public Student getStudentById(String studentId) throws Exception;
    public Student findStudentById(String studentId) throws Exception;
    public int getStudentCount() throws Exception;
}