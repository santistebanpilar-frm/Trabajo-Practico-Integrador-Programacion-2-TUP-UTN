/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.service;

import foodstore.dao.IBaseDAO;
import foodstore.dao.ProductoDao;
import foodstore.entities.Categoria;
import foodstore.entities.Producto;
import foodstore.exception.ValidacionException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author usuario
 */
public class ProductoService extends GenericService<Producto>{
    private IBaseDAO<Categoria> categoriaDao;
    private IBaseDAO<Producto> productoDao;
    private ProductoDao producto;

    public ProductoService(IBaseDAO<Categoria> categoriaDao, IBaseDAO<Producto> dao) {
        super(dao);
        this.categoriaDao = categoriaDao;
        this.productoDao = dao;
    }

    
    
    @Override
    public Producto crear(Producto producto) throws SQLException {
        validadProducto(producto);
        return super.crear(producto); 
    }
    @Override
    public Optional<Producto> leer(Long id) throws SQLException {
        Optional<Producto> producto = super.leer(id);
        if(producto.isEmpty()){
            throw new SQLException("No se encontro el producto con ID: " + id);
        }
        return producto;
    }
    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> lista = super.listar();
        if(lista.isEmpty()){
            throw new SQLException("No hay Producto cargados");
            
        }
        return lista;
    }
    
    // ProductoService
public List<Producto> listarPorCategoria(Long categoriaId) throws SQLException {
    Optional<Categoria> categoria = categoriaDao.leer(categoriaId);
    if (categoria.isEmpty()) {
        throw new SQLException("La categoria no existe o esta eliminada");
    }
    List<Producto> lista = producto.listarPorCategoria(categoriaId);
    if (lista.isEmpty()) {
        throw new SQLException("No hay productos para esta categoria");
    }
    return lista;
}
    @Override
    public boolean actualizar(Producto producto) throws SQLException {
        leer(producto.getId());
        validadProducto(producto);
        return super.actualizar(producto); 
    }

    @Override
    public boolean eliminar(Long id) throws SQLException {
        leer(id);
        return super.eliminar(id); 
    }
    private void validadProducto(Producto producto)throws SQLException{
        if(producto.getNombre() == null || producto.getNombre().isBlank()){
            throw new SQLException("El nombre no puede estar vacio");
        }
        if(producto.getDescripcion() == null || producto.getDescripcion().isBlank()){
            throw new SQLException("La descripcion no debe estar vacia");
        }
        if(producto.getPrecio() < 0){
            throw new SQLException("El precio no puede ser negativo");
        }
        if(producto.getStock() < 0){
            throw new SQLException("El stock no puede ser negativo");
        }
        if (producto.getCategoria() == null || producto.getCategoria().getId() == null) {
            throw new SQLException("La categoría es obligatoria");
        }
        Optional<Categoria> categoria = categoriaDao.leer(producto.getCategoria().getId());
        if(categoria.isEmpty()){
            throw new SQLException("La categoria no existe o esta eliminada");
        }
        producto.setCategoria(categoria.get());
        producto.setDisponible(producto.getStock() > 0);
    }
    public void descontarStock(Producto producto, Integer cantidad) throws SQLException{
        if(producto.getStock() < cantidad){
            throw new SQLException("Strock insuficiente. Disponible: " + producto.getStock());
        }
        producto.setStock(producto.getStock() - cantidad);
        producto.setDisponible(producto.getStock() > 0);
        try{
            productoDao.actualizar(producto);
        }catch (SQLException e){
            throw new SQLException("error al actualizar el stock del producto", e);
        }
    }
    public void reponerStock(Producto producto, Integer cantidad) throws SQLException {
        producto.setStock(producto.getStock() + cantidad);
        producto.setDisponible(producto.getStock() > 0);
        try {
            productoDao.actualizar(producto);
        } catch (SQLException e) {
            throw new SQLException("Error al reponer el stock del producto", e);
        }
    }
}
