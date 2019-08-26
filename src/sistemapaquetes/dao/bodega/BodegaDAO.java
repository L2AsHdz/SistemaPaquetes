package sistemapaquetes.dao.bodega;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author asael
 */
public interface BodegaDAO {
    public void AddPaqueteToBodega(int id);
    public void RemovePaquete(int id);
    public LinkedList<Integer> getListado();
}