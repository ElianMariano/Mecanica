package mecanica.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            stmt.setInt(4, veiculo.getCdModelo());
            stmt.setString(5, veiculo.getCdCliente());
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
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getNome());
            stmt.setString(3, veiculo.getMarca());
            stmt.setInt(4, veiculo.getCdModelo());
            stmt.setString(5, veiculo.getCdCliente());
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
                Veiculo veiculo = new Veiculo();
                veiculo.setPlaca(resultado.getString("placa"));
                veiculo.setNome(resultado.getString("nome"));
                veiculo.setMarca(resultado.getString("marca"));
                veiculo.setCdModelo(resultado.getInt("cod_modelo"));
                veiculo.setCdCliente(resultado.getString("cod_cliente"));
                retorno.add(veiculo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Veiculo buscar(Veiculo veiculo) {
        String sql = "SELECT * FROM veiculo WHERE placa=?;";
        Veiculo retorno = new Veiculo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                veiculo.setNome(resultado.getString("nome"));
                veiculo.setMarca(resultado.getString("marca"));
                veiculo.setCdModelo(resultado.getInt("cod_modelo"));
                veiculo.setCdCliente(resultado.getString("cod_cliente"));

                retorno = veiculo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
