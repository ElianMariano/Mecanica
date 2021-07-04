package mecanica.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mecanica.model.domain.Cliente;
import mecanica.model.domain.ModeloVeiculo;
import mecanica.model.domain.Veiculo;

public class VeiculoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Veiculo veiculo) {
        String sql = "INSERT INTO veiculo (placa, nome, marca, cod_modelo, cod_cliente) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getNome());
            stmt.setString(3, veiculo.getMarca());
            stmt.setInt(4, veiculo.getModelo().getCodigo());
            stmt.setString(5, veiculo.getCliente().getCpf());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Veiculo veiculo) {
        String sql = "UPDATE veiculo SET nome=?, marca=?, cod_modelo=?, cod_cliente=? WHERE placa=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getNome());
            stmt.setString(2, veiculo.getMarca());
            stmt.setInt(3, veiculo.getModelo().getCodigo());
            stmt.setString(4, veiculo.getCliente().getCpf());
            stmt.setString(5, veiculo.getPlaca());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Veiculo veiculo) {
        String sql = "DELETE FROM veiculo WHERE placa=?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Veiculo> listar() {
        String sql = "SELECT * FROM veiculo;";
        List<Veiculo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                // Instancia as classes
                Veiculo veiculo = new Veiculo();
                ModeloVeiculo modelo = new ModeloVeiculo();
                Cliente cliente = new Cliente();
                
                // Obtem os dados do veiculo
                veiculo.setPlaca(resultado.getString("placa"));
                veiculo.setNome(resultado.getString("nome"));
                veiculo.setMarca(resultado.getString("marca"));
                
                // Define os indices do modelo e cliente
                modelo.setCodigo(resultado.getInt("cod_modelo"));
                cliente.setCpf(resultado.getString("cod_cliente"));
                
                // Obtem os dados do ModeloVeiculo
                ModeloVeiculoDAO modeloDao = new ModeloVeiculoDAO();
                modeloDao.setConnection(connection);
                modelo = modeloDao.buscar(modelo);
                
                // Obtem os dados do cliente
                ClienteDAO clienteDao = new ClienteDAO();
                clienteDao.setConnection(connection);
                cliente = clienteDao.buscar(cliente);
                
                // Atribui o cliente e o modelo ao veiculo
                veiculo.setModelo(modelo);
                veiculo.setCliente(cliente);
                
                retorno.add(veiculo);
                //Lembrar de mudar o nome e o tipo das variaveis no banco(modelo e cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<Veiculo> listarPorDono(Cliente dono) {
        String sql = "SELECT v.placa, v.nome, v.marca,\n" +
"	   v.cod_cliente, v.cod_modelo\n" +
"	FROM veiculo v\n" +
"	INNER JOIN cliente c ON c.cpf = v.cod_cliente\n" +
"	WHERE c.cpf = ?;";
        
        List<Veiculo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, dono.getCpf());
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                // Instancia as classes
                Veiculo veiculo = new Veiculo();
                ModeloVeiculo modelo = new ModeloVeiculo();
                Cliente cliente = new Cliente();
                
                // Obtem os dados do veiculo
                veiculo.setPlaca(resultado.getString("placa"));
                veiculo.setNome(resultado.getString("nome"));
                veiculo.setMarca(resultado.getString("marca"));
                
                // Define os indices do modelo e cliente
                modelo.setCodigo(resultado.getInt("cod_modelo"));
                cliente.setCpf(resultado.getString("cod_cliente"));
                
                // Obtem os dados do ModeloVeiculo
                ModeloVeiculoDAO modeloDao = new ModeloVeiculoDAO();
                modeloDao.setConnection(connection);
                modelo = modeloDao.buscar(modelo);
                
                // Obtem os dados do cliente
                ClienteDAO clienteDao = new ClienteDAO();
                clienteDao.setConnection(connection);
                cliente = clienteDao.buscar(cliente);
                
                // Atribui o cliente e o modelo ao veiculo
                veiculo.setModelo(modelo);
                veiculo.setCliente(cliente);
                
                retorno.add(veiculo);
                //Lembrar de mudar o nome e o tipo das variaveis no banco(modelo e cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Veiculo buscar(Veiculo veiculo) {
        String sql = "SELECT * FROM veiculo WHERE placa=?;";
        Veiculo retorno = new Veiculo();
        ModeloVeiculo modelo = new ModeloVeiculo();
        Cliente cliente = new Cliente();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                veiculo.setNome(resultado.getString("nome"));
                veiculo.setMarca(resultado.getString("marca"));
                
                // Obtem o modelo e cliente
                modelo.setCodigo(resultado.getInt("cod_modelo"));
                cliente.setCpf(resultado.getString("cod_cliente"));
                
                // Define o modelo e cliente no objeto veiculo
                veiculo.setModelo(modelo);
                veiculo.setCliente(cliente);

                retorno = veiculo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
