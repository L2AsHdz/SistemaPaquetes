package sistemapaquetes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sistemapaquetes.model.Conexion;
import sistemapaquetes.model.Usuario;

/**
 *
 * @author asael
 */
public class UsuarioDAOImpl implements UsuarioDAO{
    private Connection conexion = Conexion.getConexion();

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
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public Usuario getPorId(int DPI) {
        throw new UnsupportedOperationException("Not supported yet.");
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
            ps.execute();
            System.out.println("Registro Ingresado Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se inserto el registro");
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
    
}
