package sistemapaquetes.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.dao.CRUD;
import sistemapaquetes.dao.UsuarioDAOImpl;
import sistemapaquetes.model.Usuario;
import sistemapaquetes.ui.administradorUI.AddUserView;

/**
 *
 * @author asael
 */
public class AddUserController implements ActionListener{
    private AddUserView addUserView;
    private Usuario usuario;
    private CRUD<Usuario> userDAO;

    public AddUserController(AddUserView addUser) {
        this.addUserView = addUser;
        userDAO = new UsuarioDAOImpl();
        addUserView.getBtnAdd().addActionListener(this);
        addUserView.getBtnCerrar().addActionListener(this);
        addUserView.getBtnDeshabilitar().addActionListener(this);
        addUserView.getBtnLimpiar().addActionListener(this);
        addUserView.getBtnUpdate().addActionListener(this);
    }
    
    public void iniciar(JDesktopPane desk){
        desk.add(addUserView);
        addUserView.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String DPI;
        String nombre;
        String nombreUsuario;
        String pass;
        byte tipo;
            
        if (addUserView.getBtnAdd() == e.getSource()) {
            
            DPI = addUserView.getFtxtDPI().getText();
            nombre = addUserView.getTxtNombre().getText();
            nombreUsuario = addUserView.getTxtNombreUsuario().getText();
            pass = addUserView.getTxtPass().getText();
            tipo = (byte)addUserView.getCbTipoUsuario().getSelectedIndex();
            
            if (validarDatos(DPI,nombre, nombreUsuario, tipo, pass)) {
                nuevoUsuario(DPI, nombre, nombreUsuario, tipo, pass);
                userDAO.create(usuario);
            }
        }else if (addUserView.getBtnCerrar() == e.getSource()) {
            addUserView.dispose();
        }else if (addUserView.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();
        }else if (addUserView.getBtnUpdate() == e.getSource()) {
            
            DPI = addUserView.getFtxtDPI().getText();
            nombre = addUserView.getTxtNombre().getText();
            nombreUsuario = addUserView.getTxtNombreUsuario().getText();
            pass = addUserView.getTxtPass().getText();
            tipo = (byte)addUserView.getCbTipoUsuario().getSelectedIndex();
            
            if (validarDatos(DPI, nombre, nombreUsuario, tipo, pass)) {
                nuevoUsuario(DPI, nombre, nombreUsuario, tipo, pass);
                userDAO.update(usuario);
            }
        }else if (addUserView.getBtnDeshabilitar() == e.getSource()) {
            //codigo para deshabilitara un usuario
        }
    }
    
    private boolean validarDatos(String DPI, String nombre, String nombreUsuario, int tipo, String pass){
        boolean validacion = true;
        if (DPI.isEmpty() || nombre.isEmpty() || nombreUsuario.isEmpty() || pass.isEmpty()  || tipo == -1) {
            System.out.println("Hay campos vacios");
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Advertencia", JOptionPane.ERROR_MESSAGE);
            validacion = false;
        }
        return validacion;
    }

    private void limpiarCampos() {
        addUserView.getFtxtDPI().setText("");
        addUserView.getTxtNombre().setText("");
        addUserView.getTxtNombreUsuario().setText("");
        addUserView.getTxtPass().setText("");
        addUserView.getCbTipoUsuario().setSelectedIndex(-1);
        addUserView.getTxtNombre().requestFocus();
    }

    private void nuevoUsuario(String DPI, String nombre, String nombreUsuario, byte tipo, String pass) {
        usuario = new Usuario();
        usuario.setDPI(DPI);
        usuario.setNombre(nombre);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(pass);
        usuario.setTipo(tipo);
        userDAO.create(usuario);
    }
}
