package com.quiz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Card implements Initializable {

    @FXML
    private Label lab1;

    @FXML
    private Label sub;

    @FXML
    private Label title;

    // initialize card
    @FXML
    public void initialize() {
        lab1.setText("aa");
        sub.setText("dsadsa");
        title.setText("er");
    }

    // function handle click
    @FXML
    public void handleClick() {
        System.out.println("clicked");
    }

    //
    public VBox getCard() {
        return new VBox();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lab1.setText("aa");
        sub.setText("dsadsa");
        title.setText("er");
    }

    public void setCard(Card card) {

    }

}
