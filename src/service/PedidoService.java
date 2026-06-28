/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.IBaseDAO;
import entities.Pedido;

/**
 *
 * @author pilarsg
 */
public class PedidoService extends GenericService<Pedido>  {
    
    public PedidoService(IBaseDAO<Pedido> dao) {
        super(dao);
    }
    
}
