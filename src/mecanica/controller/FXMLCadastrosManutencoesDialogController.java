package mecanica.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mecanica.model.domain.Manutencao;
import mecanica.model.domain.Veiculo;

public class FXMLCadastrosManutencoesDialogController implements Initializable {

    @FXML
    private TextField textFieldCdManutencao;

    @FXML
    private TextField textFieldDescricao;

    @FXML
    private TextField textFieldCdVeiculo;

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonRemover;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Manutencao manutencao;

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

    public Manutencao getManutencao() {
        return manutencao;
    }

    public void setManutencao(Manutencao manutencao) {
        this.manutencao = manutencao;

        if (manutencao != null) {
            textFieldCdManutencao.setText(Integer.toString(manutencao.getCdManutencao()));
            textFieldDescricao.setText(manutencao.getDescricao());
            textFieldCdVeiculo.setText(manutencao.getVeiculo(veiculo));
        }
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            manutencao.setCdManutencao(Integer.parseInt(textFieldCdManutencao.getText()));
            manutencao.setDescricao(textFieldDescricao.getText());
            manutencao.setVeiculo(textFieldCdVeiculo.getText(toString(veiculo)));

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

        if (textFieldCdManutencao.getText() == null || textFieldCdManutencao.getText().length() == 0 || !Pattern.matches("[0-9]+", textFieldCdManutencao.getText())) {
            errorMessage += "Código de Manutenção inválido\n";
        }

        if (textFieldDescricao.getText() == null || textFieldDescricao.getText().length() == 0) {
            errorMessage += "Descrição inválida\n";
        }

        if (textFieldCdVeiculo.getText() == null || textFieldCdVeiculo.getText().length() == 0 || textFieldCdVeiculo.getText().length() > 7) {
            errorMessage += "Código de Veículo inválido\n";
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
