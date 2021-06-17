package mecanica.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * @author jones
 */
public class FXMLMainControllerVeiculos implements Initializable{
    @FXML
    private AnchorPane anchorPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    public void handleCadastrosVeiculos() throws IOException{
        String url = "/mecanica/view/FXMLCadastrosVeiculos.fxml";
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource(url));
        anchorPane.getChildren().setAll(a);
    }
}
