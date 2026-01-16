package controller;

import dto.UserDTO;
import util.AppLogger;

import java.util.List;

/**
 * Controlador para la pantalla principal del profesor.
 */
public class MainController extends BaseController {
    
    @Override
    public void initialize() {
        AppLogger.info("Inicializando MainController");
        // TODO: Cargar datos iniciales
        loadUserProfile();
    }
    
    /**
     * Carga el perfil del usuario.
     */
    private void loadUserProfile() {
        try {
            UserDTO profile = userService.getMyProfile();
            if (profile != null) {
                AppLogger.info("Perfil cargado: " + profile.getNombre());
                // TODO: Actualizar UI
            }
        } catch (Exception e) {
            handleError("loadUserProfile", e);
        }
    }
    
    /**
     * Carga la lista de alumnos.
     */
    public void loadAlumnos() {
        try {
            List<UserDTO> alumnos = userService.getAlumnos();
            if (alumnos != null) {
                AppLogger.info("Alumnos cargados: " + alumnos.size());
                // TODO: Mostrar en tabla
            }
        } catch (Exception e) {
            handleError("loadAlumnos", e);
        }
    }
    
    /**
     * Carga el horario del profesor.
     */
    public void loadMyHorario() {
        try {
            horarioService.getMyHorario();
            // TODO: Mostrar en calendario/tabla
        } catch (Exception e) {
            handleError("loadMyHorario", e);
        }
    }
    
    /**
     * Carga las reuniones del profesor.
     */
    public void loadReuniones() {
        try {
            reunionService.getMyReuniones();
            // TODO: Mostrar en lista
        } catch (Exception e) {
            handleError("loadReuniones", e);
        }
    }
    
    /**
     * Maneja el logout.
     */
    public void handleLogout() {
        try {
            boolean success = authService.logout();
            if (success) {
                AppLogger.info("Logout exitoso");
                // TODO: Volver a pantalla de login
            }
        } catch (Exception e) {
            handleError("handleLogout", e);
        }
    }
}
