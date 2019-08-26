package sistemapaquetes.dao.cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sistemapaquetes.model.Cliente;
import sistemapaquetes.model.Conexion;

/**
 *
 * @author asael
 */
public class ClienteDAOImpl implements ClienteDAO{
    
    private static ClienteDAOImpl clienteDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private ClienteDAOImpl(){}
    
    public static ClienteDAOImpl getClienteDAOImpl(){
        if (clienteDAO == null) {
            clienteDAO = new ClienteDAOImpl();
        }
        return clienteDAO;
    }

    @Override
    public List<Cliente> getListado() {
        List<Cliente> clientes = null;
        
        try {
            String sql = "SELECT * FROM Cliente";
            Statement declaracion = conexion.createStatement();
            
            clientes = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNit(rs.getString("NIT"));
                cliente.setDireccion(rs.getString("Direccion"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setTelefono(rs.getString("Telefono"));
                clientes.add(cliente);
            }
            System.out.println("Listado de Cientes Obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public void create(Cliente c) {
        try {
            String sql = "INSERT INTO Cliente VALUES (?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, c.getNit());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getTelefono());
            ps.executeUpdate();
            System.out.println("Cliente Ingresado Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se inserto el cliente");
            ex.printStackTrace();
        }
    }

    @Override
    public Cliente getObject(Object nit) {
        Cliente c = null;
        try {
            String sql = "SELECT * FROM Cliente WHERE NIT = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, (String)nit);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                c = new Cliente();
                c.setNit(rs.getString("NIT"));
                c.setNombre(rs.getString("Nombre"));
                c.setDireccion(rs.getString("Direccion"));
                c.setTelefono(rs.getString("Telefono"));
            }
            
            System.out.println("Cliente obtenido de la BD");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el Cliente");
            ex.printStackTrace();
        }
        return c;
    }

    @Override
    public void update(Cliente t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(int t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Cliente c, String nit) {
        try {
            String sql = "UPDATE Cliente SET NIT = ?, Nombre = ?, Direccion = ?, "
            + "Telefono = ? WHERE NIT = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, c.getNit());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getTelefono());
            ps.setString(5, nit);
            ps.executeUpdate();
            System.out.println("Cliente actualizado");
            ps.close();
            ps=null;
        } catch (SQLException ex) {
            System.out.println("No se actualizo el registro");
            ex.printStackTrace();
        }
    }
    
}
