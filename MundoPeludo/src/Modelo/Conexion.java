package Modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    Connection conexion = null;   
    String usuario = "root";
    String password = "";
    
    public Connection obtenerConexion(){
        try{
            String cadena = "jdbc:mysql://localhost:3306/BDMundoPeludo"; // Asegúrate que la base de datos exista
            conexion = DriverManager.getConnection(cadena, this.usuario, this.password);
            // JOptionPane.showMessageDialog(null, "Conexión exitosa a MySQL!");
        }catch(SQLException ev){
            JOptionPane.showMessageDialog(null, "Error al conectar con MySQL: " + ev.toString());
        }
        return conexion;
    }
}
