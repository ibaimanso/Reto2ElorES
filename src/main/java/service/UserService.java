package service;

import client.SocketClient;
import dto.UserDTO;
import protocol.ActionType;
import protocol.Response;
import protocol.StatusCode;
import util.AppLogger;

import java.util.List;
import java.util.Map;

/**
 * Servicio para gestión de usuarios y alumnos.
 */
public class UserService {
    
    private final SocketClient client;
    
    public UserService() {
        this.client = SocketClient.getInstance();
    }
    
    /**
     * Obtiene el perfil del usuario logueado.
     */
    public UserDTO getMyProfile() {
        try {
            Response response = client.sendRequest(ActionType.GET_PROFILE, null);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                // TODO: Parsear UserDTO
                return null;
            }
            
            AppLogger.error("Error al obtener perfil: " + response.getMessage());
            return null;
            
        } catch (Exception e) {
            AppLogger.error("Error al obtener perfil", e);
            return null;
        }
    }
    
    /**
     * Obtiene la lista de alumnos.
     */
    public List<UserDTO> getAlumnos() {
        try {
            Response response = client.sendRequest(ActionType.GET_ALUMNOS, null);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                // TODO: Parsear List<UserDTO>
                AppLogger.info("Alumnos obtenidos correctamente");
                return null;
            }
            
            AppLogger.error("Error al obtener alumnos: " + response.getMessage());
            return null;
            
        } catch (Exception e) {
            AppLogger.error("Error al obtener alumnos", e);
            return null;
        }
    }
    
    /**
     * Obtiene un alumno por ID.
     */
    public UserDTO getAlumnoById(Integer alumnoId) {
        try {
            Map<String, Object> data = Map.of("alumnoId", alumnoId);
            Response response = client.sendRequest(ActionType.GET_ALUMNO_BY_ID, data);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                // TODO: Parsear UserDTO
                return null;
            }
            
            return null;
            
        } catch (Exception e) {
            AppLogger.error("Error al obtener alumno", e);
            return null;
        }
    }
    
    /**
     * Filtra alumnos por ciclo.
     */
    public List<UserDTO> filterAlumnosByCiclo(Integer cicloId) {
        try {
            Map<String, Object> data = Map.of("cicloId", cicloId);
            Response response = client.sendRequest(ActionType.FILTER_ALUMNOS_BY_CICLO, data);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                // TODO: Parsear List<UserDTO>
                return null;
            }
            
            return null;
            
        } catch (Exception e) {
            AppLogger.error("Error al filtrar alumnos por ciclo", e);
            return null;
        }
    }
}
