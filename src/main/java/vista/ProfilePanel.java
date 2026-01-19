package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;
import controller.ProfileController;
import dto.UserDTO;
import util.AppLogger;

/**
 * Panel de perfil de usuario.
 * Muestra la información del usuario logueado.
 */
public class ProfilePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private MainFrame mainFrame;
    private ProfileController controller;
    
    private JLabel lblProfilePicture;
    private JLabel lblFullName;
    private JLabel lblUsername;
    private JLabel lblEmail;
    private JLabel lblDni;
    private JLabel lblDireccion;
    private JLabel lblTelefono1;
    private JLabel lblTelefono2;
    private JLabel lblTipoUsuario;
    private JLabel lblError;
    private JButton btnBack;
    private JButton btnRefresh;

    /**
     * Crea el panel de perfil.
     */
    public ProfilePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new ProfileController();
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
        
        // Título
        JLabel lblTitle = new JLabel("Mi Perfil");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBounds(300, 20, 400, 50);
        add(lblTitle);
        
        // Panel de información
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(null);
        infoPanel.setBounds(200, 90, 600, 480);
        add(infoPanel);
        
        // Foto de perfil
        lblProfilePicture = new JLabel();
        lblProfilePicture.setHorizontalAlignment(SwingConstants.CENTER);
        lblProfilePicture.setBounds(225, 20, 150, 150);
        lblProfilePicture.setIcon(getDefaultProfileIcon());
        infoPanel.add(lblProfilePicture);
        
        // Nombre completo
        lblFullName = new JLabel("Cargando...");
        lblFullName.setHorizontalAlignment(SwingConstants.CENTER);
        lblFullName.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblFullName.setForeground(new Color(41, 128, 185));
        lblFullName.setBounds(50, 180, 500, 35);
        infoPanel.add(lblFullName);
        
        // Username
        JLabel lblUsernameLabel = new JLabel("Usuario:");
        lblUsernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsernameLabel.setBounds(50, 230, 200, 25);
        infoPanel.add(lblUsernameLabel);
        
        lblUsername = new JLabel("-");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsername.setBounds(260, 230, 290, 25);
        infoPanel.add(lblUsername);
        
        // Email
        JLabel lblEmailLabel = new JLabel("Email:");
        lblEmailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEmailLabel.setBounds(50, 265, 200, 25);
        infoPanel.add(lblEmailLabel);
        
        lblEmail = new JLabel("-");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmail.setBounds(260, 265, 290, 25);
        infoPanel.add(lblEmail);
        
        // DNI
        JLabel lblDniLabel = new JLabel("DNI:");
        lblDniLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDniLabel.setBounds(50, 300, 200, 25);
        infoPanel.add(lblDniLabel);
        
        lblDni = new JLabel("-");
        lblDni.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDni.setBounds(260, 300, 290, 25);
        infoPanel.add(lblDni);
        
        // Dirección
        JLabel lblDireccionLabel = new JLabel("Dirección:");
        lblDireccionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDireccionLabel.setBounds(50, 335, 200, 25);
        infoPanel.add(lblDireccionLabel);
        
        lblDireccion = new JLabel("-");
        lblDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDireccion.setBounds(260, 335, 290, 25);
        infoPanel.add(lblDireccion);
        
        // Teléfono 1
        JLabel lblTelefono1Label = new JLabel("Teléfono 1:");
        lblTelefono1Label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTelefono1Label.setBounds(50, 370, 200, 25);
        infoPanel.add(lblTelefono1Label);
        
        lblTelefono1 = new JLabel("-");
        lblTelefono1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTelefono1.setBounds(260, 370, 290, 25);
        infoPanel.add(lblTelefono1);
        
        // Teléfono 2
        JLabel lblTelefono2Label = new JLabel("Teléfono 2:");
        lblTelefono2Label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTelefono2Label.setBounds(50, 405, 200, 25);
        infoPanel.add(lblTelefono2Label);
        
        lblTelefono2 = new JLabel("-");
        lblTelefono2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTelefono2.setBounds(260, 405, 290, 25);
        infoPanel.add(lblTelefono2);
        
        // Tipo de usuario
        JLabel lblTipoLabel = new JLabel("Tipo de usuario:");
        lblTipoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTipoLabel.setBounds(50, 440, 200, 25);
        infoPanel.add(lblTipoLabel);
        
        lblTipoUsuario = new JLabel("-");
        lblTipoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTipoUsuario.setForeground(new Color(39, 174, 96));
        lblTipoUsuario.setBounds(260, 440, 290, 25);
        infoPanel.add(lblTipoUsuario);
        
        // Botón volver
        btnBack = new JButton("Volver al Menú");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setBackground(new Color(52, 152, 219));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setBounds(200, 590, 200, 40);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showMenuPanel();
            }
        });
        add(btnBack);
        
        // Botón refrescar
        btnRefresh = new JButton("Actualizar");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.setBackground(new Color(39, 174, 96));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setBorderPainted(false);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRefresh.setBounds(420, 590, 200, 40);
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadProfileData();
            }
        });
        add(btnRefresh);
        
        // Label de error
        lblError = new JLabel("");
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblError.setForeground(new Color(231, 76, 60));
        lblError.setBounds(200, 640, 600, 25);
        lblError.setVisible(false);
        add(lblError);
        
        AppLogger.info("ProfilePanel inicializado");
    }
    
    /**
     * Carga los datos del perfil del usuario.
     */
    public void loadProfileData() {
        hideError();
        
        // Ejecutar en un hilo separado para no bloquear la UI
        new Thread(() -> {
            try {
                AppLogger.info("Cargando datos de perfil...");
                
                UserDTO profile = controller.getUserProfile();
                
                javax.swing.SwingUtilities.invokeLater(() -> {
                    if (profile != null) {
                        updateProfileUI(profile);
                    } else {
                        showError("No se pudo cargar el perfil del usuario");
                    }
                });
                
            } catch (Exception e) {
                AppLogger.error("Error al cargar perfil", e);
                
                javax.swing.SwingUtilities.invokeLater(() -> {
                    showError("Error al conectar con el servidor");
                });
            }
        }).start();
    }
    
    /**
     * Actualiza la interfaz con los datos del perfil.
     */
    private void updateProfileUI(UserDTO user) {
        String fullName = "";
        if (user.getNombre() != null && !user.getNombre().isEmpty()) {
            fullName = user.getNombre();
            if (user.getApellidos() != null && !user.getApellidos().isEmpty()) {
                fullName += " " + user.getApellidos();
            }
        } else {
            fullName = user.getUsername();
        }
        lblFullName.setText(fullName);
        
        // Username
        lblUsername.setText(user.getUsername() != null ? user.getUsername() : "-");
        
        // Email
        lblEmail.setText(user.getEmail() != null ? user.getEmail() : "-");
        
        // DNI
        lblDni.setText(user.getDni() != null ? user.getDni() : "-");
        
        // Dirección
        lblDireccion.setText(user.getDireccion() != null ? user.getDireccion() : "-");
        
        // Teléfonos
        lblTelefono1.setText(user.getTelefono1() != null ? user.getTelefono1() : "-");
        lblTelefono2.setText(user.getTelefono2() != null ? user.getTelefono2() : "-");
        
        // Tipo de usuario
        String tipoUsuario = user.getTipoNombre() != null ? user.getTipoNombre() : "Usuario";
        lblTipoUsuario.setText(tipoUsuario.toUpperCase());
        
        // Cargar foto de perfil si existe
        if (user.getArgazkiaUrl() != null && !user.getArgazkiaUrl().isEmpty()) {
            loadProfilePicture(user.getArgazkiaUrl());
        }
        
        AppLogger.info("Perfil actualizado en la UI");
    }
    
    /**
     * Carga la foto de perfil desde una URL.
     */
    private void loadProfilePicture(String imageUrl) {
        new Thread(() -> {
            try {
                URL url = new URL(imageUrl);
                Image image = ImageIO.read(url);
                Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                
                javax.swing.SwingUtilities.invokeLater(() -> {
                    lblProfilePicture.setIcon(new ImageIcon(scaledImage));
                });
                
            } catch (Exception e) {
                AppLogger.error("Error al cargar imagen de perfil: " + e.getMessage());
                // Mantener la imagen por defecto
            }
        }).start();
    }
    
    /**
     * Obtiene el icono por defecto del perfil.
     */
    private ImageIcon getDefaultProfileIcon() {
        // Crear un icono de usuario por defecto simple
        return new ImageIcon(new java.awt.image.BufferedImage(150, 150, java.awt.image.BufferedImage.TYPE_INT_RGB) {
            {
                java.awt.Graphics2D g = createGraphics();
                g.setColor(new Color(189, 195, 199));
                g.fillRect(0, 0, 150, 150);
                g.setColor(Color.WHITE);
                g.fillOval(37, 20, 75, 75);
                g.fillOval(25, 80, 100, 100);
                g.dispose();
            }
        });
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
}
