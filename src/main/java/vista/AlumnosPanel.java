package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.imageio.ImageIO;
import controller.AlumnosController;
import dto.UserDTO;
import util.AppLogger;
import java.util.List;

/**
 * Panel de consulta de alumnos para profesores.
 * Permite visualizar, filtrar y seleccionar alumnos.
 */
public class AlumnosPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private MainFrame mainFrame;
    private AlumnosController controller;
    
    private JComboBox<String> cmbCiclo;
    private JComboBox<String> cmbCurso;
    private JList<String> listAlumnos;
    private DefaultListModel<String> listModel;
    private JButton btnBack;
    private JButton btnRefresh;
    private JButton btnClearFilters;
    private JLabel lblError;
    private JLabel lblLoading;
    
    // Panel de detalle del alumno seleccionado
    private JLabel lblAlumnoFoto;
    private JLabel lblAlumnoNombre;
    private JLabel lblAlumnoApellidos;
    private JLabel lblAlumnoEmail;
    private JLabel lblAlumnoUsername;
    private JPanel detallePanel;
    
    // Lista completa de alumnos (sin filtrar)
    private List<UserDTO> alumnosCompletos;
    
    // Mapeo de IDs de ciclos
    private static final String[] CICLOS = {
        "Todos los ciclos",
        "DAM",
        "DAW", 
        "OTROS",
        "ASIR",
        "SMR"
    };
    
    private static final String[] CURSOS = {
        "Todos los cursos",
        "1º Curso",
        "2º Curso"
    };

    /**
     * Crea el panel de alumnos.
     */
    public AlumnosPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new AlumnosController();
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
        JLabel lblTitle = new JLabel("Consultar Alumnos");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBounds(300, 20, 400, 50);
        add(lblTitle);
        
        // Panel de filtros
        JPanel filtrosPanel = new JPanel();
        filtrosPanel.setBackground(Color.WHITE);
        filtrosPanel.setLayout(null);
        filtrosPanel.setBounds(50, 90, 300, 480);
        add(filtrosPanel);
        
        JLabel lblFiltros = new JLabel("Filtros");
        lblFiltros.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblFiltros.setBounds(20, 15, 260, 25);
        filtrosPanel.add(lblFiltros);
        
        // Filtro por ciclo
        JLabel lblCiclo = new JLabel("Ciclo:");
        lblCiclo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblCiclo.setBounds(20, 55, 260, 20);
        filtrosPanel.add(lblCiclo);
        
        cmbCiclo = new JComboBox<>(CICLOS);
        cmbCiclo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbCiclo.setBounds(20, 80, 260, 30);
        cmbCiclo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyFilters();
            }
        });
        filtrosPanel.add(cmbCiclo);
        
        // Filtro por curso
        JLabel lblCurso = new JLabel("Curso:");
        lblCurso.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblCurso.setBounds(20, 125, 260, 20);
        filtrosPanel.add(lblCurso);
        
        cmbCurso = new JComboBox<>(CURSOS);
        cmbCurso.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbCurso.setBounds(20, 150, 260, 30);
        cmbCurso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyFilters();
            }
        });
        filtrosPanel.add(cmbCurso);
        
        // Lista de alumnos
        JLabel lblListado = new JLabel("Listado de Alumnos:");
        lblListado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblListado.setBounds(20, 195, 260, 20);
        filtrosPanel.add(lblListado);
        
        listModel = new DefaultListModel<>();
        listAlumnos = new JList<>(listModel);
        listAlumnos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        listAlumnos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listAlumnos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onAlumnoSelected();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(listAlumnos);
        scrollPane.setBounds(20, 220, 260, 200);
        filtrosPanel.add(scrollPane);
        
        // Botón limpiar filtros
        btnClearFilters = new JButton("Limpiar Filtros");
        btnClearFilters.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnClearFilters.setBackground(new Color(149, 165, 166));
        btnClearFilters.setForeground(Color.WHITE);
        btnClearFilters.setBorderPainted(false);
        btnClearFilters.setFocusPainted(false);
        btnClearFilters.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnClearFilters.setBounds(20, 435, 260, 30);
        btnClearFilters.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFilters();
            }
        });
        filtrosPanel.add(btnClearFilters);
        
        // Panel de detalle del alumno
        detallePanel = new JPanel();
        detallePanel.setBackground(Color.WHITE);
        detallePanel.setLayout(null);
        detallePanel.setBounds(370, 90, 580, 480);
        add(detallePanel);
        
        JLabel lblDetalle = new JLabel("Detalle del Alumno");
        lblDetalle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblDetalle.setHorizontalAlignment(SwingConstants.CENTER);
        lblDetalle.setBounds(20, 15, 540, 25);
        detallePanel.add(lblDetalle);
        
        // Foto del alumno
        lblAlumnoFoto = new JLabel();
        lblAlumnoFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblAlumnoFoto.setBounds(190, 60, 200, 200);
        lblAlumnoFoto.setBorder(new LineBorder(new Color(189, 195, 199), 2));
        lblAlumnoFoto.setIcon(getDefaultProfileIcon());
        detallePanel.add(lblAlumnoFoto);
        
        // Nombre
        JLabel lblNombreLabel = new JLabel("Nombre:");
        lblNombreLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombreLabel.setBounds(80, 280, 420, 25);
        detallePanel.add(lblNombreLabel);
        
        lblAlumnoNombre = new JLabel("-");
        lblAlumnoNombre.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblAlumnoNombre.setForeground(new Color(41, 128, 185));
        lblAlumnoNombre.setBounds(80, 305, 420, 30);
        detallePanel.add(lblAlumnoNombre);
        
        // Apellidos
        JLabel lblApellidosLabel = new JLabel("Apellidos:");
        lblApellidosLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblApellidosLabel.setBounds(80, 345, 420, 25);
        detallePanel.add(lblApellidosLabel);
        
        lblAlumnoApellidos = new JLabel("-");
        lblAlumnoApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblAlumnoApellidos.setForeground(new Color(41, 128, 185));
        lblAlumnoApellidos.setBounds(80, 370, 420, 30);
        detallePanel.add(lblAlumnoApellidos);
        
        // Email
        JLabel lblEmailLabel = new JLabel("Email:");
        lblEmailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEmailLabel.setBounds(80, 410, 420, 25);
        detallePanel.add(lblEmailLabel);
        
        lblAlumnoEmail = new JLabel("-");
        lblAlumnoEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblAlumnoEmail.setBounds(80, 435, 420, 25);
        detallePanel.add(lblAlumnoEmail);
        
        // Mensaje inicial
        JLabel lblSeleccione = new JLabel("Seleccione un alumno para ver sus detalles");
        lblSeleccione.setHorizontalAlignment(SwingConstants.CENTER);
        lblSeleccione.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblSeleccione.setForeground(new Color(149, 165, 166));
        lblSeleccione.setBounds(80, 180, 420, 30);
        detallePanel.add(lblSeleccione);
        
        // Botones inferiores
        btnBack = new JButton("Volver al Menú");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setBackground(new Color(52, 152, 219));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setBounds(370, 590, 200, 40);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showMenuPanel();
            }
        });
        add(btnBack);
        
        btnRefresh = new JButton("Actualizar Lista");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.setBackground(new Color(39, 174, 96));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setBorderPainted(false);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRefresh.setBounds(590, 590, 200, 40);
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAlumnos();
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
        
        // Label de carga
        lblLoading = new JLabel("Cargando alumnos...");
        lblLoading.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoading.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblLoading.setForeground(new Color(52, 152, 219));
        lblLoading.setBounds(200, 640, 600, 25);
        lblLoading.setVisible(false);
        add(lblLoading);
        
        AppLogger.info("AlumnosPanel inicializado");
    }
    
    /**
     * Carga la lista de alumnos desde el servidor.
     */
    public void loadAlumnos() {
        hideError();
        showLoading(true);
        clearDetalleAlumno();
        
        new Thread(() -> {
            try {
                AppLogger.info("Cargando lista de alumnos...");
                
                List<UserDTO> alumnos = controller.getAlumnos();
                
                javax.swing.SwingUtilities.invokeLater(() -> {
                    showLoading(false);
                    
                    if (alumnos != null && !alumnos.isEmpty()) {
                        alumnosCompletos = alumnos;
                        updateAlumnosList(alumnos);
                        AppLogger.info("✓ Lista de alumnos cargada: " + alumnos.size() + " alumnos");
                    } else if (alumnos != null && alumnos.isEmpty()) {
                        showError("No se encontraron alumnos");
                        listModel.clear();
                    } else {
                        showError("Error al cargar la lista de alumnos");
                    }
                });
                
            } catch (Exception e) {
                AppLogger.error("Error al cargar alumnos", e);
                
                javax.swing.SwingUtilities.invokeLater(() -> {
                    showLoading(false);
                    showError("Error al conectar con el servidor");
                });
            }
        }).start();
    }
    
    /**
     * Actualiza la lista visual de alumnos.
     */
    private void updateAlumnosList(List<UserDTO> alumnos) {
        listModel.clear();
        
        for (UserDTO alumno : alumnos) {
            String displayName = alumno.getUsername();
            if (alumno.getNombre() != null && !alumno.getNombre().isEmpty()) {
                displayName = alumno.getNombre();
                if (alumno.getApellidos() != null && !alumno.getApellidos().isEmpty()) {
                    displayName += " " + alumno.getApellidos();
                }
            }
            listModel.addElement(displayName);
        }
    }
    
    /**
     * Maneja la selección de un alumno en la lista.
     */
    private void onAlumnoSelected() {
        int selectedIndex = listAlumnos.getSelectedIndex();
        
        if (selectedIndex >= 0 && alumnosCompletos != null && selectedIndex < alumnosCompletos.size()) {
            UserDTO alumno = alumnosCompletos.get(selectedIndex);
            showDetalleAlumno(alumno);
        }
    }
    
    /**
     * Muestra el detalle de un alumno seleccionado.
     */
    private void showDetalleAlumno(UserDTO alumno) {
        if (alumno == null) {
            clearDetalleAlumno();
            return;
        }
        
        // Nombre
        String nombre = alumno.getNombre() != null ? alumno.getNombre() : "-";
        lblAlumnoNombre.setText(nombre);
        
        // Apellidos
        String apellidos = alumno.getApellidos() != null ? alumno.getApellidos() : "-";
        lblAlumnoApellidos.setText(apellidos);
        
        // Email
        String email = alumno.getEmail() != null ? alumno.getEmail() : "-";
        lblAlumnoEmail.setText(email);
        
        // Cargar foto si existe
        if (alumno.getArgazkiaUrl() != null && !alumno.getArgazkiaUrl().isEmpty()) {
            loadAlumnoFoto(alumno.getArgazkiaUrl());
        } else {
            lblAlumnoFoto.setIcon(getDefaultProfileIcon());
        }
        
        AppLogger.info("Mostrando detalle de alumno: " + alumno.getUsername());
    }
    
    /**
     * Limpia el detalle del alumno.
     */
    private void clearDetalleAlumno() {
        lblAlumnoNombre.setText("-");
        lblAlumnoApellidos.setText("-");
        lblAlumnoEmail.setText("-");
        lblAlumnoFoto.setIcon(getDefaultProfileIcon());
    }
    
    /**
     * Carga la foto del alumno desde una URL.
     */
    private void loadAlumnoFoto(String imageUrl) {
        new Thread(() -> {
            try {
                URL url = new URL(imageUrl);
                Image image = ImageIO.read(url);
                Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                
                javax.swing.SwingUtilities.invokeLater(() -> {
                    lblAlumnoFoto.setIcon(new ImageIcon(scaledImage));
                });
                
            } catch (Exception e) {
                AppLogger.error("Error al cargar foto del alumno: " + e.getMessage());
            }
        }).start();
    }
    
    /**
     * Aplica los filtros seleccionados.
     */
    private void applyFilters() {
        if (alumnosCompletos == null || alumnosCompletos.isEmpty()) {
            return;
        }
        
        int cicloIndex = cmbCiclo.getSelectedIndex();
        int cursoIndex = cmbCurso.getSelectedIndex();
        
        // Si no hay filtros, mostrar todos
        if (cicloIndex == 0 && cursoIndex == 0) {
            updateAlumnosList(alumnosCompletos);
            return;
        }
        
        // Filtrar por ciclo desde el servidor si está seleccionado
        if (cicloIndex > 0) {
            hideError();
            showLoading(true);
            
            final int finalCursoIndex = cursoIndex; // Hacer final para uso en lambda
            
            // Mapear el índice del combo al ID real del ciclo en la BD
            // Índice 0 = "Todos", 1 = DAM (id=1), 2 = DAW (id=2), etc.
            final int cicloId = cicloIndex; // El índice ya coincide con el ID del ciclo
            
            new Thread(() -> {
                try {
                    List<UserDTO> alumnosFiltrados = controller.filterAlumnosByCiclo(cicloId);
                    
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        showLoading(false);
                        
                        if (alumnosFiltrados != null) {
                            // Aplicar filtro de curso si está seleccionado
                            List<UserDTO> resultado = alumnosFiltrados;
                            if (finalCursoIndex > 0) {
                                resultado = filterByCursoLocal(alumnosFiltrados, finalCursoIndex);
                            }
                            
                            updateAlumnosList(resultado);
                            AppLogger.info("Filtrado aplicado: " + resultado.size() + " alumnos del ciclo " + cicloId);
                        } else {
                            showError("Error al filtrar alumnos");
                        }
                    });
                    
                } catch (Exception e) {
                    AppLogger.error("Error al filtrar", e);
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        showLoading(false);
                        showError("Error al aplicar filtros");
                    });
                }
            }).start();
        } else {
            // Solo filtro de curso (filtrado local)
            List<UserDTO> alumnosFiltrados = filterByCursoLocal(alumnosCompletos, cursoIndex);
            updateAlumnosList(alumnosFiltrados);
        }
    }
    
    /**
     * Filtra alumnos por curso de forma local.
     */
    private List<UserDTO> filterByCursoLocal(List<UserDTO> alumnos, int cursoIndex) {
        if (cursoIndex == 0 || alumnos == null || alumnos.isEmpty()) {
            return alumnos;
        }
        
        // cursoIndex 1 = 1º Curso, cursoIndex 2 = 2º Curso
        final int cursoFiltro = cursoIndex;
        
        List<UserDTO> filtrados = new java.util.ArrayList<>();
        for (UserDTO alumno : alumnos) {
            if (alumno.getCurso() != null && alumno.getCurso() == cursoFiltro) {
                filtrados.add(alumno);
            }
        }
        
        AppLogger.info("Filtrado local por curso " + cursoFiltro + ": " + filtrados.size() + " alumnos");
        return filtrados;
    }
    
    /**
     * Limpia los filtros aplicados.
     */
    private void clearFilters() {
        cmbCiclo.setSelectedIndex(0);
        cmbCurso.setSelectedIndex(0);
        
        if (alumnosCompletos != null) {
            updateAlumnosList(alumnosCompletos);
        }
    }
    
    /**
     * Obtiene el icono por defecto para la foto de perfil.
     */
    private ImageIcon getDefaultProfileIcon() {
        return new ImageIcon(new java.awt.image.BufferedImage(200, 200, java.awt.image.BufferedImage.TYPE_INT_RGB) {
            {
                java.awt.Graphics2D g = createGraphics();
                g.setColor(new Color(189, 195, 199));
                g.fillRect(0, 0, 200, 200);
                g.setColor(Color.WHITE);
                g.fillOval(50, 30, 100, 100);
                g.fillOval(37, 110, 126, 126);
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
    
    /**
     * Muestra u oculta el indicador de carga.
     */
    private void showLoading(boolean show) {
        lblLoading.setVisible(show);
    }
}
