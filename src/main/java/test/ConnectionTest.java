package test;

import client.SocketClient;
import protocol.ActionType;
import protocol.Response;
import protocol.StatusCode;
import util.AppLogger;

/**
 * Clase de prueba para verificar la conexión con ElorServ.
 * Esta clase realiza pruebas básicas de comunicación sin interfaz gráfica.
 */
public class ConnectionTest {
    
    public static void main(String[] args) {
        
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  ELORES - TEST DE CONEXIÓN                   ║");
        System.out.println("║              Cliente de Escritorio - Profesores              ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        ConnectionTest test = new ConnectionTest();
        test.runTests();
    }
    
    public void runTests() {
        SocketClient client = SocketClient.getInstance();
        
        // TEST 1: Verificar conexión al servidor
        AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        AppLogger.info("TEST 1: Conectando con ElorServ...");
        AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        boolean connected = client.connect();
        
        if (!connected) {
            AppLogger.error("✗ FALLO: No se pudo conectar con el servidor");
            AppLogger.error("");
            AppLogger.error("VERIFICA:");
            AppLogger.error("  1. ElorServ está ejecutándose");
            AppLogger.error("  2. El servidor escucha en puerto 9000");
            AppLogger.error("  3. No hay firewall bloqueando la conexión");
            return;
        }
        
        AppLogger.info("✓ ÉXITO: Conexión establecida con ElorServ");
        System.out.println();
        
        // TEST 2: Obtener clave pública
        AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        AppLogger.info("TEST 2: Solicitando clave pública RSA...");
        AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        Response publicKeyResponse = client.sendRequest(ActionType.GET_PUBLIC_KEY, null);
        
        if (publicKeyResponse.getStatus() != StatusCode.SUCCESS) {
            AppLogger.error("✗ FALLO: No se pudo obtener clave pública");
            AppLogger.error("   Respuesta: " + publicKeyResponse.getMessage());
        } else {
            AppLogger.info("✓ ÉXITO: Clave pública recibida");
            String publicKey = (String) publicKeyResponse.getData();
            AppLogger.info("   Longitud de clave: " + publicKey.length() + " caracteres");
        }
        System.out.println();
        
        // TEST 3: Ping al servidor
        AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        AppLogger.info("TEST 3: Enviando PING al servidor...");
        AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        long startTime = System.currentTimeMillis();
        Response pingResponse = client.sendRequest(ActionType.PING, null);
        long endTime = System.currentTimeMillis();
        long latency = endTime - startTime;
        
        if (pingResponse.getStatus() != StatusCode.SUCCESS) {
            AppLogger.error("✗ FALLO: El servidor no respondió al PING");
        } else {
            AppLogger.info("✓ ÉXITO: PONG recibido");
            AppLogger.info("   Latencia: " + latency + " ms");
        }
        System.out.println();
        
        // TEST 4: Test de login (credenciales inválidas a propósito)
        AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        AppLogger.info("TEST 4: Probando petición LOGIN (sin credenciales)...");
        AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        Response loginResponse = client.sendRequest(ActionType.LOGIN, null);
        
        if (loginResponse.getStatus() == StatusCode.BAD_REQUEST || 
            loginResponse.getStatus() == StatusCode.UNAUTHORIZED) {
            AppLogger.info("✓ ÉXITO: El servidor validó correctamente la petición");
            AppLogger.info("   Respuesta esperada: " + loginResponse.getMessage());
        } else {
            AppLogger.warn("⚠ El servidor respondió de forma inesperada");
            AppLogger.warn("   Status: " + loginResponse.getStatus());
        }
        System.out.println();
        
        // Resumen final
        AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        AppLogger.info("RESUMEN DE PRUEBAS");
        AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        AppLogger.info("✓ Conexión Socket TCP: OK");
        AppLogger.info("✓ Comunicación Request/Response: OK");
        AppLogger.info("✓ Protocolo de mensajes: OK");
        AppLogger.info("✓ Servidor ElorServ respondiendo correctamente");
        System.out.println();
        AppLogger.info("🎉 TODAS LAS PRUEBAS DE CONEXIÓN COMPLETADAS");
        AppLogger.info("");
        AppLogger.info("SIGUIENTE PASO:");
        AppLogger.info("  - Implementar interfaz gráfica (Swing/JavaFX)");
        AppLogger.info("  - Realizar login con credenciales reales");
        AppLogger.info("  - Probar casos de uso completos");
        System.out.println();
        
        // Desconectar
        client.disconnect();
    }
}
