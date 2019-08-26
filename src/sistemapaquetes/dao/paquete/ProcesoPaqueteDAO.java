package sistemapaquetes.dao.paquete;

import sistemapaquetes.dao.CRUD;
import sistemapaquetes.model.ProcesoPaquete;

/**
 *
 * @author asael
 */
public interface ProcesoPaqueteDAO extends CRUD<ProcesoPaquete>{
    public int getCostoPaquete(int idP);
}
