package com.quiz.controller;

import java.io.IOException;
import com.quiz.App;
import javafx.fxml.FXML;

public class Auth {

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
    private void login() throws IOException {

    }

    // create function Register
    @FXML
    private void register() throws IOException {

    }
}
