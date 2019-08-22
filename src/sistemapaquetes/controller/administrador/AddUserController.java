package sistemapaquetes.controller.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import sistemapaquetes.dao.CRUD;
import sistemapaquetes.dao.UsuarioDAOImpl;
import sistemapaquetes.model.ListasObservables;
import sistemapaquetes.model.Usuario;
import sistemapaquetes.ui.administradorUI.AddUserView;

/**
 *
 * @author asael
 */
public class AddUserController implements ActionListener, MouseListener {

    private AddUserView addUserView;
    private Usuario usuario;
    private CRUD<Usuario> userDAO;
    private String dpiTemp = "";
    private ListasObservables list = ListasObservables.getInstance();

    public AddUserController(AddUserView addUser) {
        userDAO = UsuarioDAOImpl.getUserDAO();
        this.addUserView = addUser;
        this.addUserView.getBtnAdd().addActionListener(this);
        this.addUserView.getBtnCerrar().addActionListener(this);
        this.addUserView.getBtnLimpiar().addActionListener(this);
        this.addUserView.getBtnUpdate().addActionListener(this);
        this.addUserView.getTblUsuarios().addMouseListener(this);
    }

    public void iniciar(JDesktopPane desk) {
        if (addUserView.isShowing()) {
            JOptionPane.showMessageDialog(null, "La ventana se encuantra abierta", 
            "Informacion", JOptionPane.INFORMATION_MESSAGE);
        } else {
            desk.add(addUserView);
            addUserView.setVisible(true);
        }
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
            tipo = (byte) (addUserView.getCbTipoUsuario().getSelectedIndex() + 1);

            if (validarDatos(DPI, nombre, nombreUsuario, tipo, pass)) {
                nuevoUsuario(DPI, nombre, nombreUsuario, tipo, pass);
                userDAO.create(usuario);
                list.reloadListadosU();
                //addUserView.reloadList();
            }
            limpiarCampos();
        } else if (addUserView.getBtnCerrar() == e.getSource()) {
            limpiarCampos();
            addUserView.dispose();
        } else if (addUserView.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();
        } else if (addUserView.getBtnUpdate() == e.getSource()) {

            DPI = addUserView.getFtxtDPI().getText();
            nombre = addUserView.getTxtNombre().getText();
            nombreUsuario = addUserView.getTxtNombreUsuario().getText();
            pass = addUserView.getTxtPass().getText();
            tipo = (byte) (addUserView.getCbTipoUsuario().getSelectedIndex() + 1);

            if (validarDatos(DPI, nombre, nombreUsuario, tipo, pass)) {
                nuevoUsuario(DPI, nombre, nombreUsuario, tipo, pass);
                userDAO.update(usuario);
                list.reloadListadosU();
                //addUserView.reloadList();
            }
            limpiarCampos();
        }
    }

    private boolean validarDatos(String DPI, String nombre, String nombreUsuario, int tipo, String pass) {
        boolean validacion = true;
        if (DPI.isEmpty() || nombre.isEmpty() || nombreUsuario.isEmpty() || pass.isEmpty() || tipo == -1) {
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
        addUserView.getBtnUpdate().setEnabled(false);
    }

    private void nuevoUsuario(String DPI, String nombre, String nombreUsuario, byte tipo, String pass) {
        usuario = new Usuario();
        usuario.setDPI(DPI);
        usuario.setNombre(nombre);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(pass);
        usuario.setTipo(tipo);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = addUserView.getTblUsuarios().getSelectedRow();
        dpiTemp = addUserView.getTblUsuarios().getValueAt(fila, 0).toString();
        usuario = userDAO.getObject(dpiTemp);
        addUserView.getTxtNombre().setText(usuario.getNombre());
        addUserView.getTxtNombreUsuario().setText(usuario.getNombreUsuario());
        addUserView.getTxtPass().setText(usuario.getPassword());
        addUserView.getFtxtDPI().setText(usuario.getDPI());
        addUserView.getCbTipoUsuario().setSelectedItem(usuario.getTipoN());
        addUserView.getBtnUpdate().setEnabled(true);
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
