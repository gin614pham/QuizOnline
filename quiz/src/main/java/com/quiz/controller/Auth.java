package com.quiz.controller;

import java.io.IOException;

import com.quiz.App;
import com.quiz.model.data.User;

import javafx.fxml.FXML;
import javafx.scene.Parent;
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
        Parent root = App.loadFXML("screen/auth/login");
        App.setRoot(root);
    }

    // create function switch to Register
    @FXML
    private void switchToRegister() throws IOException {
        Parent root = App.loadFXML("screen/auth/register");
        App.setRoot(root);
    }

    // create function Login
    @FXML
    private void login() throws Exception {
        String emailText = email.getText();
        String passwordText = password.getText();
        if (emailText.isEmpty() || passwordText.isEmpty()) {
            App.showDialog("Login Error", "Email and password cannot be empty.");
            return;
        }
        User auth = App.getServer().login(emailText, passwordText);
        if (auth == null) {
            App.showDialog("Login Error", "Incorrect email or password.");
            return;
        }
        App.setUser(auth);
        App.returnHome();

    }

    // create function Register
    @FXML
    private void register() throws Exception {
        String emailText = email.getText();
        String nameText = name.getText();
        String passwordText = password.getText();
        String confirmText = confirm.getText();

        // Check that none of the inputs are empty
        if (emailText.isEmpty() || nameText.isEmpty() || passwordText.isEmpty() || confirmText.isEmpty()) {
            App.showDialog("Registration Error", "All fields are required.");
            return;
        }
        if (passwordText.length() < 6) {
            App.showDialog("Registration Error", "Password must be at least 6 characters.");
            return;
        }

        if (!passwordText.equals(confirmText)) {
            App.showDialog("Registration Error", "Passwords do not match.");
            return;
        }

        if (!emailText.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            App.showDialog("Registration Error", "Invalid email address.");
            return;
        }

        // check if password and confirm password is same
        if (password.getText().equals(confirmText)) {
            // send data to server
            User auth = App.getServer().register(emailText, passwordText, nameText);
            if (auth == null) {
                App.showDialog("Registration Error", "Email already exists.");
                return;
            }
            App.setUser(auth);
            App.returnHome();
        }
    }
}
