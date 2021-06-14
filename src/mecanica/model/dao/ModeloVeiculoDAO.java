package mecanica.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mecanica.model.domain.ModeloVeiculo;

public class ModeloVeiculoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ModeloVeiculo modeloVeiculo) {
        String sql = "INSERT INTO modelo_veiculo (cod_modelo, moto, nome, descricao) VALUES (?,?,?,?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modeloVeiculo.getCdModeloVeiculo());
            stmt.setBoolean(2, modeloVeiculo.getMoto());
            stmt.setString(3, modeloVeiculo.getNome());
            stmt.setString(4, modeloVeiculo.getDescricao());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloVeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(ModeloVeiculo modeloVeiculo) {
        String sql = "UPDATE modelo_veiculo SET moto=?, nome=?, descicao=? WHERE cod_modelo=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setBoolean(1, modeloVeiculo.getMoto());
            stmt.setString(2, modeloVeiculo.getNome());
            stmt.setString(3, modeloVeiculo.getDescricao());
            stmt.setInt(4, modeloVeiculo.getCdModeloVeiculo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloVeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(ModeloVeiculo modeloVeiculo) {
        String sql = "DELETE FROM modelo_veiculo WHERE cod_modelo=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modeloVeiculo.getCdModeloVeiculo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloVeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<ModeloVeiculo> listar() {
        String sql = "SELECT * FROM modelo_veiculo;";
        List<ModeloVeiculo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ModeloVeiculo modeloVeiculo = new ModeloVeiculo();
                modeloVeiculo.setCdModeloVeiculo(resultado.getInt("cod_modelo"));
                modeloVeiculo.setMoto(resultado.getBoolean("moto"));
                modeloVeiculo.setNome(resultado.getString("nome"));
                modeloVeiculo.setDescricao(resultado.getString("descricao"));
                retorno.add(modeloVeiculo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloVeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public ModeloVeiculo buscar(ModeloVeiculo modeloVeiculo) {
        String sql = "SELECT * FROM modelo_veiculo WHERE cod_modelo=?;";
        ModeloVeiculo retorno = new ModeloVeiculo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modeloVeiculo.getCdModeloVeiculo());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                modeloVeiculo.setMoto(resultado.getBoolean("moto"));
                modeloVeiculo.setNome(resultado.getString("nome"));
                modeloVeiculo.setDescricao(resultado.getString("descricao"));

                retorno = modeloVeiculo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloVeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
