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
import sistemapaquetes.model.IngresoPaquete;

/**
 *
 * @author asael
 */
public class IngresoPaqueteDAOImpl implements IngresoPaqueteDAO{
    
    private static IngresoPaqueteDAOImpl ingresoPDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private IngresoPaqueteDAOImpl(){}
    
    public static IngresoPaqueteDAOImpl getIngresoPDAOImpl(){
        if (ingresoPDAO == null) {
            ingresoPDAO = new IngresoPaqueteDAOImpl();
        }
        return ingresoPDAO;
    }

    @Override
    public List<IngresoPaquete> getListado() {
        List<IngresoPaquete> ingresos = null;
        
        try {
            String sql = "SELECT * FROM IngresoPaquete";
            Statement declaracion = conexion.createStatement();
            
            ingresos = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                IngresoPaquete ingreso = new IngresoPaquete();
                ingreso.setId(rs.getInt("Id"));
                ingreso.setCodigoFactura(rs.getInt("CodigoFactura"));
                ingreso.setIdPaquete(rs.getInt("IdPaquete"));
                ingreso.setNitCliente(rs.getString("NitCliente"));
                ingreso.setFecha(LocalDate.parse(rs.getString("Fecha")));
                ingreso.setPrecioPriorizacion(rs.getFloat("PrecioPriorizacion"));
                ingreso.setPrecioLibra(rs.getFloat("PrecioLibra"));
                ingreso.setCuotaDestino(rs.getFloat("CuotaDestino"));
                ingreso.setCostoPeso(rs.getFloat("CostoPeso"));
                ingreso.setTotal();
                ingresos.add(ingreso);
            }
            System.out.println("Listado de Ingresos Obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ingresos;
    }

    @Override
    public void create(IngresoPaquete i) {
        try {
            String sql = "INSERT INTO IngresoPaquete(CodigoFactura, IdPaquete, NitCliente, "
                    + "Fecha, PrecioPriorizacion, PrecioLibra, CuotaDestino, CostoPeso, Total) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, i.getCodigoFactura());
            ps.setInt(2, i.getIdPaquete());
            ps.setString(3, i.getNitCliente());
            ps.setString(4, i.getFecha().toString());
            ps.setFloat(5, i.getPrecioPriorizacion());
            ps.setFloat(6, i.getPrecioLibra());
            ps.setFloat(7, i.getCuotaDestino());
            ps.setFloat(8, i.getCostoPeso());
            ps.setFloat(9, i.getTotal());
            ps.executeUpdate();
            System.out.println("IngresoPaquete creado Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo crear el ingreso");
            ex.printStackTrace();
        }
    }

    @Override
    public IngresoPaquete getObject(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(IngresoPaquete t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCodFactura() {
        int codigo = 0;
        try {
            String sql = "SELECT CodigoFactura FROM IngresoPaquete ORDER BY CodigoFactura"
                    + " DESC LIMIT 1";
            Statement declaracion = conexion.createStatement();
            
            ResultSet rs = declaracion.executeQuery(sql);
            if (rs.next()) {
                codigo = rs.getInt(1);
            }
            
            System.out.println("CodigoFactura obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el codigoF");
            ex.printStackTrace();
        }
        return codigo;
    }
    
}
