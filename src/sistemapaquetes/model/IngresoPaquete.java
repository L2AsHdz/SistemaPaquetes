package sistemapaquetes.model;

import java.time.LocalDate;

/**
 *
 * @author asael
 */
public class IngresoPaquete {
    private int id;
    private int codigoFactura;
    private int idPaquete;
    private String nitCliente;
    private LocalDate fecha;
    private float precioPriorizacion;
    private float precioLibra;
    private float cuotaDestino;
    private float costoPeso;
    private float total;

    public IngresoPaquete() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(int codigoFactura) {
        this.codigoFactura = codigoFactura;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public float getPrecioPriorizacion() {
        return precioPriorizacion;
    }

    public void setPrecioPriorizacion(float precioPriorizacion) {
        this.precioPriorizacion = precioPriorizacion;
    }

    public float getPrecioLibra() {
        return precioLibra;
    }

    public void setPrecioLibra(float precioLibra) {
        this.precioLibra = precioLibra;
    }

    public float getCuotaDestino() {
        return cuotaDestino;
    }

    public void setCuotaDestino(float cuotaDestino) {
        this.cuotaDestino = cuotaDestino;
    }

    public float getCostoPeso() {
        return costoPeso;
    }

    public void setCostoPeso(float costoPeso) {
        this.costoPeso = costoPeso;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = (costoPeso+cuotaDestino+precioPriorizacion);
    }
}
