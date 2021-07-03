package mecanica.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.PieChart;
import mecanica.model.domain.Servicos;
import mecanica.model.domain.Veiculo;
import org.postgresql.util.PSQLException;

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
            stmt.setInt(1, servico.getCodigo());
            stmt.setString(2, servico.getNome());
            stmt.setString(3, servico.getDescricao());
            stmt.setDouble(4, servico.getPreco());
            stmt.execute();
            return true;
        }
        catch (PSQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Servicos servico) {
        String sql = "UPDATE servico SET nome=?, descricao=?, preco=? WHERE cod_servico=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, servico.getNome());
            stmt.setString(2, servico.getDescricao());
            stmt.setDouble(3, servico.getPreco());
            stmt.setInt(4, servico.getCodigo());
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
            stmt.setInt(1, servico.getCodigo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<PieChart.Data> quantidadeServicos(){
        String sql = "SELECT COUNT(ms) AS quantidade, s.nome, \n" + 
"           s.descricao, s.preco\n" +
"	FROM manutencao_servico ms\n" +
"	INNER JOIN servico s ON ms.cod_servico = s.cod_servico\n" +
"	GROUP BY s.nome, s.descricao, s.preco\n" +
"	ORDER BY quantidade;";
        
        List<PieChart.Data> retorno = new ArrayList<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                PieChart.Data dado = new PieChart.Data(resultado.getString("nome"),
                resultado.getInt("quantidade"));
                
                retorno.add(dado);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retorno;
    }

    public List<Servicos> listar() {
        String sql = "SELECT * FROM servico;";
        List<Servicos> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Servicos servico = new Servicos();
                servico.setCodigo(resultado.getInt("cod_servico"));
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
    
    // Retorna os servicos realizados por dado veiculo
    public List<Servicos> listarPorVeiculo(Veiculo veiculo) {
        String sql = "SELECT    s.cod_servico AS codigo, s.nome,\n" +
"		s.descricao, s.preco\n" +
"	FROM servico s\n" +
"	INNER JOIN manutencao_servico ms ON ms.cod_servico = s.cod_servico\n" +
"	INNER JOIN manutencao m ON m.cod_manutencao = ms.cod_manutencao\n" +
"	INNER JOIN veiculo v ON v.placa = m.cod_veiculo\n" +
"	WHERE v.placa = ?;";
        
        List<Servicos> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Servicos servico = new Servicos();
                servico.setCodigo(resultado.getInt("codigo"));
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
            stmt.setInt(1, servico.getCodigo());
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