package com.assignment.controller;

import com.assignment.bo.BoFactory;
import com.assignment.bo.custom.UserBo;
import com.assignment.bo.custom.impl.UserBoImpl;
import com.assignment.dto.UserDTO;
import com.assignment.util.PasswordEncrypt;
import com.assignment.util.PasswordVerifier;
import com.assignment.util.Regex;
import com.assignment.util.TextField;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {
    @FXML
    private AnchorPane signupform;

    @FXML
    private JFXTextField txtPassword1;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtRePassword1;

    @FXML
    private JFXPasswordField txtRePassword;

    @FXML
    private JFXTextField txtUserEmail;

    @FXML
    private JFXTextField txtUserID;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXComboBox<String> txtrole;

    UserBo userBO = (UserBoImpl) BoFactory.getBoFactory().getBo(BoFactory.BoType.User);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearTextFileds();
        txtPassword1.setVisible(false);
        txtRePassword1.setVisible(false);
        txtrole.getItems().addAll("admin", "coordinator");
    }

    private void generateNextUserId() {
        String nextId = null;
        try {
            nextId = userBO.generateNewUserID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        txtUserID.setText(nextId);
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws Exception {
        String id = txtUserID.getText();
        String name = txtUsername.getText();
        String password = txtPassword.getText();
        String repassword = txtRePassword.getText();
        String email = txtUserEmail.getText();
        String role = (String) txtrole.getValue();

        int validationCode;
        String encryptedrePassword = PasswordEncrypt.hashPassword(repassword);
        if (id.isEmpty() || name.isEmpty() || password.isEmpty() || repassword.isEmpty() || email.isEmpty() || role == null) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
            return;
        } else {
            validationCode = isValid();
        }

        switch (validationCode) {
            case 1 -> new Alert(Alert.AlertType.ERROR, "Invalid username!").show();
            case 2 -> new Alert(Alert.AlertType.ERROR, "Invalid email format!").show();
            case 3 -> new Alert(Alert.AlertType.ERROR, "Invalid password format!").show();
            case 4 -> new Alert(Alert.AlertType.ERROR, "Invalid Repassword format!").show();
            default -> {
                if (userBO.UserIdExists(id)) {
                    new Alert(Alert.AlertType.ERROR, "User ID " + id + " already exists!").show();
                    return;
                } else if (userBO.usernameExists(name)) {
                    new Alert(Alert.AlertType.ERROR, "User name " + name + " already exists!").show();
                    return;
                }
                if (PasswordVerifier.verifyPassword(password, encryptedrePassword)) {
                    if (userBO.saveUser(new UserDTO(id, name, encryptedrePassword, email, role))) {
                        clearTextFileds();
                        new Alert(Alert.AlertType.INFORMATION, "Sign-up successful! Your account has been created!!").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Sign-up failed! Please try again later!!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "Passwords do not match! Please re-enter your password!!").show();
                }
            }
        }
    }

    private void clearTextFileds() {
        txtUserID.clear();
        txtUserEmail.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtPassword1.clear();
        txtRePassword.clear();
        txtRePassword1.clear();
        txtrole.getSelectionModel().clearSelection();
        generateNextUserId();
    }

    @FXML
    void lblHaveAccountOnMouseClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
            AnchorPane coursesPane = loader.load();
            signupform.getChildren().setAll(coursesPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showPasswordOnMousePressed(MouseEvent event) {
        txtRePassword.setVisible(false);
        txtRePassword1.setText(txtRePassword.getText());
        txtRePassword1.setVisible(true);
    }

    @FXML
    void showPasswordOnMouseReleased(MouseEvent event) {
        txtRePassword.setVisible(true);
        txtRePassword1.setVisible(false);
    }

    @FXML
    void showRePasswordOnMousePressed(MouseEvent event) {
        txtPassword.setVisible(false);
        txtPassword1.setText(txtPassword.getText());
        txtPassword1.setVisible(true);
    }

    @FXML
    void showRePasswordOnMouseReleased(MouseEvent event) {
        txtPassword.setVisible(true);
        txtPassword1.setVisible(false);
    }

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.PASSWORD, txtPassword);
    }

    @FXML
    void txtRePasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.PASSWORD, txtRePassword);
    }

    @FXML
    void txtUserEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.EMAIL, txtUserEmail);
    }

    @FXML
    void txtUsernameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.USERNAME, txtUsername);
    }

    public int isValid() {
        if (!Regex.setTextColor(TextField.USERNAME, txtUsername)) return 1;
        if (!Regex.setTextColor(TextField.EMAIL, txtUserEmail)) return 2;
        if (!Regex.setTextColor(TextField.PASSWORD, txtPassword)) return 3;
        if (!Regex.setTextColor(TextField.PASSWORD, txtRePassword)) return 4;
        return 0;
    }
}
