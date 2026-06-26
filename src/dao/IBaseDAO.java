/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;

/**
 *
 * @author pilarsg
 * @param <T>
 */
public interface IBaseDAO<T>{
    /*Para insertar una nueva entidad a la DB*/
    T crear(T entidad);
    /*Busca la entidad por su ID, si no existies devuelve null*/
    T buscarPorId(Long id);
    /*Muestra todas ls entidades de la DB por su id*/
    List<T> listar();
    /*Para poner que esta eliminado sin tener q borrarlo de la ila en DB*/
    boolean eliminar(Long id);
    
}
