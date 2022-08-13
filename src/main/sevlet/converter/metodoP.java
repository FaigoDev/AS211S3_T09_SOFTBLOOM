
package sevlet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("convertMetodo")

public class metodoP implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String valor) {
       
        return valor;
       
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object valor) {
   String tipoMetodo = "";
        if(valor != null){
            tipoMetodo = (String) valor;
            switch(tipoMetodo){
                case "TB": tipoMetodo ="Transferencia Bancaria"; break;
                case "PE": tipoMetodo ="Pago Efectivo"; break;
            }

            
    }
        return tipoMetodo;
    }
    
}
