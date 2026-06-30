/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.CategoriaDAO;
import entities.Categoria;
import exception.EntityNotFoundException;

/**
 *
 * @author pilarsg
 */
public class CategoriaService extends GenericService<Categoria> {
    
    private final CategoriaDAO categoriaDAO;
    
    public CategoriaService(CategoriaDAO categoriaDAO) {
        super(categoriaDAO);
        this.categoriaDAO = categoriaDAO;
    }
    
    @Override
    public Categoria buscarPorId(Long id){
        Categoria categ = categoriaDAO.buscarPorId(id);
        if (categ == null || Boolean.TRUE.equals(categ.isEliminado())){
            throw new EntityNotFoundException("NO EXISTE UNA CATEGORIA CON ID"+id);
        }
        return categ;
    }
    
    public Categoria crear(String nombre, String descripcion){
        if (nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre de la categoria no puede quedar vacia!");
        }
        if (descripcion == null || descripcion.trim().isEmpty()){
            throw new IllegalArgumentException("La descripcion de la categoria no puede quedar vacia!");
        }
        Categoria categ = new Categoria(nombre, descripcion);
        return categoriaDAO.crear(categ);
    }
    
    public boolean actualizar(Long id, String nuevoNombre, String nuevaDescripcion){
        Categoria categ = buscarPorId(id);
        
        if (nuevoNombre != null && nuevoNombre.trim().isEmpty()){
            categ.setNombre(nuevoNombre);
        }
        if (nuevaDescripcion != null && nuevaDescripcion.trim().isEmpty()){
            categ.setDescripcion(nuevaDescripcion);
        }
        boolean categoriaActualizada = categoriaDAO.actualizar(categ);
        if (!categoriaActualizada){
            throw new EntityNotFoundException("No se pudo ACTUALIZAR la CATEGORIA por ID"+id);
        }
        return categoriaActualizada;
    }
    /*probando*/
}
