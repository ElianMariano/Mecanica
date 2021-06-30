package mecanica.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mecanica.model.domain.Servicos;

public class FXMLCadastrosServicosDialogController implements Initializable {

    @FXML
    private TextField textFieldCodigo;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldDescricao;
    @FXML
    private TextField textFieldPreco;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Servicos servico;

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

    public Servicos getservico() {
        return servico;
    }

    public void setServicos(Servicos servico) {
        this.servico = servico;

        if (servico != null && servico.getNome() != null) {
            textFieldCodigo.setText(String.valueOf(servico.getCodigo()));
            textFieldNome.setText((servico.getNome()));
            textFieldDescricao.setText(servico.getDescricao());
            textFieldPreco.setText(Double.toString(servico.getPreco()));
            
            textFieldCodigo.setEditable(false);
        }
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            servico.setNome(textFieldNome.getText());
            servico.setCodigo(Integer.valueOf(textFieldCodigo.getText()));
            servico.setDescricao(textFieldDescricao.getText());
            servico.setPreco(Double.parseDouble(textFieldPreco.getText()));

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

        if (textFieldNome.getText() == null || textFieldNome.getText().length() == 0
                || textFieldNome.getText().length() > 100) {
            errorMessage += "Nome inválido\n";
        }

        if (textFieldCodigo.getText() == null || textFieldCodigo.getText().length() == 0
                || !Utils.eInteiro(textFieldCodigo.getText())) {
            errorMessage += "Código inválido\n";
        }

        if (textFieldDescricao.getText() == null || textFieldDescricao.getText().length() == 0
                || textFieldDescricao.getText().length() > 500) {
            errorMessage += "Descrição inválida\n";
        }

        if (textFieldPreco.getText() == null || textFieldPreco.getText().length() == 0
                || !Utils.eDouble(textFieldPreco.getText())) {
            errorMessage += "Preço Inválido\n";
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
