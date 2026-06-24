/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore;

import foodstore.entities.Categoria;
import foodstore.service.CategoriaService;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author usuario
 */
public class MenuCategoria {
    public class MenuCategorias {
    private CategoriaService categoriaService;
    private Consola lector;

        public MenuCategorias(CategoriaService categoriaService, Consola lector) {
            this.categoriaService = categoriaService;
            this.lector = lector;
        }

    
    
    public void mostrar(){
        boolean volver = false;
        while(!volver){
            lector.limpiarPantalla();
            System.out.println("========Gestion de Categorias========");
            System.out.println("1. Listar Categorias");
            System.out.println("2. Crear Categoria");
            System.out.println("3. Editar Categoria");
            System.out.println("4. Eliminar Categoria");
            System.out.println("0. Volver al menu Principal");
            int opcion = lector.leerEntero("Seleccione una opcion: ");
            try{
                switch(opcion){
                    case 1->  listar();
                    case 2 -> crear();
                    case 3-> editar();
                    case 4-> eliminar();
                    case 0-> volver = true;
                    default-> System.out.println("Opcion invalida. \n");
                }
            }catch(Exception e){
                System.out.println("Error: "+ e.getMessage()+ "\n");
            }
        }
    }
    private void crear() throws Exception{
        Categoria nueva = new Categoria();
        nueva.setNombre(lector.leerTexto("Nombre de la Categoria: "));
        nueva.setDescripcion(lector.leerTexto("Descripcion: "));
        categoriaService.crear(nueva);
        System.out.println("Categoria creada exitosamente. \n");
    }
    
    private void listar()throws Exception{
        List<Categoria> categorias = categoriaService.listar();
        System.out.println("\n=====Lista de Categorias=====");
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías cargadas.\n");
            return;
        }
        categorias.forEach(c -> System.out.printf(
            "ID: %d | %s | %s%n",
            c.getId(), c.getNombre(), c.getDescripcion()
        ));
        }
    private void editar() throws Exception {
        long id = lector.leerEntero("ID de la categoría a editar: ");
        Categoria actual = categoriaService.leer(id).orElseThrow(
            () -> new Exception("Categoría no encontrada")
        );

        actual.setNombre(lector.leerTexto("Nuevo nombre (" + actual.getNombre() + "): "));
        actual.setDescripcion(lector.leerTexto("Nueva descripción (" + actual.getDescripcion() + "): "));

        categoriaService.actualizar(actual);
        System.out.println("Categoría actualizada.\n");
    }

    private void eliminar() throws Exception {
        long id = lector.leerEntero("ID de la categoría a eliminar: ");
        categoriaService.eliminar(id);
        System.out.println("Categoría eliminada.\n");
    }
    
    }
}
