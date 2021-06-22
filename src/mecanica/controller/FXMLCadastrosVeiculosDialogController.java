package mecanica.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mecanica.model.domain.Veiculo;
import java.util.regex.Pattern;

public class FXMLCadastrosVeiculosDialogController implements Initializable {
    @FXML
    private TextField textFieldPlaca;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldMarca;
    @FXML
    private TextField textFieldModelo;
    @FXML
    private TextField textFieldCliente;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Veiculo veiculo;
    
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
    
    public Veiculo getVeiculo(){
        return veiculo;
    }
    
    public void setVeiculo(Veiculo veiculo){
        this.veiculo = veiculo;
        
        if (veiculo != null){
            textFieldPlaca.setText(veiculo.getPlaca());
            textFieldNome.setText(veiculo.getNome());
            textFieldMarca.setText(veiculo.getMarca());
            textFieldModelo.setText(veiculo.getModelo());
            textFieldCliente.setText(veiculo.getCliente());
        }
    }
    
    @FXML
    public void handleButtonConfirmar(){
        if (validarEntradaDeDados()){
            veiculo.setNome(textFieldNome.getText());
            veiculo.setMarca(textFieldMarca.getText());
            veiculo.setPlaca(textFieldPlaca.getText());
            veiculo.setModelo(textFieldModelo.getText());
            veiculo.setCliente(textFieldCliente.getText());
            
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
        
        if (textFieldMarca.getText() == null || textFieldMarca.getText().length() == 0){
            errorMessage += "Marca inválida\n";
        }
        
        if (textFieldPlaca.getText() == null || textFieldPlaca.getText().length() == 0 || textFieldPlaca.getText().length() > 7){
            errorMessage += "Placa inválido\n";
        }
        
        if (textFieldModelo.getText() == null || textFieldModelo.getText().length() == 0){
            errorMessage += "Modelo inválido\n";
        }
        
        if (textFieldCliente.getText() == null || textFieldCliente.getText().length() == 0){
            errorMessage += "Cliente inválido\n";
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
