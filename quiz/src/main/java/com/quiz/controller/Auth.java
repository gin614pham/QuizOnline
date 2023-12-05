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
}
