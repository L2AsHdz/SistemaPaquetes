package sistemapaquetes.controller.operador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.dao.colapc.ColapcDAO;
import sistemapaquetes.dao.colapc.ColapcDAOImpl;
import sistemapaquetes.dao.paquete.PaqueteDAO;
import sistemapaquetes.dao.paquete.PaqueteDAOImpl;
import sistemapaquetes.dao.paquete.ProcesoPaqueteDAO;
import sistemapaquetes.dao.paquete.ProcesoPaqueteDAOImpl;
import sistemapaquetes.dao.puntocontrol.PuntoControlDAO;
import sistemapaquetes.dao.puntocontrol.PuntoControlDAOImpl;
import sistemapaquetes.model.ListasObservables;
import sistemapaquetes.model.Paquete;
import sistemapaquetes.model.ProcesoPaquete;
import sistemapaquetes.ui.operadorUI.ProcesarPaquetesView;

/**
 *
 * @author asael
 */
public class ProcesarPaquetesController extends MouseAdapter implements ActionListener, ItemListener{
    
    private ProcesarPaquetesView procesarView;
    private Paquete paquete;
    private ProcesoPaquete procesoP;
    private PaqueteDAO paqueteDAO;
    private ProcesoPaqueteDAO procesoDAO;
    private ColapcDAO colapcDAO;
    private PuntoControlDAO puntoCDAO;
    private String dpiOp;
    private int noPC = 0;
    private int idRuta = 1;
    private ListasObservables list = ListasObservables.getInstance();

    public ProcesarPaquetesController(ProcesarPaquetesView procesarView, String dpi) {
        this.dpiOp = dpi;
        paqueteDAO = PaqueteDAOImpl.getPaqueteDAOImpl();
        procesoDAO = ProcesoPaqueteDAOImpl.getProcesoPDAOImpl();
        colapcDAO = ColapcDAOImpl.getColaDAOImpl();
        puntoCDAO = PuntoControlDAOImpl.getPuntoCDAO();
        this.procesarView = procesarView;
        this.procesarView.getBtnProcesar().addActionListener(this);
        this.procesarView.getCbRuta().addItemListener(this);
        this.procesarView.getCbEscogerPC().addItemListener(this);
        this.procesarView.getTblColaPC().addMouseListener(this);
    }
    
    public void iniciar(JDesktopPane desk){
        if (procesarView.isShowing()) {
            JOptionPane.showMessageDialog(null, "La ventana se encuantra abierta", 
            "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }else {
            try {
                desk.add(procesarView);
            } catch (Exception e) {
                System.out.println("error al abrir ventana");
                desk.add(procesarView);
            }
            procesarView.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        if (procesarView.getBtnProcesar() == e.getSource()) {
            //ultimoPunto = puntoCDAO.getLastPCNumber(0)
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (procesarView.getCbRuta() == e.getSource()) {
            idRuta = procesarView.getCbRuta().getSelectedIndex()+1;
            System.out.println(dpiOp+"-"+idRuta);
            if (idRuta > 0) {
                list.reloadListNamePC(dpiOp, idRuta);
            }
            limpiarCampos();
        }else if (procesarView.getCbEscogerPC() == e.getSource()) {
            Object o =procesarView.getCbEscogerPC().getSelectedItem();
            if (o != null) {
                noPC = Integer.parseInt(procesarView.getCbEscogerPC().getSelectedItem().toString());
            }else{
                noPC = 0;
            }
            System.out.println(idRuta+"-"+noPC);
            if (noPC > 0 && idRuta > 0) {
                list.reloadistColaPC(noPC, idRuta);
            }
            limpiarCampos();
        }
    }
    
    private void limpiarCampos(){
        procesarView.getTxtHoras().setText("");
        procesarView.getTxtHoras().setEnabled(false);
        procesarView.getBtnProcesar().setEnabled(false);
        
    }
    
    private boolean isFloat(String s){
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        procesarView.getBtnProcesar().setEnabled(true);
        procesarView.getTxtHoras().setEnabled(true);
    }
    
}
