package app;

import client.SocketClient;
import util.AppLogger;
import vista.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class App {

	
    public static void main(String[] args) {
        
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      ELORES - INICIO                         ║");
        System.out.println("║              Aplicación de Escritorio - Profesores           ║");
        System.out.println("║                  Framework Educativo Elorrieta               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        AppLogger.info("Iniciando aplicación ElorES...");
        
        SocketClient client = SocketClient.getInstance();
        boolean connected = client.connect();
        
        if (!connected) {
            AppLogger.error("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            AppLogger.error("ERROR CRÍTICO: No se pudo conectar con ElorServ");
            AppLogger.error("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            AppLogger.error("");
            AppLogger.error("VERIFICA:");
            AppLogger.error("  1. El servidor ElorServ está ejecutándose");
            AppLogger.error("  2. El servidor escucha en localhost:9000");
            AppLogger.error("  3. La base de datos está disponible");
            AppLogger.error("  4. No hay firewall bloqueando la conexión");
            AppLogger.error("");
            System.exit(1);
        }
        
        AppLogger.info("✓ Conexión establecida con ElorServ");
        System.out.println();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            AppLogger.info("Cerrando aplicación...");
            client.disconnect();
            AppLogger.info("✓ Aplicación cerrada correctamente");
        }));
        
        // Iniciar interfaz gráfica
        AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        AppLogger.info("INICIANDO INTERFAZ GRÁFICA");
        AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println();
        
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            AppLogger.info("✓ Look and Feel configurado");
        } catch (Exception e) {
            AppLogger.error("No se pudo configurar el Look and Feel", e);
        }
        
        // Lanzar ventana principal en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
                AppLogger.info("✓ Ventana principal mostrada");
                AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                AppLogger.info("APLICACIÓN LISTA - Esperando interacción del usuario");
                AppLogger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            } catch (Exception e) {
                AppLogger.error("Error al crear la ventana principal", e);
                System.exit(1);
            }
        });
    }
}