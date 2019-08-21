package sistemapaquetes.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import sistemapaquetes.ui.administradorUI.AddUserView;
import sistemapaquetes.ui.administradorUI.AdminView;
import sistemapaquetes.ui.administradorUI.DisableUserView;

/**
 *
 * @author asael
 */
public class AdminUIController implements ActionListener{
    private AdminView adminUI;
    private DisableUserView disableUserV = new DisableUserView();
    private AddUserView addUserV = new AddUserView();
    private AddUserController addUserC = new AddUserController(addUserV);
    private DisableUserController disableUserC = new DisableUserController(disableUserV);

    public AdminUIController(AdminView adminUI) {
        this.adminUI = adminUI;
        adminUI.getItmAddUser().addActionListener(this);
        adminUI.getItmDeshabilitarUser().addActionListener(this);
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
        }
    }
}
