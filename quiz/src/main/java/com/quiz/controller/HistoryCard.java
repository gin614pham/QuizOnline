package com.quiz.controller;

import com.quiz.model.data.Answer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HistoryCard {

    @FXML
    private Label date;

    @FXML
    private Label email;

    @FXML
    private Label idQuiz;

    @FXML
    private Label nameQuiz;

    @FXML
    private Label nameUser;

    @FXML
    private Label numcorrect;

    @FXML
    private Label point;

    public void setData(Answer answer) {
        date.setText(answer.getDateCreated());
        email.setText(answer.getEmail());
        idQuiz.setText(String.valueOf(answer.getIdQuiz()));
        nameQuiz.setText(answer.getNameQuiz());
        nameUser.setText(answer.getNameUser());
        numcorrect.setText(answer.getNumCorrect());
        point.setText(String.valueOf(answer.getPoint()));
    }

    public void setTitle() {
        date.setText("Date");
        email.setText("Email");
        idQuiz.setText("ID");
        nameQuiz.setText("Name Quiz");
        nameUser.setText("Name User");
        numcorrect.setText("Num Correct");
        point.setText("Point");
    }

}
