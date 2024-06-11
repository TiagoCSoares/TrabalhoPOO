module org.example.trabalho {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.trabalho to javafx.fxml;
    exports org.example.trabalho;
}