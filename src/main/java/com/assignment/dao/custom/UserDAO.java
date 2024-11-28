package com.assignment.dao.custom;

import com.assignment.dao.CrudDAO;
import com.assignment.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    public User findUserById(String userId) throws Exception;
    public User findUserByname(String username) throws Exception;
    public boolean usernameExists(String username) throws SQLException, ClassNotFoundException;
}
