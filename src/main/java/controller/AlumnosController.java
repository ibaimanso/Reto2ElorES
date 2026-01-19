package controller;

import dto.UserDTO;
import util.AppLogger;
import java.util.List;

/**
 * Controlador para la pantalla de consulta de alumnos.
 */
public class AlumnosController extends BaseController {
    
    @Override
    public void initialize() {
        AppLogger.info("Inicializando AlumnosController");
    }
    
    /**
     * Obtiene la lista completa de alumnos.
     * @return Lista de alumnos o null si hay error
     */
    public List<UserDTO> getAlumnos() {
        try {
            AppLogger.info("Obteniendo lista de alumnos...");
            
            List<UserDTO> alumnos = userService.getAlumnos();
            
            if (alumnos != null) {
                AppLogger.info("✓ Se obtuvieron " + alumnos.size() + " alumnos");
            } else {
                AppLogger.error("No se pudo obtener la lista de alumnos");
            }
            
            return alumnos;
            
        } catch (Exception e) {
            handleError("getAlumnos", e);
            return null;
        }
    }
    
    /**
     * Filtra alumnos por ciclo.
     * @param cicloId ID del ciclo
     * @return Lista de alumnos filtrados o null si hay error
     */
    public List<UserDTO> filterAlumnosByCiclo(Integer cicloId) {
        try {
            AppLogger.info("Filtrando alumnos por ciclo: " + cicloId);
            
            List<UserDTO> alumnos = userService.filterAlumnosByCiclo(cicloId);
            
            if (alumnos != null) {
                AppLogger.info("✓ Se obtuvieron " + alumnos.size() + " alumnos del ciclo " + cicloId);
            } else {
                AppLogger.error("No se pudo filtrar alumnos por ciclo");
            }
            
            return alumnos;
            
        } catch (Exception e) {
            handleError("filterAlumnosByCiclo", e);
            return null;
        }
    }
    
    /**
     * Filtra alumnos por módulo.
     * @param moduloId ID del módulo
     * @return Lista de alumnos filtrados o null si hay error
     */
    public List<UserDTO> filterAlumnosByModulo(Integer moduloId) {
        try {
            AppLogger.info("Filtrando alumnos por módulo: " + moduloId);
            
            List<UserDTO> alumnos = userService.filterAlumnosByModulo(moduloId);
            
            if (alumnos != null) {
                AppLogger.info("✓ Se obtuvieron " + alumnos.size() + " alumnos del módulo " + moduloId);
            } else {
                AppLogger.error("No se pudo filtrar alumnos por módulo");
            }
            
            return alumnos;
            
        } catch (Exception e) {
            handleError("filterAlumnosByModulo", e);
            return null;
        }
    }
    
    /**
     * Obtiene un alumno por ID.
     * @param alumnoId ID del alumno
     * @return Datos del alumno o null si hay error
     */
    public UserDTO getAlumnoById(Integer alumnoId) {
        try {
            AppLogger.info("Obteniendo alumno con ID: " + alumnoId);
            
            UserDTO alumno = userService.getAlumnoById(alumnoId);
            
            if (alumno != null) {
                AppLogger.info("✓ Alumno encontrado: " + alumno.getUsername());
            } else {
                AppLogger.error("Alumno no encontrado");
            }
            
            return alumno;
            
        } catch (Exception e) {
            handleError("getAlumnoById", e);
            return null;
        }
    }
}
