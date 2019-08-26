package sistemapaquetes.controller.colas;

import java.util.LinkedList;
import sistemapaquetes.dao.bodega.BodegaDAO;
import sistemapaquetes.dao.bodega.BodegaDAOImpl;
import sistemapaquetes.dao.colapc.ColapcDAO;
import sistemapaquetes.dao.colapc.ColapcDAOImpl;
import sistemapaquetes.dao.paquete.PaqueteDAO;
import sistemapaquetes.dao.paquete.PaqueteDAOImpl;
import sistemapaquetes.dao.puntocontrol.PuntoControlDAO;
import sistemapaquetes.dao.puntocontrol.PuntoControlDAOImpl;
import sistemapaquetes.model.Paquete;

/**
 *
 * @author asael
 */
public class ColaBodegaController {
    private static ColaBodegaController colaB = null;
    private Paquete paquete;
    private PaqueteDAO paqueteDAO;
    private PuntoControlDAO puntoCDAO;
    private BodegaDAO bodegaDAO;
    private ColapcDAO colapcDAO;
    private LinkedList<Integer> colaBodega;
    
    private ColaBodegaController(){
        bodegaDAO = BodegaDAOImpl.getBodegaDAOImpl();
        paqueteDAO = PaqueteDAOImpl.getPaqueteDAOImpl();
        puntoCDAO = PuntoControlDAOImpl.getPuntoCDAO();
        colapcDAO = ColapcDAOImpl.getColaDAOImpl();
        colaBodega = new LinkedList();
    }
    
    public static ColaBodegaController getColaBodega(){
        if (colaB == null) {
            colaB = new ColaBodegaController();
        }
        return colaB;
    }
    
    public void movilizarPaquetes(){
        colaBodega = bodegaDAO.getListado();
        for (Integer i : colaBodega) {
            paquete = paqueteDAO.getObject(i);
            int idRuta = paquete.getIdRuta();
            int noPaquetes = colapcDAO.getNoPaquetes(1, idRuta);
            int limitePaquetes = puntoCDAO.getPuntoControl(1,idRuta).getLimitePaquetes();
            if (noPaquetes < limitePaquetes) {
                //eliminar de colaBodega
                bodegaDAO.RemovePaquete(i);
                System.out.println("se elimino de la colaBodega");
                //agregar a cola puntoControl
                colapcDAO.addPaqueteToCola(paquete, 1);
                System.out.println("se agrego a la cola PC");
                //cambiar estadoRetiro a En Ruta
                paquete.setEstadoRetiro((byte)1);
                paqueteDAO.actualizarEstadoRetiro(paquete);
                System.out.println("se actualizo el estadoRetiro");
            }
        }
    }
}
