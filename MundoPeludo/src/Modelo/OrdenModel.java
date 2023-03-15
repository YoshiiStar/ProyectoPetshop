package Modelo;

import Vista.Panel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class OrdenModel {

    //Variables para guardar dentro de la tabla Orden id_orden, dni_cliente, dni_usuario, total
    private String dni_cliente;
    private String dni_usuario;
    private String total;

    //Variables con Setter y Getters "ID", "DESCRIPCION", "CANTIDAD", "PRECIO UNIT.", "SUB. TOTAL"
    private String id_orden;
    private String id_producto;
    private String descripcion;
    private String cantidad;
    private String precio_unitario;
    private String sub_total;
    
    private int Stock;

    private double SumaSubTotal = 0.0;

    ArrayList<String[]> registroProducto;

    //Variables sin setter y getter
    private final DefaultTableModel tablaModelo;//Tabla
    private Panel vistaPanel;
    private final Conexion conex;//evita que el parámetro se reescriba

    public OrdenModel() {
        //Objeto tabla modelo
        this.tablaModelo = new DefaultTableModel();
        //Crear Objeto Conexion
        this.conex = new Conexion();
        //Almacenar los registro de productos añadidos
        this.registroProducto = new ArrayList<>();
    }

    public String getId_orden() {
        return id_orden;
    }

    public void setId_orden(String id_orden) {
        this.id_orden = id_orden;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(String precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public String getDni_usuario() {
        return dni_usuario;
    }

    public void setDni_usuario(String dni_usuario) {
        this.dni_usuario = dni_usuario;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public ArrayList<String[]> getRegistroProducto() {
        return registroProducto;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }
    

    //Funcion para generar ID
    public void generarID() {
        if (this.conex != null) {
            try {
                // Consultamos la tabla cliente
                String consulta = "SELECT MAX(id_orden) AS ultimoID FROM orden";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);

                // Ejecutar la consulta 
                ResultSet resultado = p.executeQuery();

                if (resultado.next()) {
                    String ultimoId = resultado.getString("ultimoID");
                    int numero = 0;//si es null numero es 0
                    if (ultimoId != null) {
                        // Extraer la parte numérica quitando la 'P'
                        String numeroStr = ultimoId.substring(3);
                        numero = Integer.parseInt(numeroStr);
                    }
                    numero += 1; // Sumamos Uno
                    // Formatear el nuevo ID
                    String nuevoId = String.format("ORD%05d", numero);
                    this.setId_orden(nuevoId); // Asignar el nuevo ID
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error al generar ID: " + ev.getMessage());
            }
        }
    }

    //PRIMERO
    public void armandoRegistro() {
        String[] nuevoProducto = {
            this.getId_producto(),
            this.getDescripcion(),
            this.getCantidad(),
            this.getPrecio_unitario(),
            this.getSub_total()
        };
        this.registroProducto.add(nuevoProducto);
    }

    public void eliminarRegistros() {
        this.registroProducto.clear(); // Elimina todos los elementos
    }

    public void tituloTabla(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;

        // Configurar modelo de tabla
        String[] titulos = {"ID", "DESCRIPCION", "CANTIDAD", "PRECIO UNIT.", "SUB. TOTAL"};
        this.tablaModelo.setColumnIdentifiers(titulos);

        // Actualizar vista
        actualizarVistaTabla();
    }

    public void actualizarRegistro() {
        // Limpiar tabla antes de agregar datos
        this.tablaModelo.setRowCount(0);

        // Agregar todos los registros
        for (String[] fila : this.registroProducto) {
            this.tablaModelo.addRow(fila);//Imprimir todo el registro de productos
        }

        actualizarVistaTabla();
    }

    public void Total(double subtotal) {
        this.SumaSubTotal += subtotal;
        String totalFormateado = String.format("%.2f", this.SumaSubTotal);
        this.setTotal(totalFormateado);
    }

    private void actualizarVistaTabla() {
        if (this.vistaPanel != null) {
            this.vistaPanel.getTablaOrden.setModel(this.tablaModelo);
        }
    }

    //Rellenar registro de la tabla id_orden, dni_cliente, dni_usuario, fecha, total
    public void guardarOrden() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "INSERT INTO orden (id_orden, dni_cliente, dni_usuario) VALUES (?, ?, ?)";
                                
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getId_orden());   //id_orden
                p.setString(2, this.getDni_cliente());  //dni_cliente
                p.setString(3, this.getDni_usuario());   //dni_usuario

                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    //JOptionPane.showMessageDialog(null, "Se ha agregado un nuevo producto!!");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar los registros en la tabla orden!!");
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la funcion guardarOrden: " + ev.getMessage());
            }
        }

    }
     /*
        executeQuery(): Se usa en sentencias SELECT
        executeUpdate(): Se usa en sentencias INSERT, UPDATE, DELETE
    */
    public void AñadirTotal(){
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "UPDATE orden SET total = ? WHERE id_orden = ?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getTotal());   //total
                p.setString(2, this.getId_orden());  //id_orden

                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    //JOptionPane.showMessageDialog(null, "Se ha agregado un nuevo producto!!");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo añadir el total al id_orden:"+this.getId_orden());
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la funcion AñadirTotal: " + ev.getMessage());
            }
        }
    }
    
      
    

}
