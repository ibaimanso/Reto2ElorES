package controller;

import dto.ReunionDTO;
import util.AppLogger;

import java.time.LocalDateTime;

/**
 * Controlador para gestión de reuniones.
 */
public class ReunionController extends BaseController {
    
    @Override
    public void initialize() {
        AppLogger.info("Inicializando ReunionController");
    }
    
    /**
     * Crea una nueva reunión.
     */
    public void createNewReunion(String titulo, String descripcion, LocalDateTime fechaHora, 
                                  String ubicacion, Integer destinatarioId) {
        try {
            ReunionDTO reunion = new ReunionDTO();
            reunion.setTitulo(titulo);
            reunion.setDescripcion(descripcion);
            reunion.setFechaHora(fechaHora);
            reunion.setUbicacion(ubicacion);
            reunion.setDestinatarioId(destinatarioId);
            
            boolean success = reunionService.createReunion(reunion);
            if (success) {
                AppLogger.info("Reunión creada exitosamente");
                // TODO: Actualizar lista de reuniones
            }
        } catch (Exception e) {
            handleError("createNewReunion", e);
        }
    }
    
    /**
     * Acepta una reunión pendiente.
     */
    public void acceptReunion(Integer reunionId) {
        try {
            boolean success = reunionService.acceptReunion(reunionId);
            if (success) {
                // TODO: Actualizar estado en UI
            }
        } catch (Exception e) {
            handleError("acceptReunion", e);
        }
    }
    
    /**
     * Cancela una reunión.
     */
    public void cancelReunion(Integer reunionId) {
        try {
            boolean success = reunionService.cancelReunion(reunionId);
            if (success) {
                // TODO: Actualizar estado en UI
            }
        } catch (Exception e) {
            handleError("cancelReunion", e);
        }
    }
}
