package sistemapaquetes.controller.recepcionista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.dao.paquete.IngresoPaqueteDAO;
import sistemapaquetes.dao.paquete.IngresoPaqueteDAOImpl;
import sistemapaquetes.dao.paquete.PaqueteDAO;
import sistemapaquetes.dao.paquete.PaqueteDAOImpl;
import sistemapaquetes.dao.paquete.ProcesoPaqueteDAO;
import sistemapaquetes.dao.paquete.ProcesoPaqueteDAOImpl;
import sistemapaquetes.dao.paquete.RetiroPaqueteDAO;
import sistemapaquetes.dao.paquete.RetiroPaqueteDAOImpl;
import sistemapaquetes.model.ListasObservables;
import sistemapaquetes.model.Paquete;
import sistemapaquetes.model.RetiroPaquete;
import sistemapaquetes.ui.recepcionistaUI.RegistrarRetiroView;

/**
 *
 * @author asael
 */
public class RetiroPaqueteController extends MouseAdapter implements ActionListener{
    
    private RegistrarRetiroView retiroView;
    private Paquete paquete;
    private PaqueteDAO paqueteDAO;
    private RetiroPaquete retiroP;
    private RetiroPaqueteDAO retiroDAO;
    private IngresoPaqueteDAO ingresoDAO;
    private ProcesoPaqueteDAO procesoDAO;
    private int idP = 0;
    private ListasObservables list = ListasObservables.getInstance();

    public RetiroPaqueteController(RegistrarRetiroView retiroView) {
        retiroDAO = RetiroPaqueteDAOImpl.getRetiroDAOImpl();
        procesoDAO = ProcesoPaqueteDAOImpl.getProcesoPDAOImpl();
        ingresoDAO = IngresoPaqueteDAOImpl.getIngresoPDAOImpl();
        paqueteDAO = PaqueteDAOImpl.getPaqueteDAOImpl();
        this.retiroView = retiroView;
        this.retiroView.getBtnRetiro().addActionListener(this);
        this.retiroView.getBtnCerrar().addActionListener(this);
        this.retiroView.getTblRetiros().addMouseListener(this);
    }
    
    public void iniciar(JDesktopPane desk){
        if (retiroView.isShowing()) {
            JOptionPane.showMessageDialog(null, "La ventana se encuantra abierta", 
            "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }else {
            try {
                desk.add(retiroView);
            } catch (Exception e) {
                System.out.println("error a abrir la ventana");
                desk.add(retiroView);
            }
            retiroView.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    
        if (retiroView.getBtnRetiro() == e.getSource()) {
            paquete = paqueteDAO.getObject(idP);
            float costo = procesoDAO.getCostoPaquete(idP);
            float ingreso = ingresoDAO.getingresoPaquete(idP);
            String nit = ingresoDAO.getCliente(idP);
            nuevoRetiro(costo, ingreso, idP, nit);
        }else if (retiroView.getBtnCerrar() == e.getSource()){
            retiroView.getBtnRetiro().setEnabled(false);
            retiroView.dispose();
        }
    }
    
    private void nuevoRetiro(float costo, float ingreso, int idP, String nit){
        retiroP = new RetiroPaquete();
        retiroP.setCosto(costo);
        retiroP.setIngreso(ingreso);
        retiroP.setGanancia();
        retiroP.setIdPaquete(idP);
        retiroP.setFechaEntrega(LocalDate.now());
        retiroP.setNitCliente(nit);   
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = retiroView.getTblRetiros().getSelectedRow();
        idP = (int) retiroView.getTblRetiros().getValueAt(fila, 0);
        retiroView.getBtnRetiro().setEnabled(true);
    }
    
}
