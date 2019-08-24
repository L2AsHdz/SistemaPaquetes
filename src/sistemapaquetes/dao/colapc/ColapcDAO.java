package sistemapaquetes.dao.colapc;

import java.util.List;
import sistemapaquetes.model.Paquete;
import sistemapaquetes.model.PaqueteInCola;
import sistemapaquetes.model.PuntoControl;

/**
 *
 * @author asael
 */
public interface ColapcDAO{
    public int getNoPaquetes(int noPC);
    public List<PaqueteInCola> getListado(int noPC);
    public void addPaqueteToCola(Paquete p, PuntoControl pc);
    public void removePaquete(Paquete p, PuntoControl pc);
}
