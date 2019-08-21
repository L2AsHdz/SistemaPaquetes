package sistemapaquetes.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sistemapaquetes.ui.administradorUI.AddUserView;
import sistemapaquetes.ui.administradorUI.AdminView;

/**
 *
 * @author asael
 */
public class AdminUIController implements ActionListener{
    private AdminView adminUI;
    private AddUserController addUserC;
    private AddUserView addUserV;

    public AdminUIController(AdminView adminUI) {
        this.adminUI = adminUI;
        adminUI.getItmAddUser().addActionListener(this);
        adminUI.getItmDeshabilitarUser().addActionListener(this);
    }
    
    public void iniciar(){
        //adminUI.pack();
        adminUI.setLocationRelativeTo(null);
        adminUI.setResizable(false);
        adminUI.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (adminUI.getItmAddUser() == e.getSource()) {
            addUserV = new AddUserView();
            addUserC = new AddUserController(addUserV);
            addUserC.iniciar(adminUI.getDeskAdminFrame());
        }
    }
}
