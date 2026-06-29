/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore;

import foodstore.entities.Categoria;
import foodstore.entities.Producto;
import foodstore.service.ProductoService;
import java.util.List;

/**
 *
 * @author usuario
 */
public class MenuProducto {
    private ProductoService productoService;
    private Consola lector;

    public MenuProducto(ProductoService productoService, Consola lector) {
        this.productoService = productoService;
        this.lector = lector;
    }
    
    
    
    public void mostrar() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=====GESTION DE PRODUCTOS=====");
            System.out.println("1. Listar Productos");
            System.out.println("2. Crear Producto");
            System.out.println("3. Editar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("5. Listar Productos por Categoria");
            System.out.println("0. Volver al menu Principal");
            int opcion = lector.leerEntero("Seleccione opción: ");
            
            try {
                switch (opcion) {
                    case 1 -> listar();
                    case 2 -> crear();
                    case 3 -> editar();
                    case 4 -> eliminar();
                    case 5-> listarPorCategoria();
                    case 0 -> volver = true;
                    default -> System.out.println("Opción invalida.\n");
                    
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }
    
    private void crear() throws Exception {
        Producto nuevo = new Producto();
        nuevo.setNombre(lector.leerTexto("Nombre: "));
        nuevo.setDescripcion(lector.leerTexto("Descripcion: "));
        nuevo.setPrecio(lector.leerDouble("Precio: "));
        nuevo.setStock(lector.leerEntero("Stock: "));
        nuevo.setImagen(lector.leerTexto("Imagen: "));
        int disponibleInt = lector.leerEntero("Disponible (1=Sí, 0=No): ");
        nuevo.setDisponible(disponibleInt == 1);
        Categoria categoria = new Categoria();
        categoria.setId((long) lector.leerEntero("ID Categoria: "));
        nuevo.setCategoria(categoria);
        
        productoService.crear(nuevo);
        System.out.println("Producto creado exitosamente.\n");
    }
    
    private void listar() throws Exception {
        List<Producto> productos = productoService.listar();
        System.out.println("\n=====LISTA DE PRODUCTOS=====");
        if(productos.isEmpty()){
            System.out.println("No hay productos cargados. \n");
            return;
        }
        productos.forEach(p -> System.out.printf(
            "ID: %d | %s | $%.2f | Stock: %d | Cat: %d%n",
            p.getId(), p.getNombre(), p.getPrecio(), p.getStock(), p.getCategoria().getId()
        ));
    }
    private void listarPorCategoria() throws Exception {
    long idCategoria = lector.leerEntero("ID de la categoría: ");
    List<Producto> productos = productoService.listarPorCategoria(idCategoria);
    System.out.println("\n=====PRODUCTOS DE LA CATEGORÍA " + idCategoria + "=====");
    if (productos.isEmpty()) {
        System.out.println("No hay productos para esta categoría.\n");
        return;
    }
    productos.forEach(p -> System.out.printf(
        "ID: %d | %s | $%.2f | Stock: %d | Cat: %d%n",
        p.getId(), p.getNombre(), p.getPrecio(), p.getStock(), p.getCategoria().getId()
    ));
}
    private void editar() throws Exception {
        long id = lector.leerEntero("ID del producto a editar: ");
        Producto actual = productoService.leer(id).orElseThrow(
            () -> new Exception("Producto no encontrado")
        );

        actual.setNombre(lector.leerTexto("Nuevo nombre (" + actual.getNombre() + "): "));
        actual.setDescripcion(lector.leerTexto("Nueva descripcion (" + actual.getDescripcion() + "): "));
        actual.setPrecio(lector.leerDouble("Nuevo precio (" + actual.getPrecio() + "): "));
        actual.setStock(lector.leerEntero("Nuevo stock (" + actual.getStock() + "): "));
        actual.setImagen(lector.leerTexto("Nueva imagen (" + actual.getImagen() + "): "));
        int disponibleInt = lector.leerEntero("Disponible (1=Sí, 0=No): ");
        actual.setDisponible(disponibleInt == 1);
        Categoria cat = new Categoria();
        cat.setId((long) lector.leerEntero("Nueva categoría ID (" + actual.getCategoria().getId() + "): "));
        actual.setCategoria(cat);

        productoService.actualizar(actual);
        System.out.println("Producto actualizado.\n");
    }
    private void eliminar() throws Exception {
        long id = lector.leerEntero("ID del producto a eliminar: ");
        productoService.eliminar(id);
        System.out.println("Producto eliminado.\n");
    }

}
