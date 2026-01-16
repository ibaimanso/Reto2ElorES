package service;

import client.SocketClient;
import dto.UserDTO;
import protocol.ActionType;
import protocol.Response;
import protocol.StatusCode;
import util.AppLogger;
import util.RSAEncryptionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Servicio de autenticación que maneja login/logout.
 */
public class AuthService {
    
    private final SocketClient client;
    private UserDTO currentUser;
    
    public AuthService() {
        this.client = SocketClient.getInstance();
    }
    
    /**
     * Obtiene la clave pública del servidor.
     */
    public boolean fetchPublicKey() {
        try {
            AppLogger.info("Solicitando clave pública del servidor...");
            
            Response response = client.sendRequest(ActionType.GET_PUBLIC_KEY, null);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                String publicKey = (String) response.getData();
                RSAEncryptionUtil.setPublicKey(publicKey);
                AppLogger.info("✓ Clave pública obtenida");
                return true;
            } else {
                AppLogger.error("Error al obtener clave pública: " + response.getMessage());
                return false;
            }
            
        } catch (Exception e) {
            AppLogger.error("Error al obtener clave pública", e);
            return false;
        }
    }
    
    /**
     * Realiza el login del usuario.
     * @return UserDTO si el login es exitoso, null en caso contrario
     */
    public UserDTO login(String username, String password) {
        try {
            AppLogger.info("Intentando login para: " + username);
            
            // Por ahora, enviamos sin cifrar
            // TODO: Implementar cifrado RSA cuando el servidor lo requiera
            
            Map<String, Object> data = new HashMap<>();
            data.put("email", username);
            data.put("encryptedPassword", password);
            
            // Enviar petición
            Response response = client.sendRequest(ActionType.LOGIN, data);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                // Extraer datos de la respuesta
                Map<String, Object> responseData = (Map<String, Object>) response.getData();
                
                if (responseData == null) {
                    AppLogger.error("Respuesta del servidor no contiene datos");
                    return null;
                }
                
                // Guardar token de sesión
                String sessionToken = (String) responseData.get("sessionToken");
                if (sessionToken != null) {
                    client.setSessionToken(sessionToken);
                    AppLogger.debug("Token de sesión guardado");
                }
                
                // Parsear datos del usuario
                Object userObj = responseData.get("user");
                
                if (userObj instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> userData = (Map<String, Object>) userObj;
                    currentUser = parseUserFromMap(userData);
                    AppLogger.info("✓ Login exitoso para: " + currentUser.getUsername());
                    return currentUser;
                } else if (userObj instanceof UserDTO) {
                    currentUser = (UserDTO) userObj;
                    AppLogger.info("✓ Login exitoso para: " + currentUser.getUsername());
                    return currentUser;
                }
                
                AppLogger.error("Formato de datos de usuario no reconocido");
                return null;
                
            } else {
                AppLogger.error("Login fallido: " + response.getMessage());
                return null;
            }
            
        } catch (Exception e) {
            AppLogger.error("Error durante el login", e);
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Parsea un Map a UserDTO.
     */
    private UserDTO parseUserFromMap(Map<String, Object> data) {
        UserDTO user = new UserDTO();
        
        if (data.get("id") != null) {
            user.setId(((Number) data.get("id")).intValue());
        }
        if (data.get("username") != null) {
            user.setUsername((String) data.get("username"));
        }
        if (data.get("email") != null) {
            user.setEmail((String) data.get("email"));
        }
        if (data.get("nombre") != null) {
            user.setNombre((String) data.get("nombre"));
        }
        if (data.get("apellidos") != null) {
            user.setApellidos((String) data.get("apellidos"));
        }
        if (data.get("dni") != null) {
            user.setDni((String) data.get("dni"));
        }
        if (data.get("direccion") != null) {
            user.setDireccion((String) data.get("direccion"));
        }
        if (data.get("telefono1") != null) {
            user.setTelefono1((String) data.get("telefono1"));
        }
        if (data.get("telefono2") != null) {
            user.setTelefono2((String) data.get("telefono2"));
        }
        if (data.get("argazkiaUrl") != null) {
            user.setArgazkiaUrl((String) data.get("argazkiaUrl"));
        }
        if (data.get("tipoId") != null) {
            user.setTipoId((String) data.get("tipoId"));
        }
        if (data.get("tipoNombre") != null) {
            user.setTipoNombre((String) data.get("tipoNombre"));
        }
        
        return user;
    }
    
    /**
     * Cierra la sesión del usuario.
     */
    public boolean logout() {
        try {
            AppLogger.info("Cerrando sesión...");
            
            Response response = client.sendRequest(ActionType.LOGOUT, null);
            
            if (response.getStatus() == StatusCode.SUCCESS) {
                currentUser = null;
                client.setSessionToken(null);
                AppLogger.info("✓ Sesión cerrada");
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            AppLogger.error("Error al cerrar sesión", e);
            return false;
        }
    }
    
    /**
     * Obtiene el usuario actual logueado.
     */
    public UserDTO getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Verifica si hay un usuario logueado.
     */
    public boolean isLoggedIn() {
        return currentUser != null && client.getSessionToken() != null;
    }
}