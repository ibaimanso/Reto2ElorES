package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;
import controller.MainController;
import dto.UserDTO;
import util.AppLogger;

/**
 * Panel de menú principal de la aplicación ElorES.
 * Se muestra después de un login exitoso.
 */
public class MenuPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private MainFrame mainFrame;
    private MainController controller;
    private UserDTO currentUser;
    
    private JLabel lblWelcome;
    private JButton btnProfile;
    private JButton btnHorarios;
    private JButton btnReuniones;
    private JButton btnAlumnos;
    private JButton btnLogout;

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
        
        // Logo/Título
        JLabel lblTitle = new JLabel("ElorES");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblTitle.setForeground(new Color(41, 128, 185));
        lblTitle.setBounds(300, 40, 400, 60);
        add(lblTitle);
        
        // Mensaje de bienvenida
        lblWelcome = new JLabel("Bienvenido/a");
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblWelcome.setForeground(new Color(52, 73, 94));
        lblWelcome.setBounds(250, 110, 500, 30);
        add(lblWelcome);
        
        // Panel de opciones
        JPanel optionsPanel = new JPanel();
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.setLayout(null);
        optionsPanel.setBounds(250, 170, 500, 400);
        add(optionsPanel);
        
        // Título del panel
        JLabel lblMenuTitle = new JLabel("Menú Principal");
        lblMenuTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblMenuTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblMenuTitle.setForeground(new Color(44, 62, 80));
        lblMenuTitle.setBounds(50, 20, 400, 35);
        optionsPanel.add(lblMenuTitle);
        
        // Botón Mi Perfil
        btnProfile = createMenuButton("Mi Perfil", 70);
        btnProfile.setBackground(new Color(52, 152, 219));
        btnProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showProfilePanel();
            }
        });
        optionsPanel.add(btnProfile);
        
        // Botón Horarios
        btnHorarios = createMenuButton("Mis Horarios", 130);
        btnHorarios.setBackground(new Color(155, 89, 182));
        btnHorarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showInfo("Funcionalidad de Horarios en desarrollo");
                // TODO: Implementar navegación a HorarioPanel
            }
        });
        optionsPanel.add(btnHorarios);
        
        // Botón Reuniones
        btnReuniones = createMenuButton("Mis Reuniones", 190);
        btnReuniones.setBackground(new Color(230, 126, 34));
        btnReuniones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showInfo("Funcionalidad de Reuniones en desarrollo");
                // TODO: Implementar navegación a ReunionesPanel
            }
        });
        optionsPanel.add(btnReuniones);
        
        // Botón Alumnos (solo para profesores)
        btnAlumnos = createMenuButton("Gestión de Alumnos", 250);
        btnAlumnos.setBackground(new Color(46, 204, 113));
        btnAlumnos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showInfo("Funcionalidad de Alumnos en desarrollo");
                // TODO: Implementar navegación a AlumnosPanel
            }
        });
        optionsPanel.add(btnAlumnos);
        
        // Botón Cerrar Sesión
        btnLogout = createMenuButton("Cerrar Sesión", 310);
        btnLogout.setBackground(new Color(231, 76, 60));
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
        optionsPanel.add(btnLogout);
        
        // Footer
        JLabel lblFooter = new JLabel("Sistema Educativo Elorrieta - v1.0");
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblFooter.setForeground(new Color(149, 165, 166));
        lblFooter.setBounds(300, 610, 400, 20);
        add(lblFooter);
        
        AppLogger.info("MenuPanel inicializado");
    }
    
    /**
     * Crea un botón de menú con estilo consistente.
     */
    private JButton createMenuButton(String text, int yPosition) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBounds(50, yPosition, 400, 45);
        return button;
    }
    
    /**
     * Actualiza la información del usuario logueado.
     */
    public void setUserInfo(UserDTO user) {
        this.currentUser = user;
        
        if (user != null) {
            String welcomeText = "Bienvenido/a";
            if (user.getNombre() != null && !user.getNombre().isEmpty()) {
                welcomeText = "Bienvenido/a, " + user.getNombre();
                if (user.getApellidos() != null && !user.getApellidos().isEmpty()) {
                    welcomeText += " " + user.getApellidos();
                }
            } else if (user.getUsername() != null) {
                welcomeText = "Bienvenido/a, " + user.getUsername();
            }
            
            lblWelcome.setText(welcomeText);
            
            // Mostrar/ocultar botones según el tipo de usuario
            updateButtonVisibility(user);
            
            AppLogger.info("Usuario en sesión: " + user.getUsername());
        }
    }
    
    /**
     * Actualiza la visibilidad de los botones según el tipo de usuario.
     */
    private void updateButtonVisibility(UserDTO user) {
        // Por ahora todos los botones están visibles
        // En el futuro se puede personalizar según el tipo de usuario
        String tipoId = user.getTipoId();
        
        if (tipoId != null) {
            // Si es alumno (tipo 4), ocultar gestión de alumnos
            if ("4".equals(tipoId)) {
                btnAlumnos.setVisible(false);
            }
        }
    }
    
    /**
     * Maneja el cierre de sesión.
     */
    private void handleLogout() {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar",
            javax.swing.JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            AppLogger.info("Cerrando sesión...");
            currentUser = null;
            mainFrame.showLoginPanel();
        }
    }
}