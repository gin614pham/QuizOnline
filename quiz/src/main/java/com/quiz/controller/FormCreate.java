package com.quiz.controller;

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
        ArrayList<String> answers = new ArrayList<>();
        answers.add(answer1.getText().trim());
        answers.add(answer2.getText().trim());
        answers.add(answer3.getText().trim());
        answers.add(answer4.getText().trim());

        System.out.println("Question: " + questionText + "\nAnswer: " + selectedIndex);
        System.out.println("Answer 1: " + answers.get(0));
        System.out.println("Answer 2: " + answers.get(1));
        System.out.println("Answer 3: " + answers.get(2));
        System.out.println("Answer 4: " + answers.get(3));

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
}
