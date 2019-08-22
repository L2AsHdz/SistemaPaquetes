package sistemapaquetes.controller.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import sistemapaquetes.ui.administradorUI.AddRutaView;
import sistemapaquetes.ui.administradorUI.AddUserView;
import sistemapaquetes.ui.administradorUI.AdminView;
import sistemapaquetes.ui.administradorUI.DestinoView;
import sistemapaquetes.ui.administradorUI.DisableUserView;

/**
 *
 * @author asael
 */
public class AdminUIController implements ActionListener{
    private final AdminView adminUI;
    private final AddUserView addUserV = new AddUserView();
    private final AddUserController addUserC = new AddUserController(addUserV);
    private final DisableUserView disableUserV = new DisableUserView();
    private final DisableUserController disableUserC = new DisableUserController(disableUserV);
    private final DestinoView destV = new DestinoView();
    private final DestinoController destC = new DestinoController(destV);
    private final AddRutaView rutaV = new AddRutaView();
    private final AddRutaController rutaC = new AddRutaController(rutaV);

    public AdminUIController(AdminView adminUI) {
        this.adminUI = adminUI;
        this.adminUI.getItmAddUser().addActionListener(this);
        this.adminUI.getItmDeshabilitarUser().addActionListener(this);
        this.adminUI.getItmDestinos().addActionListener(this);
        this.adminUI.getItmCrearRuta().addActionListener(this);
    }
    
    public void iniciar(){
        adminUI.pack();
        adminUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
        adminUI.setLocationRelativeTo(null);
        adminUI.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (adminUI.getItmAddUser() == e.getSource()) {
            addUserC.iniciar(adminUI.getDeskAdminFrame());
        }else if (adminUI.getItmDeshabilitarUser() == e.getSource()) {
            disableUserC.iniciar(adminUI.getDeskAdminFrame());
        }else if (adminUI.getItmDestinos() == e.getSource()) {
            destC.iniciar(adminUI.getDeskAdminFrame());
        }else if (adminUI.getItmCrearRuta() == e.getSource()) {
            rutaC.iniciar(adminUI.getDeskAdminFrame());
        }
    }
}
