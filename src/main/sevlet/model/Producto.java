package model;

public class Producto {
    private int IDPRO;
    private String TIPPRO;
    private String NOMPRO;
    private double PREPRO;
    private String DESPRO;
    private String ESTPRO;
   private int STOCKPRO;
    private int IDPROV;
    private Proveedor prov = new Proveedor();

    public int getIDPRO() {
        return IDPRO;
    }

    public void setIDPRO(int IDPRO) {
        this.IDPRO = IDPRO;
    }

    public String getTIPPRO() {
        return TIPPRO;
    }

    public void setTIPPRO(String TIPPRO) {
        this.TIPPRO = TIPPRO;
    }

    public String getNOMPRO() {
        return NOMPRO;
    }

    public void setNOMPRO(String NOMPRO) {
        this.NOMPRO = NOMPRO;
    }

    public double getPREPRO() {
        return PREPRO;
    }

    public void setPREPRO(double PREPRO) {
        this.PREPRO = PREPRO;
    }

    public String getDESPRO() {
        return DESPRO;
    }

    public void setDESPRO(String DESPRO) {
        this.DESPRO = DESPRO;
    }

    public String getESTPRO() {
        return ESTPRO;
    }

    public void setESTPRO(String ESTPRO) {
        this.ESTPRO = ESTPRO;
    }

    public int getIDPROV() {
        return IDPROV;
    }

    public void setIDPROV(int IDPROV) {
        this.IDPROV = IDPROV;
    }

    public Proveedor getProv() {
        return prov;
    }

    public void setProv(Proveedor prov) {
        this.prov = prov;
    }

    public int getSTOCKPRO() {
        return STOCKPRO;
    }

    public void setSTOCKPRO(int STOCKPRO) {
        this.STOCKPRO = STOCKPRO;
    }
   
}
