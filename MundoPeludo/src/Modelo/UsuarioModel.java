package Modelo;

import Vista.Panel;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class UsuarioModel implements Funciones,Personal{
    //Variables con setter y getter
    private String dni;
    private String nombre_apellido;
    private String correo;
    private String username;
    private String password;
    private Boolean verify;
    
    //Variables sin setter y getter
    private final DefaultTableModel tablaModelo;
    private Panel vistaPanel;
    private final Conexion conex;//evita que el parámetro se reescriba
    

    public UsuarioModel() {
        //Objeto tabla modelo
        this.tablaModelo = new DefaultTableModel();
        //Crear Objeto Conexion
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public void buscarDNI(){
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "SELECT * FROM usuario WHERE dni=?";
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
                JOptionPane.showMessageDialog(null, "Error en la consulta buscarDNI del Usuario: " + ev.getMessage());
            }
        }
    }

    @Override
    public void tituloTabla(Panel vistaPanel) {
        //Recibimos el parametro vistaPanel
        this.vistaPanel = vistaPanel;
        //Inicializar la tabla Usuario
        String titulo[] = new String[]{"DNI", "Nombre y Apellidos", "Correo", "Usuario", "Contraseña", "Fecha"};
        this.tablaModelo.setColumnIdentifiers(titulo);//Añadiendo titulos a la tabla
        
        this.vistaPanel.getTablaUsuario.setModel(this.tablaModelo);//mostrar los titulos del registro de usuario al inicio
    }
    
    @Override
    public void registroTabla(Panel vistaPanel) {
        //Recibimos el parametro vistaPanel
        this.vistaPanel = vistaPanel;
        if (this.conex != null) {
            try {
                // Consultamos la tabla cliente
                String consulta = "SELECT * FROM usuario";
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
                        resultado.getString("username"),
                        resultado.getString("password"),
                        resultado.getString("fecha")
                    });
                }
                this.vistaPanel.getTablaUsuario.setModel(this.tablaModelo);//mostrar los titulos del registro de clientes al inicio
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta registroTabla Usuario: " + ev.getMessage());
            }
        }
    }
    
    @Override
    public void verificar() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "SELECT * FROM usuario WHERE username=? AND password=?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getUsername());
                p.setString(2,this.getPassword());
                
                // Ejecutar la consulta SELECT
                ResultSet resultado = p.executeQuery();
                
                if (resultado.next()) {
                    this.setNombre_apellido(resultado.getString("nombre_apellido"));//almacenar el nombre y apellido del usuario
                    this.setDni(resultado.getString("dni"));// almacenar el dni de la persona
                    this.setVerify(true);//Se confirmo el usuario existente         
                } else {
                    this.setVerify(false);//No se encontro al usuario
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
        this.vistaPanel.getUsuarioDNI.setText("DNI");
        this.vistaPanel.getUsuarioDNI.setForeground(Color.GRAY);

        // Nombres y Apellidos
        this.vistaPanel.getUsuarioNombreApellido.setText("NOMBRE Y APELLIDO");
        this.vistaPanel.getUsuarioNombreApellido.setForeground(Color.GRAY);

        // Correo
        this.vistaPanel.getUsuarioCorreo.setText("CORREO");
        this.vistaPanel.getUsuarioCorreo.setForeground(Color.GRAY);

        // Username
        this.vistaPanel.getUsuarioUsername.setText("USERNAME");
        this.vistaPanel.getUsuarioUsername.setForeground(Color.GRAY);

        // Contraseña
        this.vistaPanel.getUsuarioPassword.setText("CONTRASEÑA");
        this.vistaPanel.getUsuarioPassword.setForeground(Color.GRAY);
    }

    @Override
    public void agregar() {
        if (this.conex != null) {
            try {
                // Consultanto la existencia el UsuarioModel del vendedor
                String consulta = "INSERT INTO usuario (dni,nombre_apellido,correo,username,password) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getDni());  //dni
                p.setString(2,this.getNombre_apellido());   //nombre_apellido
                p.setString(3,this.getCorreo());    //Correo
                p.setString(4,this.getUsername());   //Username
                p.setString(5,this.getPassword()); //Password
                             
                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha agregado un nuevo registro a la tabla Usuario!!");
                }

            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "No se pudo agregar un nuevo registro a la tabla Usuario: " + ev.getMessage());
            }
        }
    }

    @Override
    public void modificar() {
        if (this.conex != null) {
            try {
                // Verificar si existen los datos en la tabla cliente
                String consulta = "UPDATE usuario SET nombre_apellido = ?, correo = ?, username = ?, password = ? WHERE dni = ?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getNombre_apellido());// Nombre y Apellido
                p.setString(2, this.getCorreo());// Correo
                p.setString(3, this.getUsername()); // Username
                p.setString(4, this.getPassword());// Password
                p.setString(5, this.getDni()); // Dni

                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha modificado el registro de la tabla usuario " + this.getNombre_apellido()+ " con exito.");
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error al modificar el registro de la tabla usuario: " + ev.getMessage());
            }
        }
    }

    @Override
    public void eliminar() {
        if (this.conex != null) {
            try {
                // Eliminar el registro de la tabla cliente por el IdCliente
                String consulta = "DELETE FROM usuario WHERE dni = ?";
                PreparedStatement p = this.conex.obtenerConexion().prepareStatement(consulta);
                p.setString(1, this.getDni());

                // cantidad de filas afectadas
                int filasAfectadas = p.executeUpdate();

                //Verficar si se han afectado algunas filas
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha eliminado el registro del usuario con exito");
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Error en la consulta eliminar los registros del usuario: " + ev.getMessage());
            }
        }
    }

    

}
