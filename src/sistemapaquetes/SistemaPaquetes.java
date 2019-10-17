package sistemapaquetes;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import sistemapaquetes.controller.LoginController;
import sistemapaquetes.ui.LoginView;

/**
 *
 * @author asael
 */
public class SistemaPaquetes {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        LoginView log = new LoginView();
        LoginController logC = new LoginController(log);
        logC.iniciar();
    }
    //sin cambios
}
