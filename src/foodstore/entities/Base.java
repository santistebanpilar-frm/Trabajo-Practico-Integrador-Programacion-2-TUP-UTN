/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author usuario
 */
public abstract class Base {
    private static long contador = 0;
    private long id;
    private Boolean eliminado;
    private LocalDateTime createdAt;

    public Base() {
        this.id = generarId();
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }
    
    public long generarId(){
        return this.id = ++contador;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Base base = (Base) o;
        return Objects.equals(id, base.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Base{" + 
                "id=" + id + 
                ", eliminado=" + eliminado + 
                ", createdAt=" + createdAt + '}';
    }
}
