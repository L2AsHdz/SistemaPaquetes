package sistemapaquetes.dao.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import sistemapaquetes.model.Conexion;
import sistemapaquetes.model.Usuario;

/**
 *
 * @author asael
 */
public class UsuarioDAOImpl implements UsuarioDAO{
    private static UsuarioDAOImpl userDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private UsuarioDAOImpl(){}
    
    public static UsuarioDAOImpl getUserDAO(){
        if (userDAO == null) {
            userDAO = new UsuarioDAOImpl();
        }
        return userDAO;
    }

    //Decuelve un listado de Usuarios
    @Override
    public List<Usuario> getListado() {
        List<Usuario> usuarios = null;
        
        try {
            String sql = "SELECT * FROM Usuario";
            Statement declaracion = conexion.createStatement();
            
            usuarios = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setDPI(rs.getString("DPI"));
                usuario.setNombre(rs.getString("Nombre"));
                usuario.setTipo(rs.getByte("Tipo"));
                usuario.setEstado(rs.getByte("Estado"));
                usuario.setPassword(rs.getString("Password"));
                usuario.setNombreUsuario(rs.getString("NombreUsuario"));
                usuarios.add(usuario);
            }
            System.out.println("Listado de Usuarios Obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public Usuario getObject(Object DPI) {
        Usuario u = new Usuario();//sera posible usar un usuario global
        try {
            String sql = "SELECT * FROM Usuario WHERE DPI = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, (String)DPI);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u.setDPI(rs.getString("DPI"));
                u.setNombre(rs.getString("Nombre"));
                u.setTipo(rs.getByte("Tipo"));
                u.setEstado(rs.getByte("Estado"));
                u.setPassword(rs.getString("Password"));
                u.setNombreUsuario(rs.getString("NombreUsuario"));
            }
            System.out.println("Usuario obtenido de la BD");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el usuario");
            ex.printStackTrace();
        }
        return u;
    }

    @Override
    public void create(Usuario u) {
        try {
            String sql = "INSERT INTO Usuario (DPI, Nombre, Tipo, Estado, Password, NombreUsuario) "
            + "VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, u.getDPI());
            ps.setString(2, u.getNombre());
            ps.setInt(3,u.getTipo());
            ps.setInt(4,1);
            ps.setString(5, u.getPassword());
            ps.setString(6, u.getNombreUsuario());
            ps.executeUpdate();
            System.out.println("Usuario Ingresado Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se inserto el usuario");
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Usuario u) {
        try {
            String sql = "UPDATE Usuario SET Nombre = ?, Tipo = ?, "
            + "NombreUsuario = ?, Password = ? WHERE DPI = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setInt(2, u.getTipo());
            ps.setString(3, u.getNombreUsuario());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getDPI());
            ps.executeUpdate();
            System.out.println("Usuario actualizado");
            ps.close();
            ps=null;
        } catch (SQLException ex) {
            System.out.println("No se actualizo el registro");
            ex.printStackTrace();
        } 
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void disableUser(String DPI) {
        try {
            String sql = "UPDATE Usuario SET Estado = ? WHERE DPI = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, 0);
            ps.setString(2, DPI);
            ps.executeUpdate();
            System.out.println("Usuario deshabilitado");
            JOptionPane.showMessageDialog(null, "Usuario con DPI: "+DPI+
            " fue deshabilitado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            ps.close();
            ps=null;
        } catch (SQLException ex) {
            System.out.println("No se deshabilito el usuario");
            ex.printStackTrace();
        } 
    }
}
