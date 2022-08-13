package controller;

import dao.VentaImpl;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Data;
import model.Producto;
import model.Venta;
import model.VentaDetalle;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import org.primefaces.event.SelectEvent;

@Data

@Named(value = "ventaC")
@SessionScoped
public class VentaC implements Serializable {

    private Venta venta;
    private VentaDetalle ventadetalle;
    private Producto producto;
    private VentaImpl dao;
    private List<Venta> listaVenta;
    private List<VentaDetalle> listadoVentaDetalle;
    private List<Producto> listadoProducto;
    private ExcelOptions excel;
    private PDFOptions pdf;

    public VentaC() {
        venta = new Venta();
        producto = new Producto();
        ventadetalle = new VentaDetalle();
        dao = new VentaImpl();
        listaVenta = new ArrayList<>();
        listadoVentaDetalle = new ArrayList<>();
        listadoProducto = new ArrayList<>();
        customizationOptions();
    }

    public void adicionarFila() {
        try {
            VentaDetalle ventadet = dao.adicionarFila(ventadetalle.getProducto().getIDPRO());
            ventadet.setIDPRO(ventadetalle.getProducto().getIDPRO());
            ventadet.setTIPPRO(ventadetalle.getTIPPRO());
            ventadet.setPREPRO(ventadet.getProducto().getPREPRO());
            ventadet.setCANVENDET(ventadetalle.getCANVENDET());
            ventadet.setSUBVENDET(ventadet.getProducto().getPREPRO() * ventadetalle.getCANVENDET());
            ventadet.setTIPPRO(ventadet.getProducto().getTIPPRO());
            ventadet.setNOMPRO(ventadet.getProducto().getNOMPRO());
            ventadet.setDESPRO(ventadet.getProducto().getDESPRO());
            ventadet.setSTOCKPRO(ventadet.getProducto().getSTOCKPRO());
            listadoVentaDetalle.add(ventadet);
            ventadetalle = new VentaDetalle();
            sumar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Producto Agregado"));
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en en adicionarFila/VentaDetalleC: {0}", e.getMessage());
        }
    }

    public void eliminarFila(VentaDetalle ventadetalle) {
        try {
            listadoVentaDetalle.remove(ventadetalle);
            sumar();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en en eliminarFila/VentaDetalleC: {0}", e.getMessage());
        }
    }

    public void registrarVenta() {
        try {
            dao.registrarVenta(venta);
            int idven = dao.ultimoIdVenta();
            dao.registrarVentaDetalle(listadoVentaDetalle, idven);
            listadoVentaDetalle.clear();
            listarVenta();
            venta = new Venta();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en en registrarVenta/VentaDetalleC: {0}", e.getMessage());

        }
    }

    public void limpiar() {
        this.ventadetalle = new VentaDetalle();
        this.venta = new Venta();
    }

    public void anular() throws Exception {
        limpiar();
        listadoVentaDetalle.clear();
    }

    public void listarVenta() {
        try {
            listaVenta = dao.listarVenta();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en en listarVenta/VentaDetalleC: {0}", e.getMessage());

        }
    }
    
    
    public void onRowSelect(SelectEvent event) throws Exception {
        Venta ven = (Venta) event.getObject();
        listadoVentaDetalle = dao.listarDet(ven.getIDVEN());
    }

    public void sumar() {
        double precioTotal = 0.0;
        for (VentaDetalle ventaDetalle : listadoVentaDetalle) {
            precioTotal += ventaDetalle.getSUBVENDET();
        }

        venta.setPRETOTVEN(precioTotal);
    }

    public void customizationOptions() {
        excel = new ExcelOptions();
        excel.setFacetBgColor("#19C7FF");
        excel.setFacetFontSize("10");
        excel.setFacetFontColor("#FFFFFF");
        excel.setFacetFontStyle("BOLD");
        excel.setCellFontColor("#000000");
        excel.setCellFontSize("8");
        excel.setFontName("Verdana");
        pdf = new PDFOptions();
        pdf.setFacetBgColor("#19C7FF");
        pdf.setFacetFontColor("#000000");
        pdf.setFacetFontStyle("BOLD");
        pdf.setCellFontSize("12");
        pdf.setFontName("Courier");
        pdf.setOrientation(PDFOrientationType.LANDSCAPE);
    }

    @PostConstruct
    public void construir() {
        listarVenta();
    }

}
