package map;
<<<<<<< Updated upstream

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window() {
        setTitle("Application Principale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Taille de la fenêtre principale
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        
        // Création d'un onglet pour le menu
        MenuPanel menuPanel = new MenuPanel();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Menu", menuPanel);

        // Création d'un onglet pour le choix du nombre de baies
        NbChoixBaiesPanel nbchoixBaiesPanel = new NbChoixBaiesPanel();
        tabbedPane.addTab("Choix du Nombre de Baies", nbchoixBaiesPanel);
        
        // Ajout des onglets à la fenêtre principale
        add(tabbedPane);
    }
=======
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.net.URL;


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
        ImageIcon saveIcon = new ImageIcon("chemin/vers/votre/logo/save.png");
        Image resizedSaveIcon = saveIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        menuItemEnregistrer.setIcon(new ImageIcon(resizedSaveIcon));
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
                
            	if (i == 0) {    
            		if (i == 0) {    
            		    ImageIcon buttonIcon = new ImageIcon("D:/SNCF_Mappage/SNCF_Mappage/Icon/composants.png");
            		   // Image img = buttonIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            		    button.setIcon(buttonIcon);
            		}

               
            }

             
         button.setPreferredSize(new Dimension(60, 60)); 

            sidePanel.add(button, gbc);
        }

        frame.add(sidePanel, BorderLayout.WEST);
       
        DrawingArea drawingArea = new DrawingArea();
        JScrollPane scrollPane = new JScrollPane(drawingArea); 
        frame.add(scrollPane, BorderLayout.CENTER); 
        }
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
>>>>>>> Stashed changes
}
