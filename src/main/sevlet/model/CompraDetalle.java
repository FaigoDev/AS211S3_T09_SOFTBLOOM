package sevlet.model;

public class CompraDetalle {
    private int IDCOMDET;
    private int CANPRO;
    private int IDCOM;
    private int IDPRO;
    private Double SUBTOTCOM;
    private Double PRECOMPRO;
    private Compra compra = new Compra();
    // se instancia todo mi modelo desde mi modelo producto, desde el verde sacas todo los metodos de esa clase
    private Producto producto =  new Producto();
    

    public int getIDCOMDET() {
        return IDCOMDET;
    }

    public void setIDCOMDET(int IDCOMDET) {
        this.IDCOMDET = IDCOMDET;
    }

    public int getCANPRO() {
        return CANPRO;
    }

    public void setCANPRO(int CANPRO) {
        this.CANPRO = CANPRO;
    }

    public int getIDCOM() {
        return IDCOM;
    }

    public void setIDCOM(int IDCOM) {
        this.IDCOM = IDCOM;
    }

    public int getIDPRO() {
        return IDPRO;
    }

    public void setIDPRO(int IDPRO) {
        this.IDPRO = IDPRO;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getSUBTOTCOM() {
        return SUBTOTCOM;
    }

    public void setSUBTOTCOM(Double SUBTOTCOM) {
        this.SUBTOTCOM = SUBTOTCOM;
    }

    public Double getPRECOMPRO() {
        return PRECOMPRO;
    }

    public void setPRECOMPRO(Double PRECOMPRO) {
        this.PRECOMPRO = PRECOMPRO;
    }
}
