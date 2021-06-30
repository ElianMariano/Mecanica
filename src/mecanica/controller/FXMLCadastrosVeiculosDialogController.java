package mecanica.controller;

import java.sql.Connection;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mecanica.model.domain.Veiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mecanica.model.dao.ClienteDAO;
import mecanica.model.dao.ModeloVeiculoDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.Cliente;
import mecanica.model.domain.ModeloVeiculo;
import java.util.regex.Pattern;

public class FXMLCadastrosVeiculosDialogController implements Initializable {
    @FXML
    private TextField textFieldPlaca;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldMarca;
    @FXML
    private ComboBox comboBoxModelo;
    @FXML
    private TableView<Cliente> tableViewClientes;
    @FXML
    private TableColumn<Cliente, String> tableColumnCpf;
    @FXML
    private TableColumn<Cliente, String> tableColumnNome;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Veiculo veiculo;
    
    // Atributos utilizados em conjunto com o ComboBox ModeloVeiculo
    List<ModeloVeiculo> listaModelo;
    ObservableList<ModeloVeiculo> observableListModelo;
    
    // Atributos utilizados em conjunto com o TableView Clientes
    List<Cliente> listaCliente;
    ObservableList<Cliente> observableListCliente;
    
    // Atributos de para manipular o banco de dados
    private final PostgreSQL postgreSQL = new PostgreSQL();
    private final Connection connection = postgreSQL.conectar();
    private ModeloVeiculoDAO modeloDao = new ModeloVeiculoDAO();
    private ClienteDAO clienteDao = new ClienteDAO();
    
    public Stage getDialogStage(){
        return dialogStage;
    }
    
    public void setDialogStage(Stage stage){
        dialogStage = stage;
    }
    
    public boolean isButtonConfirmarClicked(){
        return buttonConfirmarClicked;
    }
    
    public void setButtonConfirmarClicked(boolean buttonConfirmar){
        buttonConfirmarClicked = buttonConfirmar;
    }
    
    public Veiculo getVeiculo(){
        return veiculo;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modeloDao.setConnection(connection);
        clienteDao.setConnection(connection);
        
        carregarComboBoxModelo();
        carregarTableViewCliente();
    }
    
    public void carregarComboBoxModelo(){
        listaModelo = modeloDao.listar();
        observableListModelo = FXCollections.observableArrayList(listaModelo);
        comboBoxModelo.setItems(observableListModelo);
    }
    
    public void carregarTableViewCliente(){
        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        listaCliente = clienteDao.listar();
        
        observableListCliente = FXCollections.observableArrayList(listaCliente);
        tableViewClientes.setItems(observableListCliente);
    }
    
    public void setVeiculo(Veiculo veiculo){
        this.veiculo = veiculo;
        
        if (veiculo != null && veiculo.getPlaca() != null){
            textFieldPlaca.setText(veiculo.getPlaca());
            textFieldNome.setText(veiculo.getNome());
            textFieldMarca.setText(veiculo.getMarca());
            
            // Seleciona o item no comboBox
            comboBoxModelo.getSelectionModel().select(veiculo.getModelo());
            tableViewClientes.getSelectionModel().select(veiculo.getCliente());
            
            textFieldPlaca.setEditable(false);
        }
    }
    
    @FXML
    public void handleButtonConfirmar(){
        if (validarEntradaDeDados()){
            veiculo.setNome(textFieldNome.getText());
            veiculo.setMarca(textFieldMarca.getText());
            veiculo.setPlaca(textFieldPlaca.getText());

            // 
            ModeloVeiculo modelo = (ModeloVeiculo) comboBoxModelo.getSelectionModel().getSelectedItem();
            Cliente cliente = (Cliente) tableViewClientes.getSelectionModel().getSelectedItem();
            
            // Adiciona o modelo e o cliente ao veiculo
            veiculo.setCliente(cliente);
            veiculo.setModelo(modelo);
            
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleButtonCancelar(){
        dialogStage.close();
    }
    
    // Valida a entrada de dados
    public boolean validarEntradaDeDados(){
        String errorMessage = "";
        
        if (textFieldNome.getText() == null || textFieldNome.getText().length() == 0
                || textFieldNome.getText().length() > 100){
            errorMessage += "Nome inválido\n";
        }
        
        if (textFieldMarca.getText() == null || textFieldMarca.getText().length() == 0
                || textFieldNome.getText().length() > 100){
            errorMessage += "Marca inválida\n";
        }
        
        if (textFieldPlaca.getText() == null || textFieldPlaca.getText().length() == 0 ||
                !Pattern.matches("(.){5}\\-(.){2}", textFieldPlaca.getText())){
            errorMessage += "Placa inválida (AAAAA-AA)\n";
        }
        
        ModeloVeiculo modelo = (ModeloVeiculo) comboBoxModelo.getSelectionModel().getSelectedItem();
        
        if (modelo == null) errorMessage += "Selecione um modelo\n";
        
        Cliente cliente = (Cliente) tableViewClientes.getSelectionModel().getSelectedItem();
        
        if (cliente == null) errorMessage += "Selecione um cliente\n";
        
        if (errorMessage.equals("")){
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Cadastro");
            alert.setHeaderText("Campos Inválidos, corrija ...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
}
