package sistemapaquetes.controller.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import sistemapaquetes.ui.administradorUI.AddPuntoControlView;
import sistemapaquetes.ui.administradorUI.AddRutaView;
import sistemapaquetes.ui.administradorUI.AddUserView;
import sistemapaquetes.ui.administradorUI.AdminView;
import sistemapaquetes.ui.administradorUI.DestinoView;
import sistemapaquetes.ui.administradorUI.DisableUserView;
import sistemapaquetes.ui.administradorUI.PreciosView;

/**
 *
 * @author asael
 */
public class AdminUIController implements ActionListener{
    private final AdminView adminUI;
    
    //Vista y Controlador para agregar usuario
    private final AddUserView addUserV = new AddUserView();
    private final AddUserController addUserC = new AddUserController(addUserV);
    
    //Vista y Controlador para  deshabilitar usuario
    private final DisableUserView disableUserV = new DisableUserView();
    private final DisableUserController disableUserC = new DisableUserController(disableUserV);
    
    //Vista y controlador para agregar destino  
    private final DestinoView destV = new DestinoView();
    private final DestinoController destC = new DestinoController(destV);
    
    //Vista y controlador para agregar Ruta
    private final AddRutaView rutaV = new AddRutaView();
    private final AddRutaController rutaC = new AddRutaController(rutaV);
    
    //Vista y controlador para modificar Precios Globales
    private final PreciosView precioV = new PreciosView();
    private final PreciosController precioC = new PreciosController(precioV);
    
    //Vista y controlador para administrar Puntos de control
    private final AddPuntoControlView puntoV = new AddPuntoControlView();
    private final AddPuntoControlController puntoC = new AddPuntoControlController(puntoV);

    //Constructor del Controlador de la interfaz del Administrador
    public AdminUIController(AdminView adminUI) {
        this.adminUI = adminUI;
        this.adminUI.getItmAddUser().addActionListener(this);
        this.adminUI.getItmDeshabilitarUser().addActionListener(this);
        this.adminUI.getItmDestinos().addActionListener(this);
        this.adminUI.getItmCrearRuta().addActionListener(this);
        this.adminUI.getItmPreciosTarifas().addActionListener(this);
        this.adminUI.getItmPuntosControl().addActionListener(this);
    }
    
    //Metodo que muestra la interfaz
    public void iniciar(){
        adminUI.pack();
        adminUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
        adminUI.setLocationRelativeTo(null);
        adminUI.setVisible(true);
    }

    //Metodo para controlar las acciones de los items de los menus
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
        }else if (adminUI.getItmPreciosTarifas() == e.getSource()) {
            precioC.iniciar(adminUI.getDeskAdminFrame());
        }else if (adminUI.getItmPuntosControl() == e.getSource()) {
            puntoC.iniciar(adminUI.getDeskAdminFrame());
        }
    }
}
