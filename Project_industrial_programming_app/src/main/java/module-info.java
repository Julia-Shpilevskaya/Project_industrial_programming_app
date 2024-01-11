module com.example.project_industrial_programming_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires json.simple;


    opens com.example.project_industrial_programming_app to javafx.fxml;
    exports com.example.project_industrial_programming_app;
}