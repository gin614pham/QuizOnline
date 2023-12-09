package com.quiz;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        Parent root = App.loadFXML("primary");
        App.setRoot(root);
    }
}