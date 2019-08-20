package sistemapaquetes.model;

/**
 *
 * @author asael
 */
public class Paquete {
    private int id;
    private String nombre;
    private String descripcion;
    private float peso;
    private byte estadoRetiro;
    private byte priorizado;
    private int idRuta;

    public Paquete() {
    }

    public Paquete(int id, String nombre, String descripcion, float peso, byte estadoRetiro, byte priorizado, int idRuta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.peso = peso;
        this.estadoRetiro = estadoRetiro;
        this.priorizado = priorizado;
        this.idRuta = idRuta;
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

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public byte getEstadoRetiro() {
        return estadoRetiro;
    }

    public void setEstadoRetiro(byte estadoRetiro) {
        this.estadoRetiro = estadoRetiro;
    }

    public byte getPriorizado() {
        return priorizado;
    }

    public void setPriorizado(byte priorizado) {
        this.priorizado = priorizado;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }
}
