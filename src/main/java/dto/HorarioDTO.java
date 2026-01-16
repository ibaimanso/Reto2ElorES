package dto;

import java.io.Serializable;

/**
 * DTO para información de horario.
 */
public class HorarioDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String moduloNombre;
    private String profesorNombre;
    private String cicloNombre;
    private String aula;
    
    public HorarioDTO() {
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getDiaSemana() {
        return diaSemana;
    }
    
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
    
    public String getHoraInicio() {
        return horaInicio;
    }
    
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public String getHoraFin() {
        return horaFin;
    }
    
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    
    public String getModuloNombre() {
        return moduloNombre;
    }
    
    public void setModuloNombre(String moduloNombre) {
        this.moduloNombre = moduloNombre;
    }
    
    public String getProfesorNombre() {
        return profesorNombre;
    }
    
    public void setProfesorNombre(String profesorNombre) {
        this.profesorNombre = profesorNombre;
    }
    
    public String getCicloNombre() {
        return cicloNombre;
    }
    
    public void setCicloNombre(String cicloNombre) {
        this.cicloNombre = cicloNombre;
    }
    
    public String getAula() {
        return aula;
    }
    
    public void setAula(String aula) {
        this.aula = aula;
    }
}
