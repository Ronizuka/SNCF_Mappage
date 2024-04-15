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
import javax.swing.JPanel;

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
                // Ajoutez ici le code à exécuter lorsque l'option "Nouveau plan" est sélectionnée
                JOptionPane.showMessageDialog(frame, "Option Nouveau plan sélectionnée");
            }
        });
        menuFichier.add(menuItemNouveauPlan);

        // Option "Importer plan"
        JMenuItem menuItemImporterPlan = new JMenuItem("Importer plan");
        menuItemImporterPlan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici le code à exécuter lorsque l'option "Importer plan" est sélectionnée
                JOptionPane.showMessageDialog(frame, "Option Importer plan sélectionnée");
            }
        });
        menuFichier.add(menuItemImporterPlan);

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
        menuFichier.add(menuItemQuitter);

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
