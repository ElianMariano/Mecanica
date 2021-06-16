/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mecanica.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author elian
 */
public class FXMLMainController implements Initializable{
    @FXML
    private AnchorPane anchorPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    public void handleCadastrosClientes() throws IOException{
        String url = "/mecanica/view/FXMLCadastrosClientes.fxml";
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource(url));
        anchorPane.getChildren().setAll(a);
    }
}
