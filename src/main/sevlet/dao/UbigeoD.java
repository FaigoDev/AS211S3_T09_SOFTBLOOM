package dao;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UbigeoD extends conexion {

    public List<String> autoCompleteCliente(String consulta) throws Exception {
        List<String> lista = new ArrayList<>();

        String sql = "select top 5 CONCAT(DISUBI, ', ', PROUBI, ', ',DEPUBI) AS CODUBI from UBIGEO WHERE  DISUBI LIKE ?";
        try (PreparedStatement ps = conectar().prepareCall(sql)){
            
            ps.setString(1, "%" + consulta + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("CODUBI"));
            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en autoCompleteUbigeoDao/UbigeoD: {0}", e.getMessage());
        } finally {
            cerrar();
        }
        return lista;
    }

    public String obtenerCodigUbigeo(String cadenaUbi) throws Exception {
        String sql = "select CODUBI FROM UBIGEO WHERE CONCAT(DISUBI, ', ', PROUBI, ', ',DEPUBI) = ?";
        try  (PreparedStatement ps = conectar().prepareCall(sql)){
           
            ps.setString(1, cadenaUbi);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("CODUBI");
            }
            return rs.getString("CODUBI");
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en obtenerCodigoUbigeo/UbigeoD: {0}", e.getMessage());
            throw e;
        } finally {
            cerrar();
        }
    }
}
