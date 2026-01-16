package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import controller.MainController;
import util.AppLogger;

/**
 * Panel de menú principal de la aplicación ElorES.
 * Se muestra después de un login exitoso.
 */
public class MenuPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private MainFrame mainFrame;
    private MainController controller;

    /**
     * Crea el panel de menú.
     */
    public MenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new MainController();
        controller.initialize();
        
        initComponents();
    }
    
    /**
     * Inicializa los componentes visuales.
     */
    private void initComponents() {
        setLayout(null);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(236, 240, 241));
        
        // Título principal
        JLabel lblTitle = new JLabel("Menú Principal");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBounds(200, 150, 600, 60);
        add(lblTitle);
        
        // Subtítulo
        JLabel lblSubtitle = new JLabel("Bienvenido a ElorES");
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblSubtitle.setForeground(new Color(127, 140, 141));
        lblSubtitle.setBounds(200, 220, 600, 40);
        add(lblSubtitle);
        
        // Mensaje informativo
        JLabel lblMessage = new JLabel("Login exitoso - Panel de menú funcionando correctamente");
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lblMessage.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        lblMessage.setForeground(new Color(39, 174, 96));
        lblMessage.setBounds(150, 300, 700, 30);
        add(lblMessage);
        
        // Icono de éxito
        JLabel lblCheckIcon = new JLabel("✓");
        lblCheckIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblCheckIcon.setFont(new Font("Segoe UI", Font.BOLD, 72));
        lblCheckIcon.setForeground(new Color(39, 174, 96));
        lblCheckIcon.setBounds(400, 350, 200, 100);
        add(lblCheckIcon);
        
        // Nota para desarrollo
        JLabel lblNote = new JLabel("Próximamente: Horarios, Reuniones y más funcionalidades");
        lblNote.setHorizontalAlignment(SwingConstants.CENTER);
        lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNote.setForeground(new Color(149, 165, 166));
        lblNote.setBounds(200, 480, 600, 25);
        add(lblNote);
        
        AppLogger.info("MenuPanel inicializado");
    }
    
    /**
     * Actualiza la información del usuario logueado.
     */
    public void setUserInfo(String username, String fullName) {
        // TODO: Implementar cuando se agreguen componentes de perfil
        AppLogger.info("Usuario en sesión: " + username + " - " + fullName);
    }
}
