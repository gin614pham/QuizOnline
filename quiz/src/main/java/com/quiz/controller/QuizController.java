package com.quiz.controller;

import java.util.ArrayList;
import java.util.Collections;

import com.quiz.model.data.AnswerQuiz;
import com.quiz.model.data.Option;
import com.quiz.model.data.Question;
import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

public class QuizController {

    @FXML
    private ToggleGroup B;

    @FXML
    private Label answerQ1;

    @FXML
    private Label answerQ2;

    @FXML
    private Label answerQ3;

    @FXML
    private Label answerQ4;

    @FXML
    private Label numQ;

    @FXML
    private Label idQuestion;

    @FXML
    private Label quiz;

    private Question question;

    public void setQuestion(Question question) {
        this.numQ.setText(String.valueOf(question.getId()));
        this.quiz.setText(question.getQuestion());
        this.idQuestion.setText(String.valueOf(question.getQuizId()));
        ArrayList<Option> answers = question.getAnswers();
        Collections.shuffle(answers);
        question.setAnswers(answers);
        this.answerQ1.setText(question.getAnswers().get(0).getContent());
        this.answerQ2.setText(question.getAnswers().get(1).getContent());
        this.answerQ3.setText(question.getAnswers().get(2).getContent());
        this.answerQ4.setText(question.getAnswers().get(3).getContent());
        this.question = question;
    }

    public AnswerQuiz getAnswer() {
        AnswerQuiz answer = new AnswerQuiz();
        answer.setIdQuestion(question.getId());
        int selectedIndex = B.getToggles().indexOf(B.getSelectedToggle());
        answer.setSelectedAnswer(selectedIndex != -1 ? question.getAnswers().get(selectedIndex).getId() : -1);
        return answer;
    }

}
