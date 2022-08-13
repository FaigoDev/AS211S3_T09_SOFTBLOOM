package sevlet.controller;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import sevlet.dao.CompraImpl;
import sevlet.dao.ProductoImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import sevlet.model.Compra;
import sevlet.model.CompraDetalle;
import org.primefaces.event.SelectEvent;
import sevlet.model.Producto;


@Named(value = "compraC")
@SessionScoped
public class CompraC implements Serializable {

    private CompraImpl dao = new CompraImpl();
    private Compra com = new Compra();
    private List<Compra> listadocomp;
    private int estado = 1;
    private ProductoImpl daoProducto = new ProductoImpl();
    private CompraDetalle comdet = new CompraDetalle();
    private List<CompraDetalle> listadocomdet;
    private Date minDate;
    private Producto pro = new Producto();

    public CompraC() {
        dao = new CompraImpl();
        com = new Compra();
        daoProducto = new ProductoImpl();
        comdet = new CompraDetalle();
        listadocomdet = new ArrayList();
        listadocomp = new ArrayList();
        Date today = new Date();
        long oneDay = 24 * 60 * 60 * 1000;
        minDate = new Date(today.getTime() - (365 * oneDay));
        pro = new Producto();

    }

    public void registrar() throws Exception {
        try {
            System.out.println("Mi razon social es: " + com.getProveedor().getRAZSOCPROV());
            com.setIDPROV(daoProducto.obtenerCodigoProveedor(com.getProveedor().getRAZSOCPROV()));
            
            if (listadocomdet.isEmpty()) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Inserte datos"));

            } else {
                dao.registrar(com);
                int id = dao.obtenerUltimoId();
                dao.registrarDetalle(listadocomdet, id);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Registro Exitoso"));
                limpiar();

            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en en registrar/ClienteC: {0}", e.getMessage());
        }

    }

    public void sumar() {
        double subtotal = 0;
        com.setPRETOTCOM(0);
        try {
            for (CompraDetalle comD : listadocomdet) {
                subtotal = subtotal + comD.getSUBTOTCOM();
                com.setPRETOTCOM(subtotal);

            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en sumar/CompraC: {0}", e.getMessage());
        }
    }

    public void agregarFila() {
        try {
            int CANPRO = 0;
            Double PREPROCOM = 0.0;

            int IDPROCOM = daoProducto.obtenerCodigoProducto(comdet.getProducto().getNOMPRO());
            int IDPROVCOM = daoProducto.obtenerCodigoProveedor(com.getProveedor().getRAZSOCPROV());
            
            if (IDPROCOM > 0 && IDPROVCOM > 0) {
                
                CompraDetalle comdetagr = new CompraDetalle();
                comdetagr.getProducto().setNOMPRO(comdet.getProducto().getNOMPRO());
                comdetagr.setPRECOMPRO(comdet.getPRECOMPRO());
                comdetagr.setCANPRO(comdet.getCANPRO());
                CANPRO = comdetagr.getCANPRO();
                PREPROCOM = comdetagr.getPRECOMPRO();
                comdetagr.getProducto().setSTOCKPRO(comdet.getProducto().getSTOCKPRO());
                comdetagr.setSUBTOTCOM(CANPRO * PREPROCOM);

                listadocomdet.add(comdetagr);
                comdet = new CompraDetalle();
                sumar();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "HECHO", "Detalle añadido"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No se encontro el producto"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Ingrese correctamente los datos"));

           

        }

    }

    public void onRowSelect(SelectEvent event) throws Exception {
        Compra com = (Compra) event.getObject();
        listadocomdet = dao.listarDet(com.getIDCOM());
    }

    public void limpiar() {
        try {
            com = new Compra();
            comdet = new CompraDetalle();

            listadocomdet.clear();

        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en limpiar/CompraC: {0}", e.getMessage());
        }
    }

    public void eliminarfila(CompraDetalle comdet) throws Exception {
        try {
            listadocomdet.remove(comdet);
            sumar();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en eliminar fila/CompraC: {0}", e.getMessage());

        }

    }

    public void sumaStock() throws Exception {
        try {
            comdet.getProducto().setSTOCKPRO(dao.obtenerStockProducto(comdet.getProducto().getNOMPRO()));
            comdet.getProducto().setSTOCKPRO(comdet.getCANPRO() + comdet.getProducto().getSTOCKPRO());
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en CompraC sumaStock/CompraC: {0}", e.getMessage());
        }

    }

    public void listar() {
        try {
            listadocomp = dao.listar();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listar/CompraC: {0}", e.getMessage());
        }

    }

    public List<String> completeTextProductoProv(String query) throws Exception {
        try {
          
            int idprov = daoProducto.obtenerCodigoProveedor(com.getProveedor().getRAZSOCPROV());
            return daoProducto.autoCompleteProductoProv(query,idprov);
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listar Proveedor/completeTextProveedor: {0}", e.getMessage());
            throw e;
        }
    }
    
    
    public CompraImpl getDao() {
        return dao;
    }

    public void setDao(CompraImpl dao) {
        this.dao = dao;
    }

    public List<Compra> getListadocomp() {
        return listadocomp;
    }

    public void setListadocomp(List<Compra> listadocomp) {
        this.listadocomp = listadocomp;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Compra getCom() {
        return com;
    }

    public void setCom(Compra com) {
        this.com = com;
    }

    public ProductoImpl getDaoProducto() {
        return daoProducto;
    }

    public void setDaoProducto(ProductoImpl daoProducto) {
        this.daoProducto = daoProducto;
    }

    public CompraDetalle getComdet() {
        return comdet;
    }

    public void setComdet(CompraDetalle comdet) {
        this.comdet = comdet;
    }

    public List<CompraDetalle> getListadocomdet() {
        return listadocomdet;
    }

    public void setListadocomdet(List<CompraDetalle> listadocomdet) {
        this.listadocomdet = listadocomdet;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Producto getPro() {
        return pro;
    }

    public void setPro(Producto pro) {
        this.pro = pro;
    }

}
