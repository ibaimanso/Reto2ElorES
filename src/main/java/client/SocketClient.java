package client;

import protocol.Request;
import protocol.Response;
import protocol.ActionType;
import protocol.StatusCode;
import util.AppLogger;
import util.JsonUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

/**
 * Cliente Socket que maneja la comunicación con el servidor ElorServ.
 * Implementa el patrón Singleton para mantener una única conexión.
 */
public class SocketClient {
    
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9000;
    
    private static SocketClient instance;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connected;
    private String sessionToken;
    
    private SocketClient() {
        this.connected = false;
    }
    
    /**
     * Obtiene la instancia única del cliente.
     */
    public static synchronized SocketClient getInstance() {
        if (instance == null) {
            instance = new SocketClient();
        }
        return instance;
    }
    
    /**
     * Conecta con el servidor ElorServ.
     */
    public boolean connect() {
        try {
            AppLogger.info("Conectando con ElorServ en " + SERVER_HOST + ":" + SERVER_PORT + "...");
            
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Leer mensaje de bienvenida del servidor
            String welcomeJson = in.readLine();
            if (welcomeJson != null) {
                Response welcome = JsonUtil.fromJson(welcomeJson, Response.class);
                if (welcome != null) {
                    AppLogger.debug("Servidor: " + welcome.getMessage());
                }
            }
            
            connected = true;
            AppLogger.info("Conexión establecida con ElorServ");
            
            return true;
            
        } catch (Exception e) {
            AppLogger.error("Error al conectar con el servidor", e);
            connected = false;
            return false;
        }
    }
    
    /**
     * Envía una petición al servidor y espera la respuesta.
     */
    public Response sendRequest(Request request) {
        if (!connected) {
            AppLogger.error("No hay conexión con el servidor");
            return new Response(StatusCode.SERVICE_UNAVAILABLE, "No conectado al servidor");
        }
        
        try {
            // Añadir sessionToken si existe
            if (sessionToken != null && request.getSessionToken() == null) {
                request.setSessionToken(sessionToken);
            }
            
            AppLogger.debug("→ Enviando: " + request.getAction());
            
            // Enviar petición como JSON
            String requestJson = JsonUtil.toJson(request);
            if (requestJson == null) {
                return new Response(StatusCode.INTERNAL_ERROR, "Error al serializar petición");
            }
            out.println(requestJson);
            
            // Recibir respuesta como JSON
            String responseJson = in.readLine();
            if (responseJson == null) {
                AppLogger.error("Servidor cerró la conexión");
                disconnect();
                return new Response(StatusCode.SERVICE_UNAVAILABLE, "Conexión perdida");
            }
            
            Response response = JsonUtil.fromJson(responseJson, Response.class);
            if (response == null) {
                return new Response(StatusCode.INTERNAL_ERROR, "Error al deserializar respuesta");
            }
            
            AppLogger.debug("← Recibido: " + response.getStatus());
            
            return response;
            
        } catch (Exception e) {
            AppLogger.error("Error en la comunicación con el servidor", e);
            disconnect();
            return new Response(StatusCode.INTERNAL_ERROR, "Error de comunicación: " + e.getMessage());
        }
    }
    
    /**
     * Envía una petición construyendo el Request internamente.
     */
    public Response sendRequest(ActionType action, Map<String, Object> data) {
        Request request = new Request(action, sessionToken, data);
        return sendRequest(request);
    }
    
    /**
     * Desconecta del servidor.
     */
    public void disconnect() {
        try {
            if (connected) {
                AppLogger.info("Desconectando del servidor...");
                
                // Intentar enviar petición de desconexión
                try {
                    Request disconnectRequest = new Request(ActionType.DISCONNECT, sessionToken, null);
                    String requestJson = JsonUtil.toJson(disconnectRequest);
                    if (requestJson != null) {
                        out.println(requestJson);
                    }
                } catch (Exception e) {
                    // Ignorar errores al desconectar
                }
                
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
                
                connected = false;
                sessionToken = null;
                
                AppLogger.info("✓ Desconectado del servidor");
            }
        } catch (Exception e) {
            AppLogger.error("Error al desconectar", e);
        }
    }
    
    /**
     * Verifica si está conectado al servidor.
     */
    public boolean isConnected() {
        return connected && socket != null && !socket.isClosed();
    }
    
    /**
     * Establece el token de sesión después del login.
     */
    public void setSessionToken(String token) {
        this.sessionToken = token;
        AppLogger.debug("Token de sesión establecido");
    }
    
    /**
     * Obtiene el token de sesión actual.
     */
    public String getSessionToken() {
        return sessionToken;
    }
}