package Controlador;

import Modelo.ClienteModel;
import Vista.Panel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class ClienteControl implements ActionListener, FocusListener, MouseListener {

    private final Panel vistaPanel;

    private final ClienteModel clienteModel;
    //private DefaultTableModel tablaModelo;//pasar el objeto
    private int seleccionar;

    public ClienteControl(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;

        //Iniciar el Modelo Cliente
        this.clienteModel = new ClienteModel();

        //Mostrar el titulo de la tabla cliente
        this.clienteModel.tituloTabla(this.vistaPanel);// Parametro: VistaPanel

        //Mostrar el registro de la tabla cliente
        this.clienteModel.registroTabla(this.vistaPanel);
    }
    
    public void inicio(){
        //Accionar Botones
        this.agregarListenerUnico(this.vistaPanel.getBtnClienteAgregar, this);//Agregar 
        this.agregarListenerUnico(this.vistaPanel.getBtnClienteBorrar, this);//Borrar
        this.agregarListenerUnico(this.vistaPanel.getBtnClienteNuevo, this);//Nuevo
        this.agregarListenerUnico(this.vistaPanel.getBtnClienteModificar, this);//Modificar

        //PlaceHolder
        this.vistaPanel.getClienteDNI.addFocusListener(this);//DNI
        this.vistaPanel.getClienteNombreApellido.addFocusListener(this);//NOMBRES Y APELLIDOS
        this.vistaPanel.getClienteCorreo.addFocusListener(this);//CORREO
        this.vistaPanel.getClienteCelular.addFocusListener(this);//CELULAR
        this.vistaPanel.getClienteDireccion.addFocusListener(this);//DIRECCION
        
        //Seleccionar la tabla
        this.vistaPanel.getTablaCliente.addMouseListener(this);
    }
    
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
        if (e.getSource().equals(this.vistaPanel.getBtnClienteAgregar)) {//Boton Agregar
            this.clienteModel.setDni(this.vistaPanel.getClienteDNI.getText());//DNI
            this.clienteModel.setNombre_apellido(this.vistaPanel.getClienteNombreApellido.getText());//NOMBRE Y APELLIDO
            this.clienteModel.setCorreo(this.vistaPanel.getClienteCorreo.getText());//CORREO
            this.clienteModel.setCelular(this.vistaPanel.getClienteCelular.getText());//CELULAR
            this.clienteModel.setDireccion(this.vistaPanel.getClienteDireccion.getText());//DIRECCION

            if (!this.clienteModel.getNombre_apellido().equals("NOMBRES Y APELLIDOS") && !this.clienteModel.getDni().equals("DNI") && 
                !this.clienteModel.getCorreo().equals("CORREO") && !this.clienteModel.getCelular().equals("CELULAR") && !this.clienteModel.getDireccion().equals("DIRECCION")) {
                if (this.clienteModel.getDni().length() == 8) {
                    this.clienteModel.buscarDNI();//Buscamos por medio del DNI repetido
                    if (this.clienteModel.getVerify()) {// V: cliente registrado con ese mismo DNI F: cliente no registrado con ese DNI
                        JOptionPane.showMessageDialog(null, "El DNI del Cliente ya ha sido registrado");
                    } else {
                        this.clienteModel.agregar();
                        this.clienteModel.registroTabla(this.vistaPanel);//Renovar registro de la tabla
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El DNI debe tener 8 digitos");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor,ingrese todos los datos");
            }
        }

        if (e.getSource().equals(this.vistaPanel.getBtnClienteBorrar)) {//Boton Borrar
            if (this.seleccionar > 0) {
                this.clienteModel.setDni(this.vistaPanel.getClienteDNI.getText());
                this.clienteModel.eliminar();
                //Reiniciar la tabla
                this.clienteModel.registroTabla(this.vistaPanel);//Renovar registro de la tabla
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione el Cliente que requiere eliminar");
            }

        }

        if (e.getSource().equals(this.vistaPanel.getBtnClienteNuevo)) {//Boton Nuevo
            this.clienteModel.nuevo(this.vistaPanel);
            this.seleccionar = -1;
        }

        if (e.getSource().equals(this.vistaPanel.getBtnClienteModificar)) {//Boton Modificar
            if (this.seleccionar > 0) {
                this.clienteModel.setDni(this.vistaPanel.getClienteDNI.getText());//DNI
                this.clienteModel.setNombre_apellido(this.vistaPanel.getClienteNombreApellido.getText());//NOMBRE Y APPELLIDO
                this.clienteModel.setCorreo(this.vistaPanel.getClienteCorreo.getText());//CORREO
                this.clienteModel.setCelular(this.vistaPanel.getClienteCorreo.getText());//CELULAR
                this.clienteModel.setDireccion(this.vistaPanel.getClienteDireccion.getText());//DIRECCION
                if (this.clienteModel.getDni().length() == 8) {
                    this.clienteModel.modificar();
                    this.clienteModel.registroTabla(this.vistaPanel);//Renovar registro de la tabla    
                } else {
                    JOptionPane.showMessageDialog(null, "El DNI debe tener 8 digitos");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione el cliente que requiere modificar");
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource().equals(this.vistaPanel.getClienteDNI)) {//DNI
            if (this.vistaPanel.getClienteDNI.getText().equals("DNI")) {
                this.vistaPanel.getClienteDNI.setText("");
                this.vistaPanel.getClienteDNI.setForeground(Color.BLACK);
            }else{
                this.vistaPanel.getClienteDNI.setForeground(Color.BLACK);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getClienteNombreApellido)) {//NOMBRES Y APELLIDOS
            if (this.vistaPanel.getClienteNombreApellido.getText().equals("NOMBRES Y APELLIDOS")) {
                this.vistaPanel.getClienteNombreApellido.setText("");
                this.vistaPanel.getClienteNombreApellido.setForeground(Color.BLACK);
            }else{
                this.vistaPanel.getClienteNombreApellido.setForeground(Color.BLACK);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getClienteCorreo)) {//CORREO
            if (this.vistaPanel.getClienteCorreo.getText().equals("CORREO")) {
                this.vistaPanel.getClienteCorreo.setText("");
                this.vistaPanel.getClienteCorreo.setForeground(Color.BLACK);
            }else{
                this.vistaPanel.getClienteCorreo.setForeground(Color.BLACK);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getClienteCelular)) {//CELULAR
            if (this.vistaPanel.getClienteCelular.getText().equals("CELULAR")) {
                this.vistaPanel.getClienteCelular.setText("");
                this.vistaPanel.getClienteCelular.setForeground(Color.BLACK);
            }else{
                this.vistaPanel.getClienteCelular.setForeground(Color.BLACK);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getClienteDireccion)) {//DIRECCION
            if (this.vistaPanel.getClienteDireccion.getText().equals("DIRECCION")) {
                this.vistaPanel.getClienteDireccion.setText("");
                this.vistaPanel.getClienteDireccion.setForeground(Color.BLACK);
            }else{
                this.vistaPanel.getClienteDireccion.setForeground(Color.BLACK);
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource().equals(this.vistaPanel.getClienteDNI)) {
            if (this.vistaPanel.getClienteDNI.getText().equals("")) {//DNI
                this.vistaPanel.getClienteDNI.setText("DNI");
                this.vistaPanel.getClienteDNI.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getClienteDNI.setForeground(Color.GRAY);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getClienteNombreApellido)) {//NOMBRES Y APELLIDOS
            if (this.vistaPanel.getClienteNombreApellido.getText().equals("")) {
                this.vistaPanel.getClienteNombreApellido.setText("NOMBRES Y APELLIDOS");
                this.vistaPanel.getClienteNombreApellido.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getClienteNombreApellido.setForeground(Color.GRAY);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getClienteCorreo)) {//CORREO
            if (this.vistaPanel.getClienteCorreo.getText().equals("")) {
                this.vistaPanel.getClienteCorreo.setText("CORREO");
                this.vistaPanel.getClienteCorreo.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getClienteCorreo.setForeground(Color.GRAY);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getClienteCelular)) {//CELULAR
            if (this.vistaPanel.getClienteCelular.getText().equals("")) {
                this.vistaPanel.getClienteCelular.setText("CELULAR");
                this.vistaPanel.getClienteCelular.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getClienteCelular.setForeground(Color.GRAY);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getClienteDireccion)) {//DIRECCION
            if (this.vistaPanel.getClienteDireccion.getText().equals("")) {
                this.vistaPanel.getClienteDireccion.setText("DIRECCION");
                this.vistaPanel.getClienteDireccion.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getClienteDireccion.setForeground(Color.GRAY);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.vistaPanel.getTablaCliente) {
            seleccionar = this.vistaPanel.getTablaCliente.rowAtPoint(e.getPoint());
            if (seleccionar >= 0) {
                this.vistaPanel.getClienteDNI.setText(this.vistaPanel.getTablaCliente.getValueAt(seleccionar, 0).toString());//DNI
                this.vistaPanel.getClienteNombreApellido.setText(this.vistaPanel.getTablaCliente.getValueAt(seleccionar, 1).toString());//NOMBRE Y APPELLIDO
                this.vistaPanel.getClienteCorreo.setText(this.vistaPanel.getTablaCliente.getValueAt(seleccionar, 2).toString());//CORREO
                this.vistaPanel.getClienteCelular.setText(this.vistaPanel.getTablaCliente.getValueAt(seleccionar, 3).toString());//CELULAR
                this.vistaPanel.getClienteDireccion.setText(this.vistaPanel.getTablaCliente.getValueAt(seleccionar, 4).toString());//DIRECCION

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
