package mecanica.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mecanica.model.domain.Servicos;

public class ServicoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Servicos servico) {
        String sql = "INSERT INTO servico (cod_servico, nome, descricao, preco) VALUES (?,?,?,?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, servico.getcdServico());
            stmt.setString(2, servico.getNome());
            stmt.setString(3, servico.getDescricao());
            stmt.setDouble(4, servico.getPreco());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Servicos servico) {
        String sql = "UPDATE servico SET nome=?, descricao=?, preco=? WHERE cod_servico=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, servico.getcdServico());
            stmt.setString(2, servico.getNome());
            stmt.setString(3, servico.getDescricao());
            stmt.setDouble(4, servico.getPreco());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Servicos servico) {
        String sql = "DELETE FROM servico WHERE cod_servico=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, servico.getcdServico());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Servicos> listar() {
        String sql = "SELECT * FROM servico;";
        List<Servicos> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Servicos servico = new Servicos();
                servico.setcdServico(resultado.getInt("cod_servico"));
                servico.setNome(resultado.getString("nome"));
                servico.setDescricao(resultado.getString("descricao"));
                servico.setPreco(resultado.getDouble("preco"));
                retorno.add(servico);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Servicos buscar(Servicos servico) {
        String sql = "SELECT * FROM servico WHERE cod_servico=?;";
        Servicos retorno = new Servicos();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, servico.getcdServico());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                servico.setNome(resultado.getString("nome"));
                servico.setDescricao(resultado.getString("descricao"));
                servico.setPreco(resultado.getDouble("preco"));
                retorno = servico;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
