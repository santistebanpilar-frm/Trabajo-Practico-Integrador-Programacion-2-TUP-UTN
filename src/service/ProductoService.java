/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import dao.ProductoDAO;
import entities.Categoria;
import entities.Producto;
import exception.EntityNotFoundException;
import java.util.List;

/**
 *
 * @author pilarsg
 */
public class ProductoService extends GenericService<Producto> {
    
     private final ProductoDAO productoDAO;
     private final CategoriaService categoriaService;
    
    public ProductoService(ProductoDAO productoDAO, CategoriaService categoriaService) {
        super(productoDAO);
        this.productoDAO = productoDAO;
        this.categoriaService = categoriaService;
    }
    
    @Override
    public Producto buscarPorId(Long id){
        Producto prod = productoDAO.buscarPorId(id);
        if (prod == null || Boolean.TRUE.equals(prod.isEliminado())){
            throw new EntityNotFoundException("NO EXISTE EL PRODUCTO CON ID"+id);
        }
        return prod;
    }
    
    public Producto crear(String nombre, String descripcion,Double precio, Integer stock, String imagen, Long categoriId){
            validarNombre(nombre);
            validarPrecio(precio);
            validarStock(stock);
            
            Categoria categoria = categoriaService.buscarPorId(categoriId);
            
            Producto producto = new Producto(nombre,precio,descripcion,stock,imagen,categoria);
            return productoDAO.crear(producto);
    }
    
    public List<Producto> listarPorCategoria(Long categoriId){
        categoriaService.buscarPorId(categoriId);
        return productoDAO.listarPorCategoria(categoriId);
    }
    
    public boolean actulizar(Long id, String nuevoNombre, String nuevaDescripcion, Double nuevoPrecio, Integer nuevoStock, String nuevaImagen, Long nuevaCategoriaId){
        Producto producto = buscarPorId(id);
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            producto.setNombre(nuevoNombre);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.trim().isEmpty()) {
            producto.setDescripcion(nuevaDescripcion);
        }
        if (nuevoPrecio != null) {
            validarPrecio(nuevoPrecio);
            producto.setPrecio(nuevoPrecio);
        }
        if (nuevoStock != null) {
            validarStock(nuevoStock);
            producto.setStock(nuevoStock);
            producto.validarDisponibilidad();
        }
        if (nuevaImagen != null && !nuevaImagen.trim().isEmpty()) {
            producto.setImagen(nuevaImagen);
        }
        if (nuevaCategoriaId != null) {
            Categoria categoria = categoriaService.buscarPorId(nuevaCategoriaId);
            producto.setCategoria(categoria);
        }
 
        boolean productoActualizado = productoDAO.actualizar(producto);
        if (!productoActualizado) {
            throw new EntityNotFoundException(
                    "No se pudo actualizar el producto con id " + id);
        }
        return productoActualizado;
    }
      

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del producto no puede estar vacío");
        }
    }

    private void validarPrecio(Double precio) {
            if (precio == null || precio < 0) {
            throw new IllegalArgumentException(
                    "El precio no puede ser negativo");
        }
    }

    private void validarStock(Integer stock) {
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser negativo");
        }
    }
}
