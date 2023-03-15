package Modelo;

import Vista.Panel;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProductoModel implements Funciones {

    //Variables sin setter y getter
    private String id_producto;//ID PRODUCTO
    private String nombre;// NOMBRE
    private double precio;// PRECIO
    private int stock;// STOCK
    private String id_categoria;
    private Boolean verify;

    //Variables sin setter y getter
    private final DefaultTableModel tablaModelo;//Tabla
    private Panel vistaPanel;
    private final Conexion conex;//evita que el parámetro se reescriba
    private final LinkedHashMap<String, String> listaCategorias;

    private final DefaultComboBoxModel<String> comboBoxModel;//ComboBox

    public ProductoModel() {
        //Objeto tabla modelo
        this.tablaModelo = new DefaultTableModel();
        //Crear Objeto Conexion
        this.conex = new Conexion();

        //Objeto de Combo Box
        this.comboBoxModel = new DefaultComboBoxModel<>();

        //Objeto lista Categorias
        this.listaCategorias = new LinkedHashMap<String, String>();
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public Boolean getVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }

    public void verificarCampos(Panel vistaPanel) {
        if (!this.vistaPanel.getProductoNombre.getText().equals("NOMBRE")) {
            if (!this.vistaPanel.getProductoPrecio.getText().equals("PRECIO")) {
                if (!this.vistaPanel.getProductoStock.getValue().equals(0)) {
                    if (!this.vistaPanel.getProductoListaCategoria.getSelectedItem().equals("SELECCIONAR")) {
                        this.setVerify(true);
                    } else {
                        this.setVerify(false);
                        JOptionPane.showMessageDialog(null, "Ingrese todos los campos por favor!!");
                    }
                } else {
                    this.setVerify(false);
                    JOptionPane.showMessageDialog(null, "Ingrese todos los campos por favor!!");
                }
            } else {
                this.setVerify(false);
                JOptionPane.showMessageDialog(null, "Ingrese todos los campos por favor!!");
            }
        } else {
            this.setVerify(false);
            JOptionPane.showMessageDialog(null, "Ingrese todos los campos por favor!!");
        }
    }

    //Funcion para generar ID
    public void generarID() {
        if (this.conex != null) {
            try {
                // Consultamos la tabla cliente
                String consulta = "SELECT MAX(id_producto) AS ultimoId FROM producto";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);

                // Ejecutar la consulta 
                ResultSet resultado = p.executeQuery();

                if (resultado.next()) {
                    String ultimoId = resultado.getString("ultimoId");
                    int numero = 0;
                    if (ultimoId != null) {
                        // Extraer la parte numérica quitando la 'PRD'
                        String numeroStr = ultimoId.substring(3);
                        numero = Integer.parseInt(numeroStr);
                    }

                    numero += 1; // Sumamos Uno

                    // Formatear el nuevo ID
                    String nuevoId = String.format("PRD%05d", numero);
                    this.setId_producto(nuevoId); // Asignar el nuevo ID
                } else {
                    this.setVerify(false);//No se encontro al usuario
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error al generar ID: " + ev.getMessage());
            }
        }
    }

    //Buscar la seleccion de categoria
    public void SeleccionarCategoria(Panel vistaPanel) {
        //Registrar proveedor elegido 
        for (Map.Entry<String, String> e : this.listaCategorias.entrySet()) {
            //Se encuentra el elemento seleccionado (valor encontrado)
            if (this.vistaPanel.getProductoListaCategoria.getSelectedItem().equals(e.getValue())) {
                //Selecciona la clave para almacenarlo el idProveedor
                String IDCategoria = e.getKey();
                //Se almacena del idProve en el Modelo Provedor
                this.setId_categoria(IDCategoria);
                break;
            }
        }
    }

    //Listar Categorias 
    public void listarCategorias(Panel vistaPanel) {
        //Recibimos el parametro vistaPanel
        this.vistaPanel = vistaPanel;
        if (this.conex != null) {
            try {
                // Mostrar los datos de la tabla cliente
                String consulta = "SELECT * FROM categoria";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);

                // Ejecutar la consulta 
                ResultSet resultado = p.executeQuery();

                // Limpiar estructuras antes de reutilizar
                this.listaCategorias.clear();
                this.comboBoxModel.removeAllElements();

                // Agregar primero la opcion "SELECCIONAR" en el inicio del combobox
                this.comboBoxModel.addElement("SELECCIONAR");

                // Añadir los resultados a listaCategorias (HashMap codigo-valor)
                while (resultado.next()) {
                    this.listaCategorias.put(resultado.getString("id_categoria"), resultado.getString("nombre_categoria"));
                }

                // Luego agregar el nombre de las categorias al combobox
                for (String nombre : this.listaCategorias.values()) {
                    this.comboBoxModel.addElement(nombre);
                }

                //Añadir mostrar los elementos en el combobox
                this.vistaPanel.getProductoListaCategoria.setModel(this.comboBoxModel);

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en el metodo listar Categoria: " + ev.getMessage());
            }
        }
    }

    @Override
    public void tituloTabla(Panel vistaPanel) {
        //Recibimos el parametro vistaPanel
        this.vistaPanel = vistaPanel;
        //Inicializar la tabla Usuario
        String titulo[] = new String[]{"ID", "Nombre", "Precio", "Stock", "Categoria"};
        this.tablaModelo.setColumnIdentifiers(titulo);//Añadiendo titulos a la tabla

        this.vistaPanel.getTablaProducto.setModel(this.tablaModelo);//mostrar los titulos del registro de usuario al inicio
    }

    @Override
    public void registroTabla(Panel vistaPanel) {
        //Recibimos el parametro vistaPanel
        this.vistaPanel = vistaPanel;
        if (this.conex != null) {
            try {
                // Consultamos la tabla cliente
                String consulta = "SELECT p.id_producto, p.nombre, p.precio, p.stock, c.nombre_categoria FROM producto AS p INNER JOIN categoria AS c ON p.id_categoria = c.id_categoria";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);

                // Ejecutar la consulta 
                ResultSet resultado = p.executeQuery();

                // Limpia los registros de la tabla cliente no repetidos
                this.tablaModelo.setRowCount(0);

                // Muestra cada registro de la tabla
                while (resultado.next()) {
                    this.tablaModelo.addRow(new Object[]{
                        resultado.getString("id_producto"),//id precio
                        resultado.getString("nombre"),// nombre
                        resultado.getString("precio"),// precio
                        resultado.getString("stock"),// stock
                        resultado.getString("nombre_categoria")// id_categoria
                    });
                }
                this.vistaPanel.getTablaProducto.setModel(this.tablaModelo);//mostrar los titulos del registro de clientes al inicio
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta registroTabla Producto: " + ev.getMessage());
            }
        }

    }

    @Override
    public void verificar() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "SELECT * FROM producto WHERE id_producto = ? ";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getId_producto());

                // Ejecutar la consulta SELECT
                ResultSet resultado = p.executeQuery();

                if (resultado.next()) {
                    this.setVerify(true);//Se confirmo el usuario existente

                } else {
                    this.setVerify(false);//No se encontro al usuario
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta verificar Producto: " + ev.getMessage());
            }
        }
    }

    @Override
    public void nuevo(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;

        // Nombre Producto
        this.vistaPanel.getProductoNombre.setText("NOMBRE");
        this.vistaPanel.getProductoNombre.setForeground(Color.GRAY);

        // Precio Producto
        this.vistaPanel.getProductoPrecio.setText("PRECIO");
        this.vistaPanel.getProductoPrecio.setForeground(Color.GRAY);

        // Stock Producto
        this.vistaPanel.getProductoStock.setValue(0);
        this.vistaPanel.getProductoStock.setForeground(Color.GRAY);

        // Lista Categoria Producto
        this.vistaPanel.getProductoListaCategoria.setSelectedIndex(0);//Seleccionar el primer elemento por defecto
    }

    @Override
    public void agregar() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "INSERT INTO producto (id_producto, nombre,precio,stock,id_categoria) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getId_producto());   //id_categoria
                p.setString(2, this.getNombre());  //nombre
                p.setDouble(3, this.getPrecio());   //Precio
                p.setInt(4, this.getStock());   //stock
                p.setString(5, this.getId_categoria());   //id_categoria

                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha agregado un nuevo producto!!");
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "No se pudo agregar un nuevo registro a la tabla Producto: " + ev.getMessage());
            }
        }
    }

    @Override
    public void modificar() {
        if (this.conex != null) {
            String consulta;
            PreparedStatement p;
            int filasAfectadas;
            try {
                // Verificar si existen los datos en la tabla cliente
                consulta = "UPDATE producto SET nombre = ?, precio = ?, stock = ?, id_categoria = ? WHERE id_producto = ? ";
                p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getNombre());   //nombre
                p.setDouble(2, this.getPrecio());  //precio
                p.setInt(3, this.getStock());//stock
                p.setString(4, this.getId_categoria());//id_categoria
                p.setString(5, this.getId_producto());//id_producto

                // cantidad de filas afectadas
                filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha modificado el registro de la tabla producto con exito.");
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error al modificar el registro de la tabla producto: " + ev.getMessage());
            }
        }
    }

    @Override
    public void eliminar() {
        if (this.conex != null) {
            String consulta;
            PreparedStatement p;
            int filasAfectadas;
            try {
                // Eliminar el registro de la tabla cliente por el IdCliente
                consulta = "DELETE FROM producto WHERE id_producto = ?";
                p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getId_producto());

                // cantidad de filas afectadas
                filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha eliminado el producto con exito");
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta eliminar el producto: " + ev.getMessage());
            }
        }
    }

    public void modificarStock() {
        if (this.conex != null) {
            String consulta;
            PreparedStatement p;
            int filasAfectadas;
            try {
                // Verificar si existen los datos en la tabla cliente
                consulta = "UPDATE producto SET stock = ? WHERE id_producto = ? ";
                p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setInt(1, this.getStock());//stock
                p.setString(2, this.getId_producto());//id_producto

                // cantidad de filas afectadas
                filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    //JOptionPane.showMessageDialog(null, "Se ha modificado el registro de la tabla producto con exito.");
                } else {
                    JOptionPane.showMessageDialog(null, "Alerta en Consulta de modificarStock");
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la funcion modificarStock de ProductoModel: " + ev.getMessage());
            }
        }
    }

    public void ProductosCategorias() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "SELECT * FROM producto WHERE id_categoria = ?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getId_categoria());

                // Ejecutar la consulta SELECT
                ResultSet resultado = p.executeQuery();

                if (resultado.next()) {
                    this.setVerify(false);
                } else {
                    this.setVerify(true);//No se encontro al usuario
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta verificar Producto: " + ev.getMessage());
            }
        }
    }

}
