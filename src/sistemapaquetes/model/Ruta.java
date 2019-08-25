package sistemapaquetes.model;

/**
 *
 * @author asael
 */
public class Ruta {
    private int id;
    private String nombre;
    private String descripcion;
    private byte estado;
    private String estadoS;
    private int idDestino;
    private String nombreDestino;

    public Ruta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
        setEstadoS();
    }
    
    public void setEstadoS() {
        switch (estado) {
            case 0:
                this.estadoS = "Deshabilitada";
                break;
            case 1:
                this.estadoS ="Activa";
                break;
        }
    }

    public String getEstadoS() {
        return estadoS;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }
    
}
