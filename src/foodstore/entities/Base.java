/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

import java.time.LocalDateTime;

/**
 *
 * @author usuario
 */
public abstract class Base {
    private Long id;
    private Boolean eliminado;
    private LocalDateTime createdAt;
    
    //Sobrecarga
    //Crear un obj nuevo
    public Base() {
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    //Ref a un objeto existente
    public Base(Long id) {
        this();
        this.id = id;
    }
    
    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    //getters
    public long getId() {
        return id;
    }
    public boolean isEliminado() {
        return eliminado;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "--- BASE ---" + "\n" +
               "ID: " + id + "\n" +
               "ELIMINADO: " + eliminado + "\n" +
               "CREATE AT: " + createdAt + "\n";
    }
    
}
