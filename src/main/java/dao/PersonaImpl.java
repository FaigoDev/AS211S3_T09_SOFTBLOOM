package dao;


import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Persona;

public class PersonaImpl extends conexion implements ICRUD<Persona> {

    public void guardar(Persona cliente) throws Exception {

        try {
            String sql = "insert into persona"
                    + " (NOMPER,APEPER,DNIPER,CELPER,DIRPER,EMAPER,TIPPER,CODUBI,ESTPER)"
                    + " values (?,?,?,?,?,?,'C',?,'A') ";
            try ( PreparedStatement ps = conectar().prepareStatement(sql)) {
                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getApellido());
                ps.setString(3, cliente.getDni());
                ps.setString(4, cliente.getCelular());
                ps.setString(5, cliente.getDirreccion());
                ps.setString(6, cliente.getCorreo());
                ps.setString(7, cliente.getUbigeo());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en registrar/PersonaImpl: {0}", e.getMessage());
        } finally {
            cerrar();
        }
    }

    @Override
    public void modificar(Persona cliente) throws Exception {
        try {
            String sql = "update persona set NOMPER=?,APEPER=?,DNIPER=?,CELPER=?,DIRPER=?,EMAPER=?,CODUBI=? where IDPER=?";
            try ( PreparedStatement ps = conectar().prepareStatement(sql)) {
                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getApellido());
                ps.setString(3, cliente.getDni());
                ps.setString(4, cliente.getCelular());
                ps.setString(5, cliente.getDirreccion());
                ps.setString(6, cliente.getCorreo());
                ps.setString(7, cliente.getUbigeo());
                ps.setInt(8, cliente.getCodigo());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en modificar/PersonaImpl: {0}", e.getMessage());
        } finally {
            cerrar();
        }
    }

    @Override
    public void eliminar(Persona cli) throws Exception {
        try {
            String sql = "UPDATE persona set ESTPER = 'I' where IDPER=?";
            try ( PreparedStatement ps = conectar().prepareStatement(sql)) {
                ps.setInt(1, cli.getCodigo());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en eliminar/PersonaImpl: {0}", e.getMessage());

        } finally {
            cerrar();
        }
    }

    public void restaurar(Persona per) throws Exception {
        try {
            String sql = "UPDATE persona set ESTPER = 'A' where IDPER=?";
            try ( PreparedStatement ps = conectar().prepareStatement(sql)) {
                ps.setInt(1, per.getCodigo());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en restaurar/PersonaImpl: {0}", e.getMessage());
        } finally {

            cerrar();

        }
    }

    public int validar(Persona per, int caso) throws Exception {


        String SQL1 = "SELECT IDPER FROM PERSONA WHERE DNIPER = ?";
        try ( PreparedStatement ps1 = conectar().prepareCall(SQL1)) {
            ps1.setString(1, per.getDni());
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                caso = 1; //Dni existente
            } else {
                String SQL2 = "SELECT IDPER FROM PERSONA WHERE DNIPER = ? AND NOMPER = ? AND APEPER = ?";
                PreparedStatement ps2 = conectar().prepareCall(SQL2);
                ps2.setString(1, per.getDni());
                ps2.setString(2, per.getNombre());
                ps2.setString(3, per.getApellido());
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    caso = 2; //Dni, nombre y apellido existente
                } else {
                    String SQL3 = "SELECT IDPER FROM PERSONA WHERE CELPER = ?";
                    PreparedStatement ps3 = conectar().prepareCall(SQL3);
                    ps3.setString(1, per.getCelular());
                    ResultSet rs3 = ps3.executeQuery();
                    if (rs3.next()) {
                        caso = 3; //Celular existente
                    } else {
                        String SQL4 = "SELECT IDPER FROM PERSONA WHERE EMAPER = ?";
                        PreparedStatement ps4 = conectar().prepareCall(SQL4);
                        ps4.setString(1, per.getCorreo());
                        ResultSet rs4 = ps4.executeQuery();
                        if (rs4.next()) {
                            caso = 4; //Correo existente
                        }

                    }

                }

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

    public List<Persona> listar(int estado) throws Exception {
        List<Persona> lista = new ArrayList<>();

        ResultSet rs;
        String sql = "";
        switch (estado) {
            case 1:
                sql = "SELECT * FROM VCliente WHERE ESTCLI ='A'";
                break;
            case 2:
                sql = "SELECT * FROM VCliente WHERE ESTCLI ='I'";
                break;
            case 3:
                sql = "select * from VCliente";
                break;
        }

        try ( PreparedStatement ps = conectar().prepareStatement(sql)) {

            rs = ps.executeQuery();

            while (rs.next()) {
                Persona cli = new Persona();
                cli.setCodigo(rs.getInt("IDCLI"));
                cli.setNombre(rs.getString("NOMCLI"));
                cli.setApellido(rs.getString("APECLI"));
                cli.setDni(rs.getString("DNICLI"));
                cli.setCelular(rs.getString("CELCLI"));
                cli.setDirreccion(rs.getString("DIRCLI"));
                cli.setCorreo(rs.getString("EMACLI"));
                cli.setUbigeo(rs.getString("CODUBI"));
                cli.setEstado(rs.getString("ESTCLI"));
                lista.add(cli);
            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listar/PersonaImpl: {0}", e.getMessage());
        } finally {
            cerrar();
        }
        return lista;
    }

    public void cambiarEstado(Persona cliente) throws Exception {
        try {
            String sql = "update cliente set ESTCLI=? where IDCLI=?";
            try ( PreparedStatement ps = conectar().prepareStatement(sql)) {
                ps.setString(1, cliente.getEstado());
                ps.setInt(2, cliente.getCodigo());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en cambiarEstado/PersonaImpl: {0}", e.getMessage());
        } finally {
            cerrar();
        }
    }

    public List<String> autoCompleteUbigeo1(String consulta, String departamento) throws Exception {
        List<String> lista = new ArrayList<>();

        String sql = "select concat(DISUBI, ', ', PROUBI) AS UBIGEODESC from UBIGEO WHERE DPTUBI = ? AND DISUBI LIKE ? limit 10";
        try ( PreparedStatement ps = conectar().prepareCall(sql)) {

            ps.setString(1, departamento);
            ps.setString(2, "%" + consulta + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("UBIGEODESC"));
            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en autoCompleteUbigeoDao/PersonaImpl: {0}", e.getMessage());
        } finally {
            cerrar();
        }
        return lista;
    }

    public String obtenerCodigoUbigeo1(String cadenaUbi) throws Exception {
        String sql = "select CODUBI FROM UBIGEO WHERE concat(DISUBI, ', ', PROUBI) = ? or DISUBI = ?";
        try ( PreparedStatement ps = conectar().prepareCall(sql)) {

            ps.setString(1, cadenaUbi);
            ps.setString(2, cadenaUbi);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("CODUBI");
            }
            return rs.getString("CODUBI");
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en obtenerCodigoUbigeo/PersonaImpl: {0}", e.getMessage());
            throw e;
        } finally {
            cerrar();
        }
    }

    @Override
    public void registrar(Persona obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Persona> listarTodos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
