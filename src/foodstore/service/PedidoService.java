/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.service;

import foodstore.dao.IBaseDAO;
import foodstore.dao.PedidoDao;
import foodstore.entities.Pedido;
import foodstore.exception.GeneralException;

/**
 *
 * @author usuario
 */
public class PedidoService extends GenericService<Pedido>{
    
    public PedidoService(IBaseDAO<Pedido> dao) {
        super(new PedidoDao());
    }
    
     @Override
    public Pedido crear(Pedido pedido) throws GeneralException {

        if(pedido == null){
            throw new GeneralException("El pedido no es valido");
        }

        if(pedido.getFormaPago() == null){
            throw new GeneralException("Se debe elegir una forma de pago");
        }

        if(pedido.getTotal() == null || pedido.getTotal() < 0){
            throw new GeneralException("El total no es valido");
        }

        if(pedido.getIdUsuario() == null){
            throw new GeneralException("Debe existir un usuario");
        }

        return super.crear(pedido);
    }


    @Override
    public Pedido leerPorId(Long id) throws GeneralException {

        Pedido pedido = super.leerPorId(id);

        if(pedido == null){
            throw new GeneralException("No se encontro el pedido");
        }

        return pedido;
    }


    @Override
    public boolean actualizar(Pedido pedido) throws GeneralException {

        leerPorId(pedido.getId());

        return super.actualizar(pedido);
    }


    @Override
    public boolean eliminar(Pedido pedido) throws GeneralException {

        leerPorId(pedido.getId());

        return super.eliminar(pedido);
    }
    
}
