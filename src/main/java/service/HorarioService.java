package service;

import client.SocketClient;
import dto.HorarioDTO;
import protocol.ActionType;
import protocol.Response;
import protocol.StatusCode;
import util.AppLogger;

import java.util.List;
import java.util.Map;

/**
 * Servicio para gestión de horarios.
 */
public class HorarioService {
    
    private final SocketClient client;
    
    public HorarioService() {
        this.client = SocketClient.getInstance();
    }
    
    /**
     * Obtiene el horario del profesor logueado.
     */
    public List<HorarioDTO> getMyHorario() {
        try {
            Response response = client.sendRequest(ActionType.GET_MY_HORARIO, null);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                // TODO: Parsear List<HorarioDTO>
                AppLogger.info("Horario obtenido correctamente");
                return null;
            }
            
            AppLogger.error("Error al obtener horario: " + response.getMessage());
            return null;
            
        } catch (Exception e) {
            AppLogger.error("Error al obtener horario", e);
            return null;
        }
    }
    
    /**
     * Obtiene el horario de un usuario específico por ID.
     */
    public List<HorarioDTO> getHorarioByUserId(Integer userId) {
        try {
            Map<String, Object> data = Map.of("userId", userId);
            Response response = client.sendRequest(ActionType.GET_HORARIO_BY_USER_ID, data);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                // TODO: Parsear List<HorarioDTO>
                return null;
            }
            
            return null;
            
        } catch (Exception e) {
            AppLogger.error("Error al obtener horario del usuario", e);
            return null;
        }
    }
    
    /**
     * Obtiene lista de horarios de todos los profesores.
     */
    public List<HorarioDTO> getHorariosProfesores() {
        try {
            Response response = client.sendRequest(ActionType.GET_HORARIOS_PROFESORES, null);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                // TODO: Parsear datos
                return null;
            }
            
            return null;
            
        } catch (Exception e) {
            AppLogger.error("Error al obtener horarios de profesores", e);
            return null;
        }
    }
}
