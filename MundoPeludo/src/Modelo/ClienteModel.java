package Modelo;

import Vista.Panel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClienteModel implements Funciones, Personal {

    private String dni;
    private String nombre_apellido;
    private String correo;
    private String celular;
    private String direccion;

    private final Conexion conex;
    private Boolean verify;

    private PdfPTable tablaCliente;
    private Document documento;

    private final DefaultTableModel tablaModelo;
    private Panel vistaPanel;

    public ClienteModel() {
        this.tablaModelo = new DefaultTableModel();
        this.conex = new Conexion();

    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre_apellido() {
        return nombre_apellido;
    }

    public void setNombre_apellido(String nombre_apellido) {
        this.nombre_apellido = nombre_apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
    @Override
    public void buscarDNI() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "SELECT * FROM cliente WHERE dni=?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getDni());

                // Ejecutar la consulta SELECT
                ResultSet resultado = p.executeQuery();

                if (resultado.next()) {
                    this.setVerify(true);//True: Hay personas con ese DNI        
                } else {
                    this.setVerify(false);//No hay personas con ese DNI 
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta buscarDNI del Cliente: " + ev.getMessage());
            }
        }
    }

    @Override
    public void tituloTabla(Panel vistaPanel) {
        //Recibimos el parametro vistaPanel
        this.vistaPanel = vistaPanel;
        //Inicializar la tabla Usuario
        String titulo[] = new String[]{"DNI", "Nombre y Apellidos", "Correo", "Celular", "Direccion"};
        this.tablaModelo.setColumnIdentifiers(titulo);//Añadiendo titulos a la tabla

        this.vistaPanel.getTablaCliente.setModel(this.tablaModelo);//mostrar los titulos del registro de clientes al inicio
    }

    @Override
    public void registroTabla(Panel vistaPanel) {
        //Recibimos el parametro vistaPanel
        this.vistaPanel = vistaPanel;
        if (this.conex != null) {
            try {
                // Consultamos la tabla cliente
                String consulta = "SELECT * FROM cliente";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);

                // Ejecutar la consulta 
                ResultSet resultado = p.executeQuery();

                // Limpia los registros de la tabla cliente no repetidos
                this.tablaModelo.setRowCount(0);

                // Muestra cada registro de la tabla
                while (resultado.next()) {
                    this.tablaModelo.addRow(new Object[]{
                        resultado.getString("dni"),
                        resultado.getString("nombre_apellido"),
                        resultado.getString("correo"),
                        resultado.getString("celular"),
                        resultado.getString("direccion")
                    });
                }
                this.vistaPanel.getTablaCliente.setModel(this.tablaModelo);//mostrar los titulos del registro de clientes al inicio
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta registroTabla cliente: " + ev.getMessage());
            }
        }
    }

    @Override
    public void verificar() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "SELECT * FROM cliente WHERE dni=?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getDni());

                // Ejecutar la consulta SELECT
                ResultSet resultado = p.executeQuery();

                if (resultado.next()) {
                    //this.setNombre(resultado.getString("nombre"));
                    this.setVerify(true);
                } else {
                    this.setVerify(false);
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta verificar datos del Usuario: " + ev.getMessage());
            }

        }
    }

    @Override
    public void nuevo(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;
        // DNI
        this.vistaPanel.getClienteDNI.setText("DNI");
        this.vistaPanel.getClienteDNI.setForeground(Color.GRAY);

        // Nombres y Apellidos
        this.vistaPanel.getClienteNombreApellido.setText("NOMBRE Y APELLIDO");
        this.vistaPanel.getClienteNombreApellido.setForeground(Color.GRAY);

        // Correo
        this.vistaPanel.getClienteCorreo.setText("CORREO");
        this.vistaPanel.getClienteCorreo.setForeground(Color.GRAY);

        // Celular
        this.vistaPanel.getClienteCelular.setText("CELULAR");
        this.vistaPanel.getClienteCelular.setForeground(Color.GRAY);

        // Direccion
        this.vistaPanel.getClienteDireccion.setText("DIRECCIÓN");
        this.vistaPanel.getClienteDireccion.setForeground(Color.GRAY);
    }

    @Override
    public void agregar() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "INSERT INTO cliente (dni,nombre_apellido,correo,celular,direccion) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getDni());  //dni
                p.setString(2, this.getNombre_apellido());   //nombre_apellido
                p.setString(3, this.getCorreo());    //Correo
                p.setString(4, this.getCelular());   //Celular
                p.setString(5, this.getDireccion()); //Direccion

                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha agregado un nuevo registro a la tabla cliente!!");
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "No se pudo agregar un nuevo registro a la tabla cliente: " + ev.getMessage());
            }
        }
    }

    @Override
    public void modificar() {
        if (this.conex != null) {
            try {
                // Verificar si existen los datos en la tabla cliente
                String consulta = "UPDATE cliente SET nombre_apellido = ?, correo = ?, celular = ?, direccion = ? WHERE dni = ?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getNombre_apellido());
                p.setString(2, this.getCorreo());
                p.setString(3, this.getCelular());
                p.setString(4, this.getDireccion());
                p.setString(5, this.getDni());

                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha modificado el registro del cliente " + this.getNombre_apellido() + " con exito.");
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error al modificar el resgistro del cliente: " + ev.getMessage());
            }
        }
    }

    @Override
    public void eliminar() {
        if (this.conex != null) {
            try {
                // Eliminar el registro de la tabla cliente por el IdCliente
                String consulta = "DELETE FROM cliente WHERE dni = ?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getDni());

                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha eliminado el registro del cliente con exito");
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta eliminar los registros del cliente: " + ev.getMessage());
            }
        }
    }

    public void ImprimirCliente(Document documento, PdfPTable tablaCliente) {
        if (this.conex != null) {
            this.tablaCliente = tablaCliente;
            try {
                this.documento = documento;
                // Consultanto la existencia el UsuarioModel del vendedor 
                String consulta = "SELECT dni,nombre_apellido,correo,celular,direccion FROM cliente WHERE dni=?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getDni());

                // Ejecutar la consulta SELECT
                ResultSet resultado = p.executeQuery();

                if (resultado.next()) {
                    try {
                        //Agregar datos del cliente
                        this.tablaCliente.addCell(resultado.getString("dni")); // DNI               
                        this.tablaCliente.addCell(resultado.getString("nombre_apellido")); // NOMBRES Y APELLIDOS 
                        this.tablaCliente.addCell(resultado.getString("correo")); // CORREO
                        this.tablaCliente.addCell(resultado.getString("celular")); // CELULAR          
                        this.tablaCliente.addCell(resultado.getString("direccion")); // DIRECCION

                        this.documento.add(this.tablaCliente);
                    } catch (DocumentException e) {
                        JOptionPane.showMessageDialog(null,"Error:"+e.getMessage());
                    }

                } else {
                    this.setVerify(false);//No hay personas con ese DNI 
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta buscarDNI del Cliente: " + ev.getMessage());
            }
        }
    }

 
}
