package Controlador;

import Modelo.UsuarioModel;
import Vista.Panel;
import java.awt.Color;
import java.awt.Cursor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class UsuarioControl implements ActionListener, FocusListener, MouseListener {

    private final Panel vistaPanel;
    private final UsuarioModel usuarioModel;
    private int seleccionar;

    //Cursor para los Botones
    Cursor pointerCursor = new Cursor(Cursor.HAND_CURSOR);

    public UsuarioControl(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;

        //Objeto UsuarioModel
        this.usuarioModel = new UsuarioModel();

        //Mostrar el titulo de la tabla cliente
        this.usuarioModel.tituloTabla(this.vistaPanel);// Parametro: VistaPanel

        //Mostrar el registro de la tabla cliente
        this.usuarioModel.registroTabla(this.vistaPanel);

        //Iniciar los campos
        this.usuarioModel.nuevo(this.vistaPanel);
        
    }

    public void inicio() {
        // Botones de acción con listeners únicos
        agregarListenerUnico(this.vistaPanel.getBtnUsuarioAgregar, this);
        agregarListenerUnico(this.vistaPanel.getBtnUsuarioBorrar, this);
        agregarListenerUnico(this.vistaPanel.getBtnUsuarioNuevo, this);
        agregarListenerUnico(this.vistaPanel.getBtnUsuarioModificar, this);

        //PlaceHolder
        this.vistaPanel.getUsuarioDNI.addFocusListener(this);//DNI
        this.vistaPanel.getUsuarioNombreApellido.addFocusListener(this);//NOMBRE Y APPELLIDO
        this.vistaPanel.getUsuarioCorreo.addFocusListener(this);//CORREO
        this.vistaPanel.getUsuarioUsername.addFocusListener(this);//USERNAME
        this.vistaPanel.getUsuarioPassword.addFocusListener(this);//CONTRASEÑA

        //Seleccionar la tabla
        this.vistaPanel.getTablaUsuario.addMouseListener(this);
    }
    
    /*
    Este es un error común en aplicaciones con múltiples paneles (por ejemplo, con CardLayout, JTabbedPane, etc.), 
    donde cada vez que "vuelves" al panel, se vuelve a agregar el mismo ActionListener sin remover el anterior.
    */
    //el metodo ayuda a que accion del boton no se repite dos veces
    private void agregarListenerUnico(javax.swing.JButton boton, ActionListener listener) {
        // Remueve cualquier ActionListener previamente asignado
        for (ActionListener al : boton.getActionListeners()) {
            boton.removeActionListener(al);
        }
        // Añade el nuevo ActionListener
        boton.addActionListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vistaPanel.getBtnUsuarioAgregar)) {//Boton Agregar
            //Recibir datos
            this.usuarioModel.setDni(this.vistaPanel.getUsuarioDNI.getText());
            this.usuarioModel.setNombre_apellido(this.vistaPanel.getUsuarioNombreApellido.getText());
            this.usuarioModel.setCorreo(this.vistaPanel.getUsuarioCorreo.getText());
            this.usuarioModel.setUsername(this.vistaPanel.getUsuarioUsername.getText());
            this.usuarioModel.setPassword(this.vistaPanel.getUsuarioPassword.getText());

            if (!this.usuarioModel.getNombre_apellido().equals("NOMBRE Y APELLIDO") && !this.usuarioModel.getDni().equals("DNI")
                    && !this.usuarioModel.getCorreo().equals("CORREO") && !this.usuarioModel.getUsername().equals("USERNAME") && !this.usuarioModel.getPassword().equals("CONTRASEÑA")) {
                if (this.usuarioModel.getDni().length() == 8) {
                    this.usuarioModel.buscarDNI();//Buscamos por medio del DNI repetido
                    if (this.usuarioModel.getVerify()) {// V: usuario registrado con ese mismo DNI F: usuario no registrado con ese DNI
                        JOptionPane.showMessageDialog(null, "El DNI del usuario ya ha sido registrado");
                    } else {
                        this.usuarioModel.agregar();
                        this.usuarioModel.registroTabla(this.vistaPanel);//Renovar registro de la tabla
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El DNI debe tener 8 digitos");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor,ingrese todos los datos");
            }
        }

        if (e.getSource().equals(this.vistaPanel.getBtnUsuarioBorrar)) {//Boton Borrar
            System.out.println(Arrays.toString(this.vistaPanel.getBtnCategoriaBorrar.getActionListeners()));
            if (this.seleccionar > 0) {
                this.usuarioModel.setDni(this.vistaPanel.getUsuarioDNI.getText());
                this.usuarioModel.eliminar();
                //Reiniciar la tabla
                this.usuarioModel.registroTabla(this.vistaPanel);//Renovar registro de la tabla
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione el usuario requiere eliminar");
            }

        }

        if (e.getSource().equals(this.vistaPanel.getBtnUsuarioNuevo)) {//Boton Nuevo
            this.usuarioModel.nuevo(this.vistaPanel);
            this.seleccionar = -1;
        }

        if (e.getSource().equals(this.vistaPanel.getBtnUsuarioModificar)) {//Boton Modificar
            if (this.seleccionar >= 0) {
                this.usuarioModel.setDni(this.vistaPanel.getUsuarioDNI.getText());//DNI
                this.usuarioModel.setNombre_apellido(this.vistaPanel.getUsuarioNombreApellido.getText());//NOMBRE Y APPELLIDO
                this.usuarioModel.setCorreo(this.vistaPanel.getUsuarioCorreo.getText());//CORREO
                this.usuarioModel.setUsername(this.vistaPanel.getUsuarioUsername.getText());//USERNAME
                this.usuarioModel.setPassword(this.vistaPanel.getUsuarioPassword.getText());//CONTRASEÑA
                if (this.usuarioModel.getDni().length() == 8) {
                    this.usuarioModel.modificar();
                    this.usuarioModel.registroTabla(this.vistaPanel);//Renovar registro de la tabla    
                } else {
                    JOptionPane.showMessageDialog(null, "El DNI debe tener 8 digitos");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione el usuario requiere modificar");
            }
        }

    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource().equals(this.vistaPanel.getUsuarioDNI)) {//DNI
            if (this.vistaPanel.getUsuarioDNI.getText().equals("DNI")) {
                this.vistaPanel.getUsuarioDNI.setText("");
                this.vistaPanel.getUsuarioDNI.setForeground(Color.BLACK);
            } else {
                this.vistaPanel.getUsuarioDNI.setForeground(Color.BLACK);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getUsuarioNombreApellido)) {//NOMBRE Y APPELLIDO
            if (this.vistaPanel.getUsuarioNombreApellido.getText().equals("NOMBRE Y APELLIDO")) {
                this.vistaPanel.getUsuarioNombreApellido.setText("");
                this.vistaPanel.getUsuarioNombreApellido.setForeground(Color.BLACK);
            } else {
                this.vistaPanel.getUsuarioNombreApellido.setForeground(Color.BLACK);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getUsuarioCorreo)) {//CORREO
            if (this.vistaPanel.getUsuarioCorreo.getText().equals("CORREO")) {
                this.vistaPanel.getUsuarioCorreo.setText("");
                this.vistaPanel.getUsuarioCorreo.setForeground(Color.BLACK);
            } else {
                this.vistaPanel.getUsuarioCorreo.setForeground(Color.BLACK);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getUsuarioUsername)) {//USERNAME
            if (this.vistaPanel.getUsuarioUsername.getText().equals("USERNAME")) {
                this.vistaPanel.getUsuarioUsername.setText("");
                this.vistaPanel.getUsuarioUsername.setForeground(Color.BLACK);
            } else {
                this.vistaPanel.getUsuarioUsername.setForeground(Color.BLACK);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getUsuarioPassword)) {//CONTRASEÑA
            if (this.vistaPanel.getUsuarioPassword.getText().equals("CONTRASEÑA")) {
                this.vistaPanel.getUsuarioPassword.setText("");
                this.vistaPanel.getUsuarioPassword.setForeground(Color.BLACK);
            } else {
                this.vistaPanel.getUsuarioPassword.setForeground(Color.BLACK);
            }
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource().equals(this.vistaPanel.getUsuarioDNI)) {//DNI
            if (this.vistaPanel.getUsuarioDNI.getText().equals("")) {
                this.vistaPanel.getUsuarioDNI.setText("DNI");
                this.vistaPanel.getUsuarioDNI.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getUsuarioDNI.setForeground(Color.GRAY);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getUsuarioNombreApellido)) {//NOMBRE APPELLIDO
            if (this.vistaPanel.getUsuarioNombreApellido.getText().equals("")) {
                this.vistaPanel.getUsuarioNombreApellido.setText("NOMBRE Y APELLIDO");
                this.vistaPanel.getUsuarioNombreApellido.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getUsuarioNombreApellido.setForeground(Color.GRAY);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getUsuarioCorreo)) {//CORREO
            if (this.vistaPanel.getUsuarioCorreo.getText().equals("")) {
                this.vistaPanel.getUsuarioCorreo.setText("CORREO");
                this.vistaPanel.getUsuarioCorreo.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getUsuarioCorreo.setForeground(Color.GRAY);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getUsuarioUsername)) {//USERNAME
            if (this.vistaPanel.getUsuarioUsername.getText().equals("")) {
                this.vistaPanel.getUsuarioUsername.setText("USERNAME");
                this.vistaPanel.getUsuarioUsername.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getUsuarioUsername.setForeground(Color.GRAY);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getUsuarioPassword)) {//CONTRASEÑA
            if (this.vistaPanel.getUsuarioPassword.getText().equals("")) {
                this.vistaPanel.getUsuarioPassword.setText("CONTRASEÑA");
                this.vistaPanel.getUsuarioPassword.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getUsuarioPassword.setForeground(Color.GRAY);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.vistaPanel.getTablaUsuario) {
            seleccionar = this.vistaPanel.getTablaUsuario.rowAtPoint(e.getPoint());
            if (seleccionar >= 0) {
                this.vistaPanel.getUsuarioDNI.setText(vistaPanel.getTablaUsuario.getValueAt(seleccionar, 0).toString());//DNI
                this.vistaPanel.getUsuarioNombreApellido.setText(this.vistaPanel.getTablaUsuario.getValueAt(seleccionar, 1).toString());//NOMBRE Y APPELLIDO
                this.vistaPanel.getUsuarioCorreo.setText(this.vistaPanel.getTablaUsuario.getValueAt(seleccionar, 2).toString());//CORREO
                this.vistaPanel.getUsuarioUsername.setText(this.vistaPanel.getTablaUsuario.getValueAt(seleccionar, 3).toString());//USERNAME
                this.vistaPanel.getUsuarioPassword.setText(this.vistaPanel.getTablaUsuario.getValueAt(seleccionar, 4).toString());//CONTRASEÑA

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
