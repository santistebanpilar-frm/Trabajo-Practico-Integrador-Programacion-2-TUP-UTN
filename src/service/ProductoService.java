/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.IBaseDAO;
import entities.Producto;

/**
 *
 * @author pilarsg
 */
public class ProductoService extends GenericService<Producto> {
    
    public ProductoService(IBaseDAO<Producto> dao) {
        super(dao);
    }
    
}
