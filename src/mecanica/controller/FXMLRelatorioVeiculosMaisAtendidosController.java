/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mecanica.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mecanica.model.dao.ServicoDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.ModeloVeiculo;
import mecanica.model.domain.Servicos;

/**
 * FXML Controller class
 *
 * @author Andre
 */
public class FXMLRelatorioVeiculosMaisAtendidosController implements Initializable {
    
    @FXML
    private TableView<ModeloVeiculo> tabeleviewRelatorio;
    @FXML
    private TableColumn tableColumnVeiculo;
    @FXML
    private TableColumn tableColumnNome;
    @FXML
    private TableColumn tableColumnPlaca;
    @FXML
    private TableColumn tableColumnDescricao;
    @FXML
    private TableColumn tableColumnQuantidade;
    
    // Atributos para a manupulação do banco de dados
    private final PostgreSQL postgresql = new PostgreSQL();
    private final Connection connection = postgresql.conectar();
    private ServicoDAO servicoDao = new ServicoDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carreagarTableViewRelatorio();
    }
    
    public void carreagarTableViewRelatorio(){
        // Carregar table view
    }
    
    @FXML
    public void handleButtonImprimir(){
        // Gerar relatório
    }
}
