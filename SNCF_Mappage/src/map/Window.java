package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Window {

    private JFrame frame;
    private DrawingArea drawingArea;
    private String selectedEquipment;
    private Point selectedEquipmentPosition;
    private int selectedEquipmentWidth;
    private int selectedEquipmentHeight;
    private List<JMenuItem> disabledMenuItems;
    private List<JButton> disabledButtons;

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
            enableMenuItemsAndButtons();
            JFrame nbBaiesFrame = new JFrame("Nombre de Baies");
            nbBaiesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nbBaiesFrame.getContentPane().add(new NbBaies(this));
            nbBaiesFrame.pack();
            nbBaiesFrame.setLocationRelativeTo(null);
            nbBaiesFrame.setVisible(true);
        });
        menuItemNouveauPlan.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemNouveauPlan);

        JMenuItem menuItemImporterPlan = new JMenuItem("Importer plan");
        menuItemImporterPlan.addActionListener(e -> {
            enableMenuItemsAndButtons();
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
            disconnect();
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

        disabledButtons = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            gbc.gridy = i;
            JButton button = new JButton();

            // Charger les images et les affecter comme icônes des boutons
            try {
                URL imageUrl = null;
                switch (i) {
                    case 0:
                        imageUrl = new URL("https://github.com/Ronizuka/SNCF_Mappage/blob/Graphique/SNCF_Mappage/Icon/fleche_recule.png?raw=true");
                        break;
                    case 1:
                        imageUrl = new URL("https://github.com/Ronizuka/SNCF_Mappage/blob/Graphique/SNCF_Mappage/Icon/fleche_avance.png?raw=true");
                        break;
                    case 2:
                        imageUrl = new URL("https://github.com/Ronizuka/SNCF_Mappage/blob/Graphique/SNCF_Mappage/Icon/souris.png?raw=true");
                        break;
                    case 3:
                        imageUrl = new URL("https://github.com/Ronizuka/SNCF_Mappage/blob/Graphique/SNCF_Mappage/Icon/puce-electronique.png?raw=true");
                        break;
                    case 4:
                        imageUrl = new URL("https://github.com/Ronizuka/SNCF_Mappage/blob/Graphique/SNCF_Mappage/Icon/cable.png?raw=true");
                        break;
                    case 5:
                        imageUrl = new URL("https://github.com/Ronizuka/SNCF_Mappage/blob/Graphique/SNCF_Mappage/Icon/croix.png?raw=true");
                        break;
                }
                if (imageUrl != null) {
                    BufferedImage img = ImageIO.read(imageUrl);
                    Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    ImageIcon newIcon = new ImageIcon(newImg);
                    button.setIcon(newIcon);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            button.setFocusPainted(false);
            button.setPreferredSize(new Dimension(60, 60));

            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (index == 3) {
                        showDropdown();
                    }
                }
            });

            sidePanel.add(button, gbc);
            button.setEnabled(false); // Désactiver les boutons
            disabledButtons.add(button);
        }

        frame.add(sidePanel, BorderLayout.WEST);

        drawingArea = new DrawingArea();
        drawingArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedEquipment != null) {
                    Point clickedPoint = adjustPoint(e.getPoint());
                    for (DrawingArea.Baie baie : drawingArea.getBaies()) {
                        if (baie.contains(clickedPoint)) {
                            baie.addEquipment(selectedEquipment, clickedPoint, selectedEquipmentWidth, selectedEquipmentHeight);
                            break;
                        }
                    }
                    selectedEquipment = null;
                    selectedEquipmentPosition = null;
                    selectedEquipmentWidth = 0;
                    selectedEquipmentHeight = 0;
                }
            }
        });
        drawingArea.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (selectedEquipment != null) {
                    selectedEquipmentPosition = adjustPoint(e.getPoint());
                    drawingArea.repaint();
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(drawingArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        disabledMenuItems = new ArrayList<>();
        for (Component menuComponent : menuBar.getComponents()) {
            if (menuComponent instanceof JMenu) {
                JMenu menu = (JMenu) menuComponent;
                for (Component menuItemComponent : menu.getMenuComponents()) {
                    if (menuItemComponent instanceof JMenuItem && menuItemComponent != menuItemNouveauPlan && menuItemComponent != menuItemImporterPlan && menuItemComponent != menuItemQuitter) {
                        JMenuItem menuItem = (JMenuItem) menuItemComponent;
                        menuItem.setEnabled(false); // Désactiver les éléments de menu
                        disabledMenuItems.add(menuItem);
                    }
                }
            }
        }
    }

    public void addBaies(int nombreBaies) {
        drawingArea.addBaies(nombreBaies);
    }

    private void enableMenuItemsAndButtons() {
        for (JMenuItem menuItem : disabledMenuItems) {
            menuItem.setEnabled(true);
        }
        for (JButton button : disabledButtons) {
            button.setEnabled(true);
        }
    }

    private void showDropdown() {
        JComboBox<String> dropdown = new JComboBox<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT nomEquipt, longueur, hauteur FROM equipements");
                List<String> equipementNames = new ArrayList<>();
                while (resultSet.next()) {
                    equipementNames.add(resultSet.getString("nomEquipt"));
                }
                resultSet.close();
                statement.close();
                connection.close();

                for (String name : equipementNames) {
                    dropdown.addItem(name);
                }

                int result = JOptionPane.showConfirmDialog(frame, dropdown, "Sélectionner un équipement", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    selectedEquipment = (String) dropdown.getSelectedItem();
                    System.out.println("Equipement sélectionné : " + selectedEquipment);
                    connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("SELECT longueur, hauteur FROM equipements WHERE nomEquipt = '" + selectedEquipment + "'");
                    if (resultSet.next()) {
                        selectedEquipmentWidth = resultSet.getInt("longueur");
                        selectedEquipmentHeight = resultSet.getInt("hauteur");
                        System.out.println("Longueur de l'équipement : " + selectedEquipmentWidth);
                        System.out.println("Hauteur de l'équipement : " + selectedEquipmentHeight);
                        drawingArea.selectComponent(selectedEquipment, selectedEquipmentWidth, selectedEquipmentHeight);
                    }
                    resultSet.close();
                    statement.close();
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Point adjustPoint(Point p) {
        return new Point((int) ((p.x - drawingArea.getOffsetX()) / drawingArea.getScale()), 
                         (int) ((p.y - drawingArea.getOffsetY()) / drawingArea.getScale()));
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

    private void disconnect() {
        // Fermer la fenêtre principale
        frame.dispose();
        // Relancer la fenêtre de connexion
        Login loginWindow = new Login();
        loginWindow.addLoginSuccessListener(new Login.LoginSuccessListener() {
            @Override
            public void onLoginSuccess() {
                // Une fois que l'utilisateur est connecté, fermer la fenêtre de connexion
                JFrame frame = new JFrame("SNCF Mappage - Connexion");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(loginWindow);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                
            }
        });
        JFrame frame = new JFrame("SNCF Mappage - Connexion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(loginWindow);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}