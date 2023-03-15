package Controlador;

import Modelo.UsuarioModel;
import Vista.Login;
import Vista.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelControl implements ActionListener{
    private final Login vistaLogin;
    private final Panel vistaPanel;
    private final UsuarioModel usuarioModel; 
    
    
    //Constructor
    public PanelControl(Login vistaLogin,Panel vistaPanel,UsuarioModel usuarioModel) {
        //Resiviendo variables 
        this.vistaLogin = vistaLogin;
        this.vistaPanel = vistaPanel;
        this.usuarioModel = usuarioModel;  
   }
    
    public void inicio(){
        this.vistaPanel.setTitle("Pet House");//Titulo
        this.vistaPanel.setLocationRelativeTo(null);//Centrar ventana
        this.vistaLogin.setVisible(false);//Hacer invisible el Login
        this.vistaPanel.setVisible(true);//Visible vista Panel
        this.vistaPanel.setResizable(false);//No redimencionar el Panel
        
        //Iniciar Botones (Escuchar Ordenes)
        this.vistaPanel.getBtnOrden.addActionListener(this);//Boton Orden
        this.vistaPanel.getBtnClientes.addActionListener(this);//Boton Clientes
        this.vistaPanel.getBtnProductos.addActionListener(this);//Boton Productos
        this.vistaPanel.getBtnDetalleOrden.addActionListener(this);//Boton Detalle Orden
        this.vistaPanel.getBtnCategorias.addActionListener(this);//Boton Categorias
        this.vistaPanel.getBtnUsuarios.addActionListener(this);//Boton Usuarios
        this.vistaPanel.getBtnSalir.addActionListener(this);//Boton Salir
        
        //Colocar el DNI en la pantalla
        this.vistaPanel.getDNIUsuario.setText(this.usuarioModel.getDni());
        
        if(this.usuarioModel.getDni().equals("12345678")){
            this.vistaPanel.getBtnUsuarios.setEnabled(true);
        }else{
            this.vistaPanel.getBtnUsuarios.setEnabled(false);
        }
    }
    
    //74085725
    
    //Funcionalidad de los botones de Login 
    @Override
    public void actionPerformed(ActionEvent e){
        
        //Boton Panel Orden
        if(e.getSource() == this.vistaPanel.getBtnOrden){
            vistaPanel.getTablaPrincipal.setSelectedIndex(1);
            
            //La clase OrdenControl recibe el objeto vistaPanel
            OrdenControl ordenControl = new OrdenControl(this.vistaPanel);
            ordenControl.inicio();
            
        }
        
        //Boton Panel Clientes
        if(e.getSource() == this.vistaPanel.getBtnClientes){
            vistaPanel.getTablaPrincipal.setSelectedIndex(2);
            
            //La clase ClienteControl recibe el objeto vistaPanel
            ClienteControl clienteControl = new ClienteControl(this.vistaPanel);
            clienteControl.inicio();
        }
        
        //Boton Panel Productos
        if(e.getSource() == this.vistaPanel.getBtnProductos){
            vistaPanel.getTablaPrincipal.setSelectedIndex(3);
            
            //La clase ProductoControl recibe el objeto vistaPanel
            ProductoControl productoControl = new ProductoControl(this.vistaPanel);
            productoControl.inicio();
        }
        
        //Boton Panel Detalle Orden
        if(e.getSource() == this.vistaPanel.getBtnDetalleOrden){
            vistaPanel.getTablaPrincipal.setSelectedIndex(4);
            
            //La clase OrdeDetalle recibe el objeto vistaPanel
            DetalleOrdenControl detalleOrdenControl = new DetalleOrdenControl(this.vistaPanel);
            detalleOrdenControl.inicio();
        }
        
        //Boton Panel Categorias
        if(e.getSource() == this.vistaPanel.getBtnCategorias){
            vistaPanel.getTablaPrincipal.setSelectedIndex(5);
            
            //La clase CategoriaControl recibe el objeto vistaPanel
            CategoriaControl categoriaControl = new CategoriaControl(this.vistaPanel);
            categoriaControl.inicio();
        }
        
        //Boton Panel Usuarios
        if(e.getSource() == this.vistaPanel.getBtnUsuarios){
            vistaPanel.getTablaPrincipal.setSelectedIndex(6);
            
            //La clase UsuarioControl recibe el objeto vistaPanel
            UsuarioControl usuarioControl = new UsuarioControl(this.vistaPanel);
            usuarioControl.inicio();
        }
        
        //Boton Panel Salir
        if(e.getSource() == this.vistaPanel.getBtnSalir){
            this.vistaLogin.setVisible(true);
            this.vistaLogin.getUserLogin.setText("USERNAME");
            this.vistaLogin.getPasswordLogin.setText("CONTRASEÑA");
            this.vistaPanel.dispose();
        }
    }
   
    
}
