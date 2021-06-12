package mecanica.controller;

import java.io.IOException;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
