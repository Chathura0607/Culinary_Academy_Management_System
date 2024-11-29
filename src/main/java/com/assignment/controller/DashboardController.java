package com.assignment.controller;

import com.assignment.bo.BoFactory;
import com.assignment.bo.custom.CourseBo;
import com.assignment.bo.custom.EnrollmentBo;
import com.assignment.bo.custom.StudentBo;
import com.assignment.bo.custom.impl.CourseBoImpl;
import com.assignment.bo.custom.impl.StudentBoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private JFXButton btnCourses;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnEnrollment;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnPayments;

    @FXML
    private JFXButton btnProfile;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private JFXButton btnUser;

    @FXML
    private AnchorPane mainForm;

    @FXML
    private JFXTextField txtCoursesCount;

    @FXML
    private JFXTextField txtEnrollmentCount;

    @FXML
    private JFXTextField txtStudentCount;

    ArrayList<Node> originalMainformChildren;
    StudentBo studentBo = (StudentBoImpl) BoFactory.getBoFactory().getBo(BoFactory.BoType.Student);
    CourseBo courseBo = (CourseBoImpl) BoFactory.getBoFactory().getBo(BoFactory.BoType.Course);
    EnrollmentBo enrollmentBo = (EnrollmentBo) BoFactory.getBoFactory().getBo(BoFactory.BoType.Enrollment);


    private int role;

    @Setter
    @Getter
    private String userid;

    public void setRole(int role) {
        this.role = role;
        applyRoleBasedDecisions();
    }

    private void applyRoleBasedDecisions() {
        if (1 == role) {
            System.out.println("Admin privileges granted.");
            enableAllButtons();
        } else if (0 == role) {
            System.out.println("Coordinator privileges granted.");
            restrictToCoordinatorPermissions();
        } else {
            System.out.println("No specific privileges granted.");
            disableAllButtons();
        }
    }

    private void enableAllButtons() {
        btnCourses.setVisible(true);
        btnDashboard.setVisible(true);
        btnEnrollment.setVisible(true);
        btnLogOut.setVisible(true);
        btnPayments.setVisible(true);
        btnProfile.setVisible(true);
        btnStudent.setVisible(true);
        btnUser.setVisible(true);
    }

    private void restrictToCoordinatorPermissions() {
        btnCourses.setVisible(false);
        btnDashboard.setVisible(true);
        btnLogOut.setVisible(true);
        btnPayments.setVisible(false);
        btnProfile.setVisible(true);
        btnStudent.setVisible(true);
        btnEnrollment.setVisible(true);
        btnUser.setVisible(false);
    }

    private void disableAllButtons() {
        btnCourses.setVisible(false);
        btnDashboard.setVisible(false);
        btnEnrollment.setVisible(false);
        btnLogOut.setVisible(false);
        btnPayments.setVisible(false);
        btnProfile.setVisible(false);
        btnStudent.setVisible(false);
        btnUser.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        originalMainformChildren = new ArrayList<>(mainForm.getChildren());
        setcounts();
    }

    public void setcounts() {
        try {
            int coursesCount = courseBo.getCourseCount();
            int enrollmentCount = enrollmentBo.getEnrollmentCount();
            int studentCount = studentBo.getStudentCount();
            txtCoursesCount.setText(String.valueOf(coursesCount));
            txtEnrollmentCount.setText(String.valueOf(enrollmentCount));
            txtStudentCount.setText(String.valueOf(studentCount));

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load counts from database.").show();
        }
    }

    @FXML
    void btncoursesOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/courseForm.fxml"));
            AnchorPane coursesPane = loader.load();
            mainForm.getChildren().setAll(coursesPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btndashboardOnAction(ActionEvent event) throws IOException {
        try {
            setcounts();
            mainForm.getChildren().setAll(originalMainformChildren);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnenrollmentOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/enrollmentForm.fxml"));
            AnchorPane coursesPane = loader.load();
            mainForm.getChildren().setAll(coursesPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnlogoutOnAction(ActionEvent event) throws IOException {
        mainForm.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnpaymentsOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/paymentForm.fxml"));
            AnchorPane coursesPane = loader.load();
            mainForm.getChildren().setAll(coursesPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnseeprofileOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/profileForm.fxml"));
            AnchorPane profilePane = loader.load();
            mainForm.getChildren().setAll(profilePane);
            ProfileController profileController = loader.getController();
            profileController.setUserid(getUserid());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnstudentOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/studentForm.fxml"));
            AnchorPane coursesPane = loader.load();
            mainForm.getChildren().setAll(coursesPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnuserOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/userForm.fxml"));
            AnchorPane coursesPane = loader.load();
            mainForm.getChildren().setAll(coursesPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
