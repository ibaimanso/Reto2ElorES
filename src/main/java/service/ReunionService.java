package service;

import client.SocketClient;
import dto.ReunionDTO;
import protocol.ActionType;
import protocol.Response;
import protocol.StatusCode;
import util.AppLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio para gestión de reuniones.
 */
public class ReunionService {
    
    private final SocketClient client;
    
    public ReunionService() {
        this.client = SocketClient.getInstance();
    }
    
    /**
     * Crea una nueva reunión.
     */
    public boolean createReunion(ReunionDTO reunion) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("titulo", reunion.getTitulo());
            data.put("descripcion", reunion.getDescripcion());
            data.put("fechaHora", reunion.getFechaHora().toString());
            data.put("ubicacion", reunion.getUbicacion());
            data.put("destinatarioId", reunion.getDestinatarioId());
            
            Response response = client.sendRequest(ActionType.CREATE_REUNION, data);
            
            if (response.getStatus() == StatusCode.SUCCESS || response.getStatus() == StatusCode.CREATED) {
                AppLogger.info("✓ Reunión creada correctamente");
                return true;
            }
            
            AppLogger.error("Error al crear reunión: " + response.getMessage());
            return false;
            
        } catch (Exception e) {
            AppLogger.error("Error al crear reunión", e);
            return false;
        }
    }
    
    /**
     * Obtiene las reuniones del usuario logueado.
     */
    public List<ReunionDTO> getMyReuniones() {
        try {
            Response response = client.sendRequest(ActionType.GET_MY_REUNIONES, null);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                // TODO: Parsear List<ReunionDTO>
                AppLogger.info("Reuniones obtenidas correctamente");
                return null;
            }
            
            return null;
            
        } catch (Exception e) {
            AppLogger.error("Error al obtener reuniones", e);
            return null;
        }
    }
    
    /**
     * Acepta una reunión.
     */
    public boolean acceptReunion(Integer reunionId) {
        try {
            Map<String, Object> data = Map.of("reunionId", reunionId);
            Response response = client.sendRequest(ActionType.ACCEPT_REUNION, data);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                AppLogger.info("✓ Reunión aceptada");
                return true;
            }
            
            AppLogger.error("Error al aceptar reunión: " + response.getMessage());
            return false;
            
        } catch (Exception e) {
            AppLogger.error("Error al aceptar reunión", e);
            return false;
        }
    }
    
    /**
     * Cancela una reunión.
     */
    public boolean cancelReunion(Integer reunionId) {
        try {
            Map<String, Object> data = Map.of("reunionId", reunionId);
            Response response = client.sendRequest(ActionType.CANCEL_REUNION, data);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                AppLogger.info("✓ Reunión cancelada");
                return true;
            }
            
            AppLogger.error("Error al cancelar reunión: " + response.getMessage());
            return false;
            
        } catch (Exception e) {
            AppLogger.error("Error al cancelar reunión", e);
            return false;
        }
    }
}
