package sistemapaquetes.controller.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.dao.colapc.ColapcDAO;
import sistemapaquetes.dao.colapc.ColapcDAOImpl;
import sistemapaquetes.dao.precio.PrecioDAO;
import sistemapaquetes.dao.precio.PrecioDAOImpl;
import sistemapaquetes.dao.puntocontrol.PuntoControlDAO;
import sistemapaquetes.dao.puntocontrol.PuntoControlDAOImpl;
import sistemapaquetes.model.ListasObservables;
import sistemapaquetes.model.PuntoControl;
import sistemapaquetes.ui.administradorUI.AddPuntoControlView;

/**
 *
 * @author asael
 */
public class AddPuntoControlController implements ActionListener, MouseListener{
    
    private AddPuntoControlView addPCView;
    private PuntoControl pc;
    private PuntoControlDAO puntoCDAO;
    private PrecioDAO precioDAO;
    private ColapcDAO colaDAO;
    private int numero = 0;
    private int idRutatemp = 0;
    private ListasObservables list = ListasObservables.getInstance();

    public AddPuntoControlController(AddPuntoControlView addPCView) {
        puntoCDAO = PuntoControlDAOImpl.getPuntoCDAO();
        precioDAO = PrecioDAOImpl.getPrecioDAO();
        colaDAO = ColapcDAOImpl.getColaDAOImpl();
        this.addPCView = addPCView;
        this.addPCView.getBtnCrear().addActionListener(this);
        this.addPCView.getBtnCerrar().addActionListener(this);
        this.addPCView.getBtnLimpiar().addActionListener(this);
        this.addPCView.getBtnUpdate().addActionListener(this);
        this.addPCView.getTblPuntosControl().addMouseListener(this);
    }

    public void iniciar(JDesktopPane desk){
        if (addPCView.isShowing()) {
            JOptionPane.showMessageDialog(null, "La ventana se encuantra abierta", 
            "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }else{
            try {
                desk.add(addPCView);
            } catch (Exception e) {
                System.out.println("Error a abrir ventana\n");
                desk.add(addPCView);
            }
            addPCView.setVisible(true);
            addPCView.getCbRutas().setSelectedIndex(-1);
            addPCView.getCbDPIOperador().setSelectedIndex(-1);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        String nombre = addPCView.getTxtNombre().getText();
        String limite = addPCView.getTxtLimitePaquetes().getText();
        String valor = addPCView.getTxtTarifaOperacion().getText();
        int idRuta = addPCView.getCbRutas().getSelectedIndex()+1;
        int idOp = addPCView.getCbDPIOperador().getSelectedIndex()+1;

        if (addPCView.getBtnCrear() == e.getSource()) {
            
            if (validarDatos(nombre, valor, limite, idRuta, idOp)) {
                nuevoPuntoControl(nombre, limite, valor, idRuta);
                puntoCDAO.create(pc);
                list.reloadPuntosControl();
            }
            limpiarDatos();
            
        }else if (addPCView.getBtnCerrar() == e.getSource()) {
            limpiarDatos();
            addPCView.dispose();
        }else if (addPCView.getBtnLimpiar() == e.getSource()) {
            limpiarDatos();
            idRuta = 0;
            numero = 0;
        }else if (addPCView.getBtnUpdate() == e.getSource()) {
            
            /*
            if (validarDatos(nombre, valor, limite, idRuta, idOp)) {
                nuevoPuntoControl(nombre, limite, valor, idOp);
            }*/
            //Logiac de actualizacion de Punto de Control
        }

    }
    
    private boolean validarDatos(String nombre, String valor, String limite, int idRuta, int idOp){
        boolean validacion = true;
        if (nombre.isEmpty() || idRuta == -1 || idOp == -1 || limite.isEmpty()) {
            System.out.println("Hay campos vacios");
            JOptionPane.showMessageDialog(null, "El nombre y destino son obligatoorios", 
            "Advertencia", JOptionPane.ERROR_MESSAGE);
            validacion = false;
        }
        
        if (!valor.isEmpty()) {
            
            if (!isFloat(valor) || !isFloat(limite)) {
                System.out.println("No es un valor numerico");
                JOptionPane.showMessageDialog(null, "La tarifa y el limite deben ser datos numericos", 
                "Advertencia", JOptionPane.ERROR_MESSAGE);
                validacion = false;
            }
        }
        return validacion;
    }
    
    public void limpiarDatos(){
        addPCView.getTxtNombre().setText("");
        addPCView.getTxtLimitePaquetes().setText("");
        addPCView.getTxtTarifaOperacion().setText("");
        addPCView.getCbRutas().setSelectedIndex(-1);
        addPCView.getCbDPIOperador().setSelectedIndex(-1);
        addPCView.getTxtNombre().requestFocus();
        addPCView.getBtnUpdate().setEnabled(false);
    }
    
    private boolean isFloat(String s){
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void nuevoPuntoControl(String nombre, String limite, String valor, int idR){
        pc = new PuntoControl();
        pc.setNombre(nombre);
        pc.setLimitePaquetes(Integer.parseInt(limite));
        
        if (!valor.isEmpty()) {
            pc.setTarifaOperacion(Float.parseFloat(valor));
        }
        
        pc.setIdRuta(idR);
        pc.setDPIOperador(addPCView.getCbDPIOperador().getSelectedItem().toString());
        pc.setTarifaOperacionGlobal(precioDAO.getValor(1));
        pc.setNumero(puntoCDAO.getLastPCNumber(idR)+1);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = addPCView.getTblPuntosControl().getSelectedRow();
        idRutatemp = (int) addPCView.getTblPuntosControl().getValueAt(fila, 0);
        numero = (int) addPCView.getTblPuntosControl().getValueAt(fila, 1);
        pc = puntoCDAO.getPuntoControl(numero, idRutatemp);
        addPCView.getTxtNombre().setText(pc.getNombre());
        addPCView.getTxtLimitePaquetes().setText(String.valueOf(pc.getLimitePaquetes()));
        addPCView.getTxtTarifaOperacion().setText(String.valueOf(pc.getTarifaOperacion()));
        addPCView.getCbRutas().setSelectedIndex(pc.getIdRuta()-1);
        addPCView.getCbDPIOperador().setSelectedItem(pc.getDPIOperador());
        //Hay que validar la cola de los puntos de control para poder editar
        if (colaDAO.getNoPaquetes(pc.getNumero()) < pc.getLimitePaquetes()) {
            addPCView.getBtnUpdate().setEnabled(true);
        }else {
            JOptionPane.showMessageDialog(null, "La cola del punto de control esta completa",
                    "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
