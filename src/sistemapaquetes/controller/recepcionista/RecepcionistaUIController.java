package sistemapaquetes.controller.recepcionista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import sistemapaquetes.ui.recepcionistaUI.AddClienteView;
import sistemapaquetes.ui.recepcionistaUI.IngresoPaquetesView;
import sistemapaquetes.ui.recepcionistaUI.RecepcionistaView;

/**
 *
 * @author asael
 */
public class RecepcionistaUIController implements ActionListener{
    private final RecepcionistaView recepView;
    
    //Vista y controlador para agregar cliente
    private final AddClienteView addClientV = new AddClienteView();
    private final AddClientController addClientC = new AddClientController(addClientV);
    
    //Vista y controlador para el ingreso de paquetes
    private final IngresoPaquetesView addPaqueteV = new IngresoPaquetesView();
    private final IngresoPaquetesController addPaqueteC = new IngresoPaquetesController(addPaqueteV);

    public RecepcionistaUIController(RecepcionistaView recepView) {
        this.recepView = recepView;
        this.recepView.getItmClientes().addActionListener(this);
        this.recepView.getItmAddPaquete().addActionListener(this);
        this.recepView.getItmConsultarL().addActionListener(this);
        this.recepView.getItmRetiroP().addActionListener(this);
    }
    
    public void iniciar(){
        recepView.pack();
        recepView.setExtendedState(JFrame.MAXIMIZED_BOTH);
        recepView.setLocationRelativeTo(null);
        recepView.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (recepView.getItmAddPaquete() == e.getSource()) {
            addPaqueteC.iniciar(recepView.getDeskRecepcion());
        }else if (recepView.getItmClientes() == e.getSource()) {
            System.out.println("item clientes");
            addClientC.iniciar(recepView.getDeskRecepcion());
        }else if (recepView.getItmConsultarL() == e.getSource()) {
            
        }else if (recepView.getItmRetiroP() == e.getSource()) {
            
        }
    }
}
