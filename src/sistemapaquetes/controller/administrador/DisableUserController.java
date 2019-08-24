package sistemapaquetes.controller.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.dao.usuario.UsuarioDAOImpl;
import sistemapaquetes.model.ListasObservables;
import sistemapaquetes.ui.administradorUI.DisableUserView;

/**
 *
 * @author asael
 */
public class DisableUserController implements ActionListener {

    private DisableUserView disableUserV;
    private UsuarioDAOImpl userDAO;
    private ListasObservables list = ListasObservables.getInstance();

    public DisableUserController(DisableUserView disableUserV) {
        this.userDAO = UsuarioDAOImpl.getUserDAO();
        this.disableUserV = disableUserV;
        this.disableUserV.getBtnDeshabilitar().addActionListener(this);
        this.disableUserV.getBtnCerrar().addActionListener(this);
    }

    public void iniciar(JDesktopPane desk) {
        if (disableUserV.isShowing()) {
            JOptionPane.showMessageDialog(null, "La ventana se encuantra abierta",
            "Informacion", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                desk.add(disableUserV);
            } catch (Exception e) {
                System.out.println("Error al abrir la ventana\n");
                desk.add(disableUserV);
            }
            disableUserV.setVisible(true);
            disableUserV.getCbDPIUsers().setSelectedIndex(-1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (disableUserV.getBtnDeshabilitar() == e.getSource()) {
            String dpi = disableUserV.getCbDPIUsers().getSelectedItem().toString();
            userDAO.disableUser(dpi);
            list.reloadListadosU();
            limpiarCampos();
        } else if (disableUserV.getBtnCerrar() == e.getSource()) {
            disableUserV.getCbDPIUsers().setSelectedIndex(-1);
            disableUserV.dispose();
            limpiarCampos();
        }
    }
    
    private void limpiarCampos(){
        disableUserV.getCbDPIUsers().setSelectedIndex(-1);
        disableUserV.getCbDPIUsers().requestFocus();
    }

}
