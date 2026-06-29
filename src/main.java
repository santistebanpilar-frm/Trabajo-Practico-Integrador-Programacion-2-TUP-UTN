
import dao.CategoriaDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import dao.UsuarioDAO;
import service.CategoriaService;
import service.PedidoService;
import service.ProductoService;
import service.UsuarioService;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pilarsg
 */
public class main {
    public static void main(String[] args) {
        
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ProductoDAO productoDAO   = new ProductoDAO();
        UsuarioDAO usuarioDAO     = new UsuarioDAO();
        PedidoDAO pedidoDAO       = new PedidoDAO();

        CategoriaService categoriaService = new CategoriaService(categoriaDAO);
        ProductoService productoService   = new ProductoService(productoDAO, categoriaService);
        UsuarioService usuarioService     = new UsuarioService(usuarioDAO);
        PedidoService pedidoService       = new PedidoService(pedidoDAO, usuarioService, productoService);
 
        MenuPrincipal menu = new MenuPrincipal(
                categoriaService, productoService, usuarioService, pedidoService
        );
        menu.mostrar();
        
    }
}
