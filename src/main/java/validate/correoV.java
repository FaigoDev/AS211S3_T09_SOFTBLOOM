package validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator (value = "correoV")
public class correoV implements Validator{

    
    public static final String EMAIL_VALIDADOR = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
       Pattern pattern = Pattern.compile(EMAIL_VALIDADOR);
       
        String correo = ((String) value).trim();
        if(correo.isEmpty()){
        }
        else{
            Matcher matcher = pattern.matcher(correo);
            if(!matcher.matches()){
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error","Correo Inválido");
                throw new ValidatorException(msg);
            }
        }
        
        
        
        
    }
    
    
    
    
    
    
}