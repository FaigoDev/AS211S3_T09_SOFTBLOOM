package service;


import dao.conexion;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import static net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfStream;
import static net.sf.jasperreports.engine.JasperFillManager.fillReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

public class Reporte extends conexion {

    public void verPdf(String ruta, int caso) throws Exception {
        try {
            Map<String, Object> parameter = new HashMap<>();
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(ruta));
            byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), parameter, conectar());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            try (ServletOutputStream outStream = response.getOutputStream()) {
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
            }
        } catch (Exception e) {
            System.out.println("Error en ver pdf" + e);
        }

    }
    
    public void descarcarPdf(String ruta, String nomPDF,int caso) throws JRException, IOException, Exception {
        try {
            
            Map<String, Object> parameter = new HashMap<>(); // mapea los parametros
            File jasper = new File(getCurrentInstance().getExternalContext().getRealPath(ruta));
            JasperPrint jasperPrint = fillReport(jasper.getPath(), parameter, conectar());
            HttpServletResponse response = (HttpServletResponse) getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=" + nomPDF + "");
            
            
            
            try (ServletOutputStream stream = response.getOutputStream()) {
                exportReportToPdfStream(jasperPrint, stream);
                stream.flush();
                stream.close();
            }
            
            getCurrentInstance().responseComplete();
        } catch (IOException | JRException e) {
            System.out.println("Error en generar Reporte Servicio: " + e);
        }
    }
}
    

