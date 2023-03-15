package Modelo;

import Vista.Panel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DetalleOrdenModel {

    //Variables con setters y getters (id_orden, id_producto, cantidad, precio_unitario, subtotal)
    private String id_orden;
    private String id_producto;
    private String cantidad;
    private double precio_unitario;
    private double subtotal;
    private String total;

    private Document documento;
    private PdfPTable tablaProducto;
    
    private PdfPTable tablaDetalleOrden;

    //Variables con setters y getters 
    private String dni_cliente;

    //Variables sin setter y getter
    private final DefaultTableModel tablaModelo;//Tabla
    private Panel vistaPanel;
    private final Conexion conex;//evita que el parámetro se reescriba

    public DetalleOrdenModel() {
        //Objeto tabla modelo
        this.tablaModelo = new DefaultTableModel();
        //Crear Objeto Conexion
        this.conex = new Conexion();
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public String getId_orden() {
        return id_orden;
    }

    public void setId_orden(String id_orden) {
        this.id_orden = id_orden;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void tituloTabla(Panel vistaPanel) {
        //Recibimos el parametro vistaPanel
        this.vistaPanel = vistaPanel;
        //Inicializar la tabla DetalleOrden ()
        String titulo[] = new String[]{"ID ORDEN", "FECHA", "PRODUCTO", "CANTIDAD", "PRECIO"};
        this.tablaModelo.setColumnIdentifiers(titulo);//Añadiendo titulos a la tabla

        this.vistaPanel.getTablaDetalleOrden.setModel(this.tablaModelo);//mostrar los titulos del registro de usuario al inicio
    }

    public void registroTabla(Panel vistaPanel) {
        //Recibimos el parametro vistaPanel
        this.vistaPanel = vistaPanel;
        if (this.conex != null) {
            try {
                // Consultamos la tabla cliente  detalle_orden(id_orden) orden(fecha) producto(nombre) detalle_orden(cantidad,subtotal)
                String consulta = "SELECT d.id_orden, o.fecha, p.nombre, d.cantidad, d.precio_unitario FROM detalle_orden AS d INNER JOIN orden AS o ON d.id_orden = o.id_orden INNER JOIN producto AS p ON p.id_producto = d.id_producto INNER JOIN cliente AS c ON o.dni_cliente = c.dni WHERE c.dni = ?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getDni_cliente());
                // Ejecutar la consulta 
                ResultSet resultado = p.executeQuery();

                // Limpia los registros de la tabla cliente no repetidos
                //this.tablaModelo.setRowCount(0);
                // Muestra cada registro de la tabla
                while (resultado.next()) {
                    this.tablaModelo.addRow(new Object[]{
                        resultado.getString("id_orden"),//id_orden
                        resultado.getString("fecha"),//fecha
                        resultado.getString("nombre"),// nombre
                        resultado.getString("cantidad"),// cantidad
                        resultado.getString("precio_unitario")// precio_unitario
                    });
                }
                this.vistaPanel.getTablaDetalleOrden.setModel(this.tablaModelo);//mostrar los titulos del registro de clientes al inicio

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta dentro de registroTabla de Detalle Orden: " + ev.getMessage());
            }
        }

    }

    //Rellenar registro de la tabla id_orden, dni_cliente, dni_usuario, fecha, total
    public void guardarDetalleOrden() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor (id_orden, id_producto, cantidad, precio_unitario, subtotal)
                String consulta = "INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getId_orden());   //id_orden
                p.setString(2, this.getId_producto());  //id_producto
                p.setString(3, this.getCantidad());   //cantidad
                p.setDouble(4, this.getPrecio_unitario());   //precio_unitario
                p.setDouble(5, this.getSubtotal());   //subtotal

                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    //JOptionPane.showMessageDialog(null, "Se ha agregado un nuevo producto!!");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar los registros en la tabla orden!!");
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la funcion guardarDetalleOrden: " + ev.getMessage());
            }
        }
    }

    public void ImprimirProductos(Document documento, PdfPTable tablaProducto) {
        if (this.conex != null) {
            this.tablaProducto = tablaProducto;
            try {
                this.documento = documento;
                // Consultanto la existencia el UsuarioModel del vendedor (id_orden,id_producto,cantidad,precio_unitario,subtotal) |Cantidad| Descripcion |  Precio Unitario | Subtotal|
                String consulta = "SELECT d.cantidad, p.nombre , d.precio_unitario, d.subtotal FROM detalle_orden AS d INNER JOIN producto AS p ON d.id_producto= p.id_producto WHERE d.id_orden=?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getId_orden());

                // Ejecutar la consulta SELECT
                ResultSet resultado = p.executeQuery();
                try {
                    while (resultado.next()) {
                        this.tablaProducto.addCell(resultado.getString("cantidad"));
                        this.tablaProducto.addCell(resultado.getString("nombre"));
                        this.tablaProducto.addCell(resultado.getString("precio_unitario"));
                        this.tablaProducto.addCell(resultado.getString("subtotal"));
                    }
                    this.documento.add(this.tablaProducto);
                } catch (DocumentException e) {
                    JOptionPane.showMessageDialog(null, "Error:" + e.getMessage());
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la funcion ImprimirProductos del Cliente: " + ev.getMessage());
            }
        }
    }

    public void SumarSubtotalOrden() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor 
                String consulta = "SELECT SUM(subtotal) AS total FROM detalle_orden WHERE id_orden=?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getId_orden());

                // Ejecutar la consulta SELECT
                ResultSet resultado = p.executeQuery();

                if (resultado.next()) {

                    //Agregar datos del cliente
                    this.setTotal(resultado.getString("total")); // total

                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo realizar la consulta");
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la funcion ImprimirProductos del Cliente: " + ev.getMessage());
            }
        }
    }

    public void ImprimirDetalleOrden(Document documento, PdfPTable tablaDetalleOrden ) {
        if (this.conex != null) {
            this.tablaDetalleOrden = tablaDetalleOrden;
            try {
                this.documento = documento;
                //"ID ORDEN", "FECHA", "PRODUCTO", "CANTIDAD", "PRECIO"  tabla orden(id_orden, fecha) producto(nombre) detalle_orden(cantidad, precio_unitario)
                String consulta = "SELECT o.id_orden, o.fecha , p.nombre, d.cantidad, d.precio_unitario FROM detalle_orden AS d INNER JOIN producto AS p ON d.id_producto = p.id_producto INNER JOIN orden AS o ON d.id_orden= o.id_orden WHERE o.dni_cliente=?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getDni_cliente());

                // Ejecutar la consulta SELECT
                ResultSet resultado = p.executeQuery();
                try {
                    while (resultado.next()) {
                        this.tablaDetalleOrden.addCell(resultado.getString("id_orden"));
                        this.tablaDetalleOrden.addCell(resultado.getString("fecha"));
                        this.tablaDetalleOrden.addCell(resultado.getString("nombre"));
                        this.tablaDetalleOrden.addCell(resultado.getString("cantidad"));
                        this.tablaDetalleOrden.addCell(resultado.getString("precio_unitario"));
                    }
                    this.documento.add(this.tablaDetalleOrden);
                } catch (DocumentException e) {
                    JOptionPane.showMessageDialog(null, "Error:" + e.getMessage());
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la funcion ImprimirDetalleOrden de DetalleOrdenModel: " + ev.getMessage());
            }
        }
    }

}
