package sistemapaquetes.controller.recepcionista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.dao.cliente.ClienteDAO;
import sistemapaquetes.dao.cliente.ClienteDAOImpl;
import sistemapaquetes.model.Cliente;
import sistemapaquetes.model.ListasObservables;
import sistemapaquetes.ui.recepcionistaUI.AddClienteView;

/**
 *
 * @author asael
 */
public class AddClientController implements ActionListener, MouseListener{
    
    private AddClienteView addClientView;
    private Cliente cliente;
    private ClienteDAO clienteDAO;
    private String nitTemp = "";
    private ListasObservables list = ListasObservables.getInstance();

    public AddClientController(AddClienteView addClientView) {
        clienteDAO = ClienteDAOImpl.getClienteDAOImpl();
        this.addClientView = addClientView;
        this.addClientView.getBtnAgregar().addActionListener(this);
        this.addClientView.getBtnCerrar().addActionListener(this);
        this.addClientView.getBtnLimpiar().addActionListener(this);
        this.addClientView.getBtnUpdate().addActionListener(this);
        this.addClientView.getTblClientes().addMouseListener(this);
    }
    
    public void iniciar(JDesktopPane desk){
        if (addClientView.isShowing()) {
            JOptionPane.showMessageDialog(null, "La ventana se encuantra abierta", 
            "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }else{
            try {
                desk.add(addClientView);
            } catch (Exception e) {
                System.out.println("Error al abrir ventana\n");
                desk.add(addClientView);
            }
            addClientView.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nit = addClientView.getTxtNit().getText();
        String nombre = addClientView.getTxtNombre().getText();
        String direccion = addClientView.getTxtDireccion().getText();
        String telefono = addClientView.getTxtTelefono().getText();
        
        if (addClientView.getBtnAgregar() == e.getSource()) {
            
            if (validarDatos(nit, nombre, direccion, telefono)) {
                nuevoCliente(nit, nombre, direccion, telefono);
                clienteDAO.create(cliente);
                list.reloadLIstClientes();
            }
            limpiarCampos();
        }else if (addClientView.getBtnCerrar() == e.getSource()) {
            limpiarCampos();
            addClientView.dispose();
        }else if (addClientView.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();
            nitTemp = "";
        }else if (addClientView.getBtnUpdate() == e.getSource()) {
            
            if (validarDatos(nit, nombre, direccion, telefono)) {
                nuevoCliente(nit, nombre, direccion, telefono);
                clienteDAO.update(cliente,nitTemp);
                list.reloadLIstClientes();
            }
            limpiarCampos();
        }
    }
    
    private boolean validarDatos(String nit, String nombre, String dir, String tel){
        boolean validacion = true;
        if (nit.isEmpty() || nombre.isEmpty()) {
            System.out.println("Hay campos vacios");
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", 
                    "Advertencia", JOptionPane.ERROR_MESSAGE);
            validacion = false;
        }
        if (!tel.isEmpty()) {
            if (tel.length() != 9) {
                System.out.println("Telefono incompleto");
                JOptionPane.showMessageDialog(null, "No es un numero de telefono valido", 
                        "Advertencia", JOptionPane.ERROR_MESSAGE);
                validacion = false;
            }
        }
        return validacion;
    }
    
    private void limpiarCampos(){
        addClientView.getTxtNit().setText("");
        addClientView.getTxtNombre().setText("");
        addClientView.getTxtDireccion().setText("");
        addClientView.getTxtTelefono().setText("");
        addClientView.getTxtNit().requestFocus();
        addClientView.getBtnUpdate().setEnabled(false);
    }
    
    private void nuevoCliente(String nit, String nombre, String dir, String tel){
        cliente = new Cliente();
        cliente.setNit(nit);
        cliente.setNombre(nombre);
        if (!dir.isEmpty()) {
            cliente.setDireccion(dir);
        }
        if (!tel.isEmpty()) {
            cliente.setTelefono(tel);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = addClientView.getTblClientes().getSelectedRow();
        nitTemp = (String) addClientView.getTblClientes().getValueAt(fila, 0);
        cliente = clienteDAO.getObject(nitTemp);
        addClientView.getTxtNit().setText(cliente.getNit());
        addClientView.getTxtNombre().setText(cliente.getNombre());
        addClientView.getTxtDireccion().setText(cliente.getDireccion());
        addClientView.getTxtTelefono().setText(cliente.getTelefono());
        addClientView.getBtnUpdate().setEnabled(true);
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
