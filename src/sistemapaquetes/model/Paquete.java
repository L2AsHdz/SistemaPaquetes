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
    private String estadoRetiroS;
    private byte priorizado;
    private String priorizadoS;
    private int idRuta;
    private String nombreRuta;

    public Paquete() {
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
        setEstadoRetiroS();
    }

    public String getEstadoRetiroS() {
        return estadoRetiroS;
    }

    public void setEstadoRetiroS() {
        switch (estadoRetiro) {
            case 0:
                this.estadoRetiroS = "En Bodega";
                break;
            case 1:
                this.estadoRetiroS = "En Ruta";
                break;
            case 2:
                this.estadoRetiroS = "En Destino";
                break;
            case 3:
                this.estadoRetiroS = "Retirado";
                break;
        }
    }

    public byte getPriorizado() {
        return priorizado;
    }

    public void setPriorizado(byte priorizado) {
        this.priorizado = priorizado;
        setPriorizadoS();
    }

    public String getPriorizadoS() {
        return priorizadoS;
    }

    public void setPriorizadoS() {
        switch (priorizado) {
            case 0:
                this.priorizadoS = "Sin Priorizar";
                break;
            case 1:
                this.priorizadoS = "Priorizado";
                break;
        }
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }
}
