package sistemapaquetes.dao.paquete;

import sistemapaquetes.dao.CRUD;
import sistemapaquetes.model.Paquete;

/**
 *
 * @author asael
 */
public interface PaqueteDAO extends CRUD<Paquete>{
    public int getIdPaquete();
    public void actualizarEstadoRetiro(Paquete p);
}
