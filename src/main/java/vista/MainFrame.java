package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import util.AppLogger;

/**
 * Ventana principal de la aplicación ElorES.
 * Usa CardLayout para cambiar entre diferentes paneles (Login, Menu, etc.)
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private CardLayout cardLayout;
    
    // Paneles de la aplicación
    private LoginPanel loginPanel;
    private MenuPanel menuPanel;
    
    // Nombres de las vistas
    public static final String LOGIN_VIEW = "LOGIN";
    public static final String MENU_VIEW = "MENU";

    /**
     * Crea el frame principal.
     */
    public MainFrame() {
        initComponents();
        createPanels();
        showLoginPanel();
    }
    
    /**
     * Inicializa los componentes de la ventana.
     */
    private void initComponents() {
        setTitle("ElorES - Sistema Educativo Elorrieta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 700));
        setMinimumSize(new Dimension(800, 600));
        
        // CardLayout para cambiar entre paneles
        cardLayout = new CardLayout();
        contentPane = new JPanel(cardLayout);
        setContentPane(contentPane);
        
        pack();
        setLocationRelativeTo(null); // Centrar en pantalla
        
        AppLogger.info("MainFrame inicializado");
    }
    
    /**
     * Crea todos los paneles de la aplicación.
     */
    private void createPanels() {
        // Crear panel de login
        loginPanel = new LoginPanel(this);
        contentPane.add(loginPanel, LOGIN_VIEW);
        
        // Crear panel de menú (se inicializa después del login)
        menuPanel = new MenuPanel(this);
        contentPane.add(menuPanel, MENU_VIEW);
        
        AppLogger.info("Paneles creados correctamente");
    }
    
    /**
     * Muestra el panel de login.
     */
    public void showLoginPanel() {
        cardLayout.show(contentPane, LOGIN_VIEW);
        AppLogger.debug("Mostrando panel de login");
    }
    
    /**
     * Muestra el panel de menú principal.
     */
    public void showMenuPanel() {
        cardLayout.show(contentPane, MENU_VIEW);
        AppLogger.debug("Mostrando panel de menú");
    }
    
    /**
     * Muestra un mensaje de información.
     */
    public void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Muestra un mensaje de error.
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Muestra un mensaje de advertencia.
     */
    public void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Muestra un diálogo de confirmación.
     */
    public boolean showConfirmation(String message) {
        int result = JOptionPane.showConfirmDialog(this, message, "Confirmación", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
}
