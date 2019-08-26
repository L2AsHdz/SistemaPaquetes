package sistemapaquetes.controller.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.dao.CRUD;
import sistemapaquetes.dao.precio.PrecioDAOImpl;
import sistemapaquetes.model.ListasObservables;
import sistemapaquetes.model.PrecioGlobal;
import sistemapaquetes.ui.administradorUI.PreciosView;

/**
 *
 * @author asael
 */
public class PreciosController extends MouseAdapter implements ActionListener{
    
    private PreciosView preciosView;
    private PrecioGlobal precio;
    private CRUD<PrecioGlobal> precioDAO;
    private int idTemp = 0;
    private ListasObservables list = ListasObservables.getInstance();

    public PreciosController(PreciosView preciosView) {
        precioDAO = PrecioDAOImpl.getPrecioDAO();
        this.preciosView = preciosView;
        this.preciosView.getBtnCerrar().addActionListener(this);
        this.preciosView.getBtnLimpiar().addActionListener(this);
        this.preciosView.getBtnUpdate().addActionListener(this);
        this.preciosView.getTblPreciosGlobales().addMouseListener(this);
    }
    
    public void iniciar(JDesktopPane desk){
        if (preciosView.isShowing()) {
            JOptionPane.showMessageDialog(null, "La ventana se encuantra abierta", 
            "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }else {
            try {
                desk.add(preciosView);
                
            } catch (Exception e) {
                System.out.println("Error al abrir la ventana\n");
                desk.add(preciosView);
            }
            preciosView.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (preciosView.getBtnCerrar() == e.getSource()) {
            limpiarCampos();
            preciosView.dispose();
        }else if (preciosView.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();
            idTemp = 0;
        }else if (preciosView.getBtnUpdate() == e.getSource()) {
            
            String nombre = preciosView.getTxtNombre().getText();
            String valor = preciosView.getTxtValor().getText();
            
            if (validarDatos(nombre, valor)) {
                precio = new PrecioGlobal();
                precio.setId(idTemp);
                precio.setNombre(nombre);
                precio.setValor(Float.parseFloat(valor));
                precioDAO.update(precio);
                list.reloadPrecios();
                limpiarCampos();
            }
        }
    }

    private boolean validarDatos(String nombre, String valor){
        boolean validacion = true;
        if (nombre.isEmpty() || valor.isEmpty()) {
            System.out.println("Hay campos vacios");
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", 
            "Advertencia", JOptionPane.ERROR_MESSAGE);
            validacion = false;
        }
        if (!isFloat(valor)) {
            System.out.println("Hay campos vacios");
            JOptionPane.showMessageDialog(null, "El valor ingresada no es un numero", 
            "Advertencia", JOptionPane.ERROR_MESSAGE);
            validacion = false;
        }
        return validacion;
    }
    
    private void limpiarCampos(){
        preciosView.getTxtNombre().setText("");
        preciosView.getTxtValor().setText("");
        preciosView.getTxtNombre().requestFocus();
        preciosView.getBtnUpdate().setEnabled(false);
    }
    
    private boolean isFloat(String s){
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = preciosView.getTblPreciosGlobales().getSelectedRow();
        idTemp = (int) preciosView.getTblPreciosGlobales().getValueAt(fila, 0);
        precio = precioDAO.getObject(idTemp);
        preciosView.getTxtNombre().setText(precio.getNombre());
        preciosView.getTxtValor().setText(String.valueOf(precio.getValor()));
        preciosView.getBtnUpdate().setEnabled(true);
    }
}
