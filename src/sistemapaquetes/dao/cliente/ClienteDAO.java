package sistemapaquetes.dao.cliente;

import sistemapaquetes.dao.CRUD;
import sistemapaquetes.model.Cliente;

/**
 *
 * @author asael
 */
public interface ClienteDAO extends CRUD<Cliente>{
    public void update(Cliente c, String nit);
}
