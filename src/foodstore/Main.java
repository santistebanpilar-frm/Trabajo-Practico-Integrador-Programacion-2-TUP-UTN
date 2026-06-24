/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore;

import java.sql.Connection;
import foodstore.config.ConexionDB;
import foodstore.dao.CategoriaDao;
import foodstore.dao.DetallePedidoDao;
import foodstore.dao.PedidoDao;
import foodstore.dao.ProductoDao;
import foodstore.dao.UsuarioDao;
import foodstore.service.CategoriaService;
import foodstore.service.DetallePedidoService;
import foodstore.service.PedidoService;
import foodstore.service.ProductoService;
import foodstore.service.UsuarioService;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author usuario
 */
public class Main {
    
    public static void main(String[] args) throws SQLException {
        ConexionDB conexion = new ConexionDB("src/resources/db.properties");
        Connection conn = null;
        
        Scanner sc = new Scanner(System.in);
        try{
            conn = conexion.getConnection();
            
            Consola lector = new Consola(sc);
            
            CategoriaDao categoriaDao = new CategoriaDao(conn);
            ProductoDao productoDao = new ProductoDao(conn);
            UsuarioDao usuarioDao = new UsuarioDao(conn);
            PedidoDao pedidoDao = new PedidoDao(conn);
            DetallePedidoDao detallePedidoDao = new DetallePedidoDao(conn);
            
            CategoriaService categoriaService = new CategoriaService(categoriaDao);
            ProductoService productoService = new ProductoService(categoriaDao, productoDao);
            UsuarioService usuarioService = new UsuarioService(usuarioDao);
            DetallePedidoService detallePedidoService = new DetallePedidoService(productoDao, productoService, detallePedidoDao);
            PedidoService pedidoService = new PedidoService(usuarioDao, detallePedidoService, conn, pedidoDao);
            
            MenuCategoria menuCategoria = new MenuCategoria(categoriaService, lector);
            MenuProducto menuProducto = new MenuProducto(productoService, lector);
            MenuUsuario menuUsuario = new MenuUsuario(usuarioService, lector);
            MenuPedido menuPedido = new MenuPedido(pedidoService, detallePedidoService, lector);
            
            MenuPrincipal  menuPrincipal = new MenuPrincipal(menuProducto, menuCategoria, menuPedido, menuUsuario, lector);
            menuPrincipal.iniciar();
            
        }catch(SQLException e){
            System.out.println("Error de conexion: "+ e.getMessage());
        }finally{
            cerrarRecursos(conn, sc);
        }
       
    }
    private static void cerrarRecursos(Connection conn, Scanner sc){
        try{
            if(conn != null && !conn.isClosed()){
                conn.close();
                System.out.println("Conexion cerrada.");
            }
        }catch (SQLException e){
            System.out.println("Error al cerrar conexion: "+e.getMessage());
        }
        sc.close();
    }
}