package com.quiz.controller;

import java.util.Optional;
import java.util.ArrayList;

import com.quiz.App;
import com.quiz.model.data.Question;
import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Create {

    @FXML
    private Button btnCreate;

    @FXML
    private VBox contentCreate;

    @FXML
    private Label userLabel;

    @FXML
    private Button addQuiz;

    private ArrayList<FormCreate> list = new ArrayList<>();

    @FXML
    public void create() throws Exception {
        System.out.println("create");
        // Create a TextInputDialog for user to input a quiz name
        TextInputDialog dialog = new TextInputDialog("Quiz Name");
        dialog.setTitle("Create Quiz");
        dialog.setHeaderText("Enter the name for your new quiz:");
        dialog.setContentText("Name:");
        // set image of dialog
        dialog.setGraphic(new ImageView(App.class.getResource("public/icon/icons8-dog-tag-50.png").toExternalForm()));

        // Traditional way to get the response value
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            System.out.println("Quiz name: " + name);
            Quiz quiz = new Quiz();
            quiz.setName(name);
            quiz.setIdAuthor(App.getUser().getId());
            try {
                if (App.getServer().addQuiz(quiz, getAllForms())) {
                    // show success dialog
                    App.showDialog("Success", "Quiz created successfully");
                    App.returnHome();
                    list.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void addForm() throws Exception {
        System.out.println("add form");
        FXMLLoader fxmlLoader = App.lFXML("components/formCreate");
        VBox form = fxmlLoader.load();
        FormCreate controller = fxmlLoader.getController();
        list.add(controller);
        controller.setNum(list.size());
        contentCreate.getChildren().add(form);
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
