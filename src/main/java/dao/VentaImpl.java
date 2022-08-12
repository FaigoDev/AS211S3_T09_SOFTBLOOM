package dao;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import dao.conexion;
import dao.ICRUD;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Persona;
import model.Producto;
import model.Venta;
import model.VentaDetalle;

public class VentaImpl extends conexion {     
    DateFormat formato = new SimpleDateFormat("yyyy/MM/dd");

    public VentaDetalle adicionarFila(int idpro) throws SQLException, Exception {
        VentaDetalle ventadetalle = null;
        String sql = "SELECT * FROM PRODUCTO WHERE IDPRO = " + idpro;
        try {
            ResultSet rs;
            try (PreparedStatement ps = conectar().prepareStatement(sql)) {
                rs = ps.executeQuery();
                while (rs.next()) {
                    ventadetalle = new VentaDetalle();
                    Producto producto = new Producto();
                    ventadetalle.setIDPRO(rs.getInt("IDPRO"));
                    producto.setTIPPRO(rs.getString("TIPPRO"));
                    producto.setNOMPRO(rs.getString("NOMPRO"));
                    producto.setPREPRO(rs.getDouble("PREPRO"));
                    producto.setDESPRO(rs.getString("DESPRO"));
                    producto.setSTOCKPRO(rs.getInt("STOCKPRO"));
                    ventadetalle.setProducto(producto);
                }
            }
            rs.close();
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en add_D/UbigeoD: {0}", e.getMessage());
           
        } finally {
            cerrar();
        }
        return ventadetalle;
    }

    public void registrarVentaDetalle(List<VentaDetalle> listaVentaDetalle, int idVenta) throws Exception {
        String sql = "INSERT INTO VENTA_DETALLE (CANVENDET,SUBVENDET,IDVEN,IDPRO) VALUES (?,?,?,?)";
        try {
            try (PreparedStatement ps = conectar().prepareStatement(sql)) {
                for (VentaDetalle ventadetalle : listaVentaDetalle) {
                    ps.setInt(1, ventadetalle.getCANVENDET());
                    ps.setDouble(2, ventadetalle.getSUBVENDET());
                    ps.setInt(3, idVenta);
                    ps.setInt(4, ventadetalle.getIDPRO());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en registrarVentaDetalle_D/UbigeoD: {0}", e.getMessage());
        } finally {
            cerrar();
        }
    }

    public void registrarVenta(Venta venta) throws Exception {
        String sql = "INSERT INTO VENTA (FECVEN,TIPVEN,METPAGVEN,PRETOTVEN,IDPER) VALUES (?,?,?,?,?)";
        try {
            try (PreparedStatement ps = conectar().prepareStatement(sql)) {
                ps.setString(1, formato.format(venta.getFECVEN()));
                ps.setString(2, venta.getTIPVEN());
                ps.setString(3, venta.getMETPAGVEN());
                ps.setDouble(4, venta.getPRETOTVEN());
                ps.setInt(5, venta.getPersona().getCodigo());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en registrarVenta/UbigeoD: {0}", e.getMessage());
   
         
        } finally {
            cerrar();
        }
    }

    public int ultimoIdVenta() throws Exception {
        try {
            PreparedStatement ps1 = conectar().prepareStatement("SELECT MAX(V.IDVEN) AS IDVEN FROM VENTA V");
            try (ResultSet rs = ps1.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt("IDVEN");
                }
            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en ultimoIdVenta/UbigeoD: {0}", e.getMessage());
        }
        finally {
            cerrar();
        }
        return -1;
    }

    public List<Venta> listarVenta() throws Exception {
        List<Venta> listado = new ArrayList<>();
        Persona persona;
        Venta venta;
        String sql = "SELECT * FROM vVenta ORDER BY IDVEN DESC";
        try {
            try (Statement st = conectar().createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    venta = new Venta();
                    venta.setIDVEN(rs.getInt("IDVEN"));
                    venta.setFECVEN(rs.getDate("FECVEN"));
                    venta.setPRETOTVEN(rs.getDouble("PRETOTVEN"));
                    persona = new Persona();
                    persona.setCodigo(rs.getInt("IDPER"));
                    persona.setNombre(rs.getString("NOMPER"));
                    persona.setDni(rs.getString("DNIPER"));
                    venta.setPersona(persona);
                    listado.add(venta);
                }
            }
        } catch (SQLException e) {
              LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listarVenta/UbigeoD: {0}", e.getMessage());
        } finally {
            cerrar();
        }
        return listado;
    }
    
    
    public List<VentaDetalle> listarDet(int codigo) throws Exception {
        List<VentaDetalle> detalle = new ArrayList();
        VentaDetalle det;
        try (CallableStatement ps = conectar().prepareCall("{call spVentadetalle(?)}")){
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                det = new VentaDetalle();
                det.setIDVENDET(rs.getInt("IDVENDET"));
                det.getProducto().setNOMPRO(rs.getString("NOMPRO"));
                det.setCANVENDET(rs.getInt("CANPRO"));
                det.setSUBVENDET(rs.getDouble("SUBVENDET"));
                detalle.add(det);
            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listarDetVta/ventaImpl: {0}", e.getMessage());
        }
        finally {
            cerrar();
        }
        return detalle;
    }


}
