package mecanica.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mecanica.model.dao.ModeloVeiculoDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.ModeloVeiculo;

public class FXMLCadastrosModelosVeiculosController implements Initializable {

    @FXML
    private TableColumn<ModeloVeiculo, Integer> tableColumnCdModelo;
    @FXML
    private TableView<ModeloVeiculo> tableViewModeloVeiculo;
    @FXML
    private TableColumn<ModeloVeiculo, Boolean> tableColumnMoto;
    @FXML
    private TableColumn<ModeloVeiculo, String> tableColumnNome;
    @FXML
    private TableColumn<ModeloVeiculo, String> tableColumnDescricao;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;

    private List<ModeloVeiculo> listModeloVeiculos;
    private ObservableList<ModeloVeiculo> observableListModeloVeiculos;

    // Manipulação de banco de dados
    private final PostgreSQL postgresql = new PostgreSQL();
    private final Connection connection = postgresql.conectar();
    private ModeloVeiculoDAO ModeloVeiculoDao = new ModeloVeiculoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ModeloVeiculoDao.setConnection(connection);
        carregarTableViewModeloVeiculo();

        // Listener acionado quando alterações ocorrem no tableview
//        tableViewModeloVeiculo.getSelectionModel().selectedItemProperty().addListener(
//        (observable, oldValue, newValue) -> selecionarTableViewModeloVeiculos(newValue));
    }

    public void carregarTableViewModeloVeiculo() {
        tableColumnCdModelo.setCellValueFactory(new PropertyValueFactory<>("cdModeloVeiculo"));
        tableColumnMoto.setCellValueFactory(new PropertyValueFactory<>("moto"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        listModeloVeiculos = ModeloVeiculoDao.listar();

        observableListModeloVeiculos = FXCollections.observableArrayList(listModeloVeiculos);
        tableViewModeloVeiculo.setItems(observableListModeloVeiculos);
    }

    public boolean showCadastrosModeloVeiculosDialog(ModeloVeiculo modeloVeiculo) throws IOException {
        // Carrega o fxml ModeloVeiculosDialog
        FXMLLoader loader = new FXMLLoader();
        String url = "/mecanica/view/FXMLCadastrosModeloVeiculosDialog.fxml";
        loader.setLocation(FXMLCadastrosModeloVeiculosDialogController.class.getResource(url));
        AnchorPane page = (AnchorPane) loader.load();

        // Cria uma cena com ModeloVeiculosDialog
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastros dos modelos dos Veiculos");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Define o dialogStage e o modelo do veiculo
        FXMLCadastrosModeloVeiculosDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setModeloVeiculo(modeloVeiculo);

        // Mostra o VeiculosDialog e espera
        dialogStage.showAndWait();

        // Retorna true se o botao confirmar for clicado
        return controller.isButtonConfirmarClicked();
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        ModeloVeiculo modeloVeiculo = new ModeloVeiculo();

        // Obtem verdadeiro se o veiculo for inserido
        boolean buttonConfirmarClicked = showCadastrosModeloVeiculosDialog(modeloVeiculo);
        if (buttonConfirmarClicked) {
            // Insere o modelo do veiculo no banco de dados
            ModeloVeiculoDao.inserir(modeloVeiculo);
            // Recarrega os dados do modelo do veiculo
            carregarTableViewModeloVeiculo();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        ModeloVeiculo modeloVeiculo = tableViewModeloVeiculo.getSelectionModel().getSelectedItem();

        if (modeloVeiculo != null) {
            boolean buttonConfirmarClicked = showCadastrosModeloVeiculosDialog(modeloVeiculo);

            if (buttonConfirmarClicked) {
                ModeloVeiculoDao.alterar(modeloVeiculo);
                carregarTableViewModeloVeiculo();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um modelo ...");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        ModeloVeiculo modeloVeiculo = tableViewModeloVeiculo.getSelectionModel().getSelectedItem();

        if (modeloVeiculo != null) {
            ModeloVeiculoDao.remover(modeloVeiculo);
            carregarTableViewModeloVeiculo();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um modelo ...");
            alert.show();
        }
    }

}
