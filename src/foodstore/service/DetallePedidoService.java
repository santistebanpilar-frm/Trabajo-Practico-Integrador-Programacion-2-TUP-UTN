/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.service;

import foodstore.dao.IBaseDAO;
import foodstore.entities.DetallePedido;
import foodstore.dao.DetallePedidoDao;
import foodstore.exception.GeneralException;

/**
 *
 * @author usuario
 */
public class DetallePedidoService extends GenericService<DetallePedido>{
    
    public DetallePedidoService(IBaseDAO<DetallePedido> dao) {
        super(new DetallePedidoDao());
    }
    
     @Override
    public DetallePedido crear(DetallePedido detalle) throws GeneralException {

        if(detalle.getCantidad() == null || detalle.getCantidad() <= 0){
            throw new GeneralException("La cantidad no puede ser nula/<= 0");
        }

        if(detalle.getSubtotal() == null || detalle.getSubtotal() < 0){
            throw new GeneralException("El subtotal no puede ser < 0");
        }

        if(detalle.getProducto() == null){

            throw new GeneralException("No existen productos");
        }

        if(detalle.getIdPedido() == null){

            throw new GeneralException("No existe un pedido");
        }

        return super.crear(detalle);

    }


    @Override
    public DetallePedido leerPorId(Long id) throws GeneralException {

        DetallePedido detalle = super.leerPorId(id);

        if(detalle == null){

            throw new GeneralException("No se encontro el detalle");
        }

        return detalle;

    }


    @Override
    public boolean actualizar(DetallePedido detalle) throws GeneralException {

        leerPorId(detalle.getId());

        return super.actualizar(detalle);
    }


    @Override
    public boolean eliminar(DetallePedido detalle) throws GeneralException {

        leerPorId(detalle.getId());

        return super.eliminar(detalle);

    }
    
}
