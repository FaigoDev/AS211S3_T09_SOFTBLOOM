package dao;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Compra;
import model.CompraDetalle;

public class CompraImpl extends conexion implements ICRUD<Compra> {

    @Override
    public void registrar(Compra com) throws Exception {
        try {
            String sql = "insert into compra (FECCOM,METPAGCOM,PRETOTCOM,ESTCOM,IDPROV) values (?,?,?,'A',?)";
            try (PreparedStatement ps = conectar().prepareStatement(sql)) {
                SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd");
                ps.setString(1, forma.format(com.getFECCOM()));
                ps.setString(2, com.getMETPAGCOM());
                ps.setDouble(3, com.getPRETOTCOM());
                ps.setInt(4, com.getIDPROV());
                
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en CompraImpl/registrar {0}", e.getMessage());
        }
        finally {
            cerrar();
        }
    }

    public int obtenerUltimoId() throws Exception {
        try (PreparedStatement ps1 = conectar().prepareStatement("SELECT MAX(IDCOM) as IDCOMPRA FROM COMPRA")){
            
            try (ResultSet rs = ps1.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt("IDCOMPRA");
                }
            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en obtenerUltimoId/CompraImpl: {0}", e.getMessage());
        }
        finally {
            cerrar();
        }
        return -1;
        
    }

    public void registrarDetalle(List<CompraDetalle> comdet, int id) throws Exception {
        try {
            String sql = "insert into compra_detalle (CANPRO,IDCOM,SUBTOT,IDPRO,PRECOMPRO) values (?,?,?,?,?)";
            try (PreparedStatement ps = conectar().prepareStatement(sql)) {
                for (CompraDetalle compradetall : comdet) {
                    ps.setInt(1, compradetall.getCANPRO());
                    ps.setInt(2, id);
                    ps.setDouble(3, compradetall.getSUBTOTCOM());
                    ProductoImpl prodao = new ProductoImpl();
                    compradetall.setIDPRO(prodao.obtenerCodigoProducto(compradetall.getProducto().getNOMPRO()));
                    ps.setInt(4, compradetall.getIDPRO());
                    ps.setDouble(5, compradetall.getPRECOMPRO());
                    actualizarStock(compradetall.getProducto().getSTOCKPRO(), compradetall.getIDPRO());
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en CompraDetalleImpl/registrar: {0}", e.getMessage());
        }finally {
            cerrar();
        }
    }

    public void actualizarStock(int stock, int id) throws Exception {
        try {
            String sql = "UPDATE PRODUCTO set STOCKPRO = ? where IDPRO = ? ";
            try (PreparedStatement ps = conectar().prepareStatement(sql)) {
                ps.setInt(1, stock);
                ps.setInt(2, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en actualizarStock/CompraImpl: {0}", e.getMessage());
        }finally {
            cerrar();
        }
    }

    public Double obtenerPrecioProducto(String cadenaPro) throws Exception {
        String sql = "select PREPRO FROM PRODUCTO WHERE concat(NOMPRO, ', ', TIPPRO) = ?";
        try (PreparedStatement ps = conectar().prepareCall(sql)){
            ps.setString(1, cadenaPro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                return rs.getDouble("PREPRO");
            }
            return rs.getDouble("PREPRO");
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en ObtenerPrecioProducto CompraImpl: {0}", e.getMessage());
            throw e;
        }
        finally {
            cerrar();
        }
    }

    public int obtenerStockProducto(String cadenaPro) throws Exception {
        String sql = "select STOCKPRO FROM PRODUCTO WHERE concat(NOMPRO, ', ', TIPPRO) = ?";
        try (PreparedStatement ps = conectar().prepareCall(sql)){
            
            ps.setString(1, cadenaPro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                return rs.getInt("STOCKPRO");
            }
            return rs.getInt("STOCKPRO");
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en obtenerStockProducto/CompraImpl: {0}", e.getMessage());
            throw e;
        }
        finally {
            cerrar();
        }
    }

    @Override
    public void modificar(Compra arg0) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(Compra arg0) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Compra> listarTodos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Compra> listar() throws Exception {
        String sql = "SELECT * FROM VCompra";

        ArrayList<Compra> listado = new ArrayList<>();
        Compra coms;
        try (Statement st = conectar().createStatement();
                ResultSet rs = st.executeQuery(sql)){
            
            
            while (rs.next()) {
                coms = new Compra();
                SimpleDateFormat forma = new SimpleDateFormat("dd-MM-yyyy");
                coms.setIDCOM(rs.getInt("IDCOM"));
                coms.setFECHA(forma.format(rs.getDate("FECCOM")));
                coms.setMETPAGCOM(rs.getString("METPAGCOM"));
                coms.setPRETOTCOM(rs.getDouble("PRETOTCOM"));
                listado.add(coms);
            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listar/CompraImpl: {0}", e.getMessage());
        }
        finally {
            cerrar();
        }
        return listado;
    }

    public List<CompraDetalle> listarDet(int codigo) throws Exception {
        List<CompraDetalle> detalle = new ArrayList();
        CompraDetalle det;
        try (CallableStatement ps = conectar().prepareCall("{call spCompradetalle(?)}")){

            
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                det = new CompraDetalle();
                det.setIDCOM(rs.getInt("IDCOM"));
                det.setIDCOMDET(rs.getInt("IDCOMDET"));
                det.getProducto().setNOMPRO(rs.getString("NOMPRO"));
                det.setCANPRO(rs.getInt("CANPRO"));
                det.setPRECOMPRO(rs.getDouble("PREPRO"));
                det.setSUBTOTCOM(rs.getDouble("SUBTOT"));
                detalle.add(det);
            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listarDetVta/CompraImpl: {0}", e.getMessage());
        }
        finally {
            cerrar();
        }
        return detalle;
    }

}
