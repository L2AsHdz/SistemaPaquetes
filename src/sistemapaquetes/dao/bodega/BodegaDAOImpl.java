package sistemapaquetes.dao.bodega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            String sql = "INSERT INTO ColaBOdega VALUES (?)";
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
            String sql = "DELETE FROM ColaBodega WHERE Id = ?";
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
    
}
