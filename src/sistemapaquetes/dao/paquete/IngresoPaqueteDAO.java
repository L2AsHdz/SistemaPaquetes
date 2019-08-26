package sistemapaquetes.dao.paquete;

import sistemapaquetes.dao.CRUD;
import sistemapaquetes.model.IngresoPaquete;

/**
 *
 * @author asael
 */
public interface IngresoPaqueteDAO extends CRUD<IngresoPaquete>{
    public int getCodFactura();
    public int getingresoPaquete(int idP);
    public String getCliente(int idP);
}
