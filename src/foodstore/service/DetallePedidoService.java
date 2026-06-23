/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.service;

import foodstore.dao.DetallePedidoDao;
import foodstore.dao.IBaseDAO;
import foodstore.entities.DetallePedido;
import foodstore.entities.Producto;
import foodstore.exception.ValidacionException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author usuario
 */
public class DetallePedidoService{
    private IBaseDAO<Producto> productoDao;
    private ProductoService productoService;
    private DetallePedidoDao detallePedidoDao;

    public DetallePedidoService(IBaseDAO<Producto> productoDao, ProductoService productoService, DetallePedidoDao detallePedidoDao) {
        this.productoDao = productoDao;
        this.productoService = productoService;
        this.detallePedidoDao = detallePedidoDao;
    }
    
    public void guardar(DetallePedido detalle, Long pedidoId) throws SQLException{
        try{
            detallePedidoDao.crear(detalle, pedidoId);
        }catch (SQLException e){
            throw new SQLException("Error al guardar el detalle", e);
        }
    }
    public List<DetallePedido> listarPorPedido(Long pedidoId) throws SQLException{
        try {
            return detallePedidoDao.listarPorPedido(pedidoId);
        } catch (SQLException e) {
            throw new SQLException("Error al listar los detalles del pedido");
        }
    }
    
    private void validarDetalle(DetallePedido detalle)throws SQLException{
        if(detalle == null){
            throw new SQLException("El detalle no puede ser nulo");
        }
        if(detalle.getCantidad() <= 0){
            throw new SQLException("La cantidad debe ser mayor a cero");
        }
        if(detalle.getProducto() == null || detalle.getProducto().getId() == null){
            throw new SQLException("El producto obligatorio");
        }
        
        Optional<Producto> producto = productoDao.leer(detalle.getProducto().getId());
        if(producto.isEmpty()){
            throw new SQLException("El producto no existe");
        }
        Producto p = producto.get();
        if(p.getStock() < detalle.getCantidad()){
            throw new SQLException("Stock insuficiente. Disponible: "+ p.getStock());
        }
        detalle.setProducto(p);
        detalle.setSubTotal(detalle.getCantidad() * p.getPrecio());
    }
    public void prepararDetalle(DetallePedido detalle)throws SQLException{
        validarDetalle(detalle);
        productoService.descontarStock(detalle.getProducto(), detalle.getCantidad());
    }
    public Double modificarCantidad(Long detalleId, DetallePedido detalleActual, Integer nuevaCantidad)
            throws SQLException {
        if (nuevaCantidad == null || nuevaCantidad <= 0) {
            throw new SQLException("La cantidad debe ser mayor a cero");
        }

        Producto producto = detalleActual.getProducto();

        productoService.reponerStock(producto, detalleActual.getCantidad());
        productoService.descontarStock(producto, nuevaCantidad);

        detalleActual.setCantidad(nuevaCantidad);
        detalleActual.setSubTotal(nuevaCantidad * producto.getPrecio());

        try {
            detallePedidoDao.actualizar(detalleActual);
            } catch (SQLException e) {
            throw new SQLException("Error al actualizar el detalle");
            }   
    

            return detalleActual.getSubTotal();
    }
}
