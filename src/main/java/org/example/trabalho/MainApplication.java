package org.example.trabalho;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.trabalho.database.DatabaseUtil;

import java.io.IOException;


public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            DatabaseUtil.createDatabase();
            try {
                DatabaseUtil.createTableIfNotExists();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("productManagement.fxml"));
            ScrollPane scrollPane = fxmlLoader.load();

            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);

            Scene scene = new Scene(scrollPane);
            stage.setTitle("Product Management!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
