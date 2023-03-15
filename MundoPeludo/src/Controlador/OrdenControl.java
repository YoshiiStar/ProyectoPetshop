package Controlador;

import Modelo.OrdenModel;
import Modelo.DetalleOrdenModel;
import Modelo.ProductoModel;
import Reportes.PDF;
import Vista.Panel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class OrdenControl implements ActionListener, FocusListener {

    private final Panel vistaPanel;
    private final OrdenModel ordenModel;
    private final DetalleOrdenModel detalleOrdenModel;
    private final ProductoModel productoModel;
    private final PDF pdf;

    public OrdenControl(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;
        //Iniciar el Modelo Cliente
        this.ordenModel = new OrdenModel();
        //Iniciar el Detalle Modelo Cliente
        this.detalleOrdenModel = new DetalleOrdenModel();

        this.productoModel = new ProductoModel();
        //Iniciar el objeto pdf
        this.pdf = new PDF();
        //Mostrar el titulo de la tabla producto
        this.ordenModel.tituloTabla(this.vistaPanel);// Parametro: VistaPanel
    }

    public void inicio() {
        //Accionar Botones
        this.agregarListenerUnico(this.vistaPanel.getBtnOrdenIDProducto, this);//Buscar ID Producto 
        this.agregarListenerUnico(this.vistaPanel.getBtnOrdenNombreCliente, this);//Buscar Cliente
        this.agregarListenerUnico(this.vistaPanel.getOrdenAgregarPedido, this);//Agregar Pedido
        this.agregarListenerUnico(this.vistaPanel.getBtnOrdenImprimir, this);//Imprimir PDF

        //PlaceHolder
        this.vistaPanel.getOrdenNombreCliente.addFocusListener(this);

        //Desactivar Boton Imprimir
        this.vistaPanel.getBtnOrdenImprimir.setEnabled(false);
        //Desactivar Boton Agregar
        this.vistaPanel.getOrdenAgregarPedido.setEnabled(false);
        //Desactivar Spinner
        this.vistaPanel.getOrdenCantidad.setEnabled(false);
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
        //Boton de Buscar producto 
        if (e.getSource().equals(this.vistaPanel.getBtnOrdenIDProducto)) {
            ListaProductoControl listaProductoControl = new ListaProductoControl(this.vistaPanel);
            listaProductoControl.inicio();
            
        }
        //Boton de Buscar cliente
        if (e.getSource().equals(this.vistaPanel.getBtnOrdenNombreCliente)) {
            ListaClienteControl listaClienteControl = new ListaClienteControl(this.vistaPanel);
            listaClienteControl.inicio();
        }
        //Boton Agregar  PRODUCTO A LA LISTA "ID", "DESCRIPCION", "CANTIDAD", "PRECIO UNIT.", "SUB. TOTAL"
        if (e.getSource().equals(this.vistaPanel.getOrdenAgregarPedido)) {
            if (!this.vistaPanel.getOrdenProductoID.getText().equals("ORD00001")) {//ORD00001
                int valorCantidad = (int) this.vistaPanel.getOrdenCantidad.getValue();
                //Ingresar la cantidad despues de añadir productos
                if (valorCantidad > 0) {
                    if (!this.vistaPanel.getOrdenDniCliente.getText().equals("DNI")) {
                        int valorStock = Integer.parseInt(this.vistaPanel.getOrdenStockProducto.getText());
                        int StockActual = valorStock - valorCantidad;
                        this.productoModel.setStock(StockActual);// Recibimos el Stock actualizado del producto
                        this.productoModel.setId_producto(this.vistaPanel.getOrdenProductoID.getText());// Recibimos el id del producto en la tabla producto
                        this.productoModel.modificarStock();//Actualizamos el stock del producto

                        this.ordenModel.setId_producto(this.vistaPanel.getOrdenProductoID.getText());

                        this.ordenModel.setDescripcion(this.vistaPanel.getOrdenNombreProducto.getText());//set descripcion

                        String cantidadStr = String.valueOf(valorCantidad);
                        this.ordenModel.setCantidad(cantidadStr);//set cantidad

                        this.ordenModel.setPrecio_unitario(this.vistaPanel.getOrdenPrecioProducto.getText());//set precioUnitario
                        double precioUnitarioDouble = Double.parseDouble(this.vistaPanel.getOrdenPrecioProducto.getText());

                        double sub_total = precioUnitarioDouble * valorCantidad;
                        this.ordenModel.setSub_total(String.format("%.2f", sub_total));//total 
                        //Añadir al arraylist
                        this.ordenModel.armandoRegistro();
                        this.ordenModel.actualizarRegistro();
                        //Activar Boton Imprimir
                        this.vistaPanel.getBtnOrdenImprimir.setEnabled(true);

                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, seleccione un cliente");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Stock agotado");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Elija el producto que desea añadir");
            }

        }

        //Boton de PDF
        if (e.getSource().equals(this.vistaPanel.getBtnOrdenImprimir)) {
            //Se genera la orden
            this.ordenModel.generarID();
            //Orden
            //Guardar en Orden (id_orden [set], dni_cliente, dni_usuario, total[set])
            this.ordenModel.setDni_cliente(this.vistaPanel.getOrdenDniCliente.getText());//dni_cliente
            this.ordenModel.setDni_usuario(this.vistaPanel.getDNIUsuario.getText());//dni_usuario
            this.ordenModel.guardarOrden();

            for (String[] fila : this.ordenModel.getRegistroProducto()) {
                this.detalleOrdenModel.setId_orden(this.ordenModel.getId_orden());
                this.detalleOrdenModel.setId_producto(fila[0]);//id_producto
                this.detalleOrdenModel.setCantidad(fila[2]);//Cantidad
                this.detalleOrdenModel.setPrecio_unitario(Double.parseDouble(fila[3].replace(",", ".")));//Precio Unitario
                this.detalleOrdenModel.setSubtotal(Double.parseDouble(fila[4].replace(",", ".")));//Sub Total
                this.detalleOrdenModel.guardarDetalleOrden();
            }

            //PDF setIdOrden this.ordenModel.get
            this.pdf.setDniCliente(this.ordenModel.getDni_cliente());
            this.pdf.setIdOrden(this.ordenModel.getId_orden());
            this.pdf.ImprimirOrden();

            this.ordenModel.eliminarRegistros();
            this.ordenModel.actualizarRegistro();
            this.vistaPanel.getOrdenNombreProducto.setText("");
            this.vistaPanel.getOrdenPrecioProducto.setText("");
            this.vistaPanel.getOrdenStockProducto.setText("");
            this.vistaPanel.getOrdenProductoID.setText("ORD00001");
            this.vistaPanel.getOrdenProductoID.setForeground(Color.GRAY);
            this.vistaPanel.getOrdenDniCliente.setText("DNI");
            this.vistaPanel.getOrdenDniCliente.setForeground(Color.GRAY);
            this.vistaPanel.getOrdenNombreCliente.setText("NOMBRES Y APELLIDOS");
            this.vistaPanel.getOrdenNombreCliente.setForeground(Color.GRAY);
            this.vistaPanel.getOrdenCantidad.setValue(0);
        }

    }

    @Override
    public void focusGained(FocusEvent e) {
        //
    }

    @Override
    public void focusLost(FocusEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
