/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.service;

import foodstore.dao.CategoriaDao;
import foodstore.dao.IBaseDAO;
import foodstore.entities.Categoria;
import foodstore.exception.GeneralException;


/**
 *
 * @author usuario
 */
public class CategoriaService extends GenericService<Categoria>{
    
    public CategoriaService(IBaseDAO<Categoria> dao) {
        super(new CategoriaDao());
    }
    
     @Override
    public Categoria crear(Categoria categoria) throws GeneralException {
        
        //Validaciones
        if (categoria.getNombre() == null || categoria.getNombre().isBlank()){
            
            throw new GeneralException("El nombre no puede estar nulo/vacio");
        }

        if (categoria.getDescripcion() == null || categoria.getDescripcion().isBlank()){

            throw new GeneralException("La descripcion no puede estar nula/vacia");
        }

        return super.crear(categoria);
    }


    @Override
    public Categoria leerPorId(Long id) throws GeneralException {

        Categoria categoria = super.leerPorId(id);

        if(categoria == null){

            throw new GeneralException("No se encontro la categoria");
        }

        return categoria;
    }


    @Override
    public boolean actualizar(Categoria categoria) throws GeneralException {

        leerPorId(categoria.getId());

        return super.actualizar(categoria);
    }


    @Override
    public boolean eliminar(Categoria categoria) throws GeneralException {

        leerPorId(categoria.getId());

        return super.eliminar(categoria);
    }
    
    
}
