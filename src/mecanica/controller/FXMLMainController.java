package mecanica.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class FXMLMainController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void handleCadastrosClientes() throws IOException {
        String url = "/mecanica/view/FXMLCadastrosClientes.fxml";
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource(url));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleCadastrosVeiculo() throws IOException {
        String url = "/mecanica/view/FXMLCadastrosVeiculos.fxml";
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource(url));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleCadastrosServico() throws IOException {
        String url = "/mecanica/view/FXMLCasdastrosServicos.fxml";
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource(url));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleCadastrosModelosVeiculo() throws IOException {
        String url = "/mecanica/view/FXMLCadastrosModelosVeiculos.fxml";
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource(url));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleGraficosServicos() throws IOException {
        String url = "/mecanica/view/FXMLGraficosServicos.fxml";
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource(url));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleProcessosManutencoes() throws IOException {
        String url = "/mecanica/view/FXMLProcessosManutencoes.fxml";
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource(url));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleRelatoriosVeiculos() throws IOException {
        String url = "/mecanica/view/FXMLRelatorioVeiculosMaisAtendidos.fxml";
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource(url));
        anchorPane.getChildren().setAll(a);
    }
}
