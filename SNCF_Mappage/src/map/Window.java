package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            final int index = i; // Déclarer une variable finale pour capturer la valeur de i
            gbc.gridy = i;
            JButton button = new JButton();

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (index == 3) {
                        showDropdown();
                    }
                }
            });

            button.setFocusPainted(false);
            button.setPreferredSize(new Dimension(60, 60));

            sidePanel.add(button, gbc);
        }


        frame.add(sidePanel, BorderLayout.WEST);

        DrawingArea drawingArea = new DrawingArea();
        JScrollPane scrollPane = new JScrollPane(drawingArea);
        frame.add(scrollPane, BorderLayout.CENTER);
    }

    private void showDropdown() {
        // Créer une liste déroulante pour afficher les noms d'équipement depuis la base de données
        JComboBox<String> dropdown = new JComboBox<>();
        try {
            // Établir la connexion à la base de données
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            if (connection != null) {
                // Exécuter la requête SQL pour récupérer les noms d'équipement depuis la table 'equipements'
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT nomEquipt FROM equipements");
                // Parcourir les résultats et ajouter chaque nom d'équipement à la liste déroulante
                List<String> equipementNames = new ArrayList<>();
                while (resultSet.next()) {
                    equipementNames.add(resultSet.getString("nomEquipt"));
                }
                resultSet.close();
                statement.close();
                connection.close();

                // Ajouter les noms d'équipement à la liste déroulante
                for (String name : equipementNames) {
                    dropdown.addItem(name);
                }

                // Afficher la liste déroulante dans une boîte de dialogue
                JOptionPane.showMessageDialog(frame, dropdown, "Sélectionner un équipement", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
}
