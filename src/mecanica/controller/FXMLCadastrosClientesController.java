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
import mecanica.model.dao.ClienteDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.Cliente;

/**
 * FXML Controller class
 *
 * @author elian
 */
public class FXMLCadastrosClientesController implements Initializable {
    @FXML
    private TableView<Cliente> tableViewCliente;
    @FXML
    private TableColumn<Cliente, String> tableColumnCpf;
    @FXML
    private TableColumn<Cliente, String> tableColumnNome;
    @FXML
    private TableColumn<Cliente, String> tableColumnNascimento;
    @FXML
    private TableColumn<Cliente, String> tableColumnCidade;
    @FXML
    private TableColumn<Cliente, String> tableColumnUf;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    
    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;
    
    // Manipulação de banco de dados
    private final PostgreSQL postgresql = new PostgreSQL();
    private final Connection connection = postgresql.conectar();
    private ClienteDAO clienteDao = new ClienteDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDao.setConnection(connection);
        carregarTableViewCliente();
        
        // Listener acionado quando alterações ocorrem no tableview
//        tableViewCliente.getSelectionModel().selectedItemProperty().addListener(
//        (observable, oldValue, newValue) -> selecionarTableViewClientes(newValue));
    }
    
    public void carregarTableViewCliente(){
        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnNascimento.setCellValueFactory(new PropertyValueFactory<>("nascimento"));
        tableColumnCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        tableColumnUf.setCellValueFactory(new PropertyValueFactory<>("uf"));
        
        listClientes = clienteDao.listar();
        
        observableListClientes = FXCollections.observableArrayList(listClientes);
        tableViewCliente.setItems(observableListClientes);
    }
    
    public boolean showCadastrosClientesDialog(Cliente cliente) throws IOException{
        // Carrega o fxml ClientesDialog
        FXMLLoader loader = new FXMLLoader();
        String url = "/mecanica/view/FXMLCadastrosClientesDialog.fxml";
        loader.setLocation(FXMLCadastrosClientesDialogController.class.getResource(url));
        AnchorPane page = (AnchorPane) loader.load();
        
        // Cria uma cena com ClientesDialog
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastros dos Clientes");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        // Define o dialogStage e o cliente
        FXMLCadastrosClientesDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCliente(cliente);
        
        // Mostra o ClientesDialog e espera
        dialogStage.showAndWait();
        
        // Retorna true se o botao confirmar for clicado
        return controller.isButtonConfirmarClicked();
    }
    
    @FXML
    public void handleButtonInserir() throws IOException{
        Cliente cliente = new Cliente();
        
        // Obtem verdadeiro se o cliente for inserido
        boolean buttonConfirmarClicked = showCadastrosClientesDialog(cliente);
        if (buttonConfirmarClicked){
            // Insere o cliente no banco de dados
            clienteDao.inserir(cliente);
            // Recarrega os dados do cliente
            carregarTableViewCliente();
        }
    }
    
    @FXML
    public void handleButtonAlterar() throws IOException{
        Cliente cliente = tableViewCliente.getSelectionModel().getSelectedItem();
        
        if (cliente != null){
            boolean buttonConfirmarClicked = showCadastrosClientesDialog(cliente);
            
            if (buttonConfirmarClicked){
                clienteDao.alterar(cliente);
                carregarTableViewCliente();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um cliente ...");
            alert.show();
        }
    }
    
    @FXML
    public void handleButtonRemover() throws IOException{
        Cliente cliente = tableViewCliente.getSelectionModel().getSelectedItem();
        
        if (cliente != null){
            clienteDao.remover(cliente);
            carregarTableViewCliente();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um cliente ...");
            alert.show();
        }
    }
}
