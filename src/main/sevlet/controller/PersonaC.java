package sevlet.controller;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import sevlet.dao.PersonaImpl;
import sevlet.dao.UbigeoD;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import sevlet.model.Persona;
import org.primefaces.PrimeFaces;
import sevlet.service.Reporte;

@Named(value = "personaC")
@SessionScoped
public class PersonaC implements Serializable {

    private String departamento;
    private Persona persona;
    private PersonaImpl dao;
    private UbigeoD daoubi;
    private List<Persona> lstcCliente;
    private List<String> listaDpto;
    private int estado = 1;

    public PersonaC() {
        persona = new Persona();
        dao = new PersonaImpl();
        daoubi = new UbigeoD();
    }

    public void registrar() throws Exception {
        try {

            
            int caso = 0;
            caso = dao.validar(persona, caso);

            switch (caso) {
                case 0:
                    persona.setUbigeo(daoubi.obtenerCodigUbigeo(persona.getUbigeo()));
                    dao.guardar(persona);
                    listar();
                    limpiar();
                    PrimeFaces.current().ajax().update("frmregistrar:dlgClientes");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado", "Registrado con éxito"));
                    break;
                case 1:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "DNI ya existe"));
                    break;
                case 2:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cliente ya existe"));
                    break;
                case 3:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Celular ya existe"));
                    break;
                case 4:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Correo ya existe"));
                    break;

            }

        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en en registrar/PersonaC: {0}", e.getMessage());
        }
    }

    public void modificar() throws Exception {
        try {
            persona.setUbigeo(daoubi.obtenerCodigUbigeo(persona.getUbigeo()));
            dao.modificar(persona);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado", "Registrado con éxito"));
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en en modificar/PersonaC: {0}", e.getMessage());

        }
    }

    public void eliminar(Persona cli) throws Exception {
        try {

            dao.eliminar(cli);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminado", "Eliminado con éxito"));
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en en eliminar/PersonaC: {0}", e.getMessage());
        }
    }

    public void restaurar(Persona per) throws Exception {
        try {
            dao.restaurar(per);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Restauraste este producto exitosamente"));
            limpiar();
            listar();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en restaurarC Producto: {0}", e.getMessage());
        }

    }

    public void listar() throws Exception {
        try {
            System.out.println("Mi estadolista es " + estado);
            lstcCliente = dao.listar(estado);
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en en listar/PersonaC: {0}", e.getMessage());
        }
    }

    public void verReporte() {
        try {
            Reporte reporte = new Reporte();
            String ruta = "/resources/reports/Cliente.jasper";
            System.out.println("Mi ruta es: " + ruta);
            reporte.verPdf(ruta, 1);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error controller.ProductoC/verReporte: {0}", e.getMessage());
        }

    }

    public void descargarReporte() {
        try {
            Reporte reporte = new Reporte();
            String ruta = "/resources/reports/Cliente.jasper";
            reporte.descarcarPdf(ruta, "Listado_Clientes.pdf", 1);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Descargaste exitosamente"));
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error controller.ProductoC.descargarReporte: {0}", e.getMessage());
        }
    }

    public void limpiar() {
        persona = new Persona();
    }

    public PersonaImpl getDao() {
        return dao;
    }

    public void setDao(PersonaImpl dao) {
        this.dao = dao;
    }

    public UbigeoD getDaoubi() {
        return daoubi;
    }

    public void setDaoubi(UbigeoD daoubi) {
        this.daoubi = daoubi;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Persona> getLstcCliente() {
        return lstcCliente;
    }

    public void setLstcCliente(List<Persona> lstcCliente) {
        this.lstcCliente = lstcCliente;
    }

    public List<String> getListaDpto() {
        return listaDpto;
    }

    public void setListaDpto(List<String> listaDpto) {
        this.listaDpto = listaDpto;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

}
