package sistemapaquetes.dao.colapc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistemapaquetes.model.Conexion;
import sistemapaquetes.model.Paquete;
import sistemapaquetes.model.PaqueteInCola;

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
    public List<PaqueteInCola> getListado(int noPC, int idRuta) {
        List<PaqueteInCola> paquetesCola = null;
        
        try {
            String sql = "SELECT c.*, p.Nombre AS Paquete, r.Nombre AS Ruta, "
                    + "pc.Nombre AS PuntoControl, pc.TarifaOperacion, pc.TarifaOperacionGlobal "
                    + "FROM ColaPuntoControl AS c INNER JOIN Paquete AS p ON c.IdPaquete=p.Id "
                    + "INNER JOIN PuntoControl AS pc ON c.NoPuntoControl=pc.Numero AND "
                    + "c.IdRutaPC=pc.IdRuta INNER JOIN Ruta AS r ON pc.IdRuta = r.Id "
                    + "WHERE c.NoPuntoControl=? AND c.IdRutaPC=? Limit 1";
            paquetesCola = new ArrayList();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, noPC);
            ps.setInt(2, idRuta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Entro al resulset colaPC");
                PaqueteInCola pCola = new PaqueteInCola();
                pCola.setIdPaquete(rs.getInt("IdPaquete"));
                pCola.setNombrePaquete(rs.getString("Paquete"));
                pCola.setIdPuntoControl(noPC);
                pCola.setNombrePuntoControl(rs.getString("PuntoControl"));
                pCola.setIdRuta(idRuta);
                pCola.setNombreRuta(rs.getString("Ruta"));
                if (isFloat(rs.getString("TarifaOperacion")) && Float.parseFloat(rs.getString("TarifaOperacion")) != 0) {
                    pCola.setTarifaOperacion(rs.getFloat("TarifaOperacion"));
                }else{
                    pCola.setTarifaOperacion(rs.getFloat("TarifaOperacionGlobal"));
                }
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
    public void removePaquete(int idP) {
        try {
            String sql = "DELETE FROM ColaPuntoControl WHERE IdPaquete = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idP);
            ps.executeUpdate();
            System.out.println("se elimino el registro-colaPC");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se elimino registro");
            ex.printStackTrace();
        }
    }
    
    private boolean isFloat(String tarifa){
        try {
            Float.parseFloat(tarifa);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
