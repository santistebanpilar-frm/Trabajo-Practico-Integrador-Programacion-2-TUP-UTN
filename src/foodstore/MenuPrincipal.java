/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore;



/**
 *
 * @author usuario
 */
public class MenuPrincipal {
    private MenuProducto menuProductos;
    private MenuCategoria menuCategorias;
    private MenuPedido menuPedido;
    private MenuUsuario menuUsuario;
    private Consola lector;

        public MenuPrincipal(MenuProducto menuProductos, MenuCategoria menuCategorias, MenuPedido menuPedido, MenuUsuario menuUsuario, Consola lector) {
            this.menuProductos = menuProductos;
            this.menuCategorias = menuCategorias;
            this.menuPedido = menuPedido;
            this.menuUsuario = menuUsuario;
            this.lector = lector;
        }
    public void iniciar(){
        boolean salir = false;
        while(!salir){
            System.out.println("==========Menu Principal==========");
            System.out.println("1. Gestion de Categoria");
            System.out.println("2. Gestion de Productos");
            System.out.println("3. Gestion de Pedidos");
            System.out.println("4. Gestion Usuario");
            System.out.println("5. Salir");
            int opcion = lector.leerEntero("Seleccione una opcion: ");
            switch (opcion){
                case 1-> menuCategorias.mostrar();
                case 2-> menuProductos.mostrar();
                case 3-> menuPedido.mostrar();
                case 4-> menuUsuario.mostrar();
                case 5->{
                    salir = true;
                    System.out.println("Apagando Sistemas....");
                }
                default -> System.out.println("Opcion Invalida\n");
            }
        }
    
    }
}
    

