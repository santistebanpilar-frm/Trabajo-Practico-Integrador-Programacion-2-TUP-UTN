/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.service;

import foodstore.dao.IBaseDAO;
import foodstore.entities.Categoria;
import foodstore.exception.ValidacionException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author usuario
 */
public class CategoriaService extends GenericService<Categoria>{
    
    public CategoriaService(IBaseDAO<Categoria> dao) {
        super(dao);
    }
    @Override
    public Categoria crear(Categoria categoria) throws SQLException {
        if(categoria.getNombre() == null || categoria.getNombre().isBlank()){
            throw new SQLException("El nombre no puede estar vacio");
        }
        if(categoria.getDescripcion() == null || categoria.getDescripcion().isBlank()){
            throw new SQLException("La descripcion no puede estar vacia");
        }
        return super.crear(categoria); 
    }
    @Override
    public Optional<Categoria> leer(Long id) throws SQLException {
        Optional<Categoria> categoria = super.leer(id);
        if(categoria.isEmpty()){
            throw new SQLException("No se encontro la categoria con ID: " + id);
        }
        return categoria;
    }
    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> lista = super.listar();
        if(lista.isEmpty()){
            throw new SQLException("No hay categorias cargadas");
            
        }
        return lista;
    }
    @Override
    public boolean actualizar(Categoria categoria) throws SQLException {
        leer(categoria.getId());
        if(categoria.getNombre() == null || categoria.getNombre().isBlank()){
            throw new SQLException("El nombre no puede estar vacio");
        }
        if(categoria.getDescripcion() == null || categoria.getDescripcion().isBlank()){
            throw new SQLException("La descripcion no debe estar vacia");
        }
        return super.actualizar(categoria); 
    }

    @Override
    public boolean eliminar(Long id) throws SQLException {
        leer(id);
        
        return super.eliminar(id); 
    }
}
