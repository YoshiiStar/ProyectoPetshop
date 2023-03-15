package Modelo;

import Vista.ListaProducto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ListaProductoModel {

    //Variables con setter y getter
    private ListaProducto vistaListaProducto;
    private String id_producto;
    private String nombreProducto;
    private String precioProducto;
    private String stockProducto;

    private final DefaultTableModel tablaModelo;
    private final Conexion conex;//evita que el parámetro se reescriba

    public ListaProductoModel() {
        //Objeto tabla modelo
        this.tablaModelo = new DefaultTableModel();
        //Crear Objeto Conexion
        this.conex = new Conexion();
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(String precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getStockProducto() {
        return stockProducto;
    }

    public void setStockProducto(String stockProducto) {
        this.stockProducto = stockProducto;
    }
    
    

    public void tituloTabla(ListaProducto vistaListaProducto) {
        //Recibimos el parametro vistaPanel
        this.vistaListaProducto = vistaListaProducto;
        //Inicializar la tabla Usuario
        String titulo[] = new String[]{"Id", "Nombre", "Precio", "Stock"};
        this.tablaModelo.setColumnIdentifiers(titulo);//Añadiendo titulos a la tabla
        this.vistaListaProducto.getTablaListaProducto.setModel(this.tablaModelo);//mostrar los titulos del registro de usuario al inicio
    }

    public void registroTabla(ListaProducto vistaListaProducto) {
        //Recibimos el parametro vistaPanel
        this.vistaListaProducto = vistaListaProducto;
        if (this.conex != null) {
            try {
                // Consultamos la tabla cliente
                String consulta = "SELECT id_producto, nombre, precio, stock FROM producto";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);

                // Ejecutar la consulta 
                ResultSet resultado = p.executeQuery();

                // Limpia los registros de la tabla cliente no repetidos
                this.tablaModelo.setRowCount(0);

                // Muestra cada registro de la tabla
                while (resultado.next()) {
                    this.tablaModelo.addRow(new Object[]{
                        resultado.getString("id_producto"),
                        resultado.getString("nombre"),// nombre
                        resultado.getString("precio"),// precio
                        resultado.getString("stock"),// stock
                    });
                }
                this.vistaListaProducto.getTablaListaProducto.setModel(this.tablaModelo);//mostrar los titulos del registro de clientes al inicio
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta registroTabla Categoria: " + ev.getMessage());
            }
        }
    }
}
