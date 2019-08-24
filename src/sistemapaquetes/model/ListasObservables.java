package sistemapaquetes.model;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import sistemapaquetes.dao.CRUD;
import sistemapaquetes.dao.DestinoDAOImpl;
import sistemapaquetes.dao.UsuarioDAOImpl;

/**
 *
 * @author asael
 */
public class ListasObservables{
    
    private static ListasObservables list = null;
    private CRUD<Usuario> userDAO = UsuarioDAOImpl.getUserDAO();
    private CRUD<Destino> destinoDAO = DestinoDAOImpl.getDestinoDAO();
    
    //listados para Usuarios
    private List<Usuario> userList = new ArrayList();
    private ObservableList<Usuario> userListObservable;
    
    //listados para DPI de usuarios
    private List<String> dpiList = new ArrayList();
    private ObservableList<String> dpiListObservable;
    
    //listados para Destinos
    private List<Destino> destList = new ArrayList();
    private ObservableList<Destino> destListObservable;

    //Constructor privado para evitar instancias nuevas
    private ListasObservables() {
        userListObservable = ObservableCollections.observableList(userList);
        dpiListObservable = ObservableCollections.observableList(dpiList);
        destListObservable = ObservableCollections.observableList(destList);
    }
    
    //Devuelve la unca instancia del Objeto
    public static ListasObservables getInstance(){
        if (list == null) {
            list = new ListasObservables();
        }
        return list;
    }

    
    ////////////Metodos de Lista Observable de Usuarios/////////////////////////
    public ObservableList<Usuario> getUserListObservable() {
        return userListObservable;
    }

    public void setUserListObservable(ObservableList<Usuario> userListObservable) {
        this.userListObservable = userListObservable;
    }
    
    private void reloadListUser(){
        userList = userDAO.getListado();
        this.userListObservable.clear();
        this.userListObservable.addAll(userList);
    }
    /////////////////////Fin metodos ListaUsuarios//////////////////////////////

    
    /////////////Metodos de Lista Observable de DPI/////////////////////////////
    public ObservableList<String> getDpiListObservable() {
        return dpiListObservable;
    }

    public void setDpiListObservable(ObservableList<String> dpiListObservable) {
        this.dpiListObservable = dpiListObservable;
    }
    
    private void reloadListDPI(){
        this.dpiListObservable.clear();
        userList = userDAO.getListado();
        for (Usuario u : userList) {
            if (u.getEstado() != 0) {
                dpiListObservable.add(u.getDPI());
            }
        }
        //this.dpiListObservable.addAll(dpiList);
    }
    /////////////////////////////Fin metodos Lista DPI//////////////////////////
    public ObservableList<Destino> getDestListObservable() {
        return destListObservable;
    }

    public void setDestListObservable(ObservableList<Destino> destListObservable) {
        this.destListObservable = destListObservable;
    }
    
    public void reloadListDest(){
        destList = destinoDAO.getListado();
        this.destListObservable.clear();
        this.destListObservable.addAll(destList);
    }
    
    ///////////////Metodos de Lista Observable de Destinos//////////////////////
    //////////////////////Fin metodos Lista Destinos////////////////////////////
    
    public void reloadListadosU(){
        reloadListDPI();
        reloadListUser();
    }
}
