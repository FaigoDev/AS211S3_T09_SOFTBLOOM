package sevlet.model;

import java.util.Date;
import java.util.GregorianCalendar;
import lombok.Data;

@Data
public class Venta {

    private int IDVEN;
    private Date FECVEN = GregorianCalendar.getInstance().getTime();
    private String TIPVEN;
    private String METPAGVEN;
    private double PRETOTVEN;
    private int IDPER;
    private Persona persona = new Persona();
}
