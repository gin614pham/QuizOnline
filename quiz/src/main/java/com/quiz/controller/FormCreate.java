package com.quiz.controller;

import com.quiz.model.data.Option;
import com.quiz.model.data.Question;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class FormCreate {
    @FXML
    private ToggleGroup A;

    @FXML
    private TextField answer1;

    @FXML
    private TextField answer2;

    @FXML
    private TextField answer3;

    @FXML
    private TextField answer4;

    @FXML
    private TextArea quizInput;

    @FXML
    private Label labNum;

    public Question handleSubmit() {
        // Get text from quizInput TextArea
        String questionText = quizInput.getText().trim();

        // get index of selected radio button
        int selectedIndex = A.getToggles().indexOf(A.getSelectedToggle());
        // Get the selected radio button from the ToggleGroup
        ArrayList<Option> answers = new ArrayList<>();
        answers.add(new Option(-1, answer1.getText().trim()));
        answers.add(new Option(-1, answer2.getText().trim()));
        answers.add(new Option(-1, answer3.getText().trim()));
        answers.add(new Option(-1, answer4.getText().trim()));

        // Create a new Question object
        Question question = new Question(-1, -1, questionText, answers, selectedIndex);

        return question;
    }

    public boolean isQuestionEmpty() {
        if (quizInput.getText().trim().isEmpty()) {
            return true;
        }
        if (answer1.getText().trim().isEmpty()) {
            return true;
        }
        if (answer2.getText().trim().isEmpty()) {
            return true;
        }
        if (answer3.getText().trim().isEmpty()) {
            return true;
        }
        if (answer4.getText().trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public void setNum(int num) {
        labNum.setText("Question " + num);
    }

    public void setQuestion(Question question) {
        quizInput.setText(question.getQuestion());
        answer1.setText(question.getAnswers().get(0).getContent());
        answer2.setText(question.getAnswers().get(1).getContent());
        answer3.setText(question.getAnswers().get(2).getContent());
        answer4.setText(question.getAnswers().get(3).getContent());
        for (Option o : question.getAnswers()) {
            if (o.getId() == question.getCorrectAnswer()) {
                A.getToggles().get(question.getAnswers().indexOf(o)).setSelected(true);
            }
        }
    }
}
