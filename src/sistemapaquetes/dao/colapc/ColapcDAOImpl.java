package sistemapaquetes.dao.colapc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import sistemapaquetes.model.Conexion;
import sistemapaquetes.model.Paquete;
import sistemapaquetes.model.PaqueteInCola;
import sistemapaquetes.model.PuntoControl;

/**
 *
 * @author asael
 */
public class ColapcDAOImpl implements ColapcDAO{
    
    private static ColapcDAOImpl colaDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private ColapcDAOImpl(){};
    
    public static ColapcDAOImpl getColaDAOImpl(){
        if (colaDAO == null) {
            colaDAO = new ColapcDAOImpl();
        }
        return colaDAO;
    }

    @Override
    public int getNoPaquetes(int noPC, int idRuta) {
        int valor = 0;
        try {
            String sql = "SELECT COUNT(*) FROM ColaPuntoControl WHERE NoPuntoControl = ? AND IdRutaPC = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, noPC);
            ps.setInt(2, idRuta);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                valor = rs.getInt(1);
            }
            
            System.out.println("Numero paquetes en cola obtenido");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer la DB");
            ex.printStackTrace();
        }
        return valor;
    }

    @Override
    public List<PaqueteInCola> getListado(int noPC) {
        List<PaqueteInCola> paquetesCola = null;
        
        try {
            String sql = "SELECT * FROM ColaPuntoControl WHERE NoPuntoCotrol = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, noPC);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PaqueteInCola pCola = new PaqueteInCola();
                pCola.setIdPaquete(rs.getInt("IdPaquete"));
                pCola.setIdPuntoControl(noPC);
                pCola.setIdRuta(rs.getInt("IdRutaPC"));
                paquetesCola.add(pCola);
            }
            System.out.println("Listado de paquetes en cola leido correctamente");
            ps.close();
            ps = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return paquetesCola;
    }

    @Override
    public void addPaqueteToCola(Paquete p, int noPC) {
        try {
            String sql = "INSERT INTO ColaPuntoControl VALUES (?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ps.setInt(2, noPC);
            ps.setInt(3, p.getIdRuta());
            ps.executeUpdate();
            System.out.println("Paquete Agregado Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se agrego el paquete");
            ex.printStackTrace();
        }
    }

    @Override
    public void removePaquete(Paquete p, PuntoControl pc) {
        try {
            String sql = "DELETE FROM ColaPuntoControl WHERE NoPuntoCotrol = ?"
                    + " AND IdPaquete = ? AND IdRUtaPC = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, pc.getNumero());
            ps.setInt(2, p.getId());
            ps.setInt(3, pc.getIdRuta());
            ps.executeUpdate();
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se elimino registro");
            ex.printStackTrace();
        }
    }
    
}
