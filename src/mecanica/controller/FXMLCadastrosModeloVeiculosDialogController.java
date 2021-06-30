package mecanica.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mecanica.model.domain.ModeloVeiculo;

public class FXMLCadastrosModeloVeiculosDialogController implements Initializable {

    @FXML
    private TextField textFieldCodigo;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldDescricao;
    @FXML
    private CheckBox checkBoxMoto;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private ModeloVeiculo modeloVeiculo;

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage stage) {
        dialogStage = stage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmar) {
        buttonConfirmarClicked = buttonConfirmar;
    }

    public ModeloVeiculo getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(ModeloVeiculo modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;

        if (modeloVeiculo != null && modeloVeiculo.getNome() != null) {
            textFieldCodigo.setText(String.valueOf(modeloVeiculo.getCodigo()));
            textFieldNome.setText(modeloVeiculo.getNome());
            textFieldDescricao.setText(modeloVeiculo.getDescricao());
            checkBoxMoto.setSelected(modeloVeiculo.getMoto());
            
            textFieldCodigo.setEditable(false);
        }
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            modeloVeiculo.setCodigo(Integer.valueOf(textFieldCodigo.getText()));
            modeloVeiculo.setNome(textFieldNome.getText());
            modeloVeiculo.setMoto(checkBoxMoto.isSelected());
            modeloVeiculo.setDescricao(textFieldDescricao.getText());

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }

    // Valida a entrada de dados
    public boolean validarEntradaDeDados() {
        String errorMessage = "";
        
        if (textFieldCodigo.getText() == null || textFieldNome.getText().length() == 0 ||
                !Utils.eInteiro(textFieldCodigo.getText())) {
            errorMessage += "Código inválido\n";
        }

        if (textFieldNome.getText() == null || textFieldNome.getText().length() == 0
                || textFieldNome.getText().length() > 100) {
            errorMessage += "Nome inválido\n";
        }

        if (textFieldDescricao.getText() == null || textFieldDescricao.getText().length() == 0
                || textFieldDescricao.getText().length() > 500) {
            errorMessage += "Descrição inválido\n";
        }

        if (errorMessage.equals("")) {
            return true;
        } else {
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
