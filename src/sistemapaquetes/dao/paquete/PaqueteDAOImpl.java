package sistemapaquetes.dao.paquete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sistemapaquetes.model.Conexion;
import sistemapaquetes.model.Paquete;

/**
 *
 * @author asael
 */
public class PaqueteDAOImpl implements PaqueteDAO{
    private static PaqueteDAOImpl paqueteDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private PaqueteDAOImpl(){}
    
    public static PaqueteDAOImpl getPaqueteDAOImpl(){
        if (paqueteDAO == null) {
            paqueteDAO = new PaqueteDAOImpl();
        }
        return paqueteDAO;
    }

    @Override
    public List<Paquete> getListado() {
        List<Paquete> paquetes = null;
        
        try {
            String sql = "SELECT p.Id, p.Nombre, r.Nombre AS NombreRuta, p.Descripcion, "
                    + "p.Peso, p.Priorizado, FROM Paquete AS p INNER JOIN Ruta AS r"
                    + "ON p.IdRuta=r.Id ORDER BY p.Id ASC;";
            Statement declaracion = conexion.createStatement();
            
            paquetes = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                Paquete paquete = new Paquete();
                paquete.setId(rs.getInt("Id"));
                paquete.setNombre(rs.getString("Nombre"));
                paquete.setNombreRuta(rs.getString("NombreRuta"));
                paquete.setDescripcion(rs.getString("Descripcion"));
                paquete.setPeso(rs.getFloat("Peso"));
                paquete.setPriorizado(rs.getByte("Priorizado"));
                paquetes.add(paquete);
            }
            System.out.println("Listado de Paquetes Obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return paquetes;
    }

    @Override
    public void create(Paquete p) {
        try {
            String sql = "INSERT INTO Paquete (Nombre, Descripcion, Peso, EstadoRetiro, Priorizado, IdRuta)"
                    + "VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setFloat(3, p.getPeso());
            ps.setInt(4, 0);
            ps.setInt(5, p.getPriorizado());
            ps.setInt(6, p.getIdRuta());
            ps.executeUpdate();
            System.out.println("Paquete creado Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se crear la ruta");
            ex.printStackTrace();
        }
    }

    @Override
    public Paquete getObject(Object id) {
        Paquete p = new Paquete();
        try {
            String sql = "SELECT * FROM Paquete WHERE Id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, (Integer)id);
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            p.setId(rs.getInt("Id"));
            p.setNombre(rs.getString("Nombre"));
            p.setDescripcion(rs.getString("Descripcion"));
            p.setPeso(rs.getFloat("Peso"));
            p.setEstadoRetiro(rs.getByte("EstadoRetiro"));
            p.setPriorizado(rs.getByte("Priorizado"));
            p.setIdRuta(rs.getInt("IdRuta"));
            
            System.out.println("Paquete obtenido de la BD");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el paquete");
            ex.printStackTrace();
        }
        return p;
    }

    @Override
    public void update(Paquete p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getIdPaquete() {
        int id = 0;
        try {
            String sql = "SELECT Id FROM Paquete ORDER BY Id DESC LIMIT 1";
            Statement declaracion = conexion.createStatement();
            
            ResultSet rs = declaracion.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt(1);
            }
            
            System.out.println("IdP obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el IdP");
            ex.printStackTrace();
        }
        return id;
    }
    
}
