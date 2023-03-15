package Controlador;

//Vistas
import Vista.Login;
import Vista.Panel;
import java.awt.Color;//Color de Texto
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

//Modelos
import Modelo.UsuarioModel;// Importamos el modelo para guardar lo que recibiremos del psvm

public class LoginControl implements ActionListener, MouseListener, FocusListener {

    private final Login vistaLogin;
    private final Panel vistaPanel;
    private final UsuarioModel usuarioModel;// variable de tipo UsuarioModel

    //Constructor
    public LoginControl(Login vistaLogin, Panel vistaPanel, UsuarioModel usuarioModel) {
        //Resiviendo variables del MVC
        this.vistaLogin = vistaLogin;
        this.vistaPanel = vistaPanel;
        this.usuarioModel = usuarioModel;

        
    }

    //Iniciar la vista Login
    public void inicio() {
        vistaLogin.setTitle("Mundo Peludo");//Titulo
        vistaLogin.setLocationRelativeTo(null);//Centrar ventana
        vistaLogin.setVisible(true);//Visible vista Login
        vistaLogin.setResizable(false);
        this.vistaLogin.show.setVisible(false);//No mostrar el ojo abierto
        
        //Iniciar Botones
        this.vistaLogin.getBtnIngresar.addActionListener(this);

        //Mostrar/Ocultar Contraseña
        this.vistaLogin.show.addMouseListener(this);
        this.vistaLogin.hide.addMouseListener(this);

        //PlaceHolder
        this.vistaLogin.getUserLogin.addFocusListener(this);
        this.vistaLogin.getPasswordLogin.addFocusListener(this);
    }

    //Funcionalidad de los botones de Login
    @Override
    public void actionPerformed(ActionEvent e) {
        //Escuchar la accion del boton Ingresar
        if (e.getSource() == this.vistaLogin.getBtnIngresar) {
            //Recibir valores del texField y los enviamos al modelo Usuario
            this.usuarioModel.setUsername(this.vistaLogin.getUserLogin.getText());
            this.usuarioModel.setPassword(this.vistaLogin.getPasswordLogin.getText());
            
            //Llamar a la funcion tabla de UsuarioModel
            this.usuarioModel.verificar();
            if (this.usuarioModel.getVerify()) {
                JOptionPane.showMessageDialog(null, "Bienvenido "+ this.usuarioModel.getNombre_apellido());

                //Abrir el controlador de Panel (eviando el vistaLogin, vistaPanel, usuarioModel)
                PanelControl panelControl = new PanelControl(this.vistaLogin, this.vistaPanel, this.usuarioModel);
                panelControl.inicio();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario y Contraseña Incorrecta!!");
                //PlaceHolder
                this.vistaLogin.getUserLogin.setText("USERNAME");
                this.vistaLogin.getPasswordLogin.setText("CONTRASEÑA");
                this.vistaLogin.getPasswordLogin.setEchoChar((char) 0);
            }
        }
    }

    //Mostrar contraseña y ocultar
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {//Activa una accion al haber presionado
        if (!(this.vistaLogin.getPasswordLogin.getText().equals("CONTRASEÑA"))) {
            if (e.getSource() == this.vistaLogin.hide) {//Presionar Ojo Cerrado
                this.vistaLogin.hide.setVisible(false);//Ojo cerrado
                this.vistaLogin.show.setVisible(true);//Ojo abierto

                this.vistaLogin.getPasswordLogin.setEchoChar((char) 0);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Ingresar la contraseña");
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {// Combinado por la action de presionar lo que hace es retornar al principio
        if (e.getSource() == this.vistaLogin.hide) {//Presionar Ocultar Contraseña
            this.vistaLogin.hide.setVisible(true);//Ojo cerrado
            this.vistaLogin.show.setVisible(false);//Ojo abierto

            this.vistaLogin.getPasswordLogin.setEchoChar('*');
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == this.vistaLogin.getUserLogin) {
            // Cuando se inicia el programa se hace un foco en el Usuario pasando la primera condicional
            // Luego pasa por la segunda condicional contraseña mostrando el texto
            if (this.vistaLogin.getUserLogin.getText().equals("USERNAME")) {
                this.vistaLogin.getUserLogin.setText("");
                this.vistaLogin.getUserLogin.setForeground(Color.BLACK);
                if (this.vistaLogin.getPasswordLogin.getText().equals("CONTRASEÑA")) {
                    this.vistaLogin.getPasswordLogin.setEchoChar((char) 0);
                    this.vistaLogin.getPasswordLogin.setForeground(Color.GRAY);
                }
            }

        }

        if (e.getSource() == this.vistaLogin.getPasswordLogin) {
            this.vistaLogin.getPasswordLogin.setEchoChar('*');//Al momento de escribir debe estar cifrado la contraseña
            if (this.vistaLogin.getPasswordLogin.getText().equals("CONTRASEÑA")) {
                this.vistaLogin.getPasswordLogin.setText("");
                this.vistaLogin.getPasswordLogin.setForeground(Color.BLACK);
            }

        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == this.vistaLogin.getUserLogin) {
            if (this.vistaLogin.getUserLogin.getText().equals("")) {
                this.vistaLogin.getUserLogin.setText("USERNAME");
                this.vistaLogin.getUserLogin.setForeground(Color.GRAY);
            } else {
                this.vistaLogin.getUserLogin.setForeground(Color.GRAY);
            }
        }

        if (e.getSource() == this.vistaLogin.getPasswordLogin) {
            //System.out.println(this.vistaLogin.getPasswordLogin.getText());

            if (this.vistaLogin.getPasswordLogin.getText().equals("")) {
                this.vistaLogin.getPasswordLogin.setText("CONTRASEÑA");
                this.vistaLogin.getPasswordLogin.setEchoChar((char) 0);
                this.vistaLogin.getPasswordLogin.setForeground(Color.GRAY);
            } else {
                this.vistaLogin.getPasswordLogin.setForeground(Color.GRAY);
            }
        }
    }

}
