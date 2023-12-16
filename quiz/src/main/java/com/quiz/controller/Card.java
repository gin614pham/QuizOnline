package com.quiz.controller;

import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
