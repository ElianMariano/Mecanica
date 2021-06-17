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
import mecanica.model.dao.VeiculoDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.Veiculo;

/**
 * FXML Controller class
 * @author jones
 */

public class FXMLCadastrosVeiculosController implements Initializable {
    @FXML
    private TableView<Veiculo> tableViewVeiculo;
    @FXML
    private TableColumn<Veiculo, String> tableColumnPlaca;
    @FXML
    private TableColumn<Veiculo, String> tableColumnNome;
    @FXML
    private TableColumn<Veiculo, String> tableColumnMarca;
    @FXML
    private TableColumn<Veiculo, Integer> tableColumnCdModelo;
    @FXML
    private TableColumn<Veiculo, String> tableColumnCdCliente;
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
//        tableViewVeiculo.getSelectionModel().selectedItemProperty().addListener(
//        (observable, oldValue, newValue) -> selecionarTableViewVeiculos(newValue));
    }
    
    public void carregarTableViewVeiculo(){
        tableColumnPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tableColumnCdModelo.setCellValueFactory(new PropertyValueFactory<>("cod_modelo"));
        tableColumnCdCliente.setCellValueFactory(new PropertyValueFactory<>("cod_cliente"));
        
        listVeiculos = veiculoDao.listar();
        
        observableListVeiculos = FXCollections.observableArrayList(listVeiculos);
        tableViewVeiculo.setItems(observableListVeiculos);
    }
    
    public boolean showCadastrosVeiculosDialog(Veiculo veiculo) throws IOException{
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
    public void handleButtonInserir() throws IOException{
        Veiculo veiculo = new Veiculo();
        
        // Obtem verdadeiro se o veiculo for inserido
        boolean buttonConfirmarClicked = showCadastrosVeiculosDialog(veiculo);
        if (buttonConfirmarClicked){
            // Insere o veiculo no banco de dados
            veiculoDao.inserir(veiculo);
            // Recarrega os dados do veiculo
            carregarTableViewVeiculo();
        }
    }
    
    @FXML
    public void handleButtonAlterar() throws IOException{
        Veiculo veiculo = tableViewVeiculo.getSelectionModel().getSelectedItem();
        
        if (veiculo != null){
            boolean buttonConfirmarClicked = showCadastrosVeiculosDialog(veiculo);
            
            if (buttonConfirmarClicked){
                veiculoDao.alterar(veiculo);
                carregarTableViewVeiculo();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um veiculo ...");
            alert.show();
        }
    }
    
    @FXML
    public void handleButtonRemover() throws IOException{
        Veiculo veiculo = tableViewVeiculo.getSelectionModel().getSelectedItem();
        
        if (veiculo != null){
            veiculoDao.remover(veiculo);
            carregarTableViewVeiculo();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um veiculo ...");
            alert.show();
        }
    }
}
