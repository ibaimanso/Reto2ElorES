package controller;

import dto.HorarioDTO;
import util.AppLogger;

import java.util.List;

/**
 * Controlador para la pantalla de horarios.
 */
public class HorarioController extends BaseController {
    
    @Override
    public void initialize() {
        AppLogger.info("Inicializando HorarioController");
    }
    
    /**
     * Muestra el horario de un usuario específico.
     */
    public void showHorarioByUserId(Integer userId) {
        try {
            List<HorarioDTO> horario = horarioService.getHorarioByUserId(userId);
            if (horario != null) {
                AppLogger.info("Horario obtenido para usuario: " + userId);
                // TODO: Mostrar en UI
            }
        } catch (Exception e) {
            handleError("showHorarioByUserId", e);
        }
    }
    
    /**
     * Muestra todos los horarios de profesores.
     */
    public void showAllHorariosProfesores() {
        try {
            horarioService.getHorariosProfesores();
            // TODO: Mostrar lista de profesores para seleccionar
        } catch (Exception e) {
            handleError("showAllHorariosProfesores", e);
        }
    }
}
