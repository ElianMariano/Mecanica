package mecanica.controller;

import java.sql.Connection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
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
import mecanica.model.dao.ManutencaoServicoDAO;
import mecanica.model.dao.ServicoDAO;
import mecanica.model.dao.VeiculoDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.Cliente;
import mecanica.model.domain.Manutencao;
import mecanica.model.domain.ManutencaoServico;
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
    private ComboBox<Veiculo> comboBoxVeiculo;
    private ObservableList<Veiculo> observableListVeiculos;
    
    @FXML
    private ComboBox<Cliente> comboBoxCliente;
    private ObservableList<Cliente> observableListClientes;
    
    @FXML
    private ComboBox<Servicos> comboBoxServicos;
    private ObservableList<Servicos> observableListComboBoxServicos;
    
    @FXML
    private ListView<Servicos> listViewServicos;
    private ObservableList<Servicos> observableListViewServicos;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Manutencao manutencao;
    
    // Manipulação do banco de dados
    private final PostgreSQL postgreSql = new PostgreSQL();
    private final Connection connection = postgreSql.conectar();
    private final VeiculoDAO veiculoDao = new VeiculoDAO();
    private final ServicoDAO servicoDao = new ServicoDAO();
    private final ClienteDAO clienteDao = new ClienteDAO();
    private final ManutencaoServicoDAO msDao = new ManutencaoServicoDAO();
    private List<Veiculo> veiculos = new ArrayList<>();
    private List<Servicos> servicosAdicionados = new ArrayList<>();
    private List<Servicos> servicos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Define a conexao com o banco de dados para os daos
        veiculoDao.setConnection(connection);
        servicoDao.setConnection(connection);
        clienteDao.setConnection(connection);
        msDao.setConnection(connection);
        
        // Carrega o comboBox e o ListView
        carregarComboBoxClientes();
        carregarComboBoxVeiculos();
        carregarComboBoxServicos();
        carregarListViewServicos();
        
        // Adiciona um listener para o cliente
        comboBoxCliente.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> selecionarComboBoxCliente(newValue));
    }
    
    // Função chamada quando o comboBox cliente é selecionado
    public void selecionarComboBoxCliente(Cliente cliente){
        if (cliente != null){
            // Obtem os clientes
            veiculos = veiculoDao.listarPorDono(cliente);
            
            // Recarrega o comboBox
            carregarComboBoxVeiculos();
        }
    }
    
    // Carregar o comboBox servicos
    public void carregarComboBoxServicos(){
        servicos = servicoDao.listar();
        
        observableListComboBoxServicos = FXCollections.observableArrayList(servicos);
        comboBoxServicos.setItems(observableListComboBoxServicos);
    }
    
    // Carrega o comboBox clientes
    public void carregarComboBoxClientes(){
        clientes = clienteDao.listar();
        
        observableListClientes = FXCollections.observableArrayList(clientes);
        comboBoxCliente.setItems(observableListClientes);
    }
    
    // Carrega os dados do comboBox de veiculos
    public void carregarComboBoxVeiculos(){        
        observableListVeiculos = FXCollections.observableArrayList(veiculos);
        comboBoxVeiculo.setItems(observableListVeiculos);
    }
    
    // Carrega os dados do ListView de Servicos
    public void carregarListViewServicos(){
        observableListViewServicos = FXCollections.observableArrayList(servicosAdicionados);
        listViewServicos.setItems(observableListViewServicos);
    }
    
    @FXML
    public void handleServicoAdicionar(){
        Servicos servico = comboBoxServicos.getSelectionModel().getSelectedItem();
        
        if (servico != null){
            servicosAdicionados.add(servico);
            carregarListViewServicos();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um servico!");
            alert.show();
        }
    }
    
    @FXML
    public void handleServicoRemover(){
        Servicos servico = listViewServicos.getSelectionModel().getSelectedItem();
        
        if (servico != null){
            servicosAdicionados.remove(servico);
            carregarListViewServicos();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um servico!");
            alert.show();
        }
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
        
        if (manutencao != null && manutencao.getInicio() != null){
            textAreaDescricao.setText(manutencao.getDescricao());
            datePickerDia.setValue(manutencao.getDia());
            textFieldInicio.setText(manutencao.getInicio());
            textFieldFim.setText(manutencao.getFim());
            
            Veiculo veiculo = manutencao.getVeiculo();
            Cliente cliente = clienteDao.buscar(veiculo.getCliente());
            
            comboBoxCliente.getSelectionModel().select(cliente);
            comboBoxVeiculo.getSelectionModel().select(veiculo);
            
            List<Servicos> servicos = new ArrayList<>();
            for (ManutencaoServico ms : msDao.listarPorManutencao(manutencao)){
                Servicos servico = servicoDao.buscar(ms.getServico());
                servicos.add(servico);
            }
            
            servicosAdicionados = servicos;
            
            carregarListViewServicos();
        }
    }

    @FXML
    public void handleButtonConfirmar() throws ParseException {
        if (validarEntradaDeDados()) {
            // TODO adicionar os dados no objeto manutencao
            // Cria o objeto manutencao
            Manutencao manutencao = new Manutencao();
            
            // Obtem o dado do veiculo
            Veiculo veiculo = comboBoxVeiculo.getSelectionModel().getSelectedItem();
            
            // Define o veiculo da manutencao e os dados
            manutencao.setVeiculo(veiculo);
            manutencao.setDescricao(textAreaDescricao.getText());
            
            manutencao.setDia(datePickerDia.getValue());
            manutencao.setInicio(textFieldInicio.getText());
            manutencao.setFim(textFieldFim.getText());
            
            // Cria a lista de ManutencaoServico
            List<ManutencaoServico> ms = new ArrayList<>();
            for (Servicos servico : servicosAdicionados){
                ManutencaoServico manutencao_servico = new ManutencaoServico();
                
                // Adiciona os servicos
                manutencao_servico.setServico(servico);
                
                ms.add(manutencao_servico);
            }
            
            manutencao.setManutencaoServico(ms);
            
            // Define a manutencao
            this.manutencao = manutencao;

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
        
        if (servicosAdicionados.size() == 0){
            errorMessage += "Escolha pelo menos um Serviço\n";
        }
        
        if (textAreaDescricao.getText() == null || textAreaDescricao.getText().length() == 0 ||
                textAreaDescricao.getText().length() > 500){
            errorMessage += "Descrição Inválida\n";
        }
        
        LocalDate localDate = datePickerDia.getValue();
        if (localDate == null){
            errorMessage += "Data inválida\n";
        }
        
        if (textFieldInicio.getText() == null || textFieldInicio.getText().length() == 0 ||
                !Pattern.matches("(((\\d){2}\\:(\\d){2})\\:(\\d){2})|(((\\d){2}\\:(\\d){2}))", textFieldInicio.getText())){
            errorMessage += "Horario de inicio inválido\n";
        }
        
        if (textFieldFim.getText() == null || textFieldFim.getText().length() == 0 ||
                !Pattern.matches("(((\\d){2}\\:(\\d){2})\\:(\\d){2})|(((\\d){2}\\:(\\d){2}))", textFieldFim.getText())){
            errorMessage += "Horario de fim inválido\n";
        }
        
        Veiculo veiculo = comboBoxVeiculo.getSelectionModel().getSelectedItem();
        if (veiculo == null){
            errorMessage += "Escolha um veículo\n";
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
}
