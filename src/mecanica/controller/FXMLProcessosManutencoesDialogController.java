package mecanica.controller;

import java.sql.Connection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mecanica.model.dao.ClienteDAO;
import mecanica.model.dao.ServicoDAO;
import mecanica.model.dao.VeiculoDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.Cliente;
import mecanica.model.domain.Manutencao;
import mecanica.model.domain.Servicos;
import mecanica.model.domain.Veiculo;

public class FXMLProcessosManutencoesDialogController implements Initializable {

    @FXML
    private TextArea textAreaDescricao;
    @FXML
    private DatePicker datePickerDia;
    @FXML
    private TextField textFieldInicio;
    @FXML
    private TextField textFieldFim;
    
    @FXML
    private ComboBox comboBoxVeiculo;
    private ObservableList<Veiculo> observableListVeiculos;
    
    @FXML
    private ComboBox comboBoxCliente;
    private ObservableList<Cliente> observableListClientes;
    
    @FXML
    private ListView<Servicos> listViewServicos;
    private ObservableList<Servicos> observableListServicos;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Manutencao manutencao;
    
    // Manipulação do banco de dados
    private final PostgreSQL postgreSql = new PostgreSQL();
    private final Connection connection = postgreSql.conectar();
    private final VeiculoDAO veiculoDao = new VeiculoDAO();
    private final ServicoDAO servicoDao = new ServicoDAO();
    private final ClienteDAO clienteDao = new ClienteDAO();
    private List<Veiculo> veiculos = new ArrayList<>();
    private List<Servicos> servicos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Define a conexao com o banco de dados para os daos
        veiculoDao.setConnection(connection);
        servicoDao.setConnection(connection);
        clienteDao.setConnection(connection);
        
        // Carrega o comboBox e o ListView
        carregarComboBoxClientes();
        carregarComboBoxVeiculos();
        carregarListViewServicos();
    }
    
    // Carrega o comboBox clientes
    public void carregarComboBoxClientes(){
        clientes = clienteDao.listar();
        
        observableListClientes = FXCollections.observableArrayList(clientes);
        comboBoxCliente.setItems(observableListClientes);
    }
    
    // Carrega os dados do comboBox de veiculos
    public void carregarComboBoxVeiculos(){
        veiculos = veiculoDao.listar();
        
        observableListVeiculos = FXCollections.observableArrayList(veiculos);
        comboBoxVeiculo.setItems(observableListVeiculos);
    }
    
    // Carrega os dados do ListView de Servicos
    public void carregarListViewServicos(){
        servicos = servicoDao.listar();
        
        observableListServicos = FXCollections.observableArrayList(servicos);
        listViewServicos.setItems(observableListServicos);
    }

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
        }
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {

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

//        if (textFieldCdManutencao.getText() == null || textFieldCdManutencao.getText().length() == 0 || !Pattern.matches("[0-9]+", textFieldCdManutencao.getText())) {
//            errorMessage += "Código de Manutenção inválido\n";
//        }
//
//        if (textFieldDescricao.getText() == null || textFieldDescricao.getText().length() == 0) {
//            errorMessage += "Descrição inválida\n";
//        }
//
//        if (textFieldCdVeiculo.getText() == null || textFieldCdVeiculo.getText().length() == 0 || textFieldCdVeiculo.getText().length() > 7) {
//            errorMessage += "Código de Veículo inválido\n";
//        }

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
}
