package sistemapaquetes.dao.usuario;

import sistemapaquetes.dao.CRUD;
import sistemapaquetes.model.Usuario;

public interface UsuarioDAO extends CRUD<Usuario>{
    
    public void disableUser(String DPI);
    public void update(Usuario u, String DPI);
}
