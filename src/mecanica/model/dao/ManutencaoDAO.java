package mecanica.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.PieChart;
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
        String sql = "INSERT INTO manutencao (descricao, dia, inicio, fim, cod_veiculo) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, manutencao.getCodigo());
            stmt.setString(2, manutencao.getDescricao());
            stmt.setString(3, manutencao.getDia().toString());
            stmt.setString(4, manutencao.getInicio());
            stmt.setString(5, manutencao.getFim());
            stmt.setString(6, manutencao.getVeiculo().getPlaca());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ManutencaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Manutencao manutencao) {
        String sql = "UPDATE manutencao SET cod_veiculo=?. descricao=?, dia=?, inicio=?, fim=? WHERE cod_manutencao=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, manutencao.getVeiculo().getPlaca());
            stmt.setString(2, manutencao.getDescricao());
            stmt.setString(3, manutencao.getDia().toString());
            stmt.setString(4, manutencao.getInicio());
            stmt.setString(5, manutencao.getFim());
            stmt.setInt(6, manutencao.getCodigo());
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
            stmt.setInt(1, manutencao.getCodigo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ManutencaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Map<Integer, Manutencao> quantidadeServicos(){
        String sql = "SELECT COUNT(ms) AS quantidade, m.cod_manutencao, \n" + 
"           m.descricao, m.cod_veiculo\n" +
"	FROM manutencao m\n" +
"	GROUP BY m.cod_manutencao, m.descricao, m.cod_veiculo\n" +
"	ORDER BY quantidade;";
        
        Map<Integer, Manutencao> retorno = new HashMap<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                Manutencao manutencao = new Manutencao();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retorno;
    }
    
    public List<Manutencao> listar() {
        String sql = "SELECT * FROM manutencao;";
        List<Manutencao> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                // Objetos de manutencao e veiculo
                Manutencao manutencao = new Manutencao();
                Veiculo veiculo = new Veiculo();
                
                // Define obtem a placa do veiculo e adiciona o objeto na manutencao
                manutencao.setCodigo(resultado.getInt("cod_manutencao"));
                manutencao.setDescricao(resultado.getString("descricao"));
                manutencao.setDia(resultado.getDate("dia"));
                manutencao.setInicio(resultado.getString("inicio"));
                manutencao.setFim(resultado.getString("fim"));
                
                veiculo.setPlaca(resultado.getString("cod_veiculo"));
                
                manutencao.setVeiculo(veiculo);
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
            stmt.setInt(1, manutencao.getCodigo());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setPlaca(resultado.getString("cod_veiculo"));
                manutencao.setCodigo(resultado.getInt("cod_manutencao"));
                manutencao.setDescricao(resultado.getString("descricao"));
                manutencao.setDia(resultado.getDate("dia"));
                manutencao.setInicio(resultado.getString("inicio"));
                manutencao.setFim(resultado.getString("fim"));
                
                // Adiciona o veiculo
                manutencao.setVeiculo(veiculo);

                retorno = manutencao;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManutencaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
