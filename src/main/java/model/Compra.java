package model;

import java.util.Date;


public class Compra {

    private int IDCOM;
    private Date FECCOM;
    private String TIPCOM;
    private String METPAGCOM;
    private double PRETOTCOM;
    private String ESTCOM;
    private int IDPRO;
    private String FECHA;
    private int IDPROV;
    private Proveedor proveedor = new Proveedor();
    


    public int getIDCOM() {
        return IDCOM;
    }

    public void setIDCOM(int IDCOM) {
        this.IDCOM = IDCOM;
    }

    public Date getFECCOM() {
        return FECCOM;
    }

    public void setFECCOM(Date FECCOM) {
        this.FECCOM = FECCOM;
    }

    public String getTIPCOM() {
        return TIPCOM;
    }

    public void setTIPCOM(String TIPCOM) {
        this.TIPCOM = TIPCOM;
    }

    public String getMETPAGCOM() {
        return METPAGCOM;
    }

    public void setMETPAGCOM(String METPAGCOM) {
        this.METPAGCOM = METPAGCOM;
    }

    public double getPRETOTCOM() {
        return PRETOTCOM;
    }

    public void setPRETOTCOM(double PRETOTCOM) {
        this.PRETOTCOM = PRETOTCOM;
    }

    public String getESTCOM() {
        return ESTCOM;
    }

    public void setESTCOM(String ESTCOM) {
        this.ESTCOM = ESTCOM;
    }

    public int getIDPRO() {
        return IDPRO;
    }

    public void setIDPRO(int IDPRO) {
        this.IDPRO = IDPRO;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public int getIDPROV() {
        return IDPROV;
    }

    public void setIDPROV(int IDPROV) {
        this.IDPROV = IDPROV;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
