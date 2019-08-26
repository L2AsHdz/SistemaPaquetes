package sistemapaquetes.controller.recepcionista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.dao.CRUD;
import sistemapaquetes.dao.cliente.ClienteDAOImpl;
import sistemapaquetes.dao.paquete.PaqueteDAOImpl;
import sistemapaquetes.model.Cliente;
import sistemapaquetes.model.IngresoPaquete;
import sistemapaquetes.model.ListasObservables;
import sistemapaquetes.model.Paquete;
import sistemapaquetes.ui.recepcionistaUI.IngresoPaquetesView;

/**
 *
 * @author asael
 */
public class IngresoPaquetesController extends FocusAdapter implements ActionListener, MouseListener{
    
    private IngresoPaquetesView ingresoPView;
    private Paquete paquete;
    private IngresoPaquete ingresoP;
    private Cliente cliente;
    private CRUD<Paquete> paqueteDAO;
    private CRUD<IngresoPaquete> ingresoPDAO;
    private CRUD<Cliente> clienteDAO;
    private List<Paquete> paquetes = new ArrayList();
    private ListasObservables list = ListasObservables.getInstance();
    private int idTemp = -1;
    private boolean clienteBuscado = false;

    public IngresoPaquetesController(IngresoPaquetesView ingresoPView) {
        paqueteDAO = PaqueteDAOImpl.getPaqueteDAOImpl();
        clienteDAO = ClienteDAOImpl.getClienteDAOImpl();
        this.ingresoPView = ingresoPView;
        this.ingresoPView.getBtnAgregar().addActionListener(this);
        this.ingresoPView.getBtnCerrra().addActionListener(this);
        this.ingresoPView.getBtnEnviar().addActionListener(this);
        this.ingresoPView.getBtnUpdate().addActionListener(this);
        this.ingresoPView.getBtnEliminar().addActionListener(this);
        this.ingresoPView.getBtnLimpiar().addActionListener(this);
        this.ingresoPView.getTblPaquetes().addMouseListener(this);
        this.ingresoPView.getTxtNit().addFocusListener(this);
    }
    
    public void iniciar(JDesktopPane desk){
        if (ingresoPView.isShowing()) {
            JOptionPane.showMessageDialog(null, "La ventana se encuantra abierta", 
            "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }else {
            try {
                desk.add(ingresoPView);
            } catch (Exception e) {
                System.out.println("Error al abrir ventana\n");
                desk.add(ingresoPView);
            }
            ingresoPView.setVisible(true);
            ingresoPView.getCbRuta().setSelectedIndex(-1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nit = ingresoPView.getTxtNit().getText();
        String nombreC = ingresoPView.getTxtNombreC().getText();
        String dir = ingresoPView.getTxtDireccion().getText();
        String tel = ingresoPView.getTxtTelefono().getText();
        
        String nombreP = ingresoPView.getTxtNombreP().getText();
        String descripcion = ingresoPView.getTxtDescripcion().getText();
        String peso = ingresoPView.getTxtPeso().getText();
        int idRuta = (ingresoPView.getCbRuta().getSelectedIndex()+1);
        int priorizado = (ingresoPView.getChbPriorizar().isSelected()) ? 1 : 0;
        
        if (ingresoPView.getBtnAgregar() == e.getSource()) {
            
            if (validarDatos1(nombreP, peso, idRuta)) {
                nuevoPaquete(nombreP, descripcion, peso, priorizado, idRuta);
                paquetes.add(paquete);
                list.reloadPaquetes1(paquetes);
                limpiarCamposP();
            }
        }else if (ingresoPView.getBtnCerrra() == e.getSource()) {
            limpiarCamposP();
            limpiarCamposC();
            ingresoPView.dispose();
        }else if (ingresoPView.getBtnEnviar() == e.getSource()) {
            
        }else if (ingresoPView.getBtnUpdate() == e.getSource()) {
            
            if (validarDatos1(nombreP, peso, idRuta)) {
                nuevoPaquete(nombreP, descripcion, peso, priorizado, idRuta);
                paquetes.remove(idTemp);
                paquetes.add(idTemp, paquete);
                list.reloadPaquetes1(paquetes);
                limpiarCamposP();
            }
        }else if (ingresoPView.getBtnEliminar() == e.getSource()) {
            if (idTemp > -1) {
                paquetes.remove(idTemp);
                list.reloadPaquetes1(paquetes);
                limpiarCamposP();
            }
        }else if (ingresoPView.getBtnLimpiar() == e.getSource()) {
            limpiarCamposP();
            limpiarCamposC();
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        
        if (ingresoPView.getTxtNit() == e.getComponent()) {
            if (!clienteBuscado) {
                String nit = ingresoPView.getTxtNit().getText();
                cliente = clienteDAO.getObject(nit);
                if (cliente != null) {
                    ingresoPView.getTxtNit().setText(cliente.getNit());
                    ingresoPView.getTxtDireccion().setText(cliente.getDireccion());
                    ingresoPView.getTxtNombreC().setText(cliente.getNombre());
                    ingresoPView.getTxtTelefono().setText(cliente.getTelefono());
                    ingresoPView.getTxtNombreP().requestFocus();
                }else {
                    System.out.println("cliente no existe");
                    ingresoPView.getTxtNombreC().setEditable(true);
                    ingresoPView.getTxtDireccion().setEditable(true);
                    ingresoPView.getTxtTelefono().setEditable(true);
                    ingresoPView.getTxtNombreC().requestFocus();
                }
                clienteBuscado = true;
            }
        }
    }
    
    private boolean validarDatos1(String nombreP, String peso, int idRuta){
        boolean validacion = true;
        if (nombreP.isEmpty() || idRuta == 0) {
            System.out.println("Hay campos vacios");
            JOptionPane.showMessageDialog(null, "El nombre, el peso y la ruta son obligatorios", 
                    "Advertencia", JOptionPane.ERROR_MESSAGE);
            validacion = false;
        }
        if (!isFloat(peso)) {
            System.out.println("No es un valor numerico");
            JOptionPane.showMessageDialog(null, "El peso debe ser un valor numerico", 
            "Advertencia", JOptionPane.ERROR_MESSAGE);
            validacion = false;
        }
        
        return validacion;
    }
    
    private boolean validarDatos2(String nit, String nombre){
        boolean validacion = true;
        if (nit.isEmpty() || nombre.isEmpty()) {
            System.out.println("Hay campos vacios");
            JOptionPane.showMessageDialog(null, "El nit y el nombre son obligatorios", 
                    "Advertencia", JOptionPane.ERROR_MESSAGE);
            validacion = false;
        }
        return validacion;
    }
    
    private boolean isFloat(String s){
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void limpiarCamposP(){
        idTemp = -1;
        ingresoPView.getTxtNombreP().setText("");
        ingresoPView.getTxtPeso().setText("");
        ingresoPView.getTxtDescripcion().setText("");
        ingresoPView.getCbRuta().setSelectedIndex(-1);
        ingresoPView.getChbPriorizar().setSelected(false);
        ingresoPView.getBtnUpdate().setEnabled(false);
        ingresoPView.getBtnAgregar().setEnabled(true);
    }
    
    private void limpiarCamposC(){
        clienteBuscado = false;
        ingresoPView.getTxtNit().setText("");
        ingresoPView.getTxtNombreC().setText("");
        ingresoPView.getTxtNombreC().setEditable(false);
        ingresoPView.getTxtDireccion().setText("");
        ingresoPView.getTxtDireccion().setEditable(false);
        ingresoPView.getTxtTelefono().setText("");
        ingresoPView.getTxtTelefono().setEditable(false);
    }
    
    private void nuevoCliente(String nit, String nombre, String dir, String tel){
        cliente = new Cliente();
        cliente.setNit(nit);
        cliente.setNombre(nombre);
        if (!dir.isEmpty()) {
            cliente.setDireccion(dir);
        }
        cliente.setTelefono(tel);
    }
    
    private void nuevoPaquete(String nombre, String descri, String peso, int priori, int idRuta){
        paquete = new Paquete();
        paquete.setNombre(nombre);
        paquete.setDescripcion(descri);
        paquete.setPeso(Float.parseFloat(peso));
        paquete.setIdRuta(idRuta);
        paquete.setNombreRuta(ingresoPView.getCbRuta().getSelectedItem().toString());
        paquete.setPriorizado((byte) priori);
    }
    
    private void nuevoIngresoPaquete(){
        //nuevoIngreso
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        limpiarCamposP();
        int fila = ingresoPView.getTblPaquetes().getSelectedRow();
        idTemp = fila;
        paquete = paquetes.get(fila);
        ingresoPView.getTxtNombreP().setText(paquete.getNombre());
        ingresoPView.getTxtDescripcion().setText(paquete.getDescripcion());
        ingresoPView.getTxtPeso().setText(String.valueOf(paquete.getPeso()));
        ingresoPView.getCbRuta().setSelectedIndex(paquete.getIdRuta()-1);
        ingresoPView.getChbPriorizar().setSelected(paquete.getPriorizado() == 0);
        ingresoPView.getBtnUpdate().setEnabled(true);
        ingresoPView.getBtnAgregar().setEnabled(false);
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
