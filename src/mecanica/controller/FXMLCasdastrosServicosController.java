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
import javafx.scene.control.Label;
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
    private TableView<Servicos> tableViewServico;
    @FXML
    private TableColumn<Servicos, String> tableColumnNome;
    @FXML
    private TableColumn<Servicos, Integer> tableColumnCodigo;
    @FXML
    private Label labelCodigo;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelPreco;
    @FXML
    private Label labelDescricao;
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
        tableViewServico.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> selecionarTableViewServico(newValue));
    }
    
    public void selecionarTableViewServico(Servicos servico){
        if (servico != null){
            labelCodigo.setText(String.valueOf(servico.getCodigo()));
            labelNome.setText(servico.getNome());
            labelPreco.setText(String.format("R$ %.2f", servico.getPreco()));
            String descricao = String.format("Descrição: %s",
                    servico.getDescricao());
            labelDescricao.setText(Utils.quebrarLinha(descricao, 7));
        }
        else{
            labelCodigo.setText("");
            labelNome.setText("");
            labelPreco.setText("");
            labelDescricao.setText("Descrição");
        }
    }

    public void carregarTableViewServico() {
        tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        listServicos = ServicoDao.listar();

        observableListServicos = FXCollections.observableArrayList(listServicos);
        tableViewServico.setItems(observableListServicos);
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
            boolean resultado = ServicoDao.inserir(Servicos);
            if (!resultado){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Por favor, insira um código disponível!");
                alert.show();
            }
            // Recarrega os dados dos Serviços
            carregarTableViewServico();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Servicos Servicos = tableViewServico.getSelectionModel().getSelectedItem();

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
        Servicos Servicos = tableViewServico.getSelectionModel().getSelectedItem();

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
