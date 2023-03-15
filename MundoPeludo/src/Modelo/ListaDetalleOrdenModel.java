package Modelo;

import Vista.ListaDetalleOrdenCliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ListaDetalleOrdenModel {

    private ListaDetalleOrdenCliente vistaListaOrden;
    private final DefaultTableModel tablaModelo;
    private final Conexion conex;//evita que el parámetro se reescriba
    
    public ListaDetalleOrdenModel() {
        //Objeto tabla modelo
        this.tablaModelo = new DefaultTableModel();
        //Crear Objeto Conexion
        this.conex = new Conexion();
    }
    
    public void tituloTabla(ListaDetalleOrdenCliente vistaListaOrden) {
        //Recibimos el parametro vistaPanel
        this.vistaListaOrden = vistaListaOrden;
        //Inicializar la tabla Usuario
        String titulo[] = new String[]{"DNI", "Nombre del Cliente"};
        this.tablaModelo.setColumnIdentifiers(titulo);//Añadiendo titulos a la tabla
        this.vistaListaOrden.getTablaListaDetalleCliente.setModel(this.tablaModelo);//mostrar los titulos del registro de usuario al inicio
    }

    public void registroTabla(ListaDetalleOrdenCliente vistaListaOrden) {
        //Recibimos el parametro vistaPanel
        this.vistaListaOrden= vistaListaOrden;
        if (this.conex != null) {
            try {
                // Consultamos la tabla cliente
                String consulta = "SELECT dni,nombre_apellido FROM cliente";
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
                this.vistaListaOrden.getTablaListaDetalleCliente.setModel(this.tablaModelo);//mostrar los titulos del registro de clientes al inicio
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta dentro de detalle Orden: " + ev.getMessage());
            }
        }
    }

}
