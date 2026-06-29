/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Scanner;
import service.CategoriaService;
import service.PedidoService;
import service.ProductoService;
import service.UsuarioService;
        
/**
 *
 * @author pilarsg
 */
public class MenuPrincipal {
    private final Scanner scan = new Scanner(System.in);
    
    private final MenuCategorias menuCategorias;
    private final MenuProductos menuProductos;
    private final MenuUsuarios menuUsuarios;
    private final MenuPedidos menuPedidos;

    public MenuPrincipal(CategoriaService categoriaService, ProductoService productoService, UsuarioService usuarioService, PedidoService pedidoService) {
        this.menuCategorias = new MenuCategorias(categoriaService, scan);
        this.menuProductos = new MenuProductos(productoService, categoriaService, scan);
        this.menuUsuarios = new MenuUsuarios(usuarioService, scan);
        this.menuPedidos = new MenuPedidos(pedidoService, usuarioService, scan);
    }
    
    public void mostrar(){
        boolean salir = false;
        while(!salir){
            System.out.println("SISTEMA DE PEDIDOS - FOODSTORE");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            
            switch(Consola.leerOpcionMenu(scan)){
                case 1 -> menuCategorias.mostrar();
                case 2 -> menuProductos.mostrar();
                case 3 -> menuUsuarios.mostrar();
                case 4 -> menuPedidos.mostrar();
                case 0 -> {
                    try (scan) {
                        salir = true;
                        System.out.println("Saliendo del sistema....");
                    }
                }

                default -> System.out.println("Opcion invalida!! Elija entre 1 y 4");
            }
            
        }
    }
}
