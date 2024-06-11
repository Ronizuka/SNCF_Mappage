package map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Window {

    private JFrame frame;
    private DrawingArea drawingArea;
    private String selectedEquipment;
    private Point selectedEquipmentPosition;
    private int selectedEquipmentWidth;
    private int selectedEquipmentHeight;
    private int selectedEquipmentId;
    private List<JMenuItem> disabledMenuItems;
    private List<JButton> disabledButtons;
    private JButton selectedButton;
    private int currentAction;
    private MaterialManager materialManager;
    private TrainManager trainManager; 
    private ContextMenuManager contextMenuManager;
    private UserManager userManager;
    private int userRights;

    public Window(int userRights) {
        this.userRights = userRights;
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
            BouttonManager.enableMenuItemsAndButtons(disabledMenuItems, disabledButtons);

            trainManager.creerTrain(new TrainManager.TrainCreationCallback() {
                @Override
                public void onTrainCreated() {
                    JFrame nbChassisFrame = new JFrame("Nombre de Châssis");
                    nbChassisFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    nbChassisFrame.getContentPane().add(new NbChassis(Window.this));
                    nbChassisFrame.pack();
                    nbChassisFrame.setLocationRelativeTo(null);
                    nbChassisFrame.setVisible(true);
                }
            });
        });
        menuItemNouveauPlan.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemNouveauPlan);

        JMenuItem menuItemImporterPlan = new JMenuItem("Importer plan");
        menuItemImporterPlan.addActionListener(e -> Import.loadPlan(frame, drawingArea, disabledMenuItems, disabledButtons));
        menuItemImporterPlan.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemImporterPlan);

        JMenuItem menuItemEnregistrer = new JMenuItem("Enregistrer");
        menuItemEnregistrer.addActionListener(e -> Import.savePlan(frame, drawingArea.getBaies()));
        menuItemEnregistrer.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
        menuFichier.add(menuItemEnregistrer);

        JMenuItem menuItemExporterPlan = new JMenuItem("Exporter plan");
        menuItemExporterPlan.addActionListener(e -> ExportPDF.exportPlanToPDF(frame, drawingArea));
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

        JMenuItem menuItemDeconnexion = new JMenuItem("Déconnexion");
        menuItemDeconnexion.addActionListener(e -> {
            disconnect();
        });
        menuConnexion.add(menuItemDeconnexion);

        JMenu menuMateriel = new JMenu("Matériel");
        menuBar.add(menuMateriel);

        JMenuItem menuItemCreerMateriel = new JMenuItem("Créer un matériel");
        menuItemCreerMateriel.addActionListener(e -> {
            materialManager.creerMateriel();
        });
        menuMateriel.add(menuItemCreerMateriel);

        JMenuItem menuItemModifierMateriel = new JMenuItem("Modifier un matériel");
        menuItemModifierMateriel.addActionListener(e -> {
            materialManager.modifierMateriel();
        });
        menuMateriel.add(menuItemModifierMateriel);

        JMenuItem menuItemSupprimerMateriel = new JMenuItem("Supprimer un matériel");
        menuItemSupprimerMateriel.addActionListener(e -> {
            materialManager.supprimerMateriel();
        });
        menuMateriel.add(menuItemSupprimerMateriel);
        if (userRights == 2) {  // Only add the "Utilisateurs" menu if the user has admin rights
            JMenu menuUtilisateur = new JMenu("Utilisateurs");
            menuBar.add(menuUtilisateur);

            JMenuItem menuItemCreerUtilisateur = new JMenuItem("Créer un utilisateur");
            menuItemCreerUtilisateur.addActionListener(e -> {
                userManager.creerUtilisateur();
            });
            menuUtilisateur.add(menuItemCreerUtilisateur);

            JMenuItem menuItemModifierUtilisateur = new JMenuItem("Modifier un utilisateur");
            menuItemModifierUtilisateur.addActionListener(e -> {
                userManager.modifierUtilisateur();
            });
            menuUtilisateur.add(menuItemModifierUtilisateur);

            JMenuItem menuItemSupprimerUtilisateur = new JMenuItem("Supprimer un utilisateur");
            menuItemSupprimerUtilisateur.addActionListener(e -> {
                userManager.supprimerUtilisateur();
            });
            menuUtilisateur.add(menuItemSupprimerUtilisateur);
        }
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
                    setSelectedButton(button);
                    if (index == 2) {
                        currentAction = DrawingArea.ACTION_MOVE;
                        drawingArea.setCurrentAction(DrawingArea.ACTION_MOVE);
                    } else if (index == 3) {
                        showDropdown();
                        currentAction = DrawingArea.ACTION_NONE;
                    } else if (index == 5) {
                        currentAction = DrawingArea.ACTION_DELETE;
                        drawingArea.setCurrentAction(DrawingArea.ACTION_DELETE);
                    } else {
                        currentAction = DrawingArea.ACTION_NONE;
                        drawingArea.setCurrentAction(DrawingArea.ACTION_NONE);
                    }
                }
            });

            sidePanel.add(button, gbc);
            button.setEnabled(false); // Désactiver les boutons
            disabledButtons.add(button);
        }

        frame.add(sidePanel, BorderLayout.WEST);

        contextMenuManager = new ContextMenuManager(new ContextMenuManager.ContextMenuListener() {
            @Override
            public void onInformationConnecteurs(int equipmentId) {
                showConnectorInformation(equipmentId);
            }

            @Override
            public void onCreerLiaison(int equipmentId) {
                showCreateLiaisonPopup(equipmentId);
            }
        });

        drawingArea = new DrawingArea(contextMenuManager);
        drawingArea.setCurrentAction(DrawingArea.ACTION_NONE);
        drawingArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentAction == DrawingArea.ACTION_NONE && selectedEquipment != null) {
                    Point clickedPoint = adjustPoint(e.getPoint());
                    for (DrawingArea.Baie baie : drawingArea.getBaies()) {
                        if (baie.contains(clickedPoint)) {
                            DrawingArea.Equipment equipmentToRemove = baie.getEquipmentAt(clickedPoint);
                            if (equipmentToRemove != null) {
                                baie.removeEquipment(equipmentToRemove);
                            }

                            DrawingArea.Equipment newEquipment = drawingArea.new Equipment(selectedEquipment, new Point(clickedPoint.x - selectedEquipmentWidth / 2, clickedPoint.y - selectedEquipmentHeight / 2), selectedEquipmentWidth, selectedEquipmentHeight, selectedEquipmentId);
                            baie.addEquipment(newEquipment);
                            selectedEquipment = null;
                            selectedEquipmentPosition = null;
                            selectedEquipmentWidth = 0;
                            selectedEquipmentHeight = 0;
                            selectedEquipmentId = 0;  // Réinitialisez l'ID de l'équipement
                            drawingArea.repaint();
                            break;
                        }
                    }
                } else if (currentAction == DrawingArea.ACTION_DELETE) {
                    Point clickedPoint = adjustPoint(e.getPoint());
                    for (DrawingArea.Baie baie : drawingArea.getBaies()) {
                        if (baie.contains(clickedPoint)) {
                            DrawingArea.Equipment equipmentToRemove = baie.getEquipmentAt(clickedPoint);
                            if (equipmentToRemove != null) {
                                baie.removeEquipment(equipmentToRemove);
                                drawingArea.repaint();
                                return;
                            }
                        }
                    }
                }
            }
        });
        drawingArea.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (currentAction == DrawingArea.ACTION_NONE && selectedEquipment != null) {
                    selectedEquipmentPosition = adjustPoint(e.getPoint());
                    drawingArea.repaint();
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(drawingArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        disabledMenuItems = new ArrayList<>();
        BouttonManager.disableMenuItemsAndButtons(frame, disabledMenuItems, disabledButtons);

        materialManager = new MaterialManager(frame);
        userManager = new UserManager(frame);
        trainManager = new TrainManager(frame); // Initialize TrainManager
    }

    public void addChassis(int nombreChassis, int largeurChassis, int hauteurChassis) {
        drawingArea.addChassis(nombreChassis, largeurChassis, hauteurChassis);
    }

    private void showDropdown() {
        JComboBox<String> dropdown = new JComboBox<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT idEquipt, nomEquipt, longueur, hauteur FROM equipements");
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
                    resultSet = statement.executeQuery("SELECT idEquipt, longueur, hauteur FROM equipements WHERE nomEquipt = '" + selectedEquipment + "'");
                    if (resultSet.next()) {
                        selectedEquipmentWidth = resultSet.getInt("longueur");
                        selectedEquipmentHeight = resultSet.getInt("hauteur");
                        selectedEquipmentId = resultSet.getInt("idEquipt");
                        System.out.println("Longueur de l'équipement : " + selectedEquipmentWidth);
                        System.out.println("Hauteur de l'équipement : " + selectedEquipmentHeight);
                        System.out.println("ID de l'équipement : " + selectedEquipmentId);
                        drawingArea.selectComponent(selectedEquipment, selectedEquipmentWidth, selectedEquipmentHeight, selectedEquipmentId);
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

    private void showConnectorInformation(int equipmentId) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            String query = "SELECT codeCon FROM connecteursequipt WHERE idEquipt = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, equipmentId);
            ResultSet resultSet = statement.executeQuery();

            List<String> connectors = new ArrayList<>();
            while (resultSet.next()) {
                connectors.add(resultSet.getString("codeCon"));
            }

            resultSet.close();
            statement.close();
            connection.close();

            if (connectors.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Cet équipement n'a pas de connecteurs.");
            } else {
                JOptionPane.showMessageDialog(frame, "Connecteurs: " + String.join(", ", connectors));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors de la récupération des connecteurs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showCreateLiaisonPopup(int equipmentId) {
        try {
            System.out.println("Looking for equipment with ID: " + equipmentId);
            DrawingArea.Equipment targetEquipment = null;
            for (DrawingArea.Baie baie : drawingArea.getBaies()) {
                for (DrawingArea.Equipment equipment : baie.getEquipments()) {
                    if (equipment.getId() == equipmentId) {
                        targetEquipment = equipment;
                        break;
                    }
                }
            }

            if (targetEquipment == null) {
                System.out.println("Target equipment not found.");
                JOptionPane.showMessageDialog(frame, "Erreur: Équipement non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            System.out.println("Found target equipment: " + targetEquipment.getName());

            List<String> targetConnectors = new ArrayList<>();
            List<EquipmentWithConnectors> equipmentsWithConnectors = new ArrayList<>();

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "")) {
                // Récupérer les connecteurs de l'équipement cible
                String connectorQuery = "SELECT codeCon FROM connecteursequipt WHERE idEquipt = ?";
                PreparedStatement statement = connection.prepareStatement(connectorQuery);
                statement.setInt(1, targetEquipment.getId());
                ResultSet connectorResultSet = statement.executeQuery();

                while (connectorResultSet.next()) {
                    targetConnectors.add(connectorResultSet.getString("codeCon"));
                }

                connectorResultSet.close();
                statement.close();

                // Récupérer les équipements avec leurs connecteurs
                for (DrawingArea.Baie baie : drawingArea.getBaies()) {
                    for (DrawingArea.Equipment equipment : baie.getEquipments()) {
                        if (equipment.getId() != targetEquipment.getId()) {
                            List<String> connectors = new ArrayList<>();
                            connectorQuery = "SELECT codeCon FROM connecteursequipt WHERE idEquipt = ?";
                            statement = connection.prepareStatement(connectorQuery);
                            statement.setInt(1, equipment.getId());
                            connectorResultSet = statement.executeQuery();

                            while (connectorResultSet.next()) {
                                connectors.add(connectorResultSet.getString("codeCon"));
                            }

                            if (!connectors.isEmpty()) {
                                equipmentsWithConnectors.add(new EquipmentWithConnectors(equipment, connectors));
                            }

                            connectorResultSet.close();
                            statement.close();
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la récupération des connecteurs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Création de la fenêtre contextuelle
            JFrame popupFrame = new JFrame("Créer une liaison");
            popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            popupFrame.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 10, 10, 10);

            // Connecteurs de l'équipement cible
            JLabel labelTargetConnector = new JLabel("Lier le connecteur ");
            popupFrame.add(labelTargetConnector, gbc);

            gbc.gridx++;
            JComboBox<String> targetConnectorDropdown = new JComboBox<>(targetConnectors.toArray(new String[0]));
            popupFrame.add(targetConnectorDropdown, gbc);

            // Texte intermédiaire
            gbc.gridx++;
            JLabel labelWith = new JLabel(" avec l'équipement ");
            popupFrame.add(labelWith, gbc);

            // Connecteurs des autres équipements
            gbc.gridx++;
            JComboBox<String> equipmentDropdown = new JComboBox<>(equipmentsWithConnectors.stream().map(e -> e.equipment.getName()).toArray(String[]::new));
            popupFrame.add(equipmentDropdown, gbc);

            gbc.gridx++;
            JLabel labelAndConnector = new JLabel(" et ce connecteur ");
            popupFrame.add(labelAndConnector, gbc);

            gbc.gridx++;
            JComboBox<String> otherConnectorDropdown = new JComboBox<>();
            popupFrame.add(otherConnectorDropdown, gbc);

            equipmentDropdown.addActionListener(e -> {
                int selectedIndex = equipmentDropdown.getSelectedIndex();
                if (selectedIndex >= 0) {
                    EquipmentWithConnectors selectedEquipment = equipmentsWithConnectors.get(selectedIndex);
                    otherConnectorDropdown.setModel(new DefaultComboBoxModel<>(selectedEquipment.connectors.toArray(new String[0])));
                }
            });

            if (!equipmentsWithConnectors.isEmpty()) {
                equipmentDropdown.setSelectedIndex(0);
            }

            // Bouton pour créer la liaison
            gbc.gridx++;
            JButton createLinkButton = new JButton("Créer la liaison");
            createLinkButton.addActionListener(e -> {
                String selectedTargetConnector = (String) targetConnectorDropdown.getSelectedItem();
                String selectedOtherConnector = (String) otherConnectorDropdown.getSelectedItem();
                if (selectedTargetConnector != null && selectedOtherConnector != null) {
                    System.out.println("Creating link between " + selectedTargetConnector + " and " + selectedOtherConnector);
                    // Ajoutez ici le code pour enregistrer la liaison dans la base de données
                    popupFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(popupFrame, "Veuillez sélectionner les connecteurs à lier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });
            popupFrame.add(createLinkButton, gbc);

            popupFrame.pack();
            popupFrame.setLocationRelativeTo(frame);
            popupFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors de la récupération des équipements.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class EquipmentWithConnectors {
        DrawingArea.Equipment equipment;
        List<String> connectors;

        EquipmentWithConnectors(DrawingArea.Equipment equipment, List<String> connectors) {
            this.equipment = equipment;
            this.connectors = connectors;
        }
    }


    private Point adjustPoint(Point p) {
        return new Point((int) ((p.x - drawingArea.getOffsetX()) / drawingArea.getScale()), (int) ((p.y - drawingArea.getOffsetY()) / drawingArea.getScale()));
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
        frame.dispose();
        Login loginWindow = new Login();
        loginWindow.addLoginSuccessListener(new Login.LoginSuccessListener() {
            @Override
            public void onLoginSuccess(int userRights) {
                JFrame frame = new JFrame("SNCF Mappage - Connexion");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(loginWindow);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                Window window = new Window(userRights);
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

    private void setSelectedButton(JButton button) {
        if (selectedButton != null) {
            selectedButton.setBorder(null); // Reset the border of the previous selected button
        }
        selectedButton = button;
        selectedButton.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3)); // Highlight the selected button with a thicker blue border
    }
}
