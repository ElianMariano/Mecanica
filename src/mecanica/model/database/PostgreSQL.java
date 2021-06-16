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
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mecanica", user, password);
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
