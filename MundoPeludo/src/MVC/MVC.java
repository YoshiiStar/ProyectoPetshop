package MVC;
// MVC: MUNDO PELUDO
import Vista.Login;// Vista Login
import Vista.Panel;// Vista Panel
import Controlador.LoginControl;// Controlador Login
import Modelo.UsuarioModel;


public class MVC {
    public static void main(String[] args) {
        //Objetos de clases que possen las entradas
        Login vistaLogin = new Login();
        Panel vistaPanel = new Panel();
        
        //Crear un objeto de tipo Usuario Model dentro del psvm
        UsuarioModel usuarioModel = new UsuarioModel();
        
        //InicioController(pasamos por parametro los objetos)
        LoginControl loginControl = new LoginControl(vistaLogin,vistaPanel,usuarioModel);
        loginControl.inicio();    
    }
}
