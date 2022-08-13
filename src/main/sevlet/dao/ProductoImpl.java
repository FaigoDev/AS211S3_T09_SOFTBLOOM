package dao;

import java.util.List;
import model.Producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ProductoImpl extends conexion implements ICRUD<Producto> {

    @Override
    public void registrar(Producto pro) throws Exception {
        try {
            String sql = "insert into PRODUCTO (NOMPRO,TIPPRO,PREPRO,DESPRO,STOCKPRO,ESTPRO,IDPROV) values (?,?,?,?,?,'A',?) ";
            try (PreparedStatement ps = conectar().prepareStatement(sql)) {
                ps.setString(1, pro.getNOMPRO());
                ps.setString(2, pro.getTIPPRO());
                ps.setDouble(3, pro.getPREPRO());
                ps.setString(4, pro.getDESPRO());
                ps.setInt(5, pro.getSTOCKPRO());
                ps.setInt(6, pro.getIDPROV());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en registrar/ProductoImpl: {0}", e.getMessage());
        } finally {
            cerrar();
        }
    }

    @Override
    public void modificar(Producto pro) throws Exception {
        try {
            String sql = "update PRODUCTO set NOMPRO = ?, TIPPRO = ?,PREPRO = ? ,DESPRO = ?,STOCKPRO=? ,IDPROV=? ,ESTPRO = ? where IDPRO = ?";
            try (PreparedStatement ps = conectar().prepareStatement(sql)) {
                ps.setString(1, pro.getNOMPRO());
                ps.setString(2, pro.getTIPPRO());
                ps.setDouble(3, pro.getPREPRO());
                ps.setString(4, pro.getDESPRO());
                ps.setInt(5, pro.getSTOCKPRO());
                ps.setInt(6, pro.getIDPROV());
                ps.setString(7, pro.getESTPRO());
                ps.setInt(8, pro.getIDPRO());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en modificar/ProductoImpl: {0}", e.getMessage());
        } finally {

            cerrar();

        }
    }

    @Override
    public void eliminar(Producto pro) throws Exception {
        try {
            String sql = "UPDATE PRODUCTO set ESTPRO = 'I' where IDPRO=?";
            try ( PreparedStatement ps = ProductoImpl.conectar().prepareStatement(sql)) {
                ps.setInt(1, pro.getIDPRO());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en eliminar/ProductoImpl: {0}", e.getMessage());
        } finally {

            cerrar();

        }
    }

    public void restaurar(Producto pro) throws Exception {
        try {
            String sql = "UPDATE PRODUCTO set ESTPRO = 'A' where IDPRO=?";
            try ( PreparedStatement ps = conectar().prepareStatement(sql)) {
                ps.setInt(1, pro.getIDPRO());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en restaurar/ProductoImpl: {0}", e.getMessage());
        } finally {

            cerrar();

        }
    }

    public List<Producto> listarD(int estado) throws Exception {
        List<Producto> listado = null;

        Producto pros;

        String SQL = "";
        switch (estado) {
            case 1:
                SQL = "SELECT * FROM VProducto WHERE ESTPRO ='A'";
                break;

            case 2:
                SQL = "SELECT * FROM VProducto WHERE ESTPRO ='I'";
                break;
            case 3:
                SQL = "SELECT * FROM VProducto";
                break;
        }

        try {

            listado = new ArrayList();

            try (Statement st = conectar().createStatement(); ResultSet rs = st.executeQuery(SQL)) {
                
                while (rs.next()) {
                    
                    pros = new Producto();
                    
                    pros.setIDPRO(rs.getInt("IDPRO"));
                    
                    pros.setNOMPRO(rs.getString("NOMPRO"));
                    
                    pros.setTIPPRO(rs.getString("TIPPRO"));
                    
                    pros.setPREPRO(rs.getDouble("PREPRO"));
                    
                    pros.setDESPRO(rs.getString("DESPRO"));
                    
                    pros.setESTPRO(rs.getString("ESTPRO"));
                    
                    pros.setSTOCKPRO(rs.getInt("STOCKPRO"));
                    
                    pros.getProv().setRAZSOCPROV(rs.getString("PROVEEDOR"));
                    listado.add(pros);
                }
                
            }

        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en ListarProductoD/ProductoImpl: {0}", e.getMessage());

        } finally {

            cerrar();

        }

        return listado;
    }

    public List<String> autoCompleteProveedor(String consulta) throws Exception {
        List<String> lista = new ArrayList<>();
        String sql = "select top 10 concat(RAZSOCPROV, ', ', RUCPROV) AS PROVEEDORDESC from PROVEEDOR WHERE ESTPROV ='A' AND RAZSOCPROV LIKE ?";
        try (PreparedStatement ps = conectar().prepareCall(sql)){
            
            ps.setString(1, "%" + consulta + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("PROVEEDORDESC"));
            }
        } catch (Exception e) {
             LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en autoCompleteProveedorDao/ProductoImpl: {0}", e.getMessage());
          
        } finally {

            cerrar();

        }
        return lista;
    }

    public int validar(Producto pro, int caso) throws Exception {
        String SQL = "SELECT IDPRO FROM PRODUCTO WHERE NOMPRO = ? AND TIPPRO = ?";
        try    (PreparedStatement ps = conectar().prepareCall(SQL)){
         
            ps.setString(1, pro.getNOMPRO());
            ps.setString(2, pro.getTIPPRO());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                caso = 1;
            }
        } catch (Exception e) {
              LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en validar/ProductoImpl: {0}", e.getMessage());
        } finally {

            cerrar();

        }

        return caso;

    }

    public int validarProductoModificar(Producto pro, int caso) throws Exception {
        String SQL = "SELECT IDPRO FROM PRODUCTO WHERE NOMPRO = ? AND TIPPRO = ? AND PREPRO = ? AND STOCKPRO = ?";
        try {
            try ( PreparedStatement ps = conectar().prepareCall(SQL)) {
                ps.setString(1, pro.getNOMPRO());
                ps.setString(2, pro.getTIPPRO());
                ps.setDouble(3, pro.getPREPRO());
                ps.setInt(4, pro.getSTOCKPRO());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    caso = 1;
                }
            }
        } catch (SQLException e) {
             LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en validarProductoModificar/ProductoImpl: {0}", e.getMessage());
        } finally {

            cerrar();

        }
        return caso;
    }

    public int obtenerCodigoProveedor(String cadenaProv) throws Exception {
        String sql = "select IDPROV FROM PROVEEDOR WHERE concat(RAZSOCPROV, ', ', RUCPROV) = ?";
        try (PreparedStatement ps = conectar().prepareCall(sql)){
            
            ps.setString(1, cadenaProv);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("IDPROV");
            }
            return rs.getInt("IDPROV");
        } catch (Exception e) {
                 LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en obtenerCodigoProveedor/ProductoImpl: {0}", e.getMessage());
            throw e;
        } finally {

            cerrar();

        }
    }

    public List<String> autoCompleteProducto(String consulta) throws Exception {
        List<String> lista = new ArrayList<>();

        String sql = "select top 10 concat(NOMPRO, ', ', TIPPRO) AS PRODUCTODESC from PRODUCTO WHERE ESTPRO ='A' AND NOMPRO LIKE ?";
        try (PreparedStatement ps = conectar().prepareCall(sql)){
            
            ps.setString(1, "%" + consulta + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("PRODUCTODESC"));
            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en autoCompleteProveedorDao/ProductoImpl: {0}", e.getMessage());
        } finally {

            cerrar();

        }
        return lista;
    }
    
    public List<String> autoCompleteProductoProv(String consulta, int idprovcom) throws Exception {
        List<String> lista = new ArrayList<>();

        String sql = "select top 10 concat(NOMPRO, ', ', TIPPRO) AS PRODUCTODESC from PRODUCTO WHERE ESTPRO ='A' AND NOMPRO LIKE ? AND IDPROV = ?";
        try (PreparedStatement ps = conectar().prepareCall(sql)){
            
            ps.setString(1, "%" + consulta + "%");
            ps.setInt(2, idprovcom);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("PRODUCTODESC"));
            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en autoCompleteProveedorDao/ProductoImpl: {0}", e.getMessage());
        } finally {

            cerrar();

        }
        return lista;
    }

    public int obtenerCodigoProducto(String cadenaPro) throws Exception {
        String sql = "select IDPRO FROM PRODUCTO WHERE concat(NOMPRO, ', ', TIPPRO) = ?";
        try ( PreparedStatement ps = conectar().prepareCall(sql)){
           
            ps.setString(1, cadenaPro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("IDPRO");
            }
            return rs.getInt("IDPRO");
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en obtenerIdProducto/ProductoImpl: {0}", e.getMessage());
            throw e;
        } finally {

           cerrar();

        }
    }

    @Override
    public List<Producto> listarTodos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
