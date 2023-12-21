package com.quiz.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import com.quiz.App;
import com.quiz.model.data.Answer;
import com.quiz.model.data.Question;
import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    @FXML
    private Label noHistory;

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
        if (list.size() == 0) {
            noHistory.setVisible(true);
            noHistory.setText("No History");
            historyQuiz.setVisible(false);
            return;
        }
        for (Answer a : list) {
            if (list.indexOf(a) == 0) {
                FXMLLoader fxmlLoader = App.lFXML("components/historyCard");
                HBox root = fxmlLoader.load();
                HistoryCard controller = fxmlLoader.getController();
                controller.setTitle();
                historyQuiz.getItems().add(root);
            }
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
            noHistory.setVisible(false);
        } else {
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
            historyQuiz.setVisible(false);
            historyLabel.setVisible(false);
            noHistory.setVisible(false);
        }
    }

    @FXML
    public void handleDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete this quiz?");
        alert.setHeaderText("You are about to delete this quiz.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isDeleted = App.getServer().deleteQuiz(quiz.getId());
                if (isDeleted) {
                    App.showDialog("Success", "Quiz deleted successfully.");
                    App.returnHome();
                } else {
                    App.showDialog("Error", "Failed to delete the quiz.");
                }
            } catch (Exception e) {
                App.showDialog("Exception", "An error occurred while deleting the quiz: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void handleUpdate() throws Exception {
        FXMLLoader fxmlLoader = App.lFXML("components/edit");
        VBox root = fxmlLoader.load();
        Edit controller = fxmlLoader.getController();
        controller.setQuiz(quiz);
        controller.setData(App.getServer().getQuestionsByQuizId(quiz.getId()));
        setContent(root);
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
