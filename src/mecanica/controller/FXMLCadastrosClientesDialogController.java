package mecanica.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mecanica.model.domain.Cliente;
import java.util.regex.Pattern;

public class FXMLCadastrosClientesDialogController implements Initializable {

    @FXML
    private TextField textFieldCpf;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldNascimento;
    @FXML
    private TextField textFieldCidade;
    @FXML
    private TextField textFieldUf;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Cliente cliente;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;

        if (cliente != null && cliente.getCpf() != null) {
            textFieldCpf.setText(cliente.getCpf());
            textFieldNome.setText(cliente.getNome());
            textFieldNascimento.setText(cliente.getNascimento());
            textFieldCidade.setText(cliente.getCidade());
            textFieldUf.setText(cliente.getUf());
            
            textFieldCpf.setEditable(false);
        }
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            cliente.setNome(textFieldNome.getText());
            cliente.setNascimento(textFieldNascimento.getText());
            cliente.setCpf(textFieldCpf.getText());
            cliente.setCidade(textFieldCidade.getText());
            cliente.setUf(textFieldUf.getText());

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

        if (textFieldNome.getText() == null ||
                textFieldNome.getText().length() == 0 ||
                textFieldNome.getText().length() >= 100) {
            errorMessage += "Nome inválido\n";
        }

        if (textFieldNascimento.getText() == null ||
                textFieldNascimento.getText().length() == 0
                || !Pattern.matches("((\\d){4}\\-(\\d){2}\\-(\\d){2})", textFieldNascimento.getText())) {
            errorMessage += "Data de Nascimento inválida\n";
        }

        if (textFieldCpf.getText() == null || textFieldCpf.getText().length() == 0
                || !Pattern.matches("((\\d){3}\\.(\\d){3}\\.(\\d){3}\\-(\\d){2})", textFieldCpf.getText())) {
            errorMessage += "CPF inválido\n";
        }

        if (textFieldCidade.getText() == null ||
                textFieldCidade.getText().length() == 0 ||
                textFieldCidade.getText().length() >= 100) {
            errorMessage += "Campo de cidade inválido\n";
        }

        if (textFieldUf.getText() == null || textFieldUf.getText().length() == 0 
                || textFieldUf.getText().length() != 2) {
            errorMessage += "Campo UF inválido\n";
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
