package Controlador;

import Modelo.CategoriaModel;
import Modelo.ProductoModel;
import Vista.Panel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class CategoriaControl implements ActionListener, FocusListener, MouseListener {

    private final Panel vistaPanel;
    private final CategoriaModel categoriaModel;
    private final ProductoModel productoModel;

    private int seleccionar;

    public CategoriaControl(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;

        //Iniciar el Modelo Cliente
        this.categoriaModel = new CategoriaModel();
        
        //Iniciar el Modelo Producto
        this.productoModel = new ProductoModel();

        //Activar Generador ID
        this.categoriaModel.generarID();

        //Ingresar el codigo de categoria
        this.vistaPanel.getCategoriaID.setText(this.categoriaModel.getId_categoria());

        //Mostrar el titulo de la tabla cliente
        this.categoriaModel.tituloTabla(this.vistaPanel);// Parametro: VistaPanel

        //Mostrar el registro de la tabla cliente
        this.categoriaModel.registroTabla(this.vistaPanel);
    }

    public void inicio() {
        //Accionar Botones
        this.agregarListenerUnico(this.vistaPanel.getBtnCategoriaAgregar, this);//Agregar 
        this.agregarListenerUnico(this.vistaPanel.getBtnCategoriaBorrar, this);//Borrar
        this.agregarListenerUnico(this.vistaPanel.getBtnCategoriaNuevo, this);//Nuevo
        this.agregarListenerUnico(this.vistaPanel.getBtnCategoriaModificar, this);//Modificar

        //PlaceHolder
        this.vistaPanel.getCategoriaNombre.addFocusListener(this);

        //Seleccionar la tabla
        this.vistaPanel.getTablaCategoria.addMouseListener(this);
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
        if (e.getSource().equals(this.vistaPanel.getBtnCategoriaAgregar)) {//Boton Agregar
            this.categoriaModel.setId_categoria(this.vistaPanel.getCategoriaID.getText());//ID
            this.categoriaModel.setNombre_categoria(this.vistaPanel.getCategoriaNombre.getText());//Nombre_categoria
            if (!this.categoriaModel.getNombre_categoria().equals("NOMBRE")) {
                this.categoriaModel.verificar();
                if (this.categoriaModel.getVerify()) {
                    JOptionPane.showMessageDialog(null, "El ID ya esta registrado");
                } else {
                    this.categoriaModel.agregar();
                    this.categoriaModel.registroTabla(this.vistaPanel);//Renovar registro de la tabla
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor,ingrese todos los datos");
            }
        }

        if (e.getSource().equals(this.vistaPanel.getBtnCategoriaBorrar)) {//Boton Borrar
            if (this.seleccionar > 0) {
                this.productoModel.setId_categoria(this.vistaPanel.getCategoriaID.getText());
                //Verificar si hay producto en esa categoria
                this.productoModel.ProductosCategorias();
                if (this.productoModel.getVerify()) {
                    this.categoriaModel.setId_categoria(this.vistaPanel.getCategoriaID.getText());
                    this.categoriaModel.eliminar();
                    //Reiniciar la tabla
                    this.categoriaModel.registroTabla(this.vistaPanel);//Renovar registro de la tabla
                }else{
                    JOptionPane.showMessageDialog(null, "Elimine los productos de la categoria seleccionada");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione el Categoria que requiere eliminar");
            }
        }

        
        if (e.getSource().equals(this.vistaPanel.getBtnCategoriaNuevo)) {//Boton Nuevo
            this.categoriaModel.nuevo(this.vistaPanel);
            this.seleccionar = -1;

            //Activar Generador ID
            this.categoriaModel.generarID();

            //Ingresar el codigo de categoria
            this.vistaPanel.getCategoriaID.setText(this.categoriaModel.getId_categoria());
        }

        if (e.getSource().equals(this.vistaPanel.getBtnCategoriaModificar)) {//Boton Modificar
            if (this.seleccionar >= 0) {
                this.categoriaModel.setId_categoria(this.vistaPanel.getCategoriaID.getText());//ID
                this.categoriaModel.setNombre_categoria(this.vistaPanel.getCategoriaNombre.getText());//Nombre_categoria

                this.categoriaModel.modificar();
                this.categoriaModel.registroTabla(this.vistaPanel);//Renovar registro de la tabla 

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione el cliente que requiere modificar");
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource().equals(this.vistaPanel.getCategoriaNombre)) {//NOMBRE
            if (this.vistaPanel.getCategoriaNombre.getText().equals("NOMBRE")) {
                this.vistaPanel.getCategoriaNombre.setText("");
                this.vistaPanel.getCategoriaNombre.setForeground(Color.BLACK);
            } else {
                this.vistaPanel.getCategoriaNombre.setForeground(Color.BLACK);
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource().equals(this.vistaPanel.getCategoriaNombre)) {//NOMBRE
            if (this.vistaPanel.getCategoriaNombre.getText().equals("")) {
                this.vistaPanel.getCategoriaNombre.setText("NOMBRE");
                this.vistaPanel.getCategoriaNombre.setForeground(Color.GRAY);
            } else {
                this.vistaPanel.getCategoriaNombre.setForeground(Color.GRAY);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.vistaPanel.getTablaCategoria) {
            this.seleccionar = this.vistaPanel.getTablaCategoria.rowAtPoint(e.getPoint());
            if (this.seleccionar >= 0) {
                this.vistaPanel.getCategoriaID.setText(this.vistaPanel.getTablaCategoria.getValueAt(this.seleccionar, 0).toString());//ID
                this.vistaPanel.getCategoriaNombre.setText(this.vistaPanel.getTablaCategoria.getValueAt(this.seleccionar, 1).toString());//NOMBRE 
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
