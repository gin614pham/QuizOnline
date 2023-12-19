package com.quiz.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DoQuiz {

    @FXML
    private Button btnSubmit;

    @FXML
    private VBox contentQuiz;

    @FXML
    private Label titleQuiz;

    @FXML
    private void submit() {
        System.out.println("submit");
    }

}
