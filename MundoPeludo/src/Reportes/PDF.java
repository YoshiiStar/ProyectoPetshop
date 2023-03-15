package Reportes;

import Modelo.ClienteModel;
import Modelo.DetalleOrdenModel;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PDF {

    //Cliente Model
    private final ClienteModel clienteModel;
    private final DetalleOrdenModel detalleOrdenModel;

    //Variables con setter y getters (cliente)
    private String dniCliente;// set DNI importante set               
    private String nombreCompleto;  // GET NOMBRES Y APELLIDOS 
    private String celular;        // CELULAR
    private String direccion;        // DIRECCION 

    //Variables con setter y getters (Productos)
    private String idOrden;//set_id_orden -----
    private String cantidad; // getcantidad                
    private String descripcion; // getdescripcion 
    private String precioUnitario;  // getprecioUnitario  
    private String subTotal;   // getSubtotal 
    private String total;
    private int id_detalleOrden;

    //Variables sin setter y getters
    FileOutputStream archivo;
    File file;
    Document documento;
    Paragraph texto;
    Image image;
    Font fuenteTitulo;

    LocalDateTime ahora = LocalDateTime.now();
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String fechaFormateada = ahora.format(formato);

    public PDF() {
        this.documento = new Document();
        this.fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLDITALIC, BaseColor.BLUE);
        this.clienteModel = new ClienteModel();
        this.detalleOrdenModel = new DetalleOrdenModel();
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(String precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getId_detalleOrden() {
        return id_detalleOrden;
    }

    public void setId_detalleOrden(int id_detalleOrden) {
        this.id_detalleOrden = id_detalleOrden;
    }

    //
    public void ImprimirOrden() {
        try {
            this.archivo = new FileOutputStream("src/PDF/OrdenN°" + this.getIdOrden() + ".pdf");//"src/pdf/Orden"+getNumeroOrden+".pdf"
            /*Esto guarda el archivo en el directorio donde se ejecuta tu programa, con el nombre del director más la extensión .pdf.*/
            PdfWriter.getInstance(this.documento, this.archivo);
            /*
            this.documento es una instancia de la clase Document (es decir, representa el contenido del PDF).
            this.archivo es probablemente un FileOutputStream, que representa el archivo físico donde se guardará el PDF.
             */
            this.documento.open();

            /*
            0 → Element.ALIGN_LEFT (alineado a la izquierda)

            1 → Element.ALIGN_CENTER (centrado)

            2 → Element.ALIGN_RIGHT (alineado a la derecha)

            3 → Element.ALIGN_JUSTIFIED (justificado)

            4 → Element.ALIGN_JUSTIFIED_ALL (justificado completamente)
             */
            this.image = null;
            try {
                this.image = Image.getInstance("src/Img/intro-peludo.png");//carga imagen
                this.image.scaleAbsolute(150, 100);//cambia tamaño de la imagen
                this.image.setAlignment(2);//Alinea a la derecha
                /*
                Image.ALIGN_LEFT	0	Alineado a la izquierda
                Image.ALIGN_CENTER	1	Centrado
                Image.ALIGN_RIGHT	2	Alineado a la derecha
                Image.ALIGN_UNDEFINED	-1	Sin alineación definida
                Image.TEXTWRAP	4	Texto se ajusta alrededor de la imagen
                Image.UNDERLYING	8	Imagen debajo del texto
                Image.ALIGN_BASELINE	32	Alineado con la línea base
                 */

            } catch (BadElementException | IOException e) {
                System.out.println("Error: " + e);
            }

            this.documento.add(this.image);//agrega la imagen al documento

            this.texto = new Paragraph("PetHouse", this.fuenteTitulo);
            this.texto.setAlignment(1);
            this.documento.add(this.texto);//Añadir el titulo

            this.documento.add(new Paragraph("Fecha: " + this.fechaFormateada));
            this.documento.add(new Paragraph("Datos del Cliente: "));

            this.documento.add(Chunk.NEWLINE);

            /*CREAR TABLA CLIENTE*/
            PdfPTable tablaCliente = new PdfPTable(5);//Crea una nueva tabla PDF con 4 columnas.
            tablaCliente.setWidthPercentage(100);//Ajusta el ancho de la tabla al 100% del ancho de la página (dentro de los márgenes).

            PdfPCell dni = new PdfPCell(new Phrase("Dni"));
            dni.setBackgroundColor(BaseColor.BLUE);//Le asigna un color de fondo naranja (ORANGE) a la celda name.

            PdfPCell nombresApellidos = new PdfPCell(new Phrase("Nombres y Apellidos"));
            nombresApellidos.setBackgroundColor(BaseColor.BLUE);

            PdfPCell correo = new PdfPCell(new Phrase("Correo"));
            correo.setBackgroundColor(BaseColor.BLUE);

            PdfPCell celularCliente = new PdfPCell(new Phrase("Celular"));
            celularCliente.setBackgroundColor(BaseColor.BLUE);

            PdfPCell direccionCliente = new PdfPCell(new Phrase("Dirección"));
            direccionCliente .setBackgroundColor(BaseColor.BLUE);

            tablaCliente.addCell(dni);
            tablaCliente.addCell(nombresApellidos);
            tablaCliente.addCell(correo);
            tablaCliente.addCell(celularCliente);
            tablaCliente.addCell(direccionCliente );

            this.clienteModel.setDni(this.getDniCliente());//Pasar el Dni a la setCliente
            //Agregar datos del cliente, pasamos parametros this.document y tablacliente
            this.clienteModel.ImprimirCliente(this.documento, tablaCliente);
            this.documento.add(Chunk.NEWLINE);
            /*-------------------------------------------------------------------------------------------------*/


            /*CREAR TABLA CLIENTE*/
            PdfPTable tablaProductos = new PdfPTable(4);//Crea una nueva tabla PDF con 4 columnas.
            tablaProductos.setWidthPercentage(100);//Ajusta el ancho de la tabla al 100% del ancho de la página (dentro de los márgenes).

            PdfPCell cantidadProducto = new PdfPCell(new Phrase("Cantidad"));
            cantidadProducto.setBackgroundColor(BaseColor.BLUE);//Le asigna un color de fondo naranja (ORANGE) a la celda name.

            PdfPCell descripcionProducto = new PdfPCell(new Phrase("Descripción"));
            descripcionProducto.setBackgroundColor(BaseColor.BLUE);

            PdfPCell precioUnitarioProducto = new PdfPCell(new Phrase("Precio Unitario"));
            precioUnitarioProducto.setBackgroundColor(BaseColor.BLUE);

            PdfPCell subtotal = new PdfPCell(new Phrase("Subtotal"));
            subtotal.setBackgroundColor(BaseColor.BLUE);

            tablaProductos.addCell(cantidadProducto);
            tablaProductos.addCell(descripcionProducto);
            tablaProductos.addCell(precioUnitarioProducto);
            tablaProductos.addCell(subtotal);

            this.detalleOrdenModel.setId_orden(this.getIdOrden());//Pasar el id_orden a detalleOrden
            //Agregar datos del Productos, pasamos parametros this.document y tablacliente
            this.detalleOrdenModel.ImprimirProductos(this.documento, tablaProductos);

            this.documento.add(Chunk.NEWLINE);
            /*-------------------------------------------------------------------------------------------------*/
            this.detalleOrdenModel.SumarSubtotalOrden();
            this.texto = new Paragraph("Total: " + this.detalleOrdenModel.getTotal());
            this.texto.setAlignment(2);//Alinear a la derecha
            this.documento.add(this.texto);//Agregar el total

            this.documento.add(Chunk.NEWLINE);

            this.texto = new Paragraph("_______________________________________");
            this.texto.setAlignment(1);//Centrar el texto
            this.documento.add(this.texto);//Agregar los puntos seguidos

            this.texto = new Paragraph("Firma");
            this.texto.setAlignment(1);//Centrar el texto
            this.documento.add(this.texto);//Agregar el texto firma al centro

            this.texto = new Paragraph("Gracias por la Compra!!!");
            this.texto.setAlignment(1);//Centrar el texto
            this.documento.add(this.texto);//Agregar el texto firma al centro

            this.documento.close();
            JOptionPane.showMessageDialog(null, "Se ha impreso correctamente la orden de id:" + this.getIdOrden());
        } catch (FileNotFoundException | DocumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public void ImprimirDetalleOrden() {
        try {
            this.archivo = new FileOutputStream("src/PDF/DetalleOrden_Dni"+this.getDniCliente()+".pdf");//"src/pdf/Orden"+getNumeroOrden+".pdf"
            /*Esto guarda el archivo en el directorio donde se ejecuta tu programa, con el nombre del director más la extensión .pdf.*/
            PdfWriter.getInstance(this.documento, this.archivo);
            this.documento.open();

            /*
            0 → Element.ALIGN_LEFT (alineado a la izquierda)

            1 → Element.ALIGN_CENTER (centrado)

            2 → Element.ALIGN_RIGHT (alineado a la derecha)

            3 → Element.ALIGN_JUSTIFIED (justificado)

            4 → Element.ALIGN_JUSTIFIED_ALL (justificado completamente)
             */
            this.image = null;
            try {
                this.image = Image.getInstance("src/Img/intro-peludo.png");//carga imagen
                this.image.scaleAbsolute(150, 100);//cambia tamaño de la imagen
                this.image.setAlignment(2);//Alinea a la derecha
                /*
                Image.ALIGN_LEFT	0	Alineado a la izquierda
                Image.ALIGN_CENTER	1	Centrado
                Image.ALIGN_RIGHT	2	Alineado a la derecha
                Image.ALIGN_UNDEFINED	-1	Sin alineación definida
                Image.TEXTWRAP	4	Texto se ajusta alrededor de la imagen
                Image.UNDERLYING	8	Imagen debajo del texto
                Image.ALIGN_BASELINE	32	Alineado con la línea base
                 */

            } catch (BadElementException | IOException e) {
                System.out.println("Error: " + e);
            }

            this.documento.add(this.image);//agrega la imagen al documento

            this.texto = new Paragraph("Mundo Peludo", this.fuenteTitulo);
            this.texto.setAlignment(1);
            this.documento.add(this.texto);//Añadir el titulo
            
            //colocar en una misma linea dos palabras
            Paragraph fechaActual = new Paragraph("Fecha: " + this.fechaFormateada);
            this.documento.add(fechaActual);
            this.documento.add(Chunk.NEWLINE);

            /*CREAR TABLA CLIENTE*/
            PdfPTable tablaCliente = new PdfPTable(5);//Crea una nueva tabla PDF con 4 columnas.
            tablaCliente.setWidthPercentage(100);//Ajusta el ancho de la tabla al 100% del ancho de la página (dentro de los márgenes).

            PdfPCell dni = new PdfPCell(new Phrase("Dni"));
            dni.setBackgroundColor(BaseColor.BLUE);//Le asigna un color de fondo naranja (ORANGE) a la celda name.

            PdfPCell nombresApellidos = new PdfPCell(new Phrase("Nombres y Apellidos"));
            nombresApellidos.setBackgroundColor(BaseColor.BLUE);

            PdfPCell correo = new PdfPCell(new Phrase("Correo"));
            correo.setBackgroundColor(BaseColor.BLUE);

            PdfPCell celularCliente = new PdfPCell(new Phrase("Celular"));
            celularCliente.setBackgroundColor(BaseColor.BLUE);

            PdfPCell direccionCliente = new PdfPCell(new Phrase("Dirección"));
            direccionCliente .setBackgroundColor(BaseColor.BLUE);

            tablaCliente.addCell(dni);
            tablaCliente.addCell(nombresApellidos);
            tablaCliente.addCell(correo);
            tablaCliente.addCell(celularCliente);
            tablaCliente.addCell(direccionCliente );

            this.clienteModel.setDni(this.getDniCliente());//Pasar el Dni a la setCliente
            //Agregar datos del cliente, pasamos parametros this.document y tablacliente
            this.clienteModel.ImprimirCliente(this.documento, tablaCliente);
            this.documento.add(Chunk.NEWLINE);
            /*-------------------------------------------------------------------------------------------------*/


             /*Tabla detalles*/
            PdfPTable tablaProductos = new PdfPTable(5);//"ID ORDEN", "FECHA", "PRODUCTO", "CANTIDAD", "PRECIO"
            tablaProductos.setWidthPercentage(100);//Ajusta el ancho de la tabla al 100% del ancho de la página (dentro de los márgenes).

            PdfPCell id_orden = new PdfPCell(new Phrase("ID ORDEN"));
            id_orden.setBackgroundColor(BaseColor.BLUE);//Le asigna un color de fondo naranja (ORANGE) a la celda name.

            PdfPCell fecha = new PdfPCell(new Phrase("FECHA"));
            fecha.setBackgroundColor(BaseColor.BLUE);

            PdfPCell producto = new PdfPCell(new Phrase("PRODUCTO"));
            producto.setBackgroundColor(BaseColor.BLUE);

            PdfPCell cantidadProducto = new PdfPCell(new Phrase("CANTIDAD"));
            cantidadProducto.setBackgroundColor(BaseColor.BLUE);
            
            PdfPCell precio = new PdfPCell(new Phrase("PRECIO"));
            precio.setBackgroundColor(BaseColor.BLUE);

            tablaProductos.addCell(id_orden);
            tablaProductos.addCell(fecha);
            tablaProductos.addCell(producto);
            tablaProductos.addCell(cantidadProducto);
            tablaProductos.addCell(precio);

            this.detalleOrdenModel.setDni_cliente(this.getDniCliente());//Pasar el id_orden a detalleOrden
            //Agregar datos del Productos, pasamos parametros this.document y tablacliente
            this.detalleOrdenModel.ImprimirDetalleOrden(this.documento, tablaProductos);

            this.documento.add(Chunk.NEWLINE);
            /*-------------------------------------------------------------------------------------------------*/

            this.documento.close();
            JOptionPane.showMessageDialog(null, "Se ha impreso correctamente");
        } catch (FileNotFoundException | DocumentException e) {
            System.err.println(e.getMessage());
        }
    }

}
