package sistemapaquetes.controller.recepcionista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import sistemapaquetes.ui.recepcionistaUI.RecepcionistaView;

/**
 *
 * @author asael
 */
public class RecepcionistaUIController implements ActionListener{
    private final RecepcionistaView recepView;

    public RecepcionistaUIController(RecepcionistaView recepView) {
        
        this.recepView = recepView;
        this.recepView.getItmClientes().addActionListener(this);
        this.recepView.getItmAddPaquete().addActionListener(this);
        this.recepView.getItmConsultarL().addActionListener(this);
        this.recepView.getItmRetiroP().addActionListener(this);
        this.recepView.getItmClientes().addActionListener(this);
    }
    
    public void iniar(){
        recepView.pack();
        recepView.setExtendedState(JFrame.MAXIMIZED_BOTH);
        recepView.setLocationRelativeTo(null);
        recepView.setVisible(true);
    }
//alfo
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //metodo para aborl
}
