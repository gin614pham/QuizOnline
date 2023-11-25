module com.quiz {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.quiz to javafx.fxml;

    exports com.quiz;

    requires java.rmi;

    exports com.quiz.model to java.rmi;
}
