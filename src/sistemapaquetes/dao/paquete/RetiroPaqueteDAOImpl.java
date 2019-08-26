package sistemapaquetes.dao.paquete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sistemapaquetes.model.Conexion;
import sistemapaquetes.model.RetiroPaquete;


/**
 *
 * @author asael
 */
public class RetiroPaqueteDAOImpl implements RetiroPaqueteDAO{
    
    private static RetiroPaqueteDAOImpl retiroDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private RetiroPaqueteDAOImpl(){}
    
    public static RetiroPaqueteDAOImpl getRetiroDAOImpl(){
        if (retiroDAO == null) {
            retiroDAO = new RetiroPaqueteDAOImpl();
        }
        return retiroDAO;
    }

    @Override
    public void create(RetiroPaquete rp) {
        try {
            String sql = "INSERT INTO RetiroPaquete (Costo, Ingreso, Ganancia, IdPaquete,"
                    + " NitCliente, FechaEntrega) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setFloat(1, rp.getCosto());
            ps.setFloat(2, rp.getIngreso());
            ps.setFloat(3, rp.getGanancia());
            ps.setInt(4, rp.getIdPaquete());
            ps.setString(5, rp.getNitCliente());
            ps.setString(6, rp.getFechaEntrega().toString());
            ps.executeUpdate();
            System.out.println("RetiroPaquete creado Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo crear el retiro");
            ex.printStackTrace();
        }
    }

    @Override
    public void getListado() {
        List<RetiroPaquete> retiros = null;
        
        try {
            String sql = "SELECT * FROM RetiroPaquete";
            Statement declaracion = conexion.createStatement();
            
            retiros = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                RetiroPaquete retiro = new RetiroPaquete();
                retiro.setId(rs.getInt("Id"));
                retiro.setCosto(rs.getFloat("Costo"));
                retiro.setIngreso(rs.getFloat("Ingreso"));
                retiro.setGanancia();
                retiro.setIdPaquete(rs.getInt("IdPaquete"));
                retiro.setNitCliente(rs.getString("NitCliente"));
                retiro.setFechaEntrega(LocalDate.parse(rs.getString("FechaEntrega")));
                retiros.add(retiro);
            }
            System.out.println("Listado de Retiros Obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    
}
