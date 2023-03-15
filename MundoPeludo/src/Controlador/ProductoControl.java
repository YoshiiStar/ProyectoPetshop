package Controlador;

import Vista.Panel;
import Modelo.ProductoModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

public class ProductoControl implements ActionListener, FocusListener, MouseListener {

    private final Panel vistaPanel;
    private final ProductoModel productoModel;
    private final SpinnerNumberModel spinner;

    private int seleccionar;

    public ProductoControl(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;

        //Objeto Producto Modelo
        productoModel = new ProductoModel();

        //Mostrar el titulo de la tabla producto
        this.productoModel.tituloTabla(this.vistaPanel);// Parametro: VistaPanel

        //Mostrar el registro de la tabla producto
        this.productoModel.registroTabla(this.vistaPanel);

        //Iniciamos el metodo generar ID del producto
        this.productoModel.generarID();

        //Ingresar el id dentro del cuadro getProductoID
        this.vistaPanel.getProductoID.setText(this.productoModel.getId_producto());

        //Colocar el listado en el combobox
        this.productoModel.listarCategorias(this.vistaPanel);

        //Objeto Spinner
        spinner = new SpinnerNumberModel();
        spinner.setMaximum(100);//Establecer un maximo stock
        spinner.setMinimum(0);
        //spinner.setStepSize(spinner);
        this.vistaPanel.getProductoStock.setModel(this.spinner);

    }

    public void inicio() {
        //PlaceHolder
        this.vistaPanel.getProductoNombre.addFocusListener(this);//Nombre
        this.vistaPanel.getProductoPrecio.addFocusListener(this);//Precio
        this.vistaPanel.getProductoStock.addFocusListener(this);//Stock

        //Botones
        agregarListenerUnico(this.vistaPanel.getBtnProductoAgregar, this);//Agregar
        agregarListenerUnico(this.vistaPanel.getBtnProductoBorrar, this);//borrar
        agregarListenerUnico(this.vistaPanel.getBtnProductoNuevo, this);//Nuevo
        agregarListenerUnico(this.vistaPanel.getBtnProductoModificar, this);//Modificar

        //Seleccionar la tabla
        this.vistaPanel.getTablaProducto.addMouseListener(this);
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
        if (e.getSource().equals(this.vistaPanel.getBtnProductoAgregar)) {//Agregar Agregar
            this.productoModel.setId_producto(this.vistaPanel.getProductoID.getText());
            this.productoModel.verificar();//verificamos que no se repita el ID del producto
            if (this.productoModel.getVerify()) {
                JOptionPane.showMessageDialog(null, "El ID del producto ya esta registrado");
            } else {
                this.productoModel.verificarCampos(this.vistaPanel);
                if (this.productoModel.getVerify()) {
                    //Recibimos los datos del producto
                    this.productoModel.setNombre(this.vistaPanel.getProductoNombre.getText());
                    this.productoModel.setPrecio(Double.parseDouble(this.vistaPanel.getProductoPrecio.getText()));
                    this.productoModel.setStock((Integer) this.vistaPanel.getProductoStock.getValue());
                    this.productoModel.SeleccionarCategoria(this.vistaPanel);//Metodo que ayuda a seleccionar la categoria que elegimos

                    //Se agrega a la BD
                    this.productoModel.agregar();
                    this.productoModel.registroTabla(this.vistaPanel);//reiniciar la tabla
                } else {
                    //JOptionPane.showMessageDialog(null,"Ingrese todos los campos por favor!!");
                }
            }

        }

        if (e.getSource().equals(this.vistaPanel.getBtnProductoBorrar)) {//Boton borrar
            //
            if (this.seleccionar > 0) {
                this.productoModel.setId_producto(this.vistaPanel.getProductoID.getText());
                this.productoModel.eliminar();
                //Reiniciar la tabla
                this.productoModel.registroTabla(this.vistaPanel);//Renovar registro de la tabla
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione el Producto que requiere eliminar");
            }
        }

        if (e.getSource().equals(this.vistaPanel.getBtnProductoModificar)) {//Boton Modificar
            //
            if (this.seleccionar >= 0) {
                //recibir datos 
                this.productoModel.setId_producto(this.vistaPanel.getProductoID.getText());
                this.productoModel.setNombre(this.vistaPanel.getProductoNombre.getText());
                this.productoModel.setPrecio(Double.parseDouble(this.vistaPanel.getProductoPrecio.getText()));
                this.productoModel.setStock((Integer) this.vistaPanel.getProductoStock.getValue());
                this.productoModel.SeleccionarCategoria(this.vistaPanel);//Metodo que ayuda a seleccionar la categoria que elegimos

                this.productoModel.modificar();
                this.productoModel.registroTabla(this.vistaPanel);//Renovar registro de la tabla 

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione el cliente que requiere modificar");
            }
        }

        if (e.getSource().equals(this.vistaPanel.getBtnProductoNuevo)) {//Boton Nuevo
            //
            this.productoModel.nuevo(this.vistaPanel);
            this.seleccionar = -1;

            //Activar Generador ID
            this.productoModel.generarID();

            //Ingresar el codigo de categoria
            this.vistaPanel.getProductoID.setText(this.productoModel.getId_producto());
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource().equals(this.vistaPanel.getProductoNombre)) {//NOMBRE
            if (this.vistaPanel.getProductoNombre.getText().equals("NOMBRE")) {
                this.vistaPanel.getProductoNombre.setText("");
                this.vistaPanel.getProductoNombre.setForeground(Color.BLACK);
            } else {
                this.vistaPanel.getProductoNombre.setForeground(Color.BLACK);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getProductoPrecio)) {//PRECIO
            if (this.vistaPanel.getProductoPrecio.getText().equals("PRECIO")) {
                this.vistaPanel.getProductoPrecio.setText("");
                this.vistaPanel.getProductoPrecio.setForeground(Color.BLACK);
            } else {
                this.vistaPanel.getProductoPrecio.setForeground(Color.BLACK);
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource().equals(this.vistaPanel.getProductoNombre)) {//NOMBRE
            if (this.vistaPanel.getProductoNombre.getText().equals("")) {
                this.vistaPanel.getProductoNombre.setText("NOMBRE");
                this.vistaPanel.getProductoNombre.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getProductoNombre.setForeground(Color.GRAY);
            }
        }

        if (e.getSource().equals(this.vistaPanel.getProductoPrecio)) {//PRECIO
            if (this.vistaPanel.getProductoPrecio.getText().equals("")) {
                this.vistaPanel.getProductoPrecio.setText("PRECIO");
                this.vistaPanel.getProductoPrecio.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getProductoPrecio.setForeground(Color.GRAY);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.vistaPanel.getTablaProducto) {
            this.seleccionar = this.vistaPanel.getTablaProducto.rowAtPoint(e.getPoint());
            if (this.seleccionar >= 0) {
                this.vistaPanel.getProductoID.setText(this.vistaPanel.getTablaProducto.getValueAt(this.seleccionar, 0).toString());//ID
                this.vistaPanel.getProductoNombre.setText(this.vistaPanel.getTablaProducto.getValueAt(this.seleccionar, 1).toString());//NOMBRE 
                this.vistaPanel.getProductoPrecio.setText(this.vistaPanel.getTablaProducto.getValueAt(this.seleccionar, 2).toString());//PRECIO
                this.vistaPanel.getProductoStock.setValue(Integer.parseInt(this.vistaPanel.getTablaProducto.getValueAt(this.seleccionar, 3).toString()));//STOCK
                this.vistaPanel.getProductoListaCategoria.setSelectedItem(this.vistaPanel.getTablaProducto.getValueAt(this.seleccionar, 4).toString());//CATEGORIA
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
