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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mecanica.model.dao.ClienteDAO;
import mecanica.model.dao.ModeloVeiculoDAO;
import mecanica.model.dao.VeiculoDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.Cliente;
import mecanica.model.domain.ModeloVeiculo;
import mecanica.model.domain.Veiculo;

public class FXMLCadastrosVeiculosController implements Initializable {

    @FXML
    private TableView<Veiculo> tableViewVeiculo;
    @FXML
    private TableColumn<Veiculo, String> tableColumnPlaca;
    @FXML
    private TableColumn<Veiculo, String> tableColumnNome;
    @FXML
    private Label labelPlaca;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelDono;
    @FXML
    private Label labelMarca;
    @FXML
    private Label labelMoto;
    @FXML
    private Label labelNomeVeiculo;
    @FXML
    private Label labelDescricao;    
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;

    private List<Veiculo> listVeiculos;
    private ObservableList<Veiculo> observableListVeiculos;

    // Manipulação de banco de dados
    private final PostgreSQL postgresql = new PostgreSQL();
    private final Connection connection = postgresql.conectar();
    private VeiculoDAO veiculoDao = new VeiculoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        veiculoDao.setConnection(connection);
        carregarTableViewVeiculo();

        // Listener acionado quando alterações ocorrem no tableview
        tableViewVeiculo.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> selecionarTableViewVeiculos(newValue));
    }
    
    public void selecionarTableViewVeiculos(Veiculo veiculo){
        if (veiculo != null){
            // Obtem o cliente
            ClienteDAO clienteDao = new ClienteDAO();
            clienteDao.setConnection(connection);
            Cliente cliente = clienteDao.buscar(veiculo.getCliente());
            
            // Obtem o ModeloVeiculo
            ModeloVeiculoDAO modeloDao = new ModeloVeiculoDAO();
            modeloDao.setConnection(connection);
            ModeloVeiculo modelo = modeloDao.buscar(veiculo.getModelo());
            
            // Define os dados selecionados
            labelPlaca.setText(veiculo.getPlaca());
            labelNome.setText(veiculo.getNome());
            labelDono.setText(cliente.getNome());
            labelMarca.setText(veiculo.getMarca());
            labelMoto.setText((modelo.getMoto()) ? "Sim" : "Não");
            labelNomeVeiculo.setText(modelo.getNome());
            String descricao = String.format("Descrição: %s",
                    modelo.getDescricao());
            labelDescricao.setText(Utils.quebrarLinha(descricao, 4));
        }
        else{
            labelPlaca.setText("");
            labelNome.setText("");
            labelDono.setText("");
            labelMarca.setText("");
            labelMoto.setText("");
            labelNomeVeiculo.setText("");
            labelDescricao.setText("Descrição");
        }
    }

    public void carregarTableViewVeiculo() {
        tableColumnPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        listVeiculos = veiculoDao.listar();

        observableListVeiculos = FXCollections.observableArrayList(listVeiculos);
        tableViewVeiculo.setItems(observableListVeiculos);
    }

    public boolean showCadastrosVeiculosDialog(Veiculo veiculo) throws IOException {
        // Carrega o fxml VeiculosDialog
        FXMLLoader loader = new FXMLLoader();
        String url = "/mecanica/view/FXMLCadastrosVeiculosDialog.fxml";
        loader.setLocation(FXMLCadastrosVeiculosDialogController.class.getResource(url));
        AnchorPane page = (AnchorPane) loader.load();

        // Cria uma cena com VeiculosDialog
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastros dos Veiculos");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Define o dialogStage e o veiculo
        FXMLCadastrosVeiculosDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVeiculo(veiculo);

        // Mostra o VeiculosDialog e espera
        dialogStage.showAndWait();

        // Retorna true se o botao confirmar for clicado
        return controller.isButtonConfirmarClicked();
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Veiculo veiculo = new Veiculo();

        // Obtem verdadeiro se o veiculo for inserido
        boolean buttonConfirmarClicked = showCadastrosVeiculosDialog(veiculo);
        if (buttonConfirmarClicked) {
            // Insere o veiculo no banco de dados
            veiculoDao.inserir(veiculo);
            // Recarrega os dados do veiculo
            carregarTableViewVeiculo();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Veiculo veiculo = tableViewVeiculo.getSelectionModel().getSelectedItem();

        if (veiculo != null) {
            boolean buttonConfirmarClicked = showCadastrosVeiculosDialog(veiculo);

            if (buttonConfirmarClicked) {
                System.out.println("Confirmado");
                veiculoDao.alterar(veiculo);
                carregarTableViewVeiculo();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um veiculo ...");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Veiculo veiculo = tableViewVeiculo.getSelectionModel().getSelectedItem();

        if (veiculo != null) {
            veiculoDao.remover(veiculo);
            carregarTableViewVeiculo();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um veiculo ...");
            alert.show();
        }
    }
}
