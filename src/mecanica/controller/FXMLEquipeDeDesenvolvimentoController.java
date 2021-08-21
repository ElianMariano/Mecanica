/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mecanica.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mecanica.controller.sockets.Integrantes;

public class FXMLEquipeDeDesenvolvimentoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<String> dados = new ArrayList<>();

        try {
            dados = Integrantes.obterIntegrantes();
        } catch (IOException ex) {
            Logger.getLogger(FXMLEquipeDeDesenvolvimentoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLEquipeDeDesenvolvimentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    
    
}
