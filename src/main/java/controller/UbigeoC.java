package controller;

import dao.UbigeoD;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.Ubigeo;
@Named(value = "ubigeoC")
@SessionScoped
public class UbigeoC implements Serializable {

    Ubigeo ubigeo;
    UbigeoD dao;
    private List<String> listadoDpto;

    public UbigeoC(Ubigeo ubigeo) {
        this.ubigeo = ubigeo;
    }

    public UbigeoC() {
        ubigeo = new Ubigeo();
        dao = new UbigeoD();
    }

    public List<String> completeTextUbigeo(String query) throws Exception {
        try {
            return dao.autoCompleteCliente(query);
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en UbigeoC/completeTextUbigeo: {0}", e.getMessage());
            throw e;
        }
    }

    public List<String> getListadoDpto() {
        return listadoDpto;
    }

    public void setListadoDpto(List<String> listadoDpto) {
        this.listadoDpto = listadoDpto;
    }

}
