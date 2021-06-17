/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mecanica.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mecanica.model.domain.ModeloVeiculo;
import mecanica.model.domain.Veiculo;

/**
 * FXML Controller class
 *
 * @author Andre
 */
public class FXMLCadastrosModeloVeiculosDialogController implements Initializable {
    
    @FXML
    private TextField textFieldCdModeloVeiculo;
    @FXML
    private TextField textFieldMoto;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldDescricao;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private ModeloVeiculo modeloVeiculo;
    
    public Stage getDialogStage(){
        return dialogStage;
    }
    
    public void setDialogStage(Stage stage){
        dialogStage = stage;
    }
    
    public boolean isButtonConfirmarClicked(){
        return buttonConfirmarClicked;
    }
    
    public void setButtonConfirmarClicked(boolean buttonConfirmar){
        buttonConfirmarClicked = buttonConfirmar;
    }
    
    public ModeloVeiculo getModeloVeiculo(){
        return modeloVeiculo;
    }
    
    public void setModeloVeiculo(ModeloVeiculo modeloVeiculo){
        this.modeloVeiculo = modeloVeiculo;
        
        if (modeloVeiculo != null){
            textFieldCdModeloVeiculo.setText(Integer.toString(modeloVeiculo.getCdModeloVeiculo()));
            textFieldMoto.setText(Boolean.toString(modeloVeiculo.getMoto()));
            textFieldNome.setText(modeloVeiculo.getNome());
            textFieldDescricao.setText(modeloVeiculo.getDescricao());
            
        }
    }
    
    @FXML
    public void handleButtonConfirmar(){
        if (validarEntradaDeDados()){
            modeloVeiculo.setNome(textFieldNome.getText());
            modeloVeiculo.setMoto(Boolean.getBoolean(textFieldMoto.getText()));
            modeloVeiculo.setDescricao(textFieldDescricao.getText());
            modeloVeiculo.setCdModeloVeiculo(Integer.parseInt(textFieldCdModeloVeiculo.getText()));

            
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleButtonCancelar(){
        dialogStage.close();
    }
    
    // Valida a entrada de dados
    public boolean validarEntradaDeDados(){
        String errorMessage = "";
        
        if (textFieldNome.getText() == null || textFieldNome.getText().length() == 0){
            errorMessage += "Nome inválido\n";
        }
        
        if (textFieldMoto.getText() == null || textFieldMoto.getText().length() == 0){
            errorMessage += "Moto inválida\n";
        }
        
        if (textFieldDescricao.getText() == null || textFieldDescricao.getText().length() == 0){
            errorMessage += "Descrição inválido\n";
        }
        
        if (textFieldCdModeloVeiculo.getText() == null || textFieldCdModeloVeiculo.getText().length() == 0){
            errorMessage += "Codigo de Modelo do veiculo inválido\n";
        }
        
        if (errorMessage.equals("")){
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Cadastro");
            alert.setHeaderText("Campos Inválidos, corrija ...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
