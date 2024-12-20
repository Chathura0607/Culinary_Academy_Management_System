package com.assignment.dao.custom;

import com.assignment.dao.CrudDAO;
import com.assignment.entity.Enrollment;

import java.sql.SQLException;
import java.util.List;

public interface EnrollmentDAO extends CrudDAO<Enrollment> {
    public List<String> getAllEnrollmentIds() throws SQLException, ClassNotFoundException;
    public boolean isStudentEnrolledInCourse(String studentId, String courseId) throws Exception;
    public Enrollment findEnrollmentById(String enrollmentId) throws Exception;
    public double getRemainingFeeByEnrollmentId(String enrollmentId) throws SQLException, ClassNotFoundException;
    public boolean updateRemainingFee(String enrollmentId, double newFee) throws SQLException, ClassNotFoundException;
    public int getEnrollmentCount() throws SQLException, ClassNotFoundException;

}
