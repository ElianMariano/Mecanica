package mecanica.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mecanica.model.domain.Manutencao;
import mecanica.model.domain.Veiculo;

public class ManutencaoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Manutencao manutencao) {
        String sql = "INSERT INTO manutencao (cod_manutencao, descricao, veiculo) VALUES (?,?,?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, manutencao.getCdManutencao());
            stmt.setString(2, manutencao.getDescricao());
            stmt.setObject(3, manutencao.getVeiculo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ManutencaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Manutencao manutencao) {
        String sql = "UPDATE manutencao SET descricao=?, veiculo=? WHERE cod_manutencao=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, manutencao.getCdManutencao());
            stmt.setString(2, manutencao.getDescricao());
            stmt.setObject(3, manutencao.getVeiculo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ManutencaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Manutencao manutencao) {
        String sql = "DELETE FROM manutencao WHERE cod_manutencao=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, manutencao.getCdManutencao());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ManutencaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Manutencao> listar() {
        String sql = "SELECT * FROM manutencao;";
        List<Manutencao> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Manutencao manutencao = new Manutencao();
                manutencao.setCdManutencao(resultado.getInt("cod_manutencao"));
                manutencao.setDescricao(resultado.getString("descricao"));
                manutencao.setVeiculo((Veiculo) resultado.getObject("veiculo"));
                retorno.add(manutencao);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManutencaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Manutencao buscar(Manutencao manutencao) {
        String sql = "SELECT * FROM manutencao WHERE cod_manutencao=?;";
        Manutencao retorno = new Manutencao();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, manutencao.getCdManutencao());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                manutencao.setDescricao(resultado.getString("descricao"));
                manutencao.setVeiculo((Veiculo) resultado.getObject("veiculo"));

                retorno = manutencao;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManutencaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
