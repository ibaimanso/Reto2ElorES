package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import dto.UserDTO;
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
    private ProfilePanel profilePanel;
    
    // Usuario actual
    private UserDTO currentUser;
    
    // Nombres de las vistas
    public static final String LOGIN_VIEW = "LOGIN";
    public static final String MENU_VIEW = "MENU";
    public static final String PROFILE_VIEW = "PROFILE";

    /**
     * Crea el frame principal.
     */
    public MainFrame() {
        initComponents();
        createPanels();
        showLoginPanel();
        setResizable(false);
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
        
        // Crear panel de menú
        menuPanel = new MenuPanel(this);
        contentPane.add(menuPanel, MENU_VIEW);
        
        // Crear panel de perfil
        profilePanel = new ProfilePanel(this);
        contentPane.add(profilePanel, PROFILE_VIEW);
        
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
        if (currentUser != null) {
            menuPanel.setUserInfo(currentUser);
        }
        cardLayout.show(contentPane, MENU_VIEW);
        AppLogger.debug("Mostrando panel de menú");
    }
    
    /**
     * Muestra el panel de perfil.
     */
    public void showProfilePanel() {
        profilePanel.loadProfileData();
        cardLayout.show(contentPane, PROFILE_VIEW);
        AppLogger.debug("Mostrando panel de perfil");
    }
    
    /**
     * Establece el usuario actual después del login.
     */
    public void setCurrentUser(UserDTO user) {
        this.currentUser = user;
        AppLogger.info("Usuario actual establecido: " + (user != null ? user.getUsername() : "null"));
    }
    
    /**
     * Obtiene el usuario actual.
     */
    public UserDTO getCurrentUser() {
        return currentUser;
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