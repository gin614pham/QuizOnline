package com.quiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.Naming;

import com.quiz.controller.Client;
import com.quiz.model.ClientImp;
import com.quiz.model.ServerImp;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        try {
            ServerImp server = (ServerImp) Naming.lookup("//localhost/Quiz");
            ClientImp client = new Client();
            server.registerClient(client);
            Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
            Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
            scene = new Scene(loadFXML("screen/auth/login"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Error");
            alert.setHeaderText(null);
            alert.setContentText("Cannot connect to server:" + e.getMessage());

            alert.showAndWait();

        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}