package com.assignment.bo.custom;

import com.assignment.bo.SuperBo;
import com.assignment.dto.UserDTO;
import com.assignment.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserBo extends SuperBo {
    public boolean saveUser(UserDTO dto) throws Exception;
    public boolean updateUser(UserDTO dto) throws Exception;
    public boolean deleteUser(String ID) throws Exception;
    public List<UserDTO> getAllUser() throws SQLException, ClassNotFoundException;
    public String generateNewUserID() throws SQLException, ClassNotFoundException, IOException;
    public boolean UserIdExists(String userId) throws SQLException, ClassNotFoundException;
    public User findUserById(String userId) throws Exception;
    public User findUserByname(String username) throws Exception;
    public boolean usernameExists(String username) throws SQLException, ClassNotFoundException;

}
