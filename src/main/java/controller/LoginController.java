package controller;

import dto.UserDTO;
import util.AppLogger;

/**
 * Controlador para la pantalla de login.
 */
public class LoginController extends BaseController {
    
    @Override
    public void initialize() {
        AppLogger.info("Inicializando LoginController");
        // TODO: Inicializar componentes de UI
    }
    
    /**
     * Maneja el proceso de login.
     * @return UserDTO si el login es exitoso, null en caso contrario
     */
    public UserDTO handleLogin(String username, String password) {
        try {
            AppLogger.info("Procesando login...");
            
            // Validar campos
            if (username == null || username.trim().isEmpty()) {
                AppLogger.error("El nombre de usuario es obligatorio");
                return null;
            }
            
            if (password == null || password.trim().isEmpty()) {
                AppLogger.error("La contraseña es obligatoria");
                return null;
            }
            
            // Intentar login
            UserDTO user = authService.login(username, password);
            
            if (user != null) {
                AppLogger.info("Login exitoso para: " + username);
                // TODO: Cambiar a pantalla principal
            }
            
            return user;
            
        } catch (Exception e) {
            handleError("handleLogin", e);
            return null;
        }
    }
}