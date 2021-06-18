package mecanica.controller;

import java.io.IOException;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        String dir = "../view/FXMLMain.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(dir));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Sistema de Mecânica de Carros");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
