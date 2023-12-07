package com.quiz.controller;

import java.io.IOException;
import com.quiz.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Auth {
    @FXML
    Button btnLogin, btnRegister;
    @FXML
    PasswordField password, confirm;
    @FXML
    TextField email, name;

    // create function switch to Login
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("screen/auth/login");
    }

    // create function switch to Register
    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("screen/auth/register");
    }

    // create function Login
    @FXML
    private void login() throws Exception {
        // get data from text field and password field
        String emailText = email.getText();
        String passwordText = password.getText();

        // send data to server
        App.getServer().login(emailText, passwordText);
        App.setRoot("screen/app/home");
    }

    // create function Register
    @FXML
    private void register() throws Exception {
        // get data from text field and password field
        String emailText = email.getText();
        String nameText = name.getText();
        String passwordText = password.getText();
        String confirmText = confirm.getText();

        // check if password and confirm password is same
        if (passwordText.equals(confirmText)) {
            // send data to server
            App.getServer().register(emailText, nameText, passwordText);
        }
    }
}
