package com.quiz.controller;

import java.io.IOException;

import com.quiz.App;
import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class QuizInfo {

    @FXML
    private Label IdQuiz;

    @FXML
    private Label authorQuiz;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnDoQuiz;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label dateCreate;

    @FXML
    private ListView<?> historyQuiz;

    @FXML
    private Label idAuthor;

    @FXML
    private Label nameQuiz;

    @FXML
    private Label numQuiz;

    private Quiz quiz;

    public void setQuiz(Quiz quiz) throws IOException {
        IdQuiz.setText("ID: " + String.valueOf(quiz.getId()));
        nameQuiz.setText(quiz.getName());
        numQuiz.setText("Questions: " + String.valueOf(quiz.getNumQuestions()));
        authorQuiz.setText("Author: " + quiz.getAuthor());
        idAuthor.setText("ID Author: " + String.valueOf(quiz.getIdAuthor()));
        dateCreate.setText("Date created: " + quiz.getDateCreated());
        this.quiz = quiz;
        setContent();

    }

    private void setContent() throws IOException {
        if (App.getUser().getId() == quiz.getIdAuthor()) {
            btnUpdate.setVisible(true);
            btnDelete.setVisible(true);
            historyQuiz.setVisible(true);
        } else {
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
            historyQuiz.setVisible(false);
        }
    }

}
