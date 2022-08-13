package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("convertTipo")

public class tipoP implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String valor) {

        return valor;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object valor) {
        String tipoCompra = "";
        if(valor != null){
            tipoCompra = (String) valor;
            switch(tipoCompra){
                case "N": tipoCompra ="Nacional"; break;
                case "L": tipoCompra ="Local"; break;
            }

            
    }
        return tipoCompra;
    }
}
