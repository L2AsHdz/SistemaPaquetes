package sistemapaquetes.dao.paquete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sistemapaquetes.model.Conexion;
import sistemapaquetes.model.ProcesoPaquete;

/**
 *
 * @author asael
 */
public class ProcesoPaqueteDAOImpl implements ProcesoPaqueteDAO{
    private static ProcesoPaqueteDAOImpl procesoDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private ProcesoPaqueteDAOImpl(){}
    
    public static ProcesoPaqueteDAOImpl getProcesoPDAOImpl(){
        if (procesoDAO == null) {
            procesoDAO = new ProcesoPaqueteDAOImpl();
        }
        return procesoDAO;
    }

    @Override
    public List<ProcesoPaquete> getListado() {
        List<ProcesoPaquete> procesos = null;
        
        try {
            String sql = "SELECT * FROM ProcesoPaquete";
            Statement declaracion = conexion.createStatement();
            
            procesos = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                ProcesoPaquete proceso = new ProcesoPaquete();
                proceso.setId(rs.getInt("Id"));
                proceso.setIdPaquete(rs.getInt("IdPaquete"));
                proceso.setNoPuntoControl(rs.getInt("NoPuntoControl"));
                proceso.setIdRuta(rs.getInt("IdRutaPC"));
                proceso.setHoras(rs.getFloat("Horas"));
                proceso.setTarifaOperacion(rs.getFloat("TarifaOperacion"));
                proceso.setCosto();
                procesos.add(proceso);
            }
            System.out.println("Listado de Procesos Obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return procesos;
    }

    @Override
    public void create(ProcesoPaquete p) {
        try {
            String sql = "INSERT INTO IngresoPaquete(IdPaquete, NoPuntoControl, IdRutaPC, "
                    + "Horas, TarifaOperacion, Costo) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, p.getIdPaquete());
            ps.setInt(2, p.getNoPuntoControl());
            ps.setInt(3, p.getIdRuta());
            ps.setFloat(4, p.getHoras());
            ps.setFloat(5, p.getTarifaOperacion());
            ps.setFloat(6, p.getCosto());
            ps.executeUpdate();
            System.out.println("ProcesoPaquete creado Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo crear el proceso");
            ex.printStackTrace();
        }
    }

    @Override
    public ProcesoPaquete getObject(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(ProcesoPaquete t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
