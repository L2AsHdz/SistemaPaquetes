package sistemapaquetes.dao.puntocontrol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sistemapaquetes.model.Conexion;
import sistemapaquetes.model.PuntoControl;

/**
 *
 * @author asael
 */
public class PuntoControlDAOImpl implements PuntoControlDAO{
    
    private static PuntoControlDAOImpl puntoCDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private PuntoControlDAOImpl(){}
    
    public static PuntoControlDAOImpl getPuntoCDAO(){
        if (puntoCDAO == null) {
            puntoCDAO = new PuntoControlDAOImpl();
        }
        return puntoCDAO;
    }

    @Override
    public List<PuntoControl> getListado() {
        List<PuntoControl> puntosC = null;
        
        try {
            String sql = "SELECT pc.*, u.Nombre AS Operador, r.Nombre AS Ruta "
                    + "FROM PuntoControl AS pc INNER JOIN Usuario AS u ON pc.DPIOperador=u.DPI "
                    + "INNER JOIN Ruta AS r ON pc.IdRuta=r.Id ORDER BY pc.IdRuta, pc.Numero ASC";
            Statement declaracion = conexion.createStatement();
            
            puntosC = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                PuntoControl puntoC = new PuntoControl();
                puntoC.setIdRuta(rs.getInt("IdRuta"));
                puntoC.setNumero(rs.getInt("Numero"));
                puntoC.setNombre(rs.getString("Nombre"));
                puntoC.setLimitePaquetes(rs.getInt("LimitePaquetes"));
                puntoC.setNombreOperador(rs.getString("Operador"));
                puntoC.setNombreRuta(rs.getString("Ruta"));
                puntoC.setTarifaOperacion(rs.getFloat("TarifaOperacion"));
                puntoC.setTarifaOperacionGlobal(rs.getFloat("TarifaOperacionGlobal"));
                puntosC.add(puntoC);
            }
            System.out.println("Listado de Puntos de Control Obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return puntosC;
    }

    @Override
    public void create(PuntoControl t) {
        try {
            String sql = "INSERT INTO PuntoControl VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, t.getNumero());
            ps.setInt(2, t.getIdRuta());
            ps.setString(3, t.getNombre());
            ps.setInt(4, t.getLimitePaquetes());
            ps.setString(5, t.getDPIOperador());
            ps.setFloat(6, t.getTarifaOperacion());
            ps.setFloat(7, t.getTarifaOperacionGlobal());
            ps.executeUpdate();
            System.out.println("PuntoControl creado Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se crear la ruta");
            ex.printStackTrace();
        }
    }

    @Override
    public PuntoControl getObject(Object t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(PuntoControl t) {
        try {
            String sql = "UPDATE PuntoControl SET Nombre = ?, LimitePaquetes = ?, "
                    + "DPIOperador = ?, TarifaOperacion = ? WHERE Numero = ? AND IdRuta = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, t.getNombre());
            ps.setInt(2, t.getLimitePaquetes());
            ps.setString(3, t.getDPIOperador());
            ps.setFloat(4, t.getTarifaOperacion());
            ps.setInt(5, t.getNumero());
            ps.setInt(6, t.getIdRuta());
            ps.executeUpdate();
            System.out.println("PuntoControl actualizado");
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
    public PuntoControl getPuntoControl(int numPC, int idRuta) {
        PuntoControl pc = new PuntoControl();
        try {
            String sql = "SELECT * FROM PuntoControl WHERE Numero = ? AND IdRuta = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, numPC);
            ps.setInt(2, idRuta);
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            pc.setNumero(rs.getInt("Numero"));
            pc.setNombre(rs.getString("Nombre"));
            pc.setIdRuta(rs.getInt("IdRuta"));
            pc.setLimitePaquetes(rs.getInt("LimitePaquetes"));
            pc.setDPIOperador(rs.getString("DPIOperador"));
            pc.setTarifaOperacion(rs.getFloat("TarifaOperacion"));
            pc.setTarifaOperacionGlobal(rs.getFloat("TarifaOperacionGlobal"));
            System.out.println("Punto de Control obtenido de la BD");
            
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer la ruta");
            ex.printStackTrace();
        }
        return pc;
    }

    @Override
    public int getNoPuntosInRuta(int idRuta) {
        int valor = 0;
        try {
            String sql = "SELECT COUNT(*) FROM PuntoControl WHERE IdRuta = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idRuta);
            ResultSet rs = ps.executeQuery();
            rs.next();
            valor = rs.getInt(1);
            System.out.println("Numero de puntos de control obtenido");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer la DB");
            ex.printStackTrace();
        }
        return valor;
    }

    @Override
    public int getLastPCNumber(int idRuta) {
        int valor = 0;
        try {
            String sql = "SELECT Numero FROM PuntoControl WHERE IdRuta = ?  "
                    + "ORDER BY IdRuta, Numero DESC LIMIT 1";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idRuta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                valor = rs.getInt(1);
            }
            
            System.out.println("Numero de puntos de control obtenido");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer la DB");
            ex.printStackTrace();
        }
        return valor;
    }
    
}
