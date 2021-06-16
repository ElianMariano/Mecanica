package mecanica.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mecanica.model.domain.Cliente;

public class ClienteDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (cpf, nome, nascimento, cidade, uf) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setDate(3, Date.valueOf(cliente.getNascimento()));
            stmt.setString(4, cliente.getCidade());
            stmt.setString(5, cliente.getUf());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome=?, nascimento=?, cidade=?, uf=? WHERE cpf=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setDate(2, Date.valueOf(cliente.getNascimento()));
            stmt.setString(3, cliente.getCidade());
            stmt.setString(4, cliente.getUf());
            stmt.setString(5, cliente.getCpf());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Cliente cliente) {
        String sql = "DELETE FROM cliente WHERE cpf=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getCpf());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Cliente> listar() {
        String sql = "SELECT * FROM cliente;";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setNascimento(resultado.getDate("nascimento").toString());
                cliente.setCidade(resultado.getString("cidade"));
                cliente.setUf(resultado.getString("uf"));
                retorno.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Cliente buscar(Cliente cliente) {
        String sql = "SELECT * FROM cliente WHERE cpf=?;";
        Cliente retorno = new Cliente();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getCpf());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                cliente.setNome(resultado.getString("nome"));
                cliente.setNascimento(resultado.getDate("nascimento").toString());
                cliente.setCidade(resultado.getString("cidade"));
                cliente.setUf(resultado.getString("uf"));

                retorno = cliente;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
