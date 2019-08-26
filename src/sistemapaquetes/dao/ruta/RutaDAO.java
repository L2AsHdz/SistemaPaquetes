package sistemapaquetes.dao.ruta;

import sistemapaquetes.dao.CRUD;
import sistemapaquetes.model.Ruta;

/**
 *
 * @author asael
 */
public interface RutaDAO extends CRUD<Ruta>{
    public int getCuotaDestino(int idRuta);
}
