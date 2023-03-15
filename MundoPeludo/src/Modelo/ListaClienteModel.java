package Modelo;

import Vista.ListaCliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ListaClienteModel {

    //Variables con setter y getter
    private String NombreCompleto;
    private String dniCliente;
    private ListaCliente vistaListaCliente;

    private final DefaultTableModel tablaModelo;
    private final Conexion conex;//evita que el parámetro se reescriba

    public ListaClienteModel() {
        //Objeto tabla modelo
        this.tablaModelo = new DefaultTableModel();
        //Crear Objeto Conexion
        this.conex = new Conexion();
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }
    
    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }
   
    public void tituloTabla(ListaCliente vistaListaCliente) {
        //Recibimos el parametro vistaPanel
        this.vistaListaCliente = vistaListaCliente;
        //Inicializar la tabla Usuario
        String titulo[] = new String[]{"DNI", "Nombre del Cliente"};
        this.tablaModelo.setColumnIdentifiers(titulo);//Añadiendo titulos a la tabla
        this.vistaListaCliente.getTablaListaCliente.setModel(this.tablaModelo);//mostrar los titulos del registro de usuario al inicio
    }

    public void registroTabla(ListaCliente vistaListaCliente) {
        //Recibimos el parametro vistaPanel
        this.vistaListaCliente = vistaListaCliente;
        if (this.conex != null) {
            try {
                // Consultamos la tabla cliente
                String consulta = "SELECT dni, nombre_apellido FROM cliente";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);

                // Ejecutar la consulta 
                ResultSet resultado = p.executeQuery();

                // Limpia los registros de la tabla cliente no repetidos
                this.tablaModelo.setRowCount(0);

                // Muestra cada registro de la tabla
                while (resultado.next()) {
                    this.tablaModelo.addRow(new Object[]{
                        resultado.getString("dni"),
                        resultado.getString("nombre_apellido")
                    });
                }
                this.vistaListaCliente.getTablaListaCliente.setModel(this.tablaModelo);//mostrar los titulos del registro de clientes al inicio
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta registroTabla Categoria: " + ev.getMessage());
            }
        }
    }
}
