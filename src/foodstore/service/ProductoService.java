/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.service;

import foodstore.dao.IBaseDAO;
import foodstore.entities.Producto;
import foodstore.dao.ProductoDao;
import foodstore.exception.GeneralException;

/**
 *
 * @author usuario
 */
public class ProductoService extends GenericService<Producto>{
    
    public ProductoService(IBaseDAO<Producto> dao) {
        super(new ProductoDao());
    }
    
      @Override
    public Producto crear(Producto producto) throws GeneralException {

        if(producto.getNombre() == null || producto.getNombre().isBlank()){

            throw new GeneralException("El nombre no puede estar nulo/vacio");
        }

        if(producto.getPrecio() == null || producto.getPrecio() <= 0){

            throw new GeneralException("El precio no es valido");
        }

        if(producto.getStock() == null || producto.getStock() < 0){

            throw new GeneralException("El stock no es valido");
        }

        return super.crear(producto);
    }


    @Override
    public Producto leerPorId(Long id) throws GeneralException {

        Producto producto = super.leerPorId(id);

        if(producto == null){

            throw new GeneralException("No se encontro el producto");
        }

        return producto;
    }


    @Override
    public boolean actualizar(Producto producto)
    throws GeneralException {

        leerPorId(producto.getId());

        return super.actualizar(producto);
    }


    @Override
    public boolean eliminar(Producto producto)
    throws GeneralException {

        leerPorId(producto.getId());

        return super.eliminar(producto);
    }

    
}
