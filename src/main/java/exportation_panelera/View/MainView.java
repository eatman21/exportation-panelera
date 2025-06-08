package exportation_panelera.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

// Importaciones explícitas de las clases que estás usando
import exportation_panelera.View.ExportationDelivery;
import exportation_panelera.View.SignInForm;
import exportation_panelera.View.DeliveryManagementForm;

/**
 * Main View for the Exportation Panelera Management System
 * Provides navigation to different modules of the application
 */
public class MainView extends JFrame {
    private static final Logger logger = Logger.getLogger(MainView.class.getName());
    
    // Colors for UI consistency
    private static final Color PRIMARY_COLOR = new Color(24, 53, 103);
    private static final Color SECONDARY_COLOR = new Color(0, 119, 182);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color PANEL_COLOR = new Color(237, 242, 247);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    
    // UI Components
    private JPanel mainPanel;
    private JLabel lblTitle;
    private JButton btnExportation;
    private JButton btnDelivery;
    private JButton btnSignout;
    
    // Constructor
    public MainView() {
        setTitle("Exportation Panelera");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        logger.log(Level.INFO, "Main View initialized");
    }
    
    private void initComponents() {
        // Initialize your UI components here
        mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setLayout(new BorderLayout());
        
        // Create header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(800, 80));
        headerPanel.setLayout(new BorderLayout());
        
        lblTitle = new JLabel("Exportation Panelera Management System");
        lblTitle.setFont(TITLE_FONT);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.add(lblTitle, BorderLayout.CENTER);
        
        // Create navigation panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(PANEL_COLOR);
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        navigationPanel.setLayout(new GridLayout(3, 1, 0, 20));
        
        // Create buttons
        btnExportation = createNavigationButton("Exportation Information", "View and manage exportation data");
        btnDelivery = createNavigationButton("Delivery Information", "Track and manage deliveries");
        btnSignout = createNavigationButton("Sign Out", "Exit the application");
        
        navigationPanel.add(btnExportation);
        navigationPanel.add(btnDelivery);
        navigationPanel.add(btnSignout);
        
        // Add to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(navigationPanel, BorderLayout.CENTER);
        
        // Add action listeners
        btnExportation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openExportationForm();
            }
        });
        
        btnDelivery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDeliveryManagementForm();
            }
        });
        
        btnSignout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signOut();
            }
        });
        
        // Set content pane
        setContentPane(mainPanel);
    }
    
    private JButton createNavigationButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(SECONDARY_COLOR);
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        button.setToolTipText(tooltip);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect for better UX
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 99, 162)); // Darker shade for hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
            }
        });
        
        return button;
    }
    
    private void openExportationForm() {
        // Open the exportation information form
        try {
            logger.log(Level.INFO, "Opening Exportation Information form");
            // Use the unified ExportationDelivery form
            ExportationDelivery exportationForm = new ExportationDelivery();
            exportationForm.setVisible(true);
            this.setVisible(false); // Hide main view
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error opening Exportation Information", e);
            JOptionPane.showMessageDialog(this, 
                "Error opening Exportation Information: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openDeliveryManagementForm() {
        // Open the delivery management form
        try {
            logger.log(Level.INFO, "Opening Delivery Management form");
            DeliveryManagementForm deliveryForm = new DeliveryManagementForm();
            deliveryForm.setVisible(true);
            this.setVisible(false); // Hide main view
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error opening Delivery Management form", e);
            JOptionPane.showMessageDialog(this, 
                "Error opening Delivery Management form: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void signOut() {
        // Sign out and return to login screen
        int option = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to sign out?",
            "Sign Out", JOptionPane.YES_NO_OPTION);
            
        if (option == JOptionPane.YES_OPTION) {
            logger.log(Level.INFO, "User signing out");
            SignInForm signingForm = new SignInForm();
            signingForm.setVisible(true);
            this.dispose(); // Close main view
        }
    }
    
    /**
     * Main method for running the application
     * @param args command line arguments
     */
    public static void main(String args[]) {
        try {
            // For better look and feel
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, "Error setting look and feel", e);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }
}