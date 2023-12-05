module com.quiz {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.quiz to javafx.fxml;
    opens com.quiz.controller to javafx.fxml;

    exports com.quiz;

    requires java.rmi;
    requires atlantafx.base;

    exports com.quiz.model to java.rmi;
}
