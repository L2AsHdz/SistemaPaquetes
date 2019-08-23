package sistemapaquetes.controller.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.dao.CRUD;
import sistemapaquetes.dao.ruta.RutaDAOImpl;
import sistemapaquetes.model.ListasObservables;
import sistemapaquetes.model.Ruta;
import sistemapaquetes.ui.administradorUI.AddRutaView;

/**
 *
 * @author asael
 */
public class AddRutaController implements ActionListener, MouseListener{
    
    private AddRutaView addRutaView;
    private Ruta ruta;
    private CRUD<Ruta> rutaDAO;
    private int idTemp = 0;
    private ListasObservables list = ListasObservables.getInstance();

    public AddRutaController(AddRutaView addRutaView) {
        rutaDAO = RutaDAOImpl.getRutaDAO();
        this.addRutaView = addRutaView;
        this.addRutaView.getBtnCrear().addActionListener(this);
        this.addRutaView.getBtnCerrar().addActionListener(this);
        this.addRutaView.getBtnLimpiar().addActionListener(this);
        this.addRutaView.getBtnUpdate().addActionListener(this);
        this.addRutaView.getTblRutas().addMouseListener(this);
    }
    
    public void iniciar(JDesktopPane desk){
        if (addRutaView.isShowing()) {
            JOptionPane.showMessageDialog(null, "La ventana se encuantra abierta", 
            "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }else {
            desk.add(addRutaView);
            addRutaView.setVisible(true);
            addRutaView.getCbDestino().setSelectedIndex(-1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nombre;
        String descripcion;
        int idDestino;
        
        if (addRutaView.getBtnCrear() == e.getSource()) {
            nombre = addRutaView.getTxtnombre().getText();
            descripcion = addRutaView.getTxtComent().getText();
            idDestino = (addRutaView.getCbDestino().getSelectedIndex()+1);
            
            if (validarDatos(nombre, idDestino)) {
                nuevaRuta(nombre, idDestino, descripcion);
                rutaDAO.create(ruta);
                list.reloadListadosR();
            }
            limpiarCampos();
            
        }else if (addRutaView.getBtnCerrar() == e.getSource()) {
            limpiarCampos();
            addRutaView.dispose();
        }else if (addRutaView.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();
            idTemp = 0;
        }else if (addRutaView.getBtnUpdate() == e.getSource()) {
            nombre = addRutaView.getTxtnombre().getText();
            descripcion = addRutaView.getTxtComent().getText();
            idDestino = (addRutaView.getCbDestino().getSelectedIndex()+1);
            
            if (validarDatos(nombre, idDestino)) {
                nuevaRuta(nombre, idDestino, descripcion);
                ruta.setId(idTemp);
                rutaDAO.update(ruta);
                list.reloadListadosR();
            }
            limpiarCampos();     
        }
    }
    
    private boolean validarDatos(String nombre, int idDestino){
        boolean validacion = true;
        if (nombre.isEmpty() || idDestino == -1) {
            System.out.println("Hay campos vacios");
            JOptionPane.showMessageDialog(null, "El nombre y destino son obligatoorios", 
            "Advertencia", JOptionPane.ERROR_MESSAGE);
            validacion = false;
        }
        return validacion;
    }
    
    private void limpiarCampos(){
        addRutaView.getTxtnombre().setText("");
        addRutaView.getTxtComent().setText("");
        addRutaView.getCbDestino().setSelectedIndex(-1);
        addRutaView.getBtnUpdate().setEnabled(false);
    }
    
    private void nuevaRuta(String nombre, int destino,String descripcion){
        ruta = new Ruta();
        ruta.setNombre(nombre);
        ruta.setIdDestino(destino);
        ruta.setDescripcion(descripcion);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = addRutaView.getTblRutas().getSelectedRow();
        idTemp = (int)addRutaView.getTblRutas().getValueAt(fila, 0);
        ruta = rutaDAO.getObject(idTemp);
        addRutaView.getTxtnombre().setText(ruta.getNombre());
        addRutaView.getTxtComent().setText(ruta.getDescripcion());
        addRutaView.getCbDestino().setSelectedIndex(ruta.getIdDestino()-1);
        addRutaView.getBtnUpdate().setEnabled(true);
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
