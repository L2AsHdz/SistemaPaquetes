package sistemapaquetes.dao.precio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sistemapaquetes.model.Conexion;
import sistemapaquetes.model.PrecioGlobal;

/**
 *
 * @author asael
 */
public class PrecioDAOImpl implements PrecioDAO{
    
    private static PrecioDAOImpl precioDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private PrecioDAOImpl(){}
    
    public static PrecioDAOImpl getPrecioDAO(){
        if (precioDAO == null) {
            precioDAO = new PrecioDAOImpl();
        }
        return precioDAO;
    }

    @Override
    public List<PrecioGlobal> getListado() {
        List<PrecioGlobal> precios = null;
        
        try {
            String sql = "SELECT * FROM PreciosGlobales";
            Statement declaracion = conexion.createStatement();
            
            precios = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                PrecioGlobal precio = new PrecioGlobal();
                precio.setId(rs.getInt("Id"));
                precio.setNombre(rs.getString("Nombre"));
                precio.setValor(rs.getFloat("Valor"));
                precios.add(precio);
            }
            System.out.println("Listado de PreciosGlobales Obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return precios;
    }

    @Override
    public void create(PrecioGlobal t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PrecioGlobal getObject(Object t) {
        PrecioGlobal pg = new PrecioGlobal();
        try {
            String sql = "SELECT * FROM PreciosGlobales WHERE Id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, (Integer)t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pg.setId(rs.getInt("Id"));
                pg.setNombre(rs.getString("Nombre"));
                pg.setValor(rs.getFloat("Valor"));
            }
            System.out.println("PrecioGlobal obtenido de la BD");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el usuario");
            ex.printStackTrace();
        }
        return pg;
    }

    @Override
    public void update(PrecioGlobal pg) {
        try {
            String sql = "UPDATE PreciosGlobales SET Nombre = ?, Valor = ? WHERE Id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, pg.getNombre());
            ps.setFloat(2, pg.getValor());
            ps.setInt(3, pg.getId());
            ps.executeUpdate();
            System.out.println("PrecioGlobal actualizado");
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
    public int getValor(int Id) {
        int valor = 0;
        try {
            String sql = "SELECT Valor FROM PreciosGlobales WHERE Id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, Id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            valor = rs.getInt("Valor");
            System.out.println("Valor de Precio global obtenido");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el usuario");
            ex.printStackTrace();
        }
        return valor;
    }
    
}
