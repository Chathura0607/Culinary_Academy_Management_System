package com.assignment.dao.custom;

import com.assignment.dao.CrudDAO;
import com.assignment.entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {
    public Student getStudentById(String studentId) throws Exception;
    public Student findStudentById(String studentId) throws Exception;
    public List<String> getAllStudentIds() throws SQLException, ClassNotFoundException;
    public int getStudentCount() throws SQLException, ClassNotFoundException;
}
