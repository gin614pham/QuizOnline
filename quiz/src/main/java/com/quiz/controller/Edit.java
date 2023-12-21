package com.quiz.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import com.quiz.App;
import com.quiz.model.data.Question;
import com.quiz.model.data.Quiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Edit {

    @FXML
    private Button addQuiz;

    @FXML
    private Button btnCreate;

    @FXML
    private VBox contentCreate;

    @FXML
    private Label userLabel;

    private ArrayList<FormCreate> list = new ArrayList<>();

    private Quiz quiz;

    @FXML
    void addForm(ActionEvent event) throws IOException {
        System.out.println("add form");
        FXMLLoader fxmlLoader = App.lFXML("components/formCreate");
        VBox form = fxmlLoader.load();
        FormCreate controller = fxmlLoader.getController();
        list.add(controller);
        controller.setNum(list.size());
        contentCreate.getChildren().add(form);
    }

    @FXML
    void create(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog(quiz.getName());
        dialog.setTitle("Edit Quiz");
        dialog.setHeaderText("Enter the new name for your quiz:");
        dialog.setContentText("Name:");
        // set image of dialog
        dialog.setGraphic(new ImageView(App.class.getResource("public/icon/icons8-dog-tag-50.png").toExternalForm()));

        // Traditional way to get the response value
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            quiz.setName(name);
            quiz.setIdAuthor(App.getUser().getId());
            try {
                if (App.getServer().updateQuiz(quiz, getAllForms())) {
                    App.showDialog("Success", "Quiz edited successfully");
                    App.returnHome();
                    list.clear();
                } else {
                    App.showDialog("Error", "Failed to edit quiz");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setData(ArrayList<Question> questions) throws IOException {
        userLabel.setText("Edit quiz: " + quiz.getName());
        for (Question question : questions) {
            FXMLLoader fxmlLoader = App.lFXML("components/formCreate");
            VBox form = fxmlLoader.load();
            FormCreate controller = fxmlLoader.getController();
            list.add(controller);
            controller.setNum(list.size());
            controller.setQuestion(question);
            contentCreate.getChildren().add(form);
        }
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    private ArrayList<Question> getAllForms() {
        ArrayList<Question> list = new ArrayList<>();
        for (FormCreate form : this.list) {
            if (form.isQuestionEmpty()) {
                continue;
            }
            list.add(form.handleSubmit());
        }
        return list;
    }

}
