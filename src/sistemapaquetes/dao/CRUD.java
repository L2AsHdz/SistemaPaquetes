package sistemapaquetes.dao;

import java.util.List;

/**
 *
 * @author asael
 * @param <T>
 */
public interface CRUD<T> {
    
    public List<T> getListado();
    public void create(T t);
    public T getPorId(int id);
    public void update(T t);
    public void delete(int id);
}
