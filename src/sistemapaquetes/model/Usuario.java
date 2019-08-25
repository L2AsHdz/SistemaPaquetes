package sistemapaquetes.model;

/**
 *
 * @author asael
 */
public class Usuario {
    private String DPI;
    private String nombre;
    private String nombreUsuario;
    private String password;
    private byte tipo;
    private String tipoN;
    private byte estado;
    private String estadoS;

    public Usuario() {
    }

    public String getDPI() {
        return DPI;
    }

    public void setDPI(String DPI) {
        this.DPI = DPI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public byte getTipo() {
        return tipo;
    }

    public void setTipo(byte tipo) {
        this.tipo = tipo;
        setTipoN();
    }

    public String getTipoN() {
        return tipoN;
    }

    public void setTipoN() {
        switch (tipo) {
            case 1:
                this.tipoN = "Administrador";
                break;
            case 2:
                this.tipoN = "Operador";
                break;
            case 3:
                this.tipoN = "Recepcionista";
                break;
        }
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
        setEstadoS();
    }

    public String getEstadoS() {
        return estadoS;
    }

    public void setEstadoS() {
        switch (estado) {
            case 0:
                this.estadoS = "Deshabilitado";
                break;
            case 1:
                this.estadoS ="Activo";
                break;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
