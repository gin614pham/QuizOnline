package com.quiz.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.quiz.App;
import com.quiz.model.data.Answer;
import com.quiz.model.data.Question;
import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private ListView<HBox> historyQuiz;

    @FXML
    private Label idAuthor;

    @FXML
    private Label nameQuiz;

    @FXML
    private Label numQuiz;

    @FXML
    private Label historyLabel;

    private Quiz quiz;

    public void setQuiz(Quiz quiz) throws Exception {
        IdQuiz.setText("ID: " + String.valueOf(quiz.getId()));
        nameQuiz.setText(quiz.getName());
        numQuiz.setText("Questions: " + String.valueOf(quiz.getNumQuestions()));
        authorQuiz.setText("Author: " + quiz.getAuthor());
        idAuthor.setText("ID Author: " + String.valueOf(quiz.getIdAuthor()));
        dateCreate.setText("Date created: " + quiz.getDateCreated());
        this.quiz = quiz;
        setContent();
        setHistory(App.getServer().getHistoryByQuizId(quiz.getId()));
    }

    public void setHistory(ArrayList<Answer> list) throws IOException {
        for (Answer a : list) {
            FXMLLoader fxmlLoader = App.lFXML("components/historyCard");
            HBox root = fxmlLoader.load();
            HistoryCard controller = fxmlLoader.getController();
            controller.setData(a);
            historyQuiz.getItems().add(root);
        }
    }

    private void setContent() throws IOException {
        if (App.getUser().getId() == quiz.getIdAuthor()) {
            btnUpdate.setVisible(true);
            btnDelete.setVisible(true);
            historyQuiz.setVisible(true);
            historyLabel.setVisible(true);
        } else {
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
            historyQuiz.setVisible(false);
            historyLabel.setVisible(false);
        }
    }

    @FXML
    public void handleDelete() {

    }

    @FXML
    public void handleUpdate() {

    }

    @FXML
    public void handleDoQuiz() throws Exception {
        ArrayList<Question> list = new ArrayList<>();
        list = App.getServer().getQuestionsByQuizId(quiz.getId());
        FXMLLoader fxmlLoader = App.lFXML("components/doQuiz");
        VBox root = fxmlLoader.load();
        DoQuiz controller = fxmlLoader.getController();
        controller.setQuiz(quiz, list);
        setContent(root);
    }

    private void setContent(VBox content) throws IOException {
        FXMLLoader homeController = App.lFXML("screen/app/home");
        Parent root = homeController.load();
        Home home = homeController.getController();
        VBox homeContentVBox = home.getContent();
        // clear content of home and add search
        homeContentVBox.getChildren().clear();
        homeContentVBox.getChildren().add(content);
        home.setMenu();
        App.setRoot(root);
    }

}
