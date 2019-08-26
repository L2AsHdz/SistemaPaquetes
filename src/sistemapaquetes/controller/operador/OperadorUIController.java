package sistemapaquetes.controller.operador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import sistemapaquetes.ui.operadorUI.OperadorView;
import sistemapaquetes.ui.operadorUI.ProcesarPaquetesView;

/**
 *
 * @author asael
 */
public class OperadorUIController implements ActionListener {
    private final OperadorView operView;
    
    //vista y controlador para proceso de paquetes
    private final ProcesarPaquetesView procesoV = new ProcesarPaquetesView();
    private final ProcesarPaquetesController procesoC;

    public OperadorUIController(OperadorView operView, String dpi) {
        procesoC = new ProcesarPaquetesController(procesoV, dpi);
        this.operView = operView;
        this.operView.getItmProcesarPaquete().addActionListener(this);
    }
    
    public void iniciar(){
        operView.pack();
        operView.setExtendedState(JFrame.MAXIMIZED_BOTH);
        operView.setLocationRelativeTo(null);
        operView.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (operView.getItmProcesarPaquete() == e.getSource()) {
            procesoC.iniciar(operView.getDeskOperadorView());
        }
    }
}
