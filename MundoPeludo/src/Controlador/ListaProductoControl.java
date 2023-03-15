package Controlador;

import Modelo.ListaProductoModel;
import Vista.ListaProducto;
import Vista.Panel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

public class ListaProductoControl implements ActionListener, MouseListener {

    private ListaProducto vistaListaProducto;
    private ListaProductoModel listaProductoModel;
    private int seleccionar;
    private final Panel vistaPanel;
    private final SpinnerNumberModel spinner;

    public ListaProductoControl(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;
        //Objeto Spinner
        this.spinner = new SpinnerNumberModel();
    }

    public void inicio() {
        //iniciar 
        this.vistaListaProducto = new ListaProducto();
        this.vistaListaProducto.setTitle("Lista de Productos");//Titulo
        this.vistaListaProducto.setLocationRelativeTo(null);//Centrar ventana
        this.vistaListaProducto.setVisible(true);//Visible vista Panel
        this.vistaListaProducto.setResizable(false);//No redimencionar el Panel

        //Objeto Producto Modelo
        listaProductoModel = new ListaProductoModel();
        
        //Mostrar el titulo de la tabla producto
        this.listaProductoModel.tituloTabla(this.vistaListaProducto);// Parametro: VistaPanel

        //Mostrar el registro de la tabla producto
        this.listaProductoModel.registroTabla(this.vistaListaProducto);

        //Boton
        this.agregarListenerUnico(this.vistaListaProducto.getSeleccionarProducto, this);//Seleccionar Producto de la lista

        //Seleccionar la tabla Lista Cliente Control
        this.vistaListaProducto.getTablaListaProducto.addMouseListener(this);
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
        if (e.getSource().equals(this.vistaListaProducto.getSeleccionarProducto)) {
            if (!(this.vistaPanel.getOrdenProductoID.getText().equals("ORD00001"))) {
                this.vistaPanel.getOrdenProductoID.setForeground(Color.red);
                //Activar Boton Agregar
                this.vistaPanel.getOrdenAgregarPedido.setEnabled(true);
                //Activar Spinner
                this.vistaPanel.getOrdenCantidad.setEnabled(true);
                this.spinner.setMaximum(Integer.parseInt(this.vistaPanel.getOrdenStockProducto.getText()));//Establecer un maximo stock
                this.spinner.setMinimum(0);
                this.vistaPanel.getOrdenCantidad.setModel(this.spinner);
                this.vistaListaProducto.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Elegir un producto de la lista!!");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.vistaListaProducto.getTablaListaProducto) {
            seleccionar = this.vistaListaProducto.getTablaListaProducto.rowAtPoint(e.getPoint());
            if (seleccionar >= 0) {
                this.vistaPanel.getOrdenProductoID.setText(this.vistaListaProducto.getTablaListaProducto.getValueAt(seleccionar, 0).toString());//DNI
                this.vistaPanel.getOrdenNombreProducto.setText(this.vistaListaProducto.getTablaListaProducto.getValueAt(seleccionar, 1).toString());//Nombre del producto
                this.vistaPanel.getOrdenPrecioProducto.setText(this.vistaListaProducto.getTablaListaProducto.getValueAt(seleccionar, 2).toString());
                this.vistaPanel.getOrdenStockProducto.setText(this.vistaListaProducto.getTablaListaProducto.getValueAt(seleccionar, 3).toString());
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
