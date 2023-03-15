package Controlador;

import Modelo.ListaDetalleOrdenModel;
import Modelo.DetalleOrdenModel;
import Vista.ListaDetalleOrdenCliente;
import Vista.Panel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ListaDetalleOrdenControl implements ActionListener, MouseListener {

    private ListaDetalleOrdenCliente vistaListaDetalleOrden;
    private ListaDetalleOrdenModel listaDetalleOrdenModel;
    private final DetalleOrdenModel detalleOrdenModel;
    private final Panel vistaPanel;
    private int seleccionar;

    public ListaDetalleOrdenControl(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;

        //Objeto detalleOrdenModel
        this.detalleOrdenModel = new DetalleOrdenModel();

    }

    public void inicio() {
        //iniciar 
        this.vistaListaDetalleOrden = new ListaDetalleOrdenCliente();
        this.vistaListaDetalleOrden.setTitle("Lista de Orden");//Titulo
        this.vistaListaDetalleOrden.setLocationRelativeTo(null);//Centrar ventana
        this.vistaListaDetalleOrden.setVisible(true);//Visible vista Panel
        this.vistaListaDetalleOrden.setResizable(false);//No redimencionar el Panel

        //Objeto Producto Modelo
        this.listaDetalleOrdenModel = new ListaDetalleOrdenModel();

        //Mostrar el titulo de la tabla producto
        this.listaDetalleOrdenModel.tituloTabla(this.vistaListaDetalleOrden);// Parametro: VistaPanel

        //Mostrar el registro de la tabla producto
        this.listaDetalleOrdenModel.registroTabla(this.vistaListaDetalleOrden);

        //Boton
        this.agregarListenerUnico(this.vistaListaDetalleOrden.getSeleccionarDetalleOrden, this);//Seleccionar Producto de la lista

        //Seleccionar la tabla Lista Cliente Control
        this.vistaListaDetalleOrden.getTablaListaDetalleCliente.addMouseListener(this);

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
        if (e.getSource().equals(this.vistaListaDetalleOrden.getSeleccionarDetalleOrden)) {
            if (!(this.vistaPanel.getDetalleOrdenDNI_cliente.getText().equals("DNI"))) {
                this.detalleOrdenModel.setDni_cliente(this.vistaPanel.getDetalleOrdenDNI_cliente.getText());
                this.detalleOrdenModel.tituloTabla(this.vistaPanel);
                this.detalleOrdenModel.registroTabla(this.vistaPanel);
                this.vistaPanel.getBtnDetalleOrdenImprimir.setEnabled(true);//activar
                this.vistaListaDetalleOrden.dispose();
            }else{
                JOptionPane.showMessageDialog(null,"Elegir un cliente!!");
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.vistaListaDetalleOrden.getTablaListaDetalleCliente)) {
            seleccionar = this.vistaListaDetalleOrden.getTablaListaDetalleCliente.rowAtPoint(e.getPoint());
            if (seleccionar >= 0) {
                this.vistaPanel.getDetalleOrdenDNI_cliente.setText(this.vistaListaDetalleOrden.getTablaListaDetalleCliente.getValueAt(seleccionar, 0).toString());
                this.vistaPanel.getDetalleOrdenNombreCompleto_cliente.setText(this.vistaListaDetalleOrden.getTablaListaDetalleCliente.getValueAt(seleccionar, 1).toString());//Agregar el nombre y apellido de la persona
                this.vistaPanel.getDetalleOrdenDNI_cliente.setForeground(Color.RED);
                this.vistaPanel.getDetalleOrdenNombreCompleto_cliente.setForeground(Color.RED);
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
