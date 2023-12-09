package com.quiz.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.quiz.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        // get data from text field and password field
        String emailText = email.getText();
        String passwordText = password.getText();

        // send data to server
        App.getServer().login(emailText, passwordText);
        FXMLLoader fxmlLoader = App.lFXML("screen/app/home");
        Parent root = fxmlLoader.load();
        Home home = fxmlLoader.getController();
        // create list of card
        ArrayList<Card> list = new ArrayList<>();
        // add card
        list.add(new Card());
        list.add(new Card());
        list.add(new Card());
        list.add(new Card());

        // for each card in list
        for (Card card : list) {
            // add card to card view
            home.addCard(card);
        }
        App.setRoot(root);

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
