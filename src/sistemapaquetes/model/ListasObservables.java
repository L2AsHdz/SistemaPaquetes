package sistemapaquetes.model;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import sistemapaquetes.dao.CRUD;
import sistemapaquetes.dao.DestinoDAOImpl;
import sistemapaquetes.dao.RutaDAOImpl;
import sistemapaquetes.dao.UsuarioDAOImpl;

/**
 *
 * @author asael
 */
public class ListasObservables{
    
    private static ListasObservables list = null;
    private CRUD<Usuario> userDAO = UsuarioDAOImpl.getUserDAO();
    private CRUD<Destino> destinoDAO = DestinoDAOImpl.getDestinoDAO();
    private CRUD<Ruta> rutaDAO = RutaDAOImpl.getRutaDAO();
    
    //listados para Usuarios
    private List<Usuario> userList = new ArrayList();
    private ObservableList<Usuario> userObservableList;
    
    //listados para DPI de usuarios
    private List<String> dpiList = new ArrayList();
    private ObservableList<String> dpiObservableList;
    
    //listados para Destinos
    private List<Destino> destList = new ArrayList();
    private ObservableList<Destino> destObservableList;
    
    //listados para nombres de destinos
    private List<String> nameDestinosList = new ArrayList();
    private ObservableList<String> nameDestinosObservableList;
    
    //listados para Rutas
    private List<Ruta> rutaList = new ArrayList();
    private ObservableList<Ruta> rutaObservableList;
    
    //listados para nombres de rutas
    private List<String> nameRutasList = new ArrayList();
    private ObservableList<String> nameRutasObservableList;

    //Constructor privado para evitar instancias nuevas
    private ListasObservables() {
        userObservableList = ObservableCollections.observableList(userList);
        dpiObservableList = ObservableCollections.observableList(dpiList);
        destObservableList = ObservableCollections.observableList(destList);
        nameDestinosObservableList = ObservableCollections.observableList(nameDestinosList);
        rutaObservableList = ObservableCollections.observableList(rutaList);
        nameRutasObservableList = ObservableCollections.observableList(nameRutasList);
    }
    
    //Devuelve la unca instancia del Objeto
    public static ListasObservables getInstance(){
        if (list == null) {
            list = new ListasObservables();
        }
        return list;
    }

    
    ////////////Metodos de Lista Observable de Usuarios/////////////////////////
    public ObservableList<Usuario> getUserObservableList() {
        return userObservableList;
    }

    public void setUserObservableList(ObservableList<Usuario> userObservableList) {
        this.userObservableList = userObservableList;
    }
    
    private void reloadListUser(){
        userList = userDAO.getListado();
        this.userObservableList.clear();
        this.userObservableList.addAll(userList);
    }
    /////////////////////Fin metodos ListaUsuarios//////////////////////////////

    
    /////////////Metodos de Lista Observable de DPI/////////////////////////////
    public ObservableList<String> getDpiObservableList() {
        return dpiObservableList;
    }

    public void setDpiObservableList(ObservableList<String> dpiObservableList) {
        this.dpiObservableList = dpiObservableList;
    }
    
    private void reloadListDPI(){
        this.dpiObservableList.clear();
        userList = userDAO.getListado();
        for (Usuario u : userList) {
            if (u.getEstado() != 0) {
                dpiObservableList.add(u.getDPI());
            }
        }
        //this.dpiListObservable.addAll(dpiList);
    }
    /////////////////////////////Fin metodos Lista DPI//////////////////////////
    
    
    ///////////////Metodos de Lista Observable de Destinos//////////////////////
    public ObservableList<Destino> getDestObservableList() {
        return destObservableList;
    }

    public void setDestObservableList(ObservableList<Destino> destObservableList) {
        this.destObservableList = destObservableList;
    }
    
    private void reloadListDest(){
        destList = destinoDAO.getListado();
        this.destObservableList.clear();
        this.destObservableList.addAll(destList);
    }
    //////////////////////Fin metodos Lista Destinos////////////////////////////
    
    
    /////////////Metodos de lista de nombre de destinos//////////////////////////

    public ObservableList<String> getNameDestinosObservableList() {
        return nameDestinosObservableList;
    }

    public void setNameDestinosObservableList(ObservableList<String> nameDestinosObservableList) {
        this.nameDestinosObservableList = nameDestinosObservableList;
    }
    
    private void reloadListNameDestinos(){
        nameDestinosObservableList.clear();
        destList = destinoDAO.getListado();
        for (Destino d : destList) {
            nameDestinosObservableList.add(d.getNombre());
        }
    }
    ////////////////////Fin metodos de lista nombres Destinos///////////////////
    
    
    //////////////Metodos de lista Observable de Rutas//////////////////////////

    public ObservableList<Ruta> getRutaObservableList() {
        return rutaObservableList;
    }

    public void setRutaObservableList(ObservableList<Ruta> rutaObservableList) {
        this.rutaObservableList = rutaObservableList;
    }
    
    private void reloadListRutas(){
        rutaList = rutaDAO.getListado();
        rutaObservableList.clear();
        rutaObservableList.addAll(rutaList);
    }
    /////////////////////Fin metodos de lista de rutas//////////////////////////
    
    
    ///////////////Metodos de lista para nombres de rutas///////////////////////
    public ObservableList<String> getNameRutasObservableList() {
        return nameRutasObservableList;
    }

    public void setNameRutasObservableList(ObservableList<String> nameRutasObservableList) {
        this.nameRutasObservableList = nameRutasObservableList;
    }
    
    private void reloadListNameRutas(){
        nameRutasObservableList.clear();
        rutaList = rutaDAO.getListado();
        for (Ruta r : rutaList) {
            if (r.getEstado() != 0) {
                nameRutasObservableList.add(r.getNombre());
            }
        }
    }
    ////////////////Fin de metodos de lista nombre Rutas////////////////////////
    
    public void reloadListadosU(){
        reloadListDPI();
        reloadListUser();
    }
    
    public void reloadListadosD(){
        reloadListDest();
        reloadListNameDestinos();
    }
    
    public void reloadListadosR(){
        reloadListNameRutas();
        reloadListRutas();
    }
}
