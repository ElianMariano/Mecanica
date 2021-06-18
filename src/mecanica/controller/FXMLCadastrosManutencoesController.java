package mecanica.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mecanica.model.dao.ManutencaoDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.Manutencao;

public class FXMLCadastrosManutencoesController implements Initializable {

    @FXML
    private TableView<Manutencao> tableViewManutencao;

    @FXML
    private TableColumn<Manutencao, Integer> tableColumnCdManutencao;

    @FXML
    private TableColumn<Manutencao, String> tableColumnDescricao;

    @FXML
    private TableColumn<Manutencao, String> tableColumnCdVeiculo;

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonRemover;

    private List<Manutencao> listManutencoes;
    private ObservableList<Manutencao> observableListManutencoes;

    // Manipulação de banco de dados
    private final PostgreSQL postgresql = new PostgreSQL();
    private final Connection connection = postgresql.conectar();
    private ManutencaoDAO manutencaoDao = new ManutencaoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manutencaoDao.setConnection(connection);
        carregarTableViewManutencao();

        // Listener acionado quando alterações ocorrem no tableview
//         tableViewManutencao.getSelectionModel().selectedItemProperty().addListener(
//         (observable, oldValue, newValue) -> selecionarTableViewManutencoes(newValue));
    }

    public void carregarTableViewManutencao() {
        tableColumnCdManutencao.setCellValueFactory(new PropertyValueFactory<>("cod_manutencao"));
        tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tableColumnCdVeiculo.setCellValueFactory(new PropertyValueFactory<>("cod_veiculo"));

        listManutencoes = manutencaoDao.listar();

        observableListManutencoes = FXCollections.observableArrayList(listManutencoes);
        tableViewManutencao.setItems(observableListManutencoes);
    }

    public boolean showCadastrosManutencoesDialog(Manutencao manutencao) throws IOException {
        // Carrega o fxml ManutencoesDialog
        FXMLLoader loader = new FXMLLoader();
        String url = "/mecanica/view/FXMLCadastrosManutencoesDialog.fxml";
        loader.setLocation(FXMLCadastrosManutencoesDialogController.class.getResource(url));
        AnchorPane page = (AnchorPane) loader.load();

        // Cria uma cena com ManutencoesDialog
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastros das Manutenções");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Define o dialogStage e o manutencao
        FXMLCadastrosManutencoesDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setManutencao(manutencao);

        // Mostra o ManutencoesDialog e espera
        dialogStage.showAndWait();

        // Retorna true se o botao confirmar for clicado
        return controller.isButtonConfirmarClicked();
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Manutencao manutencao = new Manutencao();

        // Obtem verdadeiro se a manutenção for inserida
        boolean buttonConfirmarClicked = showCadastrosManutencoesDialog(manutencao);
        if (buttonConfirmarClicked) {
            // Insere a manutenção no banco de dados
            manutencaoDao.inserir(manutencao);
            // Recarrega os dados da manutencao
            carregarTableViewManutencao();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Manutencao manutencao = tableViewManutencao.getSelectionModel().getSelectedItem();

        if (manutencao != null) {
            boolean buttonConfirmarClicked = showCadastrosManutencoesDialog(manutencao);

            if (buttonConfirmarClicked) {
                manutencaoDao.alterar(manutencao);
                carregarTableViewManutencao();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione uma manutençao ...");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Manutencao manutencao = tableViewManutencao.getSelectionModel().getSelectedItem();

        if (manutencao != null) {
            manutencaoDao.remover(manutencao);
            carregarTableViewManutencao();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione uma manutenção ...");
            alert.show();
        }
    }
}
