package Controlador;

import Modelo.ListaClienteModel;
import Vista.ListaCliente;
import Vista.Panel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ListaClienteControl implements ActionListener, MouseListener {

    private ListaCliente vistaListaCliente;
    private ListaClienteModel listaClienteModel;
    private int seleccionar;
    private final Panel vistaPanel;

    public ListaClienteControl(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;
    }

    public void inicio() {
        //iniciar 
        this.vistaListaCliente = new ListaCliente();
        this.vistaListaCliente.setTitle("Lista de Clientes");//Titulo
        this.vistaListaCliente.setLocationRelativeTo(null);//Centrar ventana
        this.vistaListaCliente.setVisible(true);//Visible vista Panel
        this.vistaListaCliente.setResizable(false);//No redimencionar el Panel

        //Objeto Producto Modelo
        listaClienteModel = new ListaClienteModel();

        //Mostrar el titulo de la tabla producto
        this.listaClienteModel.tituloTabla(this.vistaListaCliente);// Parametro: VistaPanel

        //Mostrar el registro de la tabla producto
        this.listaClienteModel.registroTabla(this.vistaListaCliente);

        //Boton
        this.agregarListenerUnico(this.vistaListaCliente.getSeleccionarCliente, this);//Seleccionar el cliente en la lista

        //Seleccionar la tabla Lista Cliente Control
        this.vistaListaCliente.getTablaListaCliente.addMouseListener(this);
    }

    private void agregarListenerUnico(JButton boton, ActionListener listener) {
        // Remueve cualquier ActionListener previamente asignado
        for (ActionListener al : boton.getActionListeners()) {
            boton.removeActionListener(al);
        }
        // Añade el nuevo ActionListener
        boton.addActionListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vistaListaCliente.getSeleccionarCliente)) {
            if (!(this.vistaPanel.getOrdenDniCliente.getText().equals("DNI"))) {
                this.vistaPanel.getOrdenDniCliente.setForeground(Color.RED);
                this.vistaPanel.getOrdenNombreCliente.setForeground(Color.RED);
                this.vistaListaCliente.dispose();
            }else{
                JOptionPane.showMessageDialog(null,"Elegir un cliente!!");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.vistaListaCliente.getTablaListaCliente)) {
            seleccionar = this.vistaListaCliente.getTablaListaCliente.rowAtPoint(e.getPoint());
            if (seleccionar >= 0) {
                this.vistaPanel.getOrdenDniCliente.setText(this.vistaListaCliente.getTablaListaCliente.getValueAt(seleccionar, 0).toString());
                this.vistaPanel.getOrdenNombreCliente.setText(this.vistaListaCliente.getTablaListaCliente.getValueAt(seleccionar, 1).toString());//Agregar el nombre y apellido de la persona
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
