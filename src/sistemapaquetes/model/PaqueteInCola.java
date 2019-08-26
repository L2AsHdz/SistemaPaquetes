package sistemapaquetes.model;

/**
 *
 * @author asael
 */
public class PaqueteInCola {
    private int idPaquete;
    private String nombrePaquete;
    private int idPuntoControl;
    private String nombrePuntoControl;
    private float tarifaOperacion;
    private int idRuta;
    private String nombreRuta;
    

    public PaqueteInCola() {
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public int getIdPuntoControl() {
        return idPuntoControl;
    }

    public void setIdPuntoControl(int idPuntoControl) {
        this.idPuntoControl = idPuntoControl;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    public void setNombrePaquete(String nombrePaquete) {
        this.nombrePaquete = nombrePaquete;
    }

    public String getNombrePuntoControl() {
        return nombrePuntoControl;
    }

    public void setNombrePuntoControl(String nombrePuntoControl) {
        this.nombrePuntoControl = nombrePuntoControl;
    }

    public float getTarifaOperacion() {
        return tarifaOperacion;
    }

    public void setTarifaOperacion(float tarifaOperacion) {
        this.tarifaOperacion = tarifaOperacion;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }
}
