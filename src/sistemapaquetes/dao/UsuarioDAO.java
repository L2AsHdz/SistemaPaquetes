package sistemapaquetes.dao;

import sistemapaquetes.model.Usuario;

public interface UsuarioDAO extends CRUD<Usuario>{
    
    public void disableUser(String DPI);
}
