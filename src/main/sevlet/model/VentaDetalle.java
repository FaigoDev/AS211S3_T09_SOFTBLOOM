package sevlet.model;

import lombok.Data;

@Data

public class VentaDetalle {

    private int IDVENDET;
    private int CANVENDET = 1;
    private Double SUBVENDET;
    private int IDVEN;
    private int IDPRO;
    private Producto producto = new Producto();
    private Venta venta = new Venta();

    private String TIPPRO;
    private String NOMPRO;
    private double PREPRO;
    private String DESPRO;
    private String ESTPRO;
    private int STOCKPRO;
    private int IDPROV;

}
