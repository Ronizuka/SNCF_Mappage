package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import map.Login.LoginSuccessListener;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Window {

    private JFrame frame;

    public Window() {
        initialize();
    }

    private void initialize() {
        // Création de la fenêtre principale
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiser la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("SNCF Mappage"); // Définir le titre de la fenêtre

        Font titleFont = new Font("Tahoma", Font.BOLD, 18);
        frame.setFont(titleFont);

        // Création de la barre de menu
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Menu "Fichier"
        JMenu menuFichier = new JMenu("Fichier");
        menuBar.add(menuFichier);

        // Élément de menu "Nouveau plan"
        JMenuItem menuItemNouveauPlan = new JMenuItem("Nouveau plan");
        menuItemNouveauPlan.addActionListener(e -> {
            // Ouvrir une nouvelle fenêtre pour définir le nombre de baies
            JFrame nbBaiesFrame = new JFrame("Nombre de Baies");
            nbBaiesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nbBaiesFrame.getContentPane().add(new NbBaies());
            nbBaiesFrame.pack();
            nbBaiesFrame.setLocationRelativeTo(null);
            nbBaiesFrame.setVisible(true);
        });
        menuItemNouveauPlan.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemNouveauPlan);

        // Élément de menu "Importer plan"
        JMenuItem menuItemImporterPlan = new JMenuItem("Importer plan");
        menuItemImporterPlan.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Option Importer plan sélectionnée");
        });
        menuItemImporterPlan.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemImporterPlan);

        // Élément de menu "Enregistrer"
        JMenuItem menuItemEnregistrer = new JMenuItem("Enregistrer");
        menuItemEnregistrer.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Option Enregistrer sélectionnée");
        });
        menuItemEnregistrer.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemEnregistrer);

        // Élément de menu "Exporter plan"
        JMenuItem menuItemExporterPlan = new JMenuItem("Exporter plan");
        menuItemExporterPlan.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Option Exporter plan sélectionnée");
        });
        menuFichier.add(menuItemExporterPlan);

        // Ajouter un séparateur
        menuFichier.addSeparator();

        // Élément de menu "Quitter"
        JMenuItem menuItemQuitter = new JMenuItem("Quitter");
        menuItemQuitter.addActionListener(e -> {
            System.exit(0);
        });
        menuItemQuitter.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemQuitter);

        // Menu "Connexion"
        JMenu menuConnexion = new JMenu("Connexion");
        menuBar.add(menuConnexion);

        // Élément de menu "Se connecter avec un autre compte"
        JMenuItem menuItemSeConnecter = new JMenuItem("Se connecter avec un autre compte");
        menuItemSeConnecter.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Option Se connecter avec un autre compte sélectionnée");
        });
        menuConnexion.add(menuItemSeConnecter);

        // Élément de menu "Déconnexion"
        JMenuItem menuItemDeconnexion = new JMenuItem("Déconnexion");
        menuItemDeconnexion.addActionListener(e -> {
            disconnect();
        });
        menuConnexion.add(menuItemDeconnexion);

        frame.getAccessibleContext().setAccessibleDescription("SNCF Mappage");

        // Création du panneau latéral avec des boutons
        JPanel sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setPreferredSize(new Dimension(100, frame.getHeight())); // Définir la taille préférée
        sidePanel.setBackground(new Color(34, 34, 34)); // Définir la couleur de fond

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5, 5, 5, 5);

     // Création des boutons et ajout au panneau latéral
        for (int i = 0; i < 6; i++) {
            gbc.gridy = i;
            JButton button = new JButton();

            // Charger l'image et l'affecter comme icône du premier bouton uniquement
         // Charger l'image et l'affecter comme icône du premier bouton uniquement
            if (i == 0) {
                try {
                    URL imageUrl = new URL("https://github.com/Ronizuka/SNCF_Mappage/blob/Graphique/SNCF_Mappage/Icon/fleche_recule.png?raw=true");
                    BufferedImage img = ImageIO.read(imageUrl);
                    Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Redimensionner l'image
                    ImageIcon newIcon = new ImageIcon(newImg);
                    button.setIcon(newIcon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

         // Charger l'image et l'affecter comme icône du premier bouton uniquement
            if (i == 1) {
                try {
                    URL imageUrl = new URL("https://github.com/Ronizuka/SNCF_Mappage/blob/Graphique/SNCF_Mappage/Icon/fleche_avance.png?raw=true");
                    BufferedImage img = ImageIO.read(imageUrl);
                    Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Redimensionner l'image
                    ImageIcon newIcon = new ImageIcon(newImg);
                    button.setIcon(newIcon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
         // Charger l'image et l'affecter comme icône du premier bouton uniquement
            if (i == 2) {
                try {
                    URL imageUrl = new URL("https://github.com/Ronizuka/SNCF_Mappage/blob/Graphique/SNCF_Mappage/Icon/souris.png?raw=true");
                    BufferedImage img = ImageIO.read(imageUrl);
                    Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Redimensionner l'image
                    ImageIcon newIcon = new ImageIcon(newImg);
                    button.setIcon(newIcon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

         // Charger l'image et l'affecter comme icône du premier bouton uniquement
            if (i == 3) {
                try {
                    URL imageUrl = new URL("https://github.com/Ronizuka/SNCF_Mappage/blob/Graphique/SNCF_Mappage/Icon/puce-electronique.png?raw=true");
                    BufferedImage img = ImageIO.read(imageUrl);
                    Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Redimensionner l'image
                    ImageIcon newIcon = new ImageIcon(newImg);
                    button.setIcon(newIcon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

         // Charger l'image et l'affecter comme icône du premier bouton uniquement
            if (i == 4) {
                try {
                    URL imageUrl = new URL("https://github.com/Ronizuka/SNCF_Mappage/blob/Graphique/SNCF_Mappage/Icon/cable.png?raw=true");
                    BufferedImage img = ImageIO.read(imageUrl);
                    Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Redimensionner l'image
                    ImageIcon newIcon = new ImageIcon(newImg);
                    button.setIcon(newIcon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
         // Charger l'image et l'affecter comme icône du premier bouton uniquement
            if (i == 5) {
                try {
                    URL imageUrl = new URL("https://github.com/Ronizuka/SNCF_Mappage/blob/Graphique/SNCF_Mappage/Icon/croix.png?raw=true");
                    BufferedImage img = ImageIO.read(imageUrl);
                    Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Redimensionner l'image
                    ImageIcon newIcon = new ImageIcon(newImg);
                    button.setIcon(newIcon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            button.setFocusPainted(false);
            button.setPreferredSize(new Dimension(50, 50));

            sidePanel.add(button, gbc);
        }


        frame.add(sidePanel, BorderLayout.WEST);

        // Création d'une zone de dessin et ajout à un panneau de défilement
        DrawingArea drawingArea = new DrawingArea();
        JScrollPane scrollPane = new JScrollPane(drawingArea);
        frame.add(scrollPane, BorderLayout.CENTER);
    }
    
 // Méthode de déconnexion
    private void disconnect() {
        // Fermer la fenêtre principale
        frame.dispose();
        // Relancer la fenêtre de connexion
        Login loginWindow = new Login();
        loginWindow.addLoginSuccessListener(new LoginSuccessListener() {
            @Override
            public void onLoginSuccess() {
                // Une fois que l'utilisateur est connecté, fermer la fenêtre de connexion
                JFrame frame = new JFrame("SNCF Mappage - Connexion");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(loginWindow);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                // Lancer l'application principale
                Window window = new Window();
                window.launch();
            }
        });
        JFrame frame = new JFrame("SNCF Mappage - Connexion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(loginWindow);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
