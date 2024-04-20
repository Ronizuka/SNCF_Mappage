package map;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window {

    private JFrame frame;

    public Window() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("SNCF Mappage");

        Font titleFont = new Font("Tahoma", Font.BOLD, 18);
        frame.setFont(titleFont);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menuFichier = new JMenu("Fichier");
        menuBar.add(menuFichier);

        JMenuItem menuItemNouveauPlan = new JMenuItem("Nouveau plan");
        menuItemNouveauPlan.addActionListener(e -> {
            JFrame nbBaiesFrame = new JFrame("Nombre de Baies");
            nbBaiesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nbBaiesFrame.getContentPane().add(new NbBaies());
            nbBaiesFrame.pack();
            nbBaiesFrame.setLocationRelativeTo(null);
            nbBaiesFrame.setVisible(true);
        });
        menuItemNouveauPlan.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemNouveauPlan);

        JMenuItem menuItemImporterPlan = new JMenuItem("Importer plan");
        menuItemImporterPlan.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Option Importer plan sélectionnée");
        });
        menuItemImporterPlan.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemImporterPlan);

        JMenuItem menuItemEnregistrer = new JMenuItem("Enregistrer");
        menuItemEnregistrer.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Option Enregistrer sélectionnée");
        });
        menuItemEnregistrer.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemEnregistrer);

        JMenuItem menuItemExporterPlan = new JMenuItem("Exporter plan");
        menuItemExporterPlan.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Option Exporter plan sélectionnée");
        });
        menuFichier.add(menuItemExporterPlan);

        menuFichier.addSeparator();

        JMenuItem menuItemQuitter = new JMenuItem("Quitter");
        menuItemQuitter.addActionListener(e -> {
            System.exit(0);
        });
        menuItemQuitter.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemQuitter);

        JMenu menuConnexion = new JMenu("Connexion");
        menuBar.add(menuConnexion);

        JMenuItem menuItemSeConnecter = new JMenuItem("Se connecter avec un autre compte");
        menuItemSeConnecter.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Option Se connecter avec un autre compte sélectionnée");
        });
        menuConnexion.add(menuItemSeConnecter);

        JMenuItem menuItemDeconnexion = new JMenuItem("Déconnexion");
        menuItemDeconnexion.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Option Déconnexion sélectionnée");
        });
        menuConnexion.add(menuItemDeconnexion);

        frame.getAccessibleContext().setAccessibleDescription("SNCF Mappage");
        
        JPanel sidePanel = new JPanel(new GridBagLayout()); 
        sidePanel.setPreferredSize(new Dimension(100, frame.getHeight())); 
        sidePanel.setBackground(new Color(34, 34, 34)); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.VERTICAL; 
        gbc.insets = new Insets(5, 5, 5, 5); 

        for (int i = 0; i < 6; i++) {
            gbc.gridy = i; 
            JButton button = new JButton();

            // Charger l'image et l'affecter comme icône du premier bouton uniquement
            if (i == 0) {
                ImageIcon icon = new ImageIcon("puce-electronique.png"); // Remplacez "puce-electronique.png" par le chemin de votre image
                button.setIcon(icon);
            }

            button.setFocusPainted(false); 
            button.setPreferredSize(new Dimension(50, 50)); 

            sidePanel.add(button, gbc);
        }

        frame.add(sidePanel, BorderLayout.WEST);
       
        DrawingArea drawingArea = new DrawingArea();
        JScrollPane scrollPane = new JScrollPane(drawingArea); 
        frame.add(scrollPane, BorderLayout.CENTER); 
    }

    public void launch() {
        EventQueue.invokeLater(() -> {
            try {
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
