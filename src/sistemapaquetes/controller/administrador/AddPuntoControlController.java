package sistemapaquetes.controller.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
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
    private int numero = 0;
    private int idRutatemp = 0;
    private ListasObservables list = ListasObservables.getInstance();

    public AddPuntoControlController(AddPuntoControlView addPCView) {
        puntoCDAO = PuntoControlDAOImpl.getPuntoCDAO();
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
            desk.add(addPCView);
            addPCView.setVisible(true);
            addPCView.getCbRutas().setSelectedIndex(-1);
            addPCView.getCbDPIOperador().setSelectedIndex(-1);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
