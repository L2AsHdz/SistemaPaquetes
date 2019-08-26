package sistemapaquetes.dao.bodega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import sistemapaquetes.model.Conexion;

/**
 *
 * @author asael
 */
public class BodegaDAOImpl implements BodegaDAO{
    
    private static BodegaDAOImpl bodegaDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private BodegaDAOImpl(){}
    
    public static BodegaDAOImpl getBodegaDAOImpl(){
        if (bodegaDAO == null) {
            bodegaDAO = new BodegaDAOImpl();
        }
        return bodegaDAO;
    }

    @Override
    public void AddPaqueteToBodega(int id) {
        try {
            String sql = "INSERT INTO ColaBodega VALUES (?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Paquete ingresado a bodega Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se ingreso el paquete");
            ex.printStackTrace();
        }
    }

    @Override
    public void RemovePaquete(int id) {
        try {
            String sql = "DELETE FROM ColaBodega WHERE IdPaquete = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se elimino registro");
            ex.printStackTrace();
        }
    }

    @Override
    public LinkedList<Integer> getListado() {
        LinkedList<Integer> cola = null;
        
        try {
            String sql = "SELECT * FROM ColaBodega";
            Statement declaracion = conexion.createStatement();
            
            cola = new LinkedList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                cola.add(rs.getInt(1));
            }
            System.out.println("Cola bodega obtenida");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return cola;
    }
    
}
