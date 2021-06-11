package mecanica.model.database;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQL{
    private Connection connection;
    
    public Connection conectar(){
        try{
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mecanica");
            return this.connection;
        }
        catch(SQLException | ClassNotFoundException ex){
            Logger.getLogger(PostgreSQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void desconectar(Connection connection){
        try{
            connection.close();
        }
        catch(SQLException ex){
            Logger.getLogger(PostgreSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
