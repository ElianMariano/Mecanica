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
import mecanica.model.dao.ServicoDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.Servicos;

public class FXMLCasdastrosServicosController implements Initializable {

    @FXML
    private TableColumn<Servicos, Integer> tableColumncdServico;
    @FXML
    private TableView<Servicos> tableViewServicos;
    @FXML
    private TableColumn<Servicos, String> tableColumnNome;
    @FXML
    private TableColumn<Servicos, String> tableColumnDescricao;
    @FXML
    private TableColumn<Servicos, Double> tableColumnPreco;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;

    private List<Servicos> listServicos;
    private ObservableList<Servicos> observableListServicos;

    // Manipulação de banco de dados
    private final PostgreSQL postgresql = new PostgreSQL();
    private final Connection connection = postgresql.conectar();
    private ServicoDAO ServicoDao = new ServicoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServicoDao.setConnection(connection);
        carregarTableViewServico();

        // Listener acionado quando alterações ocorrem no tableview
//        tableViewModeloVeiculo.getSelectionModel().selectedItemProperty().addListener(
//        (observable, oldValue, newValue) -> selecionarTableViewModeloVeiculos(newValue));
    }

    public void carregarTableViewServico() {
        tableColumncdServico.setCellValueFactory(new PropertyValueFactory<>("cod_servico"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        listServicos = ServicoDao.listar();

        observableListServicos = FXCollections.observableArrayList(listServicos);
        tableViewServicos.setItems(observableListServicos);
    }

    public boolean showCadastrosServicosDialog(Servicos Servicos) throws IOException {
        // Carrega o fxml ServicosDialog
        FXMLLoader loader = new FXMLLoader();
        String url = "/mecanica/view/FXMLCadastrosServicosDialog.fxml";
        loader.setLocation(FXMLCadastrosServicosDialogController.class.getResource(url));
        AnchorPane page = (AnchorPane) loader.load();

        // Cria uma cena com ServiosDialog
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Serviços Cadastrados");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Define o dialogStage e o Serviço
        FXMLCadastrosServicosDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setServicos(Servicos);

    
        dialogStage.showAndWait();

        // Retorna true se o botao confirmar for clicado
        return controller.isButtonConfirmarClicked();
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Servicos Servicos = new Servicos();

        // Obtem verdadeiro se o veiculo for inserido
        boolean buttonConfirmarClicked = showCadastrosServicosDialog(Servicos);
        if (buttonConfirmarClicked) {
            // Insere os Serviços no banco de dados
            ServicoDao.inserir(Servicos);
            // Recarrega os dados dos Serviços
            carregarTableViewServico();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Servicos Servicos = tableViewServicos.getSelectionModel().getSelectedItem();

        if (Servicos != null) {
            boolean buttonConfirmarClicked = showCadastrosServicosDialog(Servicos);

            if (buttonConfirmarClicked) {
                ServicoDao.alterar(Servicos);
                carregarTableViewServico();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selecione Por Favor");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Servicos Servicos = tableViewServicos.getSelectionModel().getSelectedItem();

        if (Servicos != null) {
            ServicoDao.remover(Servicos);
            carregarTableViewServico();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selecione Por Favor");
            alert.show();
        }
    }

}
