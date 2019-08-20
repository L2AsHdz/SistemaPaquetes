package sistemapaquetes.model;

/**
 *
 * @author asael
 */
public class Destino {
    private int id;
    private String nombre;
    private float cuotaDestino;

    public Destino() {
    }

    public Destino(int id, String nombre, float cuotaDestino) {
        this.id = id;
        this.nombre = nombre;
        this.cuotaDestino = cuotaDestino;
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

    public float getCuotaDestino() {
        return cuotaDestino;
    }

    public void setCuotaDestino(float cuotaDestino) {
        this.cuotaDestino = cuotaDestino;
    }
}
