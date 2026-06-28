/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.IBaseDAO;
import entities.Categoria;

/**
 *
 * @author pilarsg
 */
public class CategoriaService extends GenericService<Categoria> {
    
    public CategoriaService(IBaseDAO<Categoria> dao) {
        super(dao);
    }
    
}
