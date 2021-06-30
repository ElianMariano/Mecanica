package mecanica.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import mecanica.model.dao.ServicoDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.Servicos;
import java.sql.Connection;
import javafx.collections.FXCollections;

public class FXMLGraficosServicosController implements Initializable {
    @FXML
    private PieChart pieChart;
    private ObservableList<PieChart.Data> observableListPie;
    
    // Atributos para a manupulação do banco de dados
    private final PostgreSQL postgresql = new PostgreSQL();
    private final Connection connection = postgresql.conectar();
    private ServicoDAO servicoDao = new ServicoDAO();
    private List<PieChart.Data> servicos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Define a conexão do servico
        servicoDao.setConnection(connection);
        // Obtem os dados do servico
        servicos = servicoDao.quantidadeServicos();
        
        // Adiciona os dados no observableList e o adiciona no PieChart
        observableListPie = FXCollections.observableArrayList(servicos);
        pieChart.setData(observableListPie);
    }
}
