package sistemapaquetes.dao.colapc;

import java.util.List;
import sistemapaquetes.model.Paquete;
import sistemapaquetes.model.PaqueteInCola;

/**
 *
 * @author asael
 */
public interface ColapcDAO{
    public int getNoPaquetes(int noPC, int idRuta);
    public List<PaqueteInCola> getListado(int noPC, int idRuta);
    public void addPaqueteToCola(Paquete p, int noPC);
    public void removePaquete(int idP);
}
