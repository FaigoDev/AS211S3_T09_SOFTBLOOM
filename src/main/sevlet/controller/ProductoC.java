package controller;

import dao.ProductoImpl;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import model.Producto;
import org.primefaces.PrimeFaces;
import service.Reporte;

@Named(value = "producto")
@SessionScoped
public class ProductoC implements Serializable {

    private ProductoImpl dao = new ProductoImpl();
    private Producto pro = new Producto();
    private List<Producto> listadopro;
    private int estado = 1;

    public void registrar() throws Exception {
        try {

            pro.setIDPROV(dao.obtenerCodigoProveedor(pro.getProv().getRAZSOCPROV()));

            int caso = 0;
            
            caso = dao.validar(pro, caso);
            
            switch (caso) {
                case 0:
                    dao.registrar(pro);
                    PrimeFaces.current().ajax().update("frmregistrar:dlgProducto");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Registro exitoso"));
                    limpiar();
                    listar();
                    break;
                case 1:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "El producto ya existe"));
                    break;
            }
            
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en RegistrarC Producto: {0}", e.getMessage());

        }

    }

    public void modificar() throws Exception {
        try {
            pro.setIDPROV(dao.obtenerCodigoProveedor(pro.getProv().getRAZSOCPROV()));
            int caso = 0;
            caso = dao.validarProductoModificar(pro, caso);
            switch (caso) {
                case 0:
                    dao.modificar(pro);
                    PrimeFaces.current().ajax().update("dlgDatos");
                    limpiar();
                    listar();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Modificacion exitosa"));
                    break;
                case 1:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El producto ya existe"));
                    break;
            }

        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en ModificarC Producto: {0}", e.getMessage());
        }

    }

    public void eliminar(Producto pro) throws Exception {
        try {

            dao.eliminar(pro);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "OK", "Eliminaste Exitosamente"));

            limpiar();
            listar();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en eliminarC Producto: {0}", e.getMessage());
        }

    }

    public void restaurar(Producto pro) throws Exception {
        try {
            dao.restaurar(pro);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Restauraste este producto exitosamente"));
            limpiar();
            listar();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en restaurarC Producto: {0}", e.getMessage());
        }

    }

    public void listar() {
        try {
            listadopro = dao.listarD(estado);
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listar ProductoC {0}", e.getMessage());
        }

    }

    public List<String> completeTextProveedor(String query) throws Exception {
        try {
            return dao.autoCompleteProveedor(query);
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listar Proveedor/completeTextProveedor: {0}", e.getMessage());
            throw e;
        }
    }

    public List<String> completeTextProducto(String query) throws Exception {
        try {
            return dao.autoCompleteProducto(query);
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listar Proveedor/completeTextProveedor: {0}", e.getMessage());
            throw e;
        }
    }
    
    
    

    public void verReporte() {
        try {
            Reporte reporte = new Reporte();
            String ruta = "/resources/reports/Producto.jasper";
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
            String ruta = "/resources/reports/Producto.jasper";
            reporte.descarcarPdf(ruta, "Listado_Productos.pdf", 1);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Descargaste exitosamente"));
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error controller.ProductoC.descargarReporte: {0}", e.getMessage());
        }
    }

    public void limpiar() {
        pro = new Producto();

    }

    public ProductoImpl getDao() {
        return dao;
    }

    public void setDao(ProductoImpl dao) {
        this.dao = dao;
    }

    public Producto getPro() {
        return pro;
    }

    public void setPro(Producto pro) {
        this.pro = pro;
    }

    public List<Producto> getListadopro() {
        return listadopro;
    }

    public void setListadopro(List<Producto> listadopro) {
        this.listadopro = listadopro;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
