package sistemapaquetes.model;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import sistemapaquetes.dao.CRUD;
import sistemapaquetes.dao.cliente.ClienteDAOImpl;
import sistemapaquetes.dao.colapc.ColapcDAO;
import sistemapaquetes.dao.colapc.ColapcDAOImpl;
import sistemapaquetes.dao.destino.DestinoDAOImpl;
import sistemapaquetes.dao.paquete.PaqueteDAOImpl;
import sistemapaquetes.dao.precio.PrecioDAOImpl;
import sistemapaquetes.dao.puntocontrol.PuntoControlDAOImpl;
import sistemapaquetes.dao.ruta.RutaDAOImpl;
import sistemapaquetes.dao.usuario.UsuarioDAOImpl;

/**
 *
 * @author asael
 */
public class ListasObservables{
    
    private static ListasObservables list = null;
    private CRUD<Usuario> userDAO = UsuarioDAOImpl.getUserDAO();
    private CRUD<Destino> destinoDAO = DestinoDAOImpl.getDestinoDAO();
    private CRUD<Ruta> rutaDAO = RutaDAOImpl.getRutaDAO();
    private CRUD<PrecioGlobal> precioDAO = PrecioDAOImpl.getPrecioDAO();
    private CRUD<PuntoControl> puntoCDAO = PuntoControlDAOImpl.getPuntoCDAO();
    private CRUD<Cliente> clienteDAO = ClienteDAOImpl.getClienteDAOImpl();
    private ColapcDAO colaPCDAO = ColapcDAOImpl.getColaDAOImpl();
    private CRUD<Paquete> paqueteDAO = PaqueteDAOImpl.getPaqueteDAOImpl();
    
    //listados para Usuarios
    private List<Usuario> userList = new ArrayList();
    private ObservableList<Usuario> userObservableList;
    
    //listados para DPI de usuarios
    private List<String> dpiList = new ArrayList();
    private ObservableList<String> dpiObservableList;
    private List<String> dpiOList = new ArrayList();
    private ObservableList<String> dpiOObservableList;
    
    //listados para Destinos
    private List<Destino> destList = new ArrayList();
    private ObservableList<Destino> destObservableList;
    
    //listados para nombres de destinos
    private List<String> nameDestinosList = new ArrayList();
    private ObservableList<String> nameDestinosObservableList;
    
    //listados para Rutas
    private List<Ruta> rutasList = new ArrayList();
    private ObservableList<Ruta> rutaObservableList;
    
    //listados para nombres de rutas
    private List<String> nameRutasList = new ArrayList();
    private ObservableList<String> nameRutasObservableList;
    
    //listados para Precios Globales
    private List<PrecioGlobal> preciosList = new ArrayList();
    private ObservableList<PrecioGlobal> preciosObservableList;
    
    //listados para puntos de control
    private List<PuntoControl> puntosCList = new ArrayList();
    private ObservableList<PuntoControl> puntosCObservableList;
    
    //listados para nombres puntos de control
    private List<String> namePCList = new ArrayList();
    private ObservableList<String> namePCObservableList;
    
    //Listados para clientes
    private List<Cliente> clientesList = new ArrayList();
    private ObservableList<Cliente> clientesObservableList;
    
    //listados para paquetes
    private List<Paquete> paqueteList = new ArrayList();
    private ObservableList<Paquete> paquetesObservableLIst;
    
    //listado para paquetes en colaPC
    private List<PaqueteInCola> colaPCList = new ArrayList();
    private ObservableList<PaqueteInCola> colaPCObservableList;
    
    //listaods para paquetes en destino
    private List<Paquete> paqueteDList = new ArrayList();
    private ObservableList<Paquete> paquetesDObservableList;

    //Constructor privado para evitar instancias nuevas
    private ListasObservables() {
        userObservableList = ObservableCollections.observableList(userList);
        dpiObservableList = ObservableCollections.observableList(dpiList);
        dpiOObservableList = ObservableCollections.observableList(dpiOList);
        destObservableList = ObservableCollections.observableList(destList);
        nameDestinosObservableList = ObservableCollections.observableList(nameDestinosList);
        rutaObservableList = ObservableCollections.observableList(rutasList);
        nameRutasObservableList = ObservableCollections.observableList(nameRutasList);
        preciosObservableList = ObservableCollections.observableList(preciosList);
        puntosCObservableList = ObservableCollections.observableList(puntosCList);
        clientesObservableList = ObservableCollections.observableList(clientesList);
        paquetesObservableLIst = ObservableCollections.observableList(paqueteList);
        namePCObservableList = ObservableCollections.observableList(namePCList);
        colaPCObservableList = ObservableCollections.observableList(colaPCList);
        paquetesDObservableList = ObservableCollections.observableList(paqueteDList);
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
        List<Usuario> userList = userDAO.getListado();
        for (Usuario u : userList) {
            if (u.getEstado() == 1) {
                dpiObservableList.add(u.getDPI());
            }
        }
        //this.dpiListObservable.addAll(dpiList);
    }

    public ObservableList<String> getDpiOObservableList() {
        return dpiOObservableList;
    }

    public void setDpiOObservableList(ObservableList<String> dpiOObservableList) {
        this.dpiOObservableList = dpiOObservableList;
    }
    
    private void reloadListDPIO(){
        this.dpiOObservableList.clear();
        List<Usuario> userList = userDAO.getListado();
        for (Usuario u : userList) {
            if (u.getEstado() == 1 && u.getTipo() == 2) {
                dpiOObservableList.add(u.getDPI());
            }
        }
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
        rutasList = rutaDAO.getListado();
        rutaObservableList.clear();
        rutaObservableList.addAll(rutasList);
    }
    /////////////////////Fin metodos de lista de rutas//////////////////////////
    
    
    ///////////////Metodos de lista para nombres de rutas///////////////////////
    public ObservableList<String> getNameRutasObservableList() {
        return nameRutasObservableList;
    }

    public void setNameRutasObservableList(ObservableList<String> nameRutasObservableList) {
        this.nameRutasObservableList = nameRutasObservableList;
    }
    
    public void reloadListNameRutas(){
        nameRutasObservableList.clear();
        rutasList = rutaDAO.getListado();
        for (Ruta r : rutasList) {
            if (r.getEstado() != 0) {
                nameRutasObservableList.add(r.getNombre());
            }
        }
    }
    ////////////////Fin de metodos de lista nombre Rutas////////////////////////
    
    ///////////////////////Metodos para listado precios Globales////////////////

    public ObservableList<PrecioGlobal> getPreciosObservableList() {
        return preciosObservableList;
    }

    public void setPreciosObservableList(ObservableList<PrecioGlobal> preciosObservableList) {
        this.preciosObservableList = preciosObservableList;
    }
    
    public void reloadPrecios(){
        preciosList = precioDAO.getListado();
        preciosObservableList.clear();
        preciosObservableList.addAll(preciosList);
    }
    
    ////////////////////////Fin metodos listado precios globales////////////////
    
    ////////////////Metodos para listado Punto Control//////////////////////////

    public ObservableList<PuntoControl> getPuntosCObservableList() {
        return puntosCObservableList;
    }

    public void setPuntosCObservableList(ObservableList<PuntoControl> puntosCObservableList) {
        this.puntosCObservableList = puntosCObservableList;
    }
    
    public void reloadPuntosControl(){
        puntosCList = puntoCDAO.getListado();
        puntosCObservableList.clear();
        puntosCObservableList.addAll(puntosCList);
    }
    
    //////////////////Fin metodos listados puntos de control////////////////////
    
    ////////////////Metodos para listados de nombres de PuntosC/////////////////

    public ObservableList<String> getNamePCObservableList() {
        return namePCObservableList;
    }

    public void setNamePCObservableList(ObservableList<String> namePCObservableList) {
        this.namePCObservableList = namePCObservableList;
    }
    
    public void reloadListNamePC(String dpi, int idRuta){
        namePCObservableList.clear();
        puntosCList = puntoCDAO.getListado();
        for (PuntoControl pc : puntosCList) {
            if (pc.getDPIOperador().equals(dpi) && pc.getIdRuta() == idRuta) {
                namePCObservableList.add(String.valueOf(pc.getNumero()));
            }
        }
    }
    
    /////////////////fin metodos listados des nombres de PuntoC/////////////////
    
    //////////////////Metodos para listado observable de clientes///////////////

    public ObservableList<Cliente> getClientesObservableList() {
        return clientesObservableList;
    }

    public void setClientesObservableList(ObservableList<Cliente> clientesObservableList) {
        this.clientesObservableList = clientesObservableList;
    }
    
    public void reloadLIstClientes(){
        clientesList = clienteDAO.getListado();
        clientesObservableList.clear();
        clientesObservableList.addAll(clientesList);
    }
    
    //////////////////Fin de metodos de listado de clientes/////////////////////
    
    ///////////////////metodos para listado de paquetes/////////////////////////

    public ObservableList<Paquete> getPaquetesObservableLIst() {
        return paquetesObservableLIst;
    }

    public void setPaquetesObservableLIst(ObservableList<Paquete> paquetesObservableLIst) {
        this.paquetesObservableLIst = paquetesObservableLIst;
    }
    
    public void reloadPaquetes1(List<Paquete> paquetes){
        paqueteList = paquetes;
        paquetesObservableLIst.clear();
        paquetesObservableLIst.addAll(paqueteList);
    }
    //////////////////fin de metodos para listado de paquetes///////////////////
    
    /////////////////metodos para listado de paquetes en colaPC/////////////////

    public ObservableList<PaqueteInCola> getColaPCObservableList() {
        return colaPCObservableList;
    }

    public void setColaPCObservableList(ObservableList<PaqueteInCola> colaPCObservableList) {
        this.colaPCObservableList = colaPCObservableList;
    }
    
    public void reloadistColaPC(int noPC, int idRuta){
        colaPCList = colaPCDAO.getListado(noPC, idRuta);
        colaPCObservableList.clear();
        colaPCObservableList.addAll(colaPCList);
    }
    
    ////////////////fin metodos de listado de paquetes en colaPC////////////////
    
    /////////////////////metodos de listado paquetes en destino/////////////////

    public ObservableList<Paquete> getPaquetesDObservableList() {
        return paquetesDObservableList;
    }

    public void setPaquetesDObservableList(ObservableList<Paquete> paquetesDObservableList) {
        this.paquetesDObservableList = paquetesDObservableList;
    }
    
    public void reloadListPaquetesD(){
        paquetesDObservableList.clear();
        paqueteList = paqueteDAO.getListado();
        for (Paquete p : paqueteList) {
            if (p.getEstadoRetiro() == 2) {
                paquetesDObservableList.add(p);
            }
        }
    }
    
    public void reloadListadosU(){
        reloadListUser();
        reloadListDPIO();
        reloadListDPI();
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
