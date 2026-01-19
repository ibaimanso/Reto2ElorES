package controller;

import dto.UserDTO;
import util.AppLogger;

/**
 * Controlador para la pantalla de perfil de usuario.
 */
public class ProfileController extends BaseController {
    
    @Override
    public void initialize() {
        AppLogger.info("Inicializando ProfileController");
    }
    
    /**
     * Obtiene el perfil del usuario actual.
     * @return UserDTO con los datos del perfil o null si hay error
     */
    public UserDTO getUserProfile() {
        try {
            AppLogger.info("Obteniendo perfil de usuario...");
            
            UserDTO profile = userService.getMyProfile();
            
            if (profile != null) {
                AppLogger.info("✓ Perfil obtenido exitosamente: " + profile.getUsername());
            } else {
                AppLogger.error("No se pudo obtener el perfil del usuario");
            }
            
            return profile;
            
        } catch (Exception e) {
            handleError("getUserProfile", e);
            return null;
        }
    }
}
