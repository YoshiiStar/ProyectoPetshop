package Modelo;
import Vista.Panel;

public interface Funciones {
    public void tituloTabla(Panel vistaPanel);//Titulos de la tablas
    public void registroTabla(Panel vistaPanel);//Mostrar la tabla de cada tabla
    public void verificar();//Mostrar la tabla de cada tabla
    public void nuevo(Panel vistaPanel);//Borrar registro anterior para colocar uno nuevo
    public void agregar();//Agregar un nuevo registro
    public void modificar();//Modificar datos de cada registro 
    public void eliminar();//Eliminar registro
}
