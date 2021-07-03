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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mecanica.model.dao.ManutencaoDAO;
import mecanica.model.dao.ManutencaoServicoDAO;
import mecanica.model.dao.ServicoDAO;
import mecanica.model.dao.VeiculoDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.Manutencao;
import mecanica.model.domain.ManutencaoServico;
import mecanica.model.domain.Servicos;
import mecanica.model.domain.Veiculo;

public class FXMLProcessosManutencoesController implements Initializable {

    @FXML
    private TableView<Manutencao> tableViewManutencao;
    @FXML
    private TableColumn<Manutencao, Integer> tableColumnCodigo;
    @FXML
    private TableColumn<Manutencao, Date> tableColumnData;
    private ObservableList<Manutencao> observableListManutencao;
    
    @FXML
    private Label labelCodigo;
    @FXML
    private Label labelDia;
    @FXML
    private Label labelInicio;
    @FXML
    private Label labelFim;
    
    // DADOS DO SERVICO
    @FXML
    private Label labelPlaca;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelMarca;
    
    @FXML
    private TableView<Servicos> tableViewServicos;
    @FXML
    private TableColumn<Servicos, Integer> tableColumnServicoCodigo;
    @FXML
    private TableColumn<Servicos, Integer> tableColumnServicoNome;
    private ObservableList<Servicos> observableListServico;
    
    // Listas de dados
    private List<Manutencao> listManutencao;
    private List<Servicos> listServico;
    private List<ManutencaoServico> listMs;

    // Manipulação de banco de dados
    private final PostgreSQL postgresql = new PostgreSQL();
    private final Connection connection = postgresql.conectar();
    // DAO's
    private final ServicoDAO servicoDao = new ServicoDAO();
    private final ManutencaoDAO manutencaoDao = new ManutencaoDAO();
    private final ManutencaoServicoDAO msDao = new ManutencaoServicoDAO();
    private final VeiculoDAO veiculoDao = new VeiculoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manutencaoDao.setConnection(connection);
        servicoDao.setConnection(connection);
        msDao.setConnection(connection);
        veiculoDao.setConnection(connection);
        
        carregarTableViewManutencao();

        // Listener acionado quando alterações ocorrem no tableview
         tableViewManutencao.getSelectionModel().selectedItemProperty().addListener(
         (observable, oldValue, newValue) -> selecionarTableViewManutencoes(newValue));
    }
    
    public void selecionarTableViewManutencoes(Manutencao ms){
        if (ms != null){
            labelCodigo.setText(String.valueOf(ms.getCodigo()));
            labelDia.setText(ms.getDia().toString());
            labelInicio.setText(ms.getInicio());
            labelFim.setText(ms.getFim());
            
            Veiculo veiculo = veiculoDao.buscar(ms.getVeiculo());
            labelPlaca.setText(veiculo.getPlaca());
            labelNome.setText(veiculo.getNome());
            labelMarca.setText(veiculo.getMarca());
            
            // Obtem todos os servicos relacionados a um veiculo especifico
            carregarTableViewServicos(veiculo);
        }
        else{
            labelCodigo.setText("");
            labelDia.setText("");
            labelInicio.setText("");
            labelFim.setText("");
            
            labelPlaca.setText("");
            labelNome.setText("");
            labelMarca.setText("");
            
            observableListServico = FXCollections.observableArrayList(new ArrayList<>());
            tableViewServicos.setItems(observableListServico);
        }
    }
    
    public void carregarTableViewServicos(Veiculo veiculo){
        // Carrega o tableViewServico
        tableColumnServicoCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tableColumnServicoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        listServico = servicoDao.listarPorVeiculo(veiculo);
        
        observableListServico = FXCollections.observableArrayList(listServico);
        tableViewServicos.setItems(observableListServico);
    }

    public void carregarTableViewManutencao() {
        tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dia"));

        listManutencao = manutencaoDao.listar();
        
        observableListManutencao = FXCollections.observableArrayList(listManutencao);
        tableViewManutencao.setItems(observableListManutencao);
    }

    public boolean showCadastrosManutencoesDialog(Manutencao manutencao) throws IOException {
        // Carrega o fxml ManutencoesDialog
        FXMLLoader loader = new FXMLLoader();
        String url = "/mecanica/view/FXMLProcessosManutencoesDialog.fxml";
        loader.setLocation(FXMLProcessosManutencoesDialogController.class.getResource(url));
        AnchorPane page = (AnchorPane) loader.load();

        // Cria uma cena com ManutencoesDialog
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastros das Manutenções");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Define o dialogStage e o manutencao
        FXMLProcessosManutencoesDialogController controller = loader.getController();
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
//        Manutencao manutencao = tableViewManutencao.getSelectionModel().getSelectedItem();
//
//        if (manutencao != null) {
//            boolean buttonConfirmarClicked = showCadastrosManutencoesDialog(manutencao);
//
//            if (buttonConfirmarClicked) {
//                manutencaoDao.alterar(manutencao);
//                carregarTableViewManutencao();
//            }
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("Por favor, selecione uma manutençao ...");
//            alert.show();
//        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
//        Manutencao manutencao = tableViewManutencao.getSelectionModel().getSelectedItem();
//
//        if (manutencao != null) {
//            manutencaoDao.remover(manutencao);
//            carregarTableViewManutencao();
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("Por favor, selecione uma manutenção ...");
//            alert.show();
//        }
    }
}
