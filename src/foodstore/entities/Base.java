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
    private Long id;
    private Boolean eliminado;
    private LocalDateTime createdAt;

    public Base() {
    }
    

    public Base(Long id) {
        this.id = id;
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Long getId() {
        return id;
    }

    public Boolean isEliminado() {
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
