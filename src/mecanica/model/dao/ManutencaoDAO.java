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
import java.sql.Date;
import mecanica.model.domain.Manutencao;
import java.sql.Time;
import java.time.LocalDateTime;
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
        String sql = "INSERT INTO manutencao (descricao, dia, inicio, fim, cod_veiculo) VALUES (?,?,Cast(? AS TIME),Cast(? AS TIME),?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, manutencao.getDescricao());
            stmt.setDate(2, Date.valueOf(manutencao.getDia()));
            stmt.setString(3, manutencao.getInicio());
            stmt.setString(4, manutencao.getFim());
            stmt.setString(5, manutencao.getVeiculo().getPlaca());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ManutencaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Manutencao manutencao) {
        String sql = "UPDATE manutencao SET cod_veiculo=?, descricao=?, dia=?, inicio=Cast(? AS TIME), fim=Cast(? AS TIME) WHERE cod_manutencao=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, manutencao.getVeiculo().getPlaca());
            stmt.setString(2, manutencao.getDescricao());
            stmt.setDate(3, Date.valueOf(manutencao.getDia()));
            stmt.setString(4, manutencao.getInicio());
            stmt.setString(5, manutencao.getFim());
            stmt.setInt(6, manutencao.getCodigo());
            stmt.execute();
            System.out.println("Editado");
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
    
    // Retorna true se o horario é disponivel
    public boolean horarioDisponivel(Manutencao manutencao) {
        String sql = "SELECT (COUNT(*) = 0) disponivel\n" +
"	FROM manutencao m\n" +
"	WHERE m.dia = ? AND \n" +
"	((Cast(? AS TIME) < m.inicio OR Cast(? AS TIME) < m.fim) AND\n" +
"	(Cast(? AS TIME) > m.inicio OR Cast(? AS TIME) > m.fim));";
        
        boolean retorno = false;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(manutencao.getDia()));
            stmt.setString(2, manutencao.getInicio());
            stmt.setString(3, manutencao.getInicio());
            stmt.setString(4, manutencao.getFim());
            stmt.setString(5, manutencao.getFim());
            ResultSet resultado = stmt.executeQuery();
            
            // Valor de retorno
            if (resultado.next()) retorno = resultado.getBoolean("disponivel");
        } catch (SQLException ex) {
            Logger.getLogger(ManutencaoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
                manutencao.setDia(resultado.getDate("dia").toLocalDate());
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
                manutencao.setDia(resultado.getDate("dia").toLocalDate());
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
    
    public Manutencao buscarUltimaManutencao() {
        String sql = "SELECT MAX(m.cod_manutencao) AS cod_manutencao "
                + "FROM manutencao m;";
        Manutencao retorno = new Manutencao();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.setCodigo(resultado.getInt("cod_manutencao"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManutencaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
