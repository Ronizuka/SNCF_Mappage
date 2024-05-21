package map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Window {

    private JFrame frame;
    private DrawingArea drawingArea;
    private String selectedEquipment;
    private Point selectedEquipmentPosition;
    private int selectedEquipmentWidth;
    private int selectedEquipmentHeight;
    private List<JMenuItem> disabledMenuItems;
    private List<JButton> disabledButtons;
    private JButton selectedButton;
    private int currentAction;

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
        menuItemExporterPlan.addActionListener(e -> exportPlanToPDF());
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
                    setSelectedButton(button);
                    if (index == 2) {
                        currentAction = DrawingArea.ACTION_MOVE;
                        drawingArea.setCurrentAction(DrawingArea.ACTION_MOVE);
                    } else if (index == 3) {
                        showDropdown();
                        currentAction = DrawingArea.ACTION_NONE;
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

        drawingArea = new DrawingArea();
        drawingArea.setCurrentAction(DrawingArea.ACTION_NONE);
        drawingArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentAction == DrawingArea.ACTION_NONE && selectedEquipment != null) {
                    Point clickedPoint = adjustPoint(e.getPoint());
                    for (DrawingArea.Baie baie : drawingArea.getBaies()) {
                        if (baie.contains(clickedPoint)) {
                            // Supprimer l'équipement existant à la position cliquée
                            DrawingArea.Equipment equipmentToRemove = baie.getEquipmentAt(clickedPoint);
                            if (equipmentToRemove != null) {
                                baie.removeEquipment(equipmentToRemove);
                            }

                            // Ajouter le nouvel équipement à la position cliquée
                            baie.addEquipment(selectedEquipment, new Point(clickedPoint.x - selectedEquipmentWidth / 2, clickedPoint.y - selectedEquipmentHeight / 2), selectedEquipmentWidth, selectedEquipmentHeight);
                            selectedEquipment = null;
                            selectedEquipmentPosition = null;
                            selectedEquipmentWidth = 0;
                            selectedEquipmentHeight = 0;
                            drawingArea.repaint();
                            break;
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

    private void exportPlanToPDF() {
        // Ouvrir un dialogue de sélection de fichier
        FileDialog fileDialog = new FileDialog(frame, "Choisir un fichier pour enregistrer le PDF", FileDialog.SAVE);
        fileDialog.setFile("*.pdf");
        fileDialog.setVisible(true);
        String directory = fileDialog.getDirectory();
        String file = fileDialog.getFile();

        if (directory != null && file != null) {
            String pdfPath = directory + file;
            if (!pdfPath.toLowerCase().endsWith(".pdf")) {
                pdfPath += ".pdf";
            }

            try {
                // Créer le document PDF en format paysage
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
                document.open();

                // Dessiner les baies et les équipements dans le PDF
                drawPlanToPDF(document, writer);

                document.close();
                JOptionPane.showMessageDialog(frame, "Plan exporté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException | DocumentException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de l'exportation du plan.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void drawPlanToPDF(Document document, PdfWriter writer) {
        PdfContentByte cb = writer.getDirectContent();
        PdfTemplate template = cb.createTemplate(document.getPageSize().getWidth(), document.getPageSize().getHeight());
        Graphics2D g2d = template.createGraphics(template.getWidth(), template.getHeight());

        double scaleX = template.getWidth() / drawingArea.getWidth();
        double scaleY = template.getHeight() / drawingArea.getHeight();
        double scale = Math.min(scaleX, scaleY);

        g2d.scale(scale, scale);
        drawingArea.printAll(g2d);

        g2d.dispose();
        cb.addTemplate(template, 0, 0);
    }

    private void setSelectedButton(JButton button) {
        if (selectedButton != null) {
            selectedButton.setBorder(null); // Reset the border of the previous selected button
        }
        selectedButton = button;
        selectedButton.setBorder(BorderFactory.createLineBorder(Color.BLUE)); // Highlight the selected button with a blue border
    }
}
