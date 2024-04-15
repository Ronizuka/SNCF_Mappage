package map;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class Window {

    private JFrame frame;

    /**
     * Create the application.
     */
    public Window() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Définit la fenêtre en plein écran
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Définit le titre de la fenêtre
        frame.setTitle("SNCF Mappage");

        // Change la police du titre de la fenêtre
        Font titleFont = new Font("Tahoma", Font.BOLD, 18);
        frame.setFont(titleFont);

        // Création du menu
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Menu "Fichier"
        JMenu menuFichier = new JMenu("Fichier");
        menuBar.add(menuFichier);

        // Option "Nouveau plan"
        JMenuItem menuItemNouveauPlan = new JMenuItem("Nouveau plan");
        menuItemNouveauPlan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Afficher la fenêtre NbBaies lorsque l'option "Nouveau plan" est sélectionnée
                JFrame nbBaiesFrame = new JFrame("Nombre de Baies");
                nbBaiesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nbBaiesFrame.getContentPane().add(new NbBaies());
                nbBaiesFrame.pack();
                nbBaiesFrame.setLocationRelativeTo(null);
                nbBaiesFrame.setVisible(true);
            }
        });
        menuItemNouveauPlan.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemNouveauPlan);

        // Option "Importer plan"
        JMenuItem menuItemImporterPlan = new JMenuItem("Importer plan");
        menuItemImporterPlan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici le code à exécuter lorsque l'option "Importer plan" est sélectionnée
                JOptionPane.showMessageDialog(frame, "Option Importer plan sélectionnée");
            }
        });
        menuItemImporterPlan.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemImporterPlan);

        // Option "Enregistrer"
        JMenuItem menuItemEnregistrer = new JMenuItem("Enregistrer");
        menuItemEnregistrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici le code à exécuter lorsque l'option "Enregistrer" est sélectionnée
                JOptionPane.showMessageDialog(frame, "Option Enregistrer sélectionnée");
            }
        });
        menuItemEnregistrer.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemEnregistrer);

        // Option "Exporter plan"
        JMenuItem menuItemExporterPlan = new JMenuItem("Exporter plan");
        menuItemExporterPlan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici le code à exécuter lorsque l'option "Exporter plan" est sélectionnée
                JOptionPane.showMessageDialog(frame, "Option Exporter plan sélectionnée");
            }
        });
        menuFichier.add(menuItemExporterPlan);

        // Séparateur
        menuFichier.addSeparator();

        // Option "Quitter"
        JMenuItem menuItemQuitter = new JMenuItem("Quitter");
        menuItemQuitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici le code à exécuter lorsque l'option "Quitter" est sélectionnée
                System.exit(0);
            }
        });
        menuItemQuitter.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemQuitter);

        // Menu "Connexion"
        JMenu menuConnexion = new JMenu("Connexion");
        menuBar.add(menuConnexion);

        // Option "Se connecter avec un autre compte"
        JMenuItem menuItemSeConnecter = new JMenuItem("Se connecter avec un autre compte");
        menuItemSeConnecter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici le code à exécuter lorsque l'option "Se connecter avec un autre compte" est sélectionnée
                JOptionPane.showMessageDialog(frame, "Option Se connecter avec un autre compte sélectionnée");
            }
        });
        menuConnexion.add(menuItemSeConnecter);

        // Option "Déconnexion"
        JMenuItem menuItemDeconnexion = new JMenuItem("Déconnexion");
        menuItemDeconnexion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici le code à exécuter lorsque l'option "Déconnexion" est sélectionnée
                JOptionPane.showMessageDialog(frame, "Option Déconnexion sélectionnée");
            }
        });
        menuConnexion.add(menuItemDeconnexion);

        // Récupère le cadre de la fenêtre et centre le titre
        frame.getAccessibleContext().setAccessibleDescription("SNCF Mappage");
    }

    /**
     * Launch the application.
     */
    public void launch() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
