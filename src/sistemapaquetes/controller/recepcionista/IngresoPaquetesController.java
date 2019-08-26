package sistemapaquetes.controller.recepcionista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.controller.colas.ColaBodegaController;
import sistemapaquetes.dao.CRUD;
import sistemapaquetes.dao.bodega.BodegaDAO;
import sistemapaquetes.dao.bodega.BodegaDAOImpl;
import sistemapaquetes.dao.cliente.ClienteDAOImpl;
import sistemapaquetes.dao.paquete.IngresoPaqueteDAO;
import sistemapaquetes.dao.paquete.IngresoPaqueteDAOImpl;
import sistemapaquetes.dao.paquete.PaqueteDAO;
import sistemapaquetes.dao.paquete.PaqueteDAOImpl;
import sistemapaquetes.dao.precio.PrecioDAO;
import sistemapaquetes.dao.precio.PrecioDAOImpl;
import sistemapaquetes.dao.ruta.RutaDAO;
import sistemapaquetes.dao.ruta.RutaDAOImpl;
import sistemapaquetes.model.Cliente;
import sistemapaquetes.model.IngresoPaquete;
import sistemapaquetes.model.ListasObservables;
import sistemapaquetes.model.Paquete;
import sistemapaquetes.ui.recepcionistaUI.FacturaView;
import sistemapaquetes.ui.recepcionistaUI.IngresoPaquetesView;

/**
 *
 * @author asael
 */
public class IngresoPaquetesController extends FocusAdapter implements ActionListener, MouseListener{
    
    private IngresoPaquetesView ingresoPView;
    private FacturaView facturaView;
    private ColaBodegaController colaB;
    private Paquete paquete;
    private IngresoPaquete ingresoP;
    private Cliente cliente;
    private PaqueteDAO paqueteDAO;
    private IngresoPaqueteDAO ingresoPDAO;
    private CRUD<Cliente> clienteDAO;
    private RutaDAO rutaDAO;
    private BodegaDAO bodegaDAO;
    private PrecioDAO precioDAO; 
    private List<Paquete> paquetes = new ArrayList();
    private ListasObservables list = ListasObservables.getInstance();
    private int idTemp = -1;
    private boolean clienteBuscado = false;
    private boolean nuevoCliente = false;

    public IngresoPaquetesController(IngresoPaquetesView ingresoPView) {
        paqueteDAO = PaqueteDAOImpl.getPaqueteDAOImpl();
        ingresoPDAO = IngresoPaqueteDAOImpl.getIngresoPDAOImpl();
        clienteDAO = ClienteDAOImpl.getClienteDAOImpl();
        precioDAO = PrecioDAOImpl.getPrecioDAO();
        rutaDAO = RutaDAOImpl.getRutaDAO();
        bodegaDAO = BodegaDAOImpl.getBodegaDAOImpl();
        colaB = ColaBodegaController.getColaBodega();
        this.facturaView = new FacturaView();
        this.ingresoPView = ingresoPView;
        this.ingresoPView.getBtnAgregar().addActionListener(this);
        this.ingresoPView.getBtnCerrra().addActionListener(this);
        this.ingresoPView.getBtnSiguiente().addActionListener(this);
        this.ingresoPView.getBtnUpdate().addActionListener(this);
        this.ingresoPView.getBtnEliminar().addActionListener(this);
        this.ingresoPView.getBtnLimpiar().addActionListener(this);
        this.facturaView.getBtnEnviar().addActionListener(this);
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
            ingresoPView.getTxtNit().requestFocus();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Datos para un nuevo cliente
        String nit = ingresoPView.getTxtNit().getText();
        String nombreC = ingresoPView.getTxtNombreC().getText();
        String dir = ingresoPView.getTxtDireccion().getText();
        String tel = ingresoPView.getTxtTelefono().getText();
        
        //datos para un nuevo paquete
        String nombreP = ingresoPView.getTxtNombreP().getText();
        String descripcion = ingresoPView.getTxtDescripcion().getText();
        String peso = ingresoPView.getTxtPeso().getText();
        int idRuta = (ingresoPView.getCbRuta().getSelectedIndex()+1);
        int priorizado = (ingresoPView.getChbPriorizar().isSelected()) ? 1 : 0;
        
        //datos para un nuevo IngresoPaquete
        int codFact = ingresoPDAO.getCodFactura()+1;
        String fecha = LocalDate.now().toString();
        float costoPeso;
        float cuotaD;
        float precioPriorizacion;
        float precioLibra = precioDAO.getValor(2);
        
        if (ingresoPView.getBtnAgregar() == e.getSource()) {
            
            if (validarDatos1(nombreP, peso, idRuta)) {
                nuevoPaquete(nombreP, descripcion, peso, priorizado, idRuta);
                paquetes.add(paquete);
                list.reloadPaquetes1(paquetes);
                limpiarCamposP();
            }
        }else if (ingresoPView.getBtnCerrra() == e.getSource()) {
            limpiarCamposP();
            enableCamposP(false);
            limpiarCamposC();
            paquetes.clear();
            list.reloadPaquetes1(paquetes);
            ingresoPView.dispose();
        }else if (ingresoPView.getBtnSiguiente() == e.getSource()) {
            boolean seguir = true;
            
            
            facturaView.getTxtAFactura().setText("");
            if (clienteDAO.getObject(nit) != null) {
                cliente = clienteDAO.getObject(nit);
                nuevoCliente = false;
            }else if (validarDatos2(nit, nombreC)){
                nuevoCliente(nit, nombreC, dir, tel);
                nuevoCliente = true;
            }else{
                seguir = false;
            }
            if (!paquetes.isEmpty() && seguir) {
                facturaView.getTxtAFactura().append("Cliente:");
                facturaView.getTxtAFactura().append("\nNombre:\t"+cliente.getNombre());
                facturaView.getTxtAFactura().append("\nNit:\t"+cliente.getNit());
                facturaView.getTxtAFactura().append("\nDireccion:\t"+cliente.getDireccion());
                facturaView.getTxtAFactura().append("\nTelefono:\t"+cliente.getTelefono()+"\n");

                facturaView.getTxtAFactura().append("\nPaquetes:");

                
                float subtotal;
                float total = 0;
                for (Paquete p : paquetes) {

                    costoPeso = (p.getPeso()*precioLibra);
                    cuotaD = rutaDAO.getCuotaDestino(p.getIdRuta());
                    if (p.getPriorizado() == 0) {
                        precioPriorizacion = 0;
                    }else {
                        precioPriorizacion = precioDAO.getValor(3);
                    }
                    subtotal = (costoPeso + precioPriorizacion + cuotaD);
                    facturaView.getTxtAFactura().append("\n"+p.getNombre()+":");
                    facturaView.getTxtAFactura().append("\n\tCuotaDestino:\t"+cuotaD);
                    facturaView.getTxtAFactura().append("\n\tCostoPeso:\t\t"+costoPeso);
                    facturaView.getTxtAFactura().append("\n\tPriorizacion:\t\t"+precioPriorizacion);
                    facturaView.getTxtAFactura().append("\n\tSubtotal:\t\t"+subtotal);
                    total+= subtotal; 
                }
                facturaView.getTxtAFactura().append("\n\nTotal:\t\t"+total);
                facturaView.setVisible(true);
                facturaView.setLocationRelativeTo(null);
            }else{
                if (seguir) {
                    JOptionPane.showMessageDialog(null, "No hay paquetes ingresados", 
                    "Advertencia", JOptionPane.ERROR_MESSAGE);
                }
            }
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
            enableCamposP(false);
            paquetes.clear();
            list.reloadPaquetes1(paquetes);
        }else if (facturaView.getBtnEnviar() == e.getSource()) {
            
            //Creacion de nuevo cliente
            if (nuevoCliente) {
                clienteDAO.create(cliente);
            }
            
            //Creacion de paquetes en BD
            for (Paquete p : paquetes) {
                paqueteDAO.create(p);
                //Crecion de ingresos de paquetes
                p.setId(paqueteDAO.getIdPaquete());
                
                costoPeso = (p.getPeso()*precioDAO.getValor(2));
                cuotaD = rutaDAO.getCuotaDestino(p.getIdRuta());
                if (p.getPriorizado() == 0) {
                    precioPriorizacion = 0;
                }else {
                    precioPriorizacion = precioDAO.getValor(3);
                }
                
                nuevoIngresoPaquete(codFact, p.getId(), cliente.getNit(), fecha, 
                        precioPriorizacion, precioLibra, cuotaD, costoPeso);
                ingresoPDAO.create(ingresoP);
                
                //ingreso a bodega
                bodegaDAO.AddPaqueteToBodega(p.getId());
            }
            
            JOptionPane.showMessageDialog(null, "Ingreso Correcto");
            colaB.movilizarPaquetes();
            facturaView.dispose();
            ingresoPView.getBtnLimpiar().doClick();
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        
        if (ingresoPView.getTxtNit() == e.getComponent()) {
            if (!clienteBuscado) {
                String nit = ingresoPView.getTxtNit().getText();
                cliente = clienteDAO.getObject(nit);
                if (cliente != null) {
                    ingresoPView.getTxtDireccion().setText(cliente.getDireccion());
                    ingresoPView.getTxtNombreC().setText(cliente.getNombre());
                    ingresoPView.getTxtTelefono().setText(cliente.getTelefono());
                    enableCamposC(false);
                }else {
                    System.out.println("cliente no existe");
                    limpiarCamposC();
                    enableCamposC(true);
                    ingresoPView.getTxtNombreC().requestFocus();
                }
                ingresoPView.getTxtNit().setText(nit);
                enableCamposP(true);
                clienteBuscado = true;
            }
        }
    }
    
    @Override
    public void focusGained(FocusEvent e){
        if (ingresoPView.getTxtNit() == e.getComponent()) {
            clienteBuscado = false;
            ingresoPView.getTxtNit().setText("");
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
        ingresoPView.getTxtNombreP().requestFocus();
        ingresoPView.getBtnUpdate().setEnabled(false);
        ingresoPView.getBtnAgregar().setEnabled(true);
    }
    
    private void limpiarCamposC(){
        ingresoPView.getTxtNit().setText("");
        ingresoPView.getTxtNombreC().setText("");
        ingresoPView.getTxtDireccion().setText("");
        ingresoPView.getTxtTelefono().setText("");
        enableCamposC(false);
    }
    
    private void enableCamposP(boolean bool){
        ingresoPView.getTxtNombreP().setEnabled(bool);
        ingresoPView.getTxtPeso().setEnabled(bool);
        ingresoPView.getTxtDescripcion().setEnabled(bool);
        ingresoPView.getCbRuta().setEnabled(bool);
        ingresoPView.getChbPriorizar().setEnabled(bool);
    }
    
    private void enableCamposC(boolean bool){
        ingresoPView.getTxtNombreC().setEnabled(bool);
        ingresoPView.getTxtTelefono().setEnabled(bool);
        ingresoPView.getTxtDireccion().setEnabled(bool);
        
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
    
    private void nuevoIngresoPaquete(int codF, int idP, String nitC, String fecha,
            float precioPrio, float precioLib, float cuotaD, float costoP){
        ingresoP = new IngresoPaquete();
        ingresoP.setCodigoFactura(codF);
        ingresoP.setIdPaquete(idP);
        ingresoP.setNitCliente(nitC);
        ingresoP.setFecha(LocalDate.parse(fecha));
        ingresoP.setPrecioPriorizacion(precioPrio);
        ingresoP.setPrecioLibra(precioLib);
        ingresoP.setCuotaDestino(cuotaD);
        ingresoP.setCostoPeso(costoP);
        ingresoP.setTotal();
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
        ingresoPView.getChbPriorizar().setSelected(paquete.getPriorizado() == 1);
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
