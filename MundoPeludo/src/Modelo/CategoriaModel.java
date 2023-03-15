package Modelo;

import Vista.Panel;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CategoriaModel implements Funciones {

    //Variables con setter y getter
    private String id_categoria;
    private String nombre_categoria;
    private Boolean verify;

    private final DefaultTableModel tablaModelo;
    private Panel vistaPanel;
    private final Conexion conex;//evita que el parámetro se reescriba

    public CategoriaModel() {
        //Objeto tabla modelo
        this.tablaModelo = new DefaultTableModel();
        //Crear Objeto Conexion
        this.conex = new Conexion();
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

    public Boolean getVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }


    /*
        executeQuery(): Se usa en sentencias SELECT
        executeUpdate(): Se usa en sentencias INSERT, UPDATE, DELETE
     */
    //Funcion para generar ID
    public void generarID() {
        if (this.conex != null) {
            try {
                // Consultamos la tabla cliente
                String consulta = "SELECT MAX(id_categoria) AS ultimoID FROM categoria";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);

                // Ejecutar la consulta 
                ResultSet resultado = p.executeQuery();

                if (resultado.next()) {
                    String ultimoId = resultado.getString("ultimoID");
                    int numero = 0;
                    if (ultimoId != null) {
                        // Extraer la parte numérica quitando la 'CAT'
                        String numeroStr = ultimoId.substring(3);
                        numero = Integer.parseInt(numeroStr);
                    }

                    numero += 1; // Sumamos Uno
                    // Formatear el nuevo ID
                    String nuevoId = String.format("CAT%05d", numero);
                    this.setId_categoria(nuevoId); // Asignar el nuevo ID
                } else {
                    this.setVerify(false);//No se encontro al usuario
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error al generar ID: " + ev.getMessage());
            }
        }
    }

    @Override
    public void tituloTabla(Panel vistaPanel) {
        //Recibimos el parametro vistaPanel
        this.vistaPanel = vistaPanel;
        //Inicializar la tabla Usuario
        String titulo[] = new String[]{"id", "Nombre"};
        this.tablaModelo.setColumnIdentifiers(titulo);//Añadiendo titulos a la tabla

        this.vistaPanel.getTablaCategoria.setModel(this.tablaModelo);//mostrar los titulos del registro de usuario al inicio
    }

    @Override
    public void registroTabla(Panel vistaPanel) {
        //Recibimos el parametro vistaPanel
        this.vistaPanel = vistaPanel;
        if (this.conex != null) {
            try {
                // Consultamos la tabla cliente
                String consulta = "SELECT * FROM categoria";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);

                // Ejecutar la consulta 
                ResultSet resultado = p.executeQuery();

                // Limpia los registros de la tabla cliente no repetidos
                this.tablaModelo.setRowCount(0);

                // Muestra cada registro de la tabla
                while (resultado.next()) {
                    this.tablaModelo.addRow(new Object[]{
                        resultado.getString("id_categoria"),
                        resultado.getString("nombre_categoria")
                    });
                }
                this.vistaPanel.getTablaCategoria.setModel(this.tablaModelo);//mostrar los titulos del registro de clientes al inicio
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta registroTabla Categoria: " + ev.getMessage());
            }
        }
    }

    @Override
    public void verificar() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "SELECT * FROM categoria WHERE id_categoria = ? ";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getId_categoria());

                // Ejecutar la consulta SELECT
                ResultSet resultado = p.executeQuery();

                if (resultado.next()) {
                    this.setVerify(true);//Se confirmo el usuario existente

                } else {
                    this.setVerify(false);//No se encontro al usuario
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta verificar datos de Categoria: " + ev.getMessage());
            }
        }
    }

    @Override
    public void nuevo(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;

        // Nombre categoria
        this.vistaPanel.getCategoriaNombre.setText("NOMBRE");
        this.vistaPanel.getCategoriaNombre.setForeground(Color.GRAY);
    }

    @Override
    public void agregar() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "INSERT INTO categoria (id_categoria,nombre_categoria) VALUES (?, ?)";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getId_categoria());  //id_categoria
                p.setString(2, this.getNombre_categoria());   //nombre_categoria

                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha agregado un nuevo registro a la tabla Categoria!!");
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "No se pudo agregar un nuevo registro a la tabla Categoria: " + ev.getMessage());
            }
        }
    }

    @Override
    public void modificar() {
        if (this.conex != null) {
            try {
                // Verificar si existen los datos en la tabla cliente
                String consulta = "UPDATE categoria SET nombre_categoria = ? WHERE id_categoria = ? ";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getNombre_categoria());   //nombre_categoria
                p.setString(2, this.getId_categoria());  //id_categoria

                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha modificado el registro de la tabla categoria con exito.");
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error al modificar el registro de la tabla categoria: " + ev.getMessage());
            }
        }
    }

    @Override
    public void eliminar() {
        if (this.conex != null) {
            try {
                // Eliminar el registro de la tabla cliente por el IdCliente
                String consulta = "DELETE FROM categoria WHERE id_categoria = ?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getId_categoria());

                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha eliminado el registro la categoria con exito");
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta eliminar los registros del categoria: " + ev.getMessage());
            }
        }
    }

}
