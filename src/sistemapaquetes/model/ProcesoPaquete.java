package sistemapaquetes.model;

/**
 *
 * @author asael
 */
public class ProcesoPaquete {
    private int id;
    private int idPaquete;
    private int noPuntoControl;
    private int idRuta;
    private float horas;
    private float tarifaOperacion;
    private float costo;

    public ProcesoPaquete() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public int getNoPuntoControl() {
        return noPuntoControl;
    }

    public void setNoPuntoControl(int noPuntoControl) {
        this.noPuntoControl = noPuntoControl;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public float getHoras() {
        return horas;
    }

    public void setHoras(float horas) {
        this.horas = horas;
    }

    public float getTarifaOperacion() {
        return tarifaOperacion;
    }

    public void setTarifaOperacion(float tarifaOperacion) {
        this.tarifaOperacion = tarifaOperacion;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto() {
        this.costo = (horas*tarifaOperacion);
    }
}
