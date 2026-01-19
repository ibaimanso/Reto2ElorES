package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import controller.LoginController;
import dto.UserDTO;
import util.AppLogger;
import javax.swing.border.EmptyBorder;
import java.awt.Cursor;
import javax.swing.ImageIcon;

/**
 * Panel de login de la aplicación ElorES.
 * Compatible con WindowBuilder.
 */
public class LoginPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblError;
    private JLabel lblLoading;
    
    private MainFrame mainFrame;
    private LoginController controller;

    /**
     * Crea el panel de login.
     */
    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new LoginController();
        controller.initialize();
        
        initComponents();
    }
    
    /**
     * Inicializa los componentes visuales.
     */
    private void initComponents() {
        setLayout(null);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(240, 240, 240));
        
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(null);
        formPanel.setBounds(325, 220, 350, 280);
        add(formPanel);
        
        JLabel lblUsername = new JLabel("Usuario:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsername.setBounds(30, 30, 290, 25);
        formPanel.add(lblUsername);
        
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setBounds(30, 60, 290, 35);
        formPanel.add(txtUsername);
        txtUsername.setColumns(10);
        
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPassword.setBounds(30, 110, 290, 25);
        formPanel.add(lblPassword);
        
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBounds(30, 140, 290, 35);
        formPanel.add(txtPassword);
        
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setBounds(30, 200, 290, 40);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        formPanel.add(btnLogin);
        
        lblError = new JLabel("");
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblError.setForeground(new Color(231, 76, 60));
        lblError.setBounds(325, 520, 350, 25);
        lblError.setVisible(false);
        add(lblError);
        
        lblLoading = new JLabel("Iniciando sesión...");
        lblLoading.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoading.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblLoading.setForeground(new Color(52, 152, 219));
        lblLoading.setBounds(325, 495, 350, 25);
        lblLoading.setVisible(false);
        add(lblLoading);
        
        JLabel logo = new JLabel("");
        logo.setIcon(new ImageIcon("resources\\logoElorrieta.png"));
        logo.setBounds(295, 81, 400, 107);
        add(logo);
        
        // Enter en password también hace login
        txtPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
    }
    
    /**
     * Maneja el proceso de login.
     */
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        // Validar campos vacíos
        if (username.isEmpty() || password.isEmpty()) {
            showError("Por favor, completa todos los campos");
            return;
        }
        
        // Mostrar estado de carga
        showLoading(true);
        hideError();
        setFormEnabled(false);
        
        // Ejecutar login en un hilo separado para no bloquear la UI
        new Thread(() -> {
            try {
                AppLogger.info("Intentando login con usuario: " + username);
                
                UserDTO user = controller.handleLogin(username, password);
                
                // Actualizar UI en el hilo de Swing
                javax.swing.SwingUtilities.invokeLater(() -> {
                    showLoading(false);
                    setFormEnabled(true);
                    
                    if (user != null) {
                        AppLogger.info("Login exitoso para: " + username);
                        onLoginSuccess(user);
                    } else {
                        AppLogger.error("Login fallido para: " + username);
                        showError("Acceso denegado. Solo profesores pueden acceder.");
                        txtPassword.setText("");
                        txtPassword.requestFocus();
                    }
                });
                
            } catch (Exception e) {
                AppLogger.error("Error durante el login", e);
                
                javax.swing.SwingUtilities.invokeLater(() -> {
                    showLoading(false);
                    setFormEnabled(true);
                    showError("Error al conectar con el servidor");
                });
            }
        }).start();
    }
    
    /**
     * Callback cuando el login es exitoso.
     */
    private void onLoginSuccess(UserDTO user) {
        AppLogger.info("Usuario autenticado: " + user.getUsername());
        
        // Establecer usuario actual en MainFrame
        mainFrame.setCurrentUser(user);
        
        // Limpiar campos
        txtUsername.setText("");
        txtPassword.setText("");
        
        // Cambiar al panel de menú
        mainFrame.showMenuPanel();
    }
    
    /**
     * Muestra u oculta el indicador de carga.
     */
    private void showLoading(boolean show) {
        lblLoading.setVisible(show);
    }
    
    /**
     * Muestra un mensaje de error.
     */
    private void showError(String message) {
        lblError.setText(message);
        lblError.setVisible(true);
    }
    
    /**
     * Oculta el mensaje de error.
     */
    private void hideError() {
        lblError.setVisible(false);
    }
    
    /**
     * Habilita o deshabilita el formulario.
     */
    private void setFormEnabled(boolean enabled) {
        txtUsername.setEnabled(enabled);
        txtPassword.setEnabled(enabled);
        btnLogin.setEnabled(enabled);
    }
}