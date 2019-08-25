package sistemapaquetes.model;

import java.time.LocalDate;

/**
 *
 * @author asael
 */
public class RetiroPaquete {
    private int id;
    private float costo;
    private float ingreso;
    private float ganancia;
    private int idPaquete;
    private String nitCliente;
    private LocalDate FechaEntrega;

    public RetiroPaquete() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getIngreso() {
        return ingreso;
    }

    public void setIngreso(float ingreso) {
        this.ingreso = ingreso;
    }

    public float getGanancia() {
        return ganancia;
    }

    public void setGanancia() {
        this.ganancia = (ingreso-costo);
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public LocalDate getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(LocalDate FechaEntrega) {
        this.FechaEntrega = FechaEntrega;
    }
}
