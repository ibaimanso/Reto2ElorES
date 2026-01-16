package dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO para información de reuniones.
 */
public class ReunionDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaHora;
    private String ubicacion;
    private String estado;
    private Integer creadorId;
    private String creadorNombre;
    private Integer destinatarioId;
    private String destinatarioNombre;
    
    public ReunionDTO() {
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Integer getCreadorId() {
        return creadorId;
    }
    
    public void setCreadorId(Integer creadorId) {
        this.creadorId = creadorId;
    }
    
    public String getCreadorNombre() {
        return creadorNombre;
    }
    
    public void setCreadorNombre(String creadorNombre) {
        this.creadorNombre = creadorNombre;
    }
    
    public Integer getDestinatarioId() {
        return destinatarioId;
    }
    
    public void setDestinatarioId(Integer destinatarioId) {
        this.destinatarioId = destinatarioId;
    }
    
    public String getDestinatarioNombre() {
        return destinatarioNombre;
    }
    
    public void setDestinatarioNombre(String destinatarioNombre) {
        this.destinatarioNombre = destinatarioNombre;
    }
}
