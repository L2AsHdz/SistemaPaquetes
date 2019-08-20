package sistemapaquetes.dao;

import java.sql.Connection;
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

    //Decuelve un listado de Usuarios
    @Override
    public List<Usuario> getListado() {
        List<Usuario> usuarios = null;
        
        try {
            String sql = "SELECT * FROM Usuario";
            Connection conexion = Conexion.getConexion();
            Statement declaracion = conexion.createStatement();
            
            usuarios = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("Id"));
                usuario.setNombre(rs.getString("Nombre"));
                usuario.setTipo(rs.getByte("TipoUsuario"));
                usuario.setEstado(rs.getByte("Estado"));
                usuario.setPassword(rs.getString("Password"));
                usuarios.add(usuario);
            }
            rs.close();
            declaracion.close();
            Conexion.desconectar();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return usuarios;
    }

    @Override
    public Usuario getPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void create(Usuario t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Usuario t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
