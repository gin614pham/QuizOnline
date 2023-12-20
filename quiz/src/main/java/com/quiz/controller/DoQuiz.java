package com.quiz.controller;

import java.io.IOException;

import com.quiz.App;
import com.quiz.model.data.Question;
import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private Label idQuiz;

    @FXML
    private void submit() {
        System.out.println("submit");
    }

    private Quiz quiz;

    public void setQuiz(Quiz quiz) throws IOException {
        this.idQuiz.setText(String.valueOf(quiz.getId()));
        this.titleQuiz.setText(quiz.getName());
        this.contentQuiz.getChildren().clear();
        this.quiz = quiz;
        // for (Question question : quiz.getQuestions()) {
        // FXMLLoader fxmlLoader = App.lFXML("components/quiz");
        // VBox form = fxmlLoader.load();
        // QuizController controller = fxmlLoader.getController();
        // controller.setQuestion(question);
        // contentQuiz.getChildren().add(form);
        // }
    }

}
