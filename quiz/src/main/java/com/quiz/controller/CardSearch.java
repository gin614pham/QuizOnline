package com.quiz.controller;

import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CardSearch {

    private int id;

    @FXML
    private Label author;

    @FXML
    private VBox cradSearch;

    @FXML
    private Label numQuiz;

    @FXML
    private Label title;

    public void setCard(Quiz quiz) {
        this.id = quiz.getId();
        this.title.setText(quiz.getName());
        this.author.setText(quiz.getAuthor());
        this.numQuiz.setText(String.valueOf(quiz.getNumQuestions()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void handleClick() {
        System.out.println("clicked on card " + id);
    }

}
