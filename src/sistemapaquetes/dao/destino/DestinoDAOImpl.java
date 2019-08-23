package sistemapaquetes.dao.destino;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sistemapaquetes.model.Conexion;
import sistemapaquetes.model.Destino;

/**
 *
 * @author asael
 */
public class DestinoDAOImpl implements DestinoDAO{
        
    private static DestinoDAOImpl destinoDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private DestinoDAOImpl(){}
    
    public static DestinoDAOImpl getDestinoDAO(){
        if (destinoDAO == null) {
            destinoDAO = new DestinoDAOImpl();
        }
        return destinoDAO;
    }

    @Override
    public List<Destino> getListado() {
        List<Destino> destinos = null;
        
        try {
            String sql = "SELECT * FROM Destino";
            Statement declaracion = conexion.createStatement();
            
            destinos = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                Destino destino = new Destino();
                destino.setId(rs.getInt("Id"));
                destino.setNombre(rs.getString("Nombre"));
                destino.setCuotaDestino(rs.getFloat("CuotaDestino"));
                destinos.add(destino);
            }
            System.out.println("Listado de destinos leido correctamente");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return destinos;
    }

    @Override
    public void create(Destino d) {
        try {
            String sql = "INSERT INTO Destino (Nombre, CuotaDestino) VALUES (?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, d.getNombre());
            ps.setFloat(2, d.getCuotaDestino());
            ps.executeUpdate();
            System.out.println("Destino Ingresado Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se inserto el destino");
            ex.printStackTrace();
        }
    }

    @Override
    public Destino getObject(Object id) {
        Destino d = new Destino();
        try {
            String sql = "SELECT * FROM Destino WHERE Id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, (Integer)id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setId(rs.getInt("Id"));
                d.setNombre(rs.getString("Nombre"));
                d.setCuotaDestino(rs.getFloat("CuotaDestino"));
            }
            System.out.println("Destino obtenido de la BD");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el destino");
            ex.printStackTrace();
        }
        return d;
    }

    @Override
    public void update(Destino d) {
        try {
            String sql = "UPDATE Destino SET Nombre = ?, CuotaDestino = ? WHERE Id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, d.getNombre());
            ps.setFloat(2, d.getCuotaDestino());
            ps.setInt(3, d.getId());
            ps.executeUpdate();
            System.out.println("Destino actualizado");
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
    
}
