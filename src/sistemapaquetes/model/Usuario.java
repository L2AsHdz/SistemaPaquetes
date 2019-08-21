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
    private byte estado;

    public Usuario(String DPI, String nombre, byte tipo, byte estado, String password) {
        this.DPI = DPI;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
        this.password = password;
    }

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
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
