package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ContextMenuManager {
    private DrawingArea drawingArea;

    public ContextMenuManager(DrawingArea drawingArea) {
        this.drawingArea = drawingArea;
    }

    public void showContextMenu(Component invoker, Point point, DrawingArea.Equipment equipment) {
        JPopupMenu contextMenu = new JPopupMenu();

        JMenuItem infoConnecteursItem = new JMenuItem("Info Connecteurs");
        infoConnecteursItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInfoConnecteurs(equipment);
            }
        });
        contextMenu.add(infoConnecteursItem);

        JMenuItem creerLiaisonItem = new JMenuItem("Créer une liaison");
        contextMenu.add(creerLiaisonItem);

        JMenuItem modifierLiaisonItem = new JMenuItem("Modifier une liaison");
        contextMenu.add(modifierLiaisonItem);

        JMenuItem supprimerLiaisonItem = new JMenuItem("Supprimer une liaison");
        contextMenu.add(supprimerLiaisonItem);

        contextMenu.show(invoker, point.x, point.y);
    }

    private void showInfoConnecteurs(DrawingArea.Equipment equipment) {
        int idEquipt = equipment.getIdEquipt();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            String query = "SELECT c.codeCon, c.information, c.descriptionCon, c.typeCon " +
                    "FROM connecteurs c " +
                    "INNER JOIN connecteursequipt ce ON c.codeCon = ce.codeCon " +
                    "WHERE ce.idEquipt = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idEquipt);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                StringBuilder message = new StringBuilder();
                do {
                    String codeCon = resultSet.getString("codeCon");
                    String information = resultSet.getString("information");
                    String descriptionCon = resultSet.getString("descriptionCon");
                    String typeCon = resultSet.getString("typeCon");
                    message.append("Code Con: ").append(codeCon).append("\n")
                            .append("Information: ").append(information).append("\n")
                            .append("Description: ").append(descriptionCon).append("\n")
                            .append("Type: ").append(typeCon).append("\n\n");
                } while (resultSet.next());

                JOptionPane.showMessageDialog(drawingArea,
                        message.toString(),
                        "Info Connecteurs",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                int option = JOptionPane.showOptionDialog(drawingArea,
                        "Il n'y a pas de connecteurs pour cet équipement.",
                        "Info Connecteurs",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Ok", "Ajouter un connecteur"},
                        "Ok");

                if (option == 1) {
                    // Logique pour ajouter un connecteur
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(drawingArea, "Erreur lors de la récupération des connecteurs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
