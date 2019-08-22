package sistemapaquetes.controller.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.dao.CRUD;
import sistemapaquetes.dao.DestinoDAOImpl;
import sistemapaquetes.model.Destino;
import sistemapaquetes.model.ListasObservables;
import sistemapaquetes.ui.administradorUI.DestinoView;

/**
 *
 * @author asael
 */
public class DestinoController implements ActionListener, MouseListener{
    
    private DestinoView destinoView;
    private Destino destino;
    private CRUD<Destino> destinoDAO;
    private int idTemp = 0;
    private ListasObservables list = ListasObservables.getInstance();

    public DestinoController(DestinoView destinoView) {
        destinoDAO = DestinoDAOImpl.getDestinoDAO();
        this.destinoView = destinoView;
        this.destinoView.getBtnCrear().addActionListener(this);
        this.destinoView.getBtnActualizar().addActionListener(this);
        this.destinoView.getBtnCerrar().addActionListener(this);
        this.destinoView.getBtnLimpiar().addActionListener(this);
        this.destinoView.getTblDestinos().addMouseListener(this);
    }
    
    public void iniciar(JDesktopPane desk){
        if (destinoView.isShowing()) {
            JOptionPane.showMessageDialog(null, "La ventana se encuantra abierta", 
            "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }else {
            try {
                desk.add(destinoView);
                destinoView.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int id;
        String nombre;
        String cuotaDestino;
        
        if (destinoView.getBtnCrear() == e.getSource()) {
            nombre = destinoView.getTxtnombreDestino().getText();
            cuotaDestino = destinoView.getTxtCuotaDestino().getText();
            
            if (validarDatos(nombre, cuotaDestino)) {
                nuevoDestino(nombre, cuotaDestino);
                destinoDAO.create(destino);
                list.reloadListadosD();
            }
            limpiarCampos();
            
        }else if (destinoView.getBtnCerrar() == e.getSource()) {
            limpiarCampos();
            destinoView.dispose();
        }else if (destinoView.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();
            idTemp = 0;
        }else if (destinoView.getBtnActualizar() == e.getSource()) {
            
            nombre = destinoView.getTxtnombreDestino().getText();
            cuotaDestino = destinoView.getTxtCuotaDestino().getText();
            
            if (validarDatos(nombre, cuotaDestino)) {
                nuevoDestino(nombre, cuotaDestino);
                destino.setId(idTemp);
                destinoDAO.update(destino);
                list.reloadListadosD();
            }
            limpiarCampos();
        }
    }
    
    public boolean validarDatos(String nombre, String cuota){
        boolean validacion = true;
        if (nombre.isEmpty() || cuota.isEmpty()) {
            System.out.println("Hay campos vacios");
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", 
            "Advertencia", JOptionPane.ERROR_MESSAGE);
            validacion = false;
        }
        if (!isFloat(cuota)) {
            System.out.println("Hay campos vacios");
            JOptionPane.showMessageDialog(null, "La cuota ingresada no es un numero", 
            "Advertencia", JOptionPane.ERROR_MESSAGE);
            validacion = false;
        }
        return validacion;
    }
    
    private void limpiarCampos(){
        destinoView.getTxtCuotaDestino().setText("");
        destinoView.getTxtnombreDestino().setText("");
        destinoView.getTxtnombreDestino().requestFocus();
        destinoView.getBtnActualizar().setEnabled(false);
    }
    
    private void nuevoDestino(String nombre, String cuota){
        destino = new Destino();
        destino.setNombre(nombre);
        destino.setCuotaDestino(Float.parseFloat(cuota));
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
        int fila = destinoView.getTblDestinos().getSelectedRow();
        idTemp = (int) destinoView.getTblDestinos().getValueAt(fila, 0);
        destino = destinoDAO.getObject(idTemp);
        destinoView.getTxtnombreDestino().setText(destino.getNombre());
        destinoView.getTxtCuotaDestino().setText(String.valueOf(destino.getCuotaDestino()));
        destinoView.getBtnActualizar().setEnabled(true);
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
