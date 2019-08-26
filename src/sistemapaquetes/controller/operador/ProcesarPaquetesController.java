package sistemapaquetes.controller.operador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.controller.colas.ColaBodegaController;
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
import sistemapaquetes.model.PuntoControl;
import sistemapaquetes.ui.operadorUI.ProcesarPaquetesView;

/**
 *
 * @author asael
 */
public class ProcesarPaquetesController extends MouseAdapter implements ActionListener, ItemListener{
    
    private ProcesarPaquetesView procesarView;
    private ColaBodegaController colaBodega = ColaBodegaController.getColaBodega();
    private Paquete paquete;
    private ProcesoPaquete procesoP;
    private PuntoControl puntoC;
    private PaqueteDAO paqueteDAO;
    private ProcesoPaqueteDAO procesoDAO;
    private ColapcDAO colapcDAO;
    private PuntoControlDAO puntoCDAO;
    private String dpiOp;
    private int idP= 0;
    private int noPC = 0;
    private int idRuta = 1;
    private float tarifaO;
    private ListasObservables list = ListasObservables.getInstance();

    public ProcesarPaquetesController(ProcesarPaquetesView procesarView, String dpi) {
        this.dpiOp = dpi;
        paqueteDAO = PaqueteDAOImpl.getPaqueteDAOImpl();
        procesoDAO = ProcesoPaqueteDAOImpl.getProcesoPDAOImpl();
        colapcDAO = ColapcDAOImpl.getColaDAOImpl();
        puntoCDAO = PuntoControlDAOImpl.getPuntoCDAO();
        
        this.procesarView = procesarView;
        this.procesarView.getBtnProcesar().addActionListener(this);
        this.procesarView.getBtnCerrar().addActionListener(this);
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
        
        String horas = procesarView.getTxtHoras().getText();
        
        if (procesarView.getBtnProcesar() == e.getSource()) {
            int ultimoPunto = puntoCDAO.getLastPCNumber(idRuta);
            
            if (isFloat(horas)) {
                paquete = paqueteDAO.getObject(idP);
                if (ultimoPunto == noPC) {
                    puntoC = puntoCDAO.getPuntoControl(noPC, idRuta);
                    
                    //crearProcesoPaquete
                    nuevoProceso(idP, noPC, idRuta, Float.parseFloat(horas), tarifaO);
                    procesoDAO.create(procesoP);
                    //removiendo de cola Punto Control
                    colapcDAO.removePaquete(idP);
                    //Cambiando estadoRetiro a En Destino
                    paquete.setEstadoRetiro((byte)2);
                    paqueteDAO.actualizarEstadoRetiro(paquete);
                    JOptionPane.showMessageDialog(null, "Paquete Procesado con exito!", 
                    "Informacion", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    puntoC = puntoCDAO.getPuntoControl((noPC+1), idRuta);
                    int noPaquetes = colapcDAO.getNoPaquetes((noPC+1), idRuta);
                    int limitePaquetes = puntoC.getLimitePaquetes();
                    if (noPaquetes < limitePaquetes) {
                        //crearProcesoPaquete
                        nuevoProceso(idP, noPC, idRuta, Float.parseFloat(horas), tarifaO);
                        procesoDAO.create(procesoP);
                        //removiendo de cola PC antigua
                        colapcDAO.removePaquete(idP);
                        //creando registro en cola PC nueva
                        colapcDAO.addPaqueteToCola(paquete, (noPC+1));
                        JOptionPane.showMessageDialog(null, "Paquete Procesado con exito!", 
                        "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "Cola del siguiente punto esta completa", 
                    "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                
                if (noPC == 1) {
                    colaBodega.movilizarPaquetes();
                }
                procesarView.getCbRuta().setSelectedIndex(-1);
                procesarView.getCbEscogerPC().setSelectedIndex(-1);
                
            }else{
                JOptionPane.showMessageDialog(null, "El valor de horas ingresado es erroneo", 
                    "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
        }else if (procesarView.getBtnCerrar() == e.getSource()) {
            limpiarCampos();
            procesarView.dispose();
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
    
    private void nuevoProceso(int idP, int noPC, int idRuta, float horas, float tarifaO){
        procesoP = new ProcesoPaquete();
        procesoP.setIdPaquete(idP);
        procesoP.setNoPuntoControl(noPC);
        procesoP.setIdRuta(idRuta);
        procesoP.setHoras(horas);
        procesoP.setTarifaOperacion(tarifaO);
        procesoP.setCosto();
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        int fila = procesarView.getTblColaPC().getSelectedRow();
        idP = Integer.parseInt(procesarView.getTblColaPC().getValueAt(fila, 0).toString());
        tarifaO = Float.parseFloat(procesarView.getTblColaPC().getValueAt(fila, 4).toString());
        procesarView.getBtnProcesar().setEnabled(true);
        procesarView.getTxtHoras().setEnabled(true);
    }
    
}
