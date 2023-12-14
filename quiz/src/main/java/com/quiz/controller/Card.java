package com.quiz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Card {
    int id;

    @FXML
    private Label lab1;

    @FXML
    private Label sub;

    @FXML
    private Label title;

    // function handle click
    @FXML
    public void handleClick() {
        System.out.println("clicked on card " + id);
    }

    //
    public VBox getCard() {
        return new VBox();
    }

    public void setCard(Quiz quiz) {
        this.id = quiz.getId();
        this.title.setText(quiz.getName());
        this.sub.setText(quiz.getAuthor());
        this.lab1.setText(String.valueOf(quiz.getNumQuestions()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
