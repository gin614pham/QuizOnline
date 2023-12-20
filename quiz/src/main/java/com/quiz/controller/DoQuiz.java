package com.quiz.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.quiz.App;
import com.quiz.model.data.AnswerQuiz;
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
    private void submit() throws Exception {
        System.out.println("submit");
        ArrayList<AnswerQuiz> answers = new ArrayList<>();
        for (QuizController controller : list) {
            answers.add(controller.getAnswer());
        }
        for (AnswerQuiz answer : answers) {
            System.out.println(answer.getIdQuestion() + " " + answer.getSelectedAnswer());
        }
        // App.getServer().doQuiz(App.getUser().getId(), quiz.getId(), answers);
    }

    private Quiz quiz;
    private ArrayList<QuizController> list = new ArrayList<>();

    public void setQuiz(Quiz quiz, ArrayList<Question> questions) throws IOException {
        this.idQuiz.setText(String.valueOf(quiz.getId()));
        this.titleQuiz.setText(quiz.getName());
        this.contentQuiz.getChildren().clear();
        this.quiz = quiz;
        for (Question question : questions) {
            FXMLLoader fxmlLoader = App.lFXML("components/quiz");
            VBox form = fxmlLoader.load();
            QuizController controller = fxmlLoader.getController();
            controller.setQuestion(question);
            contentQuiz.getChildren().add(form);
            list.add(controller);
        }
    }

}
