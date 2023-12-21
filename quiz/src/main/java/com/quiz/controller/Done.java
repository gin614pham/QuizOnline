package com.quiz.controller;

import com.quiz.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Done {
    @FXML
    private Button homeBTN;

    @FXML
    private Label numc;

    @FXML
    private Label point;

    @FXML
    private Label completed;

    public void setData(String numCorrect, double point) {
        this.numc.setText(numCorrect);
        this.point.setText("Point:" + String.valueOf(point) + "/10");
        this.completed.setText("Completed Quiz");
    }

    @FXML
    private void home() throws Exception {
        App.returnHome();
    }
}
