package sevlet.dao;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class conexion {
        
    public static Connection cnx = null;
    public static Connection conectar() throws Exception {
            String user = "sa";
            String pwd = "@abc123@";
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=DBsoftbloom";
        try {
            Class.forName(driver).newInstance();
            cnx = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error de conexion: {0}", e.getMessage());
        }
        return cnx;
    }

    public static void cerrar() throws Exception {
        if (cnx != null) {
            if (cnx.isClosed() == false) {
                cnx.close();
            }
        }
    }


    

    

  

    public static void main(String[] args) throws Exception {
        conectar();
        if(cnx != null) System.out.println("Estoy Conectado");

    }

    

}