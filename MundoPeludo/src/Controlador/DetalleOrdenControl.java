package Controlador;

import Modelo.DetalleOrdenModel;
import Vista.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import Reportes.PDF;
import java.awt.Color;

public class DetalleOrdenControl implements ActionListener {

    private final Panel vistaPanel;
    private final DetalleOrdenModel detalleOrdenModel;
    private final PDF pdf;

    public DetalleOrdenControl(Panel vistaPanel) {
        this.vistaPanel = vistaPanel;
        //Iniciar el Modelo Cliente
        this.detalleOrdenModel = new DetalleOrdenModel();
        //Mostrar el titulo de la tabla producto
        this.detalleOrdenModel.tituloTabla(this.vistaPanel);// titulo de la tabla

        //Iniciar el objeto pdf
        this.pdf = new PDF();

    }

    public void inicio() {
        //Accionar Botones
        this.agregarListenerUnico(this.vistaPanel.getBtnDetalleOrdenIDOrden, this);//Buscar ID Producto 
        this.agregarListenerUnico(this.vistaPanel.getBtnDetalleOrdenImprimir, this);//Imprimir PDF
        this.vistaPanel.getBtnDetalleOrdenImprimir.setEnabled(false);//Desactivar
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
        if (e.getSource().equals(this.vistaPanel.getBtnDetalleOrdenIDOrden)) {
            ListaDetalleOrdenControl listaDetalleOrdenControl = new ListaDetalleOrdenControl(this.vistaPanel);
            listaDetalleOrdenControl.inicio();
        }

        if (e.getSource().equals(this.vistaPanel.getBtnDetalleOrdenImprimir)) {
            this.pdf.setDniCliente(this.vistaPanel.getDetalleOrdenDNI_cliente.getText());
            this.pdf.ImprimirDetalleOrden();

            this.vistaPanel.getDetalleOrdenDNI_cliente.setText("DNI");
            this.vistaPanel.getDetalleOrdenDNI_cliente.setForeground(Color.GRAY);
            this.vistaPanel.getDetalleOrdenNombreCompleto_cliente.setText("NOMBRE Y APELLIDO");
            this.vistaPanel.getDetalleOrdenNombreCompleto_cliente.setForeground(Color.GRAY);
            this.vistaPanel.getBtnDetalleOrdenImprimir.setEnabled(false);//Desactivar Imprimir
            this.detalleOrdenModel.registroTabla(this.vistaPanel);
        }
    }
}
