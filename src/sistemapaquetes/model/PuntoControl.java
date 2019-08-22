package sistemapaquetes.model;

/**
 *
 * @author asael
 */
public class PuntoControl {
    private int numero;
    private String nombre;
    private int idRuta;
    private String nombreRuta;
    private int limitePaquetes;
    private String DPIOperador;
    private String nombreOperador;
    private float tarifaOperacion;
    private float tarifaOperacionGlobal;

    public PuntoControl() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getLimitePaquetes() {
        return limitePaquetes;
    }

    public void setLimitePaquetes(int limitePaquetes) {
        this.limitePaquetes = limitePaquetes;
    }

    public String getDPIOperador() {
        return DPIOperador;
    }

    public void setDPIOperador(String DPIOperador) {
        this.DPIOperador = DPIOperador;
    }

    public String getNombreOperador() {
        return nombreOperador;
    }

    public void setNombreOperador(String nombreOperador) {
        this.nombreOperador = nombreOperador;
    }

    public float getTarifaOperacion() {
        return tarifaOperacion;
    }

    public void setTarifaOperacion(float tarifaOperacion) {
        this.tarifaOperacion = tarifaOperacion;
    }

    public float getTarifaOperacionGlobal() {
        return tarifaOperacionGlobal;
    }

    public void setTarifaOperacionGlobal(float tarifaOperacionGlobal) {
        this.tarifaOperacionGlobal = tarifaOperacionGlobal;
    }
}
