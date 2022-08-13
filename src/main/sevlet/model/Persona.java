package sevlet.model;

public class Persona {
    // Mi modelo persona que tiene los mismos atributos que mi tabla de BD Persona
    private int codigo;
    private String dni;
    private String nombre;
    private String apellido;
    private String celular;
    private String dirreccion;
    private String correo;
    private String ubigeo;
    private String estado;

    public void clear() {
        codigo = 0;
        nombre = null;
        apellido = null;
        dni = null;
        celular = null;
        correo = null;
        ubigeo = null;
        dirreccion = null;
    }
    
    //Aqui he encapsulado los atributos - Getter y Setter
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String movil) {
        this.celular = movil;
    }

    public String getDirreccion() {
        return dirreccion;
    }

    public void setDirreccion(String dirreccion) {
        this.dirreccion = dirreccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
