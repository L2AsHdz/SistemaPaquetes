package sistemapaquetes.dao.ruta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sistemapaquetes.model.Conexion;
import sistemapaquetes.model.Ruta;

/**
 *
 * @author asael
 */
public class RutaDAOImpl implements RutaDAO{
    
    private static RutaDAOImpl rutaDAO = null;
    private Connection conexion = Conexion.getConexion();

    private RutaDAOImpl(){}
    
    public static RutaDAOImpl getRutaDAO(){
        if (rutaDAO == null) {
            rutaDAO = new RutaDAOImpl();
        }
        return rutaDAO;
    }
    
    @Override
    public List<Ruta> getListado() {
        List<Ruta> rutas = null;
        
        try {
            String sql = "SELECT r.*, d.Nombre AS NombreDestino FROM Ruta AS r "
            + "INNER JOIN Destino AS d ON r.IdDestino=d.Id";
            Statement declaracion = conexion.createStatement();
            
            rutas = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                Ruta ruta = new Ruta();
                ruta.setId(rs.getInt("Id"));
                ruta.setNombre(rs.getString("Nombre"));
                ruta.setEstado(rs.getByte("Estado"));
                ruta.setIdDestino(rs.getInt("IdDestino"));
                ruta.setNombreDestino(rs.getString("NombreDestino"));
                rutas.add(ruta);
            }
            System.out.println("Listado de Rutas Obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rutas;
    }

    @Override
    public void create(Ruta r) {
        try {
            String sql = "INSERT INTO Ruta (Nombre, Estado, Descripcion, IdDestino) "
            + "VALUES (?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, r.getNombre());
            ps.setInt(2, 1);
            ps.setString(3,r.getDescripcion());
            ps.setInt(4,r.getIdDestino());
            ps.executeUpdate();
            System.out.println("Ruta creada Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se crear la ruta");
            ex.printStackTrace();
        }
    }

    @Override
    public Ruta getObject(Object id) {
        Ruta r = new Ruta();
        try {
            String sql = "SELECT * FROM Ruta WHERE Id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, (Integer)id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                r.setId(rs.getInt("Id"));
                r.setNombre(rs.getString("Nombre"));
                r.setEstado(rs.getByte("Estado"));
                r.setDescripcion(rs.getString("Descripcion"));
                r.setIdDestino(rs.getInt("IdDestino"));
            }
            System.out.println("Ruta obtenido de la BD");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer la ruta");
            ex.printStackTrace();
        }
        return r;
    }

    @Override
    public void update(Ruta r) {
        try {
            String sql = "UPDATE Ruta SET Nombre = ?, Descripcion = ?, IdDestino = ? WHERE Id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getDescripcion());
            ps.setInt(3, r.getIdDestino());
            ps.setInt(4, r.getId());
            ps.executeUpdate();
            System.out.println("Ruta actualizada");
            ps.close();
            ps=null;
        } catch (SQLException ex) {
            System.out.println("No se actualizo el registro");
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getCuotaDestino(int idRuta) {
        int cuotaD = 0;
        try {
            String sql = "SELECT d.CuotaDestino FROM Ruta AS r INNER JOIN Destino AS d "
                    + "ON r.IdDestino=d.ID WHERE r.Id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idRuta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cuotaD = rs.getInt(1);
            }
            System.out.println("CuotaDestino obtenida");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer la cuotaD");
            ex.printStackTrace();
        }
        return cuotaD;
    }
    
}
