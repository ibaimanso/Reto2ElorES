package controller;

import service.AuthService;
import service.UserService;
import service.HorarioService;
import service.ReunionService;
import util.AppLogger;

/**
 * Controlador base que contiene las instancias de servicios.
 * Los controladores específicos heredarán de esta clase.
 */
public abstract class BaseController {
    
    protected AuthService authService;
    protected UserService userService;
    protected HorarioService horarioService;
    protected ReunionService reunionService;
    
    public BaseController() {
        this.authService = new AuthService();
        this.userService = new UserService();
        this.horarioService = new HorarioService();
        this.reunionService = new ReunionService();
    }
    
    /**
     * Método abstracto que cada controlador debe implementar.
     */
    public abstract void initialize();
    
    /**
     * Maneja errores comunes en los controladores.
     */
    protected void handleError(String context, Exception e) {
        AppLogger.error("Error en " + context, e);
    }
}
