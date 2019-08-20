package sistemapaquetes.controller;

import sistemapaquetes.dao.CRUD;
import sistemapaquetes.dao.UsuarioDAOImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sistemapaquetes.model.Usuario;
import sistemapaquetes.ui.LoginFrame;

/**
 *
 * @author asael
 */
public class LoginController implements ActionListener{
    private LoginFrame login;
    private CRUD<Usuario> userDAO;

    public LoginController(LoginFrame log) {
        this.login = log;
        userDAO = new UsuarioDAOImpl();
        login.btnLogin.addActionListener(this);
        login.btnSalir.addActionListener(this);
    }
    
    //Iniciar interfaz login
    public void iniciar(){
        login.pack();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }

    //Eventos de los botones
    @Override
    public void actionPerformed(ActionEvent e) {
        if (login.btnLogin == e.getSource()) {
             try {
                validarLogin((ArrayList<Usuario>) userDAO.getListado(), 
                login.txtUsuario.getText(), login.txtPassword.getText());
             } catch (Exception ex) {
                System.out.println("Error al intentar validar");
             }
        }else if (login.btnSalir == e.getSource()) {
            System.exit(0);
        }
    }

    private void validarLogin(ArrayList<Usuario> users,String nombre, String pass) {
        if (nombre.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error! - Campos vacios");
            login.txtUsuario.requestFocus();
        }else {
            int tipoUser = 0;
            for (Usuario u : users) {
                if (nombre.equalsIgnoreCase(u.getNombre()) && pass.equals(u.getPassword())) {
                tipoUser = u.getTipo();               
                }
            }
            switch (tipoUser) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta", 
                    "Error login", JOptionPane.WARNING_MESSAGE);
                    limpiarCampos();
                    break;
                    
                case 1:
                    login.dispose();
                    JOptionPane.showMessageDialog(null, "Login correcto", "Inicio", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    //abrir gui administrador sistema
                    break;
                
                case 2:
                    //abrir gui operador
                    break;
                    
                case 3:
                    //abrir gui recepcionista
                    break;
            }
        }
    }
    
    //Limpia los campos del login y coloca el cursor en el txtUsuario
    public void limpiarCampos(){
        login.txtUsuario.setText("");
        login.txtPassword.setText("");
        login.txtUsuario.requestFocus();
    }
}
