package sistemapaquetes.controller;

import sistemapaquetes.controller.administrador.AdminUIController;
import sistemapaquetes.dao.CRUD;
import sistemapaquetes.dao.usuario.UsuarioDAOImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sistemapaquetes.controller.recepcionista.RecepcionistaUIController;
import sistemapaquetes.model.Usuario;
import sistemapaquetes.ui.LoginView;
import sistemapaquetes.ui.administradorUI.AdminView;
import sistemapaquetes.ui.recepcionistaUI.RecepcionistaView;

/**
 *
 * @author asael
 */
public class LoginController implements ActionListener{
    private LoginView login;
    private CRUD<Usuario> userDAO;
    
    //Vista y controlador para AdminUI
    private AdminView adminView;
    private AdminUIController adminC;
    
    //Vista y controlador para RecepcionistaUI
    private RecepcionistaView recepView;
    private RecepcionistaUIController recepC;

    public LoginController(LoginView log) {
        this.login = log;
        userDAO = UsuarioDAOImpl.getUserDAO();
        login.getBtnLogin().addActionListener(this);
        login.getBtnSalir().addActionListener(this);
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
        if (login.getBtnLogin() == e.getSource()) {
             try {
                validarLogin((ArrayList<Usuario>) userDAO.getListado(), 
                login.getTxtUsuario().getText(), login.getTxtPassword().getText());
             } catch (Exception ex) {
                System.out.println("Error al intentar validar");
                System.out.println(ex.getMessage());
                ex.printStackTrace();
             }
        }else if (login.getBtnSalir() == e.getSource()) {
            System.exit(0);
        }
    }

    private void validarLogin(ArrayList<Usuario> users,String nombre, String pass) {
        if (nombre.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error! - Campos vacios");
            login.getTxtUsuario().requestFocus();
        }else {
            int tipoUser = 0;
            for (Usuario u : users) {
                if (nombre.equalsIgnoreCase(u.getNombreUsuario()) && pass.equals(u.getPassword())) {
                    tipoUser = u.getTipo();          
                    if (u.getEstado() == 0) {
                        tipoUser = 4;
                    }
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
                    limpiarCampos();
                    adminView = new AdminView();
                    adminC = new AdminUIController(adminView);
                    adminC.iniciar();
                    break;
                
                case 2:
                    //abrir gui operador
                    break;
                    
                case 3:
                    login.dispose();
                    limpiarCampos();
                    recepView = new RecepcionistaView();
                    recepC = new RecepcionistaUIController(recepView);
                    recepC.iniciar();
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Usuario Deshabilitado", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }
    
    //Limpia los campos del login y coloca el cursor en el txtUsuario
    private void limpiarCampos(){
        login.getTxtUsuario().setText("");
        login.getTxtPassword().setText("");
        login.getTxtUsuario().requestFocus();
    }
}
