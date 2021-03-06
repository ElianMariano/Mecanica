package mecanica.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mecanica.model.domain.Manutencao;
import mecanica.model.domain.ManutencaoServico;
import mecanica.model.domain.Servicos;

public class ManutencaoServicoDAO {
    private Connection connection;
    
    public Connection getConnection(){
        return this.connection;
    }
    
    public void setConnection(Connection connection){
        this.connection = connection;
    }
    
    public boolean inserir(ManutencaoServico ms){
        String sql = "INSERT INTO manutencao_servico(cod_manutencao,"
                + " cod_servico) VALUES (?,?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ms.getManutencao().getCodigo());
            stmt.setInt(2, ms.getServico().getCodigo());
            
            stmt.execute();
            return true;
        }catch (SQLException ex) {
            Logger.getLogger(ManutencaoServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(ManutencaoServico ms){
        String sql = "UPDATE * manutencao_servico SET cod_manutencao=?, cod_servico=? WHERE codigo_ms=?;";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ms.getManutencao().getCodigo());
            stmt.setInt(2, ms.getServico().getCodigo());
            stmt.setInt(6, ms.getCodigo());
            
            stmt.execute();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(ManutencaoServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean remover(ManutencaoServico ms){
        String sql = "DELETE FROM manutencao_servico WHERE codigo_ms = ?;";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ms.getCodigo());
            
            stmt.execute();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(ManutencaoServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<ManutencaoServico> listar(){
        String sql = "SELECT * FROM manutencao_servico;";
        List<ManutencaoServico> retorno = new ArrayList<>();
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                // Instancia os objetos
                ManutencaoServico ms = new ManutencaoServico();
                
                // Define os dados da manutencao e do servico
                Manutencao manutencao = new Manutencao();
                Servicos servico = new Servicos();
                
                manutencao.setCodigo(resultado.getInt("cod_manutencao"));
                servico.setCodigo(resultado.getInt("cod_servico"));
                
                // Define esses dados no Manutencao Servico
                ms.setCodigo(resultado.getInt("codigo_ms"));
                ms.setManutencao(manutencao);
                ms.setServico(servico);
                
                // Adiciona na lista
                retorno.add(ms);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ManutencaoServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retorno;
    }
    
    public List<ManutencaoServico> listarPorManutencao(Manutencao m){
        String sql = "SELECT * FROM manutencao_servico WHERE cod_manutencao = ?;";
        List<ManutencaoServico> retorno = new ArrayList<>();
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, m.getCodigo());
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                // Instancia os objetos
                ManutencaoServico ms = new ManutencaoServico();
                
                // Define os dados da manutencao e do servico
                Manutencao manutencao = new Manutencao();
                Servicos servico = new Servicos();
                
                manutencao.setCodigo(resultado.getInt("cod_manutencao"));
                servico.setCodigo(resultado.getInt("cod_servico"));
                
                // Define esses dados no Manutencao Servico
                ms.setCodigo(resultado.getInt("codigo_ms"));
                ms.setManutencao(manutencao);
                ms.setServico(servico);
                
                // Adiciona na lista
                retorno.add(ms);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ManutencaoServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retorno;
    }
    
    public ManutencaoServico buscar(ManutencaoServico ms){
        String sql = "SELECT * FROM manutencao_servico WHERE codigo_ms=?;";
        ManutencaoServico retorno = new ManutencaoServico();
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ms.getCodigo());
            ResultSet resultado = stmt.executeQuery();
            
            if (resultado.first()){
                // Obtem o objeto manutencao e servico
                Manutencao manutencao = new Manutencao();
                Servicos servico = new Servicos();
                
                // Adiciona os codigos manutencao e servico
                manutencao.setCodigo(resultado.getInt("cod_manutencao"));
                servico.setCodigo(resultado.getInt("cod_servico"));
                
                // Adiciona os dados no retorno
                retorno.setManutencao(manutencao);
                retorno.setServico(servico);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ManutencaoServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retorno;
    }
}
