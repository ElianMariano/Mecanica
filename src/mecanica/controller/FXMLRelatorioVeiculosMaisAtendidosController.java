/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mecanica.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import mecanica.model.dao.ModeloVeiculoDAO;
import mecanica.model.dao.ServicoDAO;
import mecanica.model.database.PostgreSQL;
import mecanica.model.domain.ModeloVeiculo;
import mecanica.model.domain.Servicos;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FXMLRelatorioVeiculosMaisAtendidosController implements Initializable {
    
    @FXML
    private TableView<ModeloVeiculo> tableViewModelo;
    @FXML
    private TableColumn tableColumnCodigo;
    @FXML
    private TableColumn tableColumnMoto;
    @FXML
    private TableColumn tableColumnNome;
    @FXML
    private TableColumn tableColumnDescricao;
    
    private ObservableList<ModeloVeiculo> observableListModelo;
    private List<ModeloVeiculo> modelos = new ArrayList<>();
    
    // Atributos para a manupulação do banco de dados
    private final PostgreSQL postgresql = new PostgreSQL();
    private final Connection connection = postgresql.conectar();
    private ModeloVeiculoDAO modeloDao = new ModeloVeiculoDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modeloDao.setConnection(connection);
        
        carregarTableViewRelatorio();
    }
    
    public void carregarTableViewRelatorio(){
        tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tableColumnMoto.setCellValueFactory(new PropertyValueFactory<>("moto"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        modelos = modeloDao.listar();
        
        observableListModelo = FXCollections.observableArrayList(modelos);
        tableViewModelo.setItems(observableListModelo);
    }
    
    @FXML
    public void handleButtonImprimir() throws JRException{
        URL url = getClass().getResource("/mecanica/relatorios/RelatorioVeiculosMaisAtendidos.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
        
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
        jasperViewer.setVisible(true);
    }
}
