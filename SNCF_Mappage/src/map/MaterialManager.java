package map;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialManager {

    private JFrame frame;

    public MaterialManager(JFrame frame) {
        this.frame = frame;
    }

    public void creerMateriel() {
        JTextField nomField = new JTextField();
        JTextField longueurField = new JTextField();
        JTextField hauteurField = new JTextField();
        JTextField profondeurField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField refDocField = new JTextField();
        JTextField symboleField = new JTextField();
        JTextField refConstructeurField = new JTextField();
        JTextField puissanceMaxField = new JTextField();
        JTextField alimMinField = new JTextField();
        JTextField alimMaxField = new JTextField();
        JTextField alimNomField = new JTextField();
        JTextField tarifField = new JTextField();
        JComboBox<String> fixationField = new JComboBox<>(new String[]{"ND", "R", "F", "PT", "PF", "PS", "Dv"});
        JTextField poidsField = new JTextField();
        JTextField protectionCCField = new JTextField();
        JComboBox<String> typeField = new JComboBox<>(new String[]{"PRM", "STD"});

        JPanel panel = new JPanel(new GridLayout(18, 2));
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Longueur (int):"));
        panel.add(longueurField);
        panel.add(new JLabel("Hauteur (int):"));
        panel.add(hauteurField);
        panel.add(new JLabel("Profondeur (int):"));
        panel.add(profondeurField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Ref Doc:"));
        panel.add(refDocField);
        panel.add(new JLabel("Symbole:"));
        panel.add(symboleField);
        panel.add(new JLabel("Ref Constructeur:"));
        panel.add(refConstructeurField);
        panel.add(new JLabel("Puissance Max (float):"));
        panel.add(puissanceMaxField);
        panel.add(new JLabel("Alim Min (float):"));
        panel.add(alimMinField);
        panel.add(new JLabel("Alim Max (float):"));
        panel.add(alimMaxField);
        panel.add(new JLabel("Alim Nom (float):"));
        panel.add(alimNomField);
        panel.add(new JLabel("Tarif (double):"));
        panel.add(tarifField);
        panel.add(new JLabel("Fixation:"));
        panel.add(fixationField);
        panel.add(new JLabel("Poids (double):"));
        panel.add(poidsField);
        panel.add(new JLabel("Protection CC (int):"));
        panel.add(protectionCCField);
        panel.add(new JLabel("Type:"));
        panel.add(typeField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Créer un nouveau matériel", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (nomField.getText().isEmpty() || longueurField.getText().isEmpty() || hauteurField.getText().isEmpty() || profondeurField.getText().isEmpty() ||
                    descriptionField.getText().isEmpty() || refDocField.getText().isEmpty() || symboleField.getText().isEmpty() ||
                    refConstructeurField.getText().isEmpty() || puissanceMaxField.getText().isEmpty() || alimMinField.getText().isEmpty() ||
                    alimMaxField.getText().isEmpty() || alimNomField.getText().isEmpty() || tarifField.getText().isEmpty() ||
                    fixationField.getSelectedItem() == null || poidsField.getText().isEmpty() || protectionCCField.getText().isEmpty() ||
                    typeField.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(frame, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nom = nomField.getText();
            int longueur = Integer.parseInt(longueurField.getText());
            int hauteur = Integer.parseInt(hauteurField.getText());
            int profondeur = Integer.parseInt(profondeurField.getText());
            String description = descriptionField.getText();
            String refDoc = refDocField.getText();
            String symbole = symboleField.getText();
            String refConstructeur = refConstructeurField.getText();
            float puissanceMax = Float.parseFloat(puissanceMaxField.getText());
            float alimMin = Float.parseFloat(alimMinField.getText());
            float alimMax = Float.parseFloat(alimMaxField.getText());
            float alimNom = Float.parseFloat(alimNomField.getText());
            double tarif = Double.parseDouble(tarifField.getText());
            String fixation = (String) fixationField.getSelectedItem();
            double poids = Double.parseDouble(poidsField.getText());
            int protectionCC = Integer.parseInt(protectionCCField.getText());
            String type = (String) typeField.getSelectedItem();

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
                String query = "INSERT INTO equipements (nomEquipt, longueur, hauteur, profondeur, description, refDoc, symbole, RefConstructeur, puissanceMax, alimMin, alimMax, alimNom, tarif, fixation, poids, protectionCC, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, nom);
                statement.setInt(2, longueur);
                statement.setInt(3, hauteur);
                statement.setInt(4, profondeur);
                statement.setString(5, description);
                statement.setString(6, refDoc);
                statement.setString(7, symbole);
                statement.setString(8, refConstructeur);
                statement.setFloat(9, puissanceMax);
                statement.setFloat(10, alimMin);
                statement.setFloat(11, alimMax);
                statement.setFloat(12, alimNom);
                statement.setDouble(13, tarif);
                statement.setString(14, fixation);
                statement.setDouble(15, poids);
                statement.setInt(16, protectionCC);
                statement.setString(17, type);
                statement.executeUpdate();
                statement.close();
                connection.close();

                JOptionPane.showMessageDialog(frame, "Matériel créé avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la création du matériel.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void modifierMateriel() {
        JComboBox<String> dropdown = new JComboBox<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nomEquipt FROM equipements");
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

            int result = JOptionPane.showConfirmDialog(frame, dropdown, "Sélectionner un équipement à modifier", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String nom = (String) dropdown.getSelectedItem();
                modifierMaterielDetail(nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des équipements.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierMaterielDetail(String nom) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            String query = "SELECT * FROM equipements WHERE nomEquipt = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                JTextField nomField = new JTextField(resultSet.getString("nomEquipt"));
                JTextField longueurField = new JTextField(String.valueOf(resultSet.getInt("longueur")));
                JTextField hauteurField = new JTextField(String.valueOf(resultSet.getInt("hauteur")));
                JTextField profondeurField = new JTextField(String.valueOf(resultSet.getInt("profondeur")));
                JTextField descriptionField = new JTextField(resultSet.getString("description"));
                JTextField refDocField = new JTextField(resultSet.getString("refDoc"));
                JTextField symboleField = new JTextField(resultSet.getString("symbole"));
                JTextField refConstructeurField = new JTextField(resultSet.getString("RefConstructeur"));
                JTextField puissanceMaxField = new JTextField(String.valueOf(resultSet.getFloat("puissanceMax")));
                JTextField alimMinField = new JTextField(String.valueOf(resultSet.getFloat("alimMin")));
                JTextField alimMaxField = new JTextField(String.valueOf(resultSet.getFloat("alimMax")));
                JTextField alimNomField = new JTextField(String.valueOf(resultSet.getFloat("alimNom")));
                JTextField tarifField = new JTextField(String.valueOf(resultSet.getDouble("tarif")));
                JComboBox<String> fixationField = new JComboBox<>(new String[]{"ND", "R", "F", "PT", "PF", "PS", "Dv"});
                fixationField.setSelectedItem(resultSet.getString("fixation"));
                JTextField poidsField = new JTextField(String.valueOf(resultSet.getDouble("poids")));
                JTextField protectionCCField = new JTextField(String.valueOf(resultSet.getInt("protectionCC")));
                JComboBox<String> typeField = new JComboBox<>(new String[]{"PRM", "STD"});
                typeField.setSelectedItem(resultSet.getString("type"));

                JPanel panel = new JPanel(new GridLayout(18, 2));
                panel.add(new JLabel("Nom:"));
                panel.add(nomField);
                panel.add(new JLabel("Longueur (int):"));
                panel.add(longueurField);
                panel.add(new JLabel("Hauteur (int):"));
                panel.add(hauteurField);
                panel.add(new JLabel("Profondeur (int):"));
                panel.add(profondeurField);
                panel.add(new JLabel("Description:"));
                panel.add(descriptionField);
                panel.add(new JLabel("Ref Doc:"));
                panel.add(refDocField);
                panel.add(new JLabel("Symbole:"));
                panel.add(symboleField);
                panel.add(new JLabel("Ref Constructeur:"));
                panel.add(refConstructeurField);
                panel.add(new JLabel("Puissance Max (float):"));
                panel.add(puissanceMaxField);
                panel.add(new JLabel("Alim Min (float):"));
                panel.add(alimMinField);
                panel.add(new JLabel("Alim Max (float):"));
                panel.add(alimMaxField);
                panel.add(new JLabel("Alim Nom (float):"));
                panel.add(alimNomField);
                panel.add(new JLabel("Tarif (double):"));
                panel.add(tarifField);
                panel.add(new JLabel("Fixation:"));
                panel.add(fixationField);
                panel.add(new JLabel("Poids (double):"));
                panel.add(poidsField);
                panel.add(new JLabel("Protection CC (int):"));
                panel.add(protectionCCField);
                panel.add(new JLabel("Type:"));
                panel.add(typeField);

                int result = JOptionPane.showConfirmDialog(frame, panel, "Modifier le matériel", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    if (nomField.getText().isEmpty() || longueurField.getText().isEmpty() || hauteurField.getText().isEmpty() || profondeurField.getText().isEmpty() ||
                            descriptionField.getText().isEmpty() || refDocField.getText().isEmpty() || symboleField.getText().isEmpty() ||
                            refConstructeurField.getText().isEmpty() || puissanceMaxField.getText().isEmpty() || alimMinField.getText().isEmpty() ||
                            alimMaxField.getText().isEmpty() || alimNomField.getText().isEmpty() || tarifField.getText().isEmpty() ||
                            fixationField.getSelectedItem() == null || poidsField.getText().isEmpty() || protectionCCField.getText().isEmpty() ||
                            typeField.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(frame, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String nouveauNom = nomField.getText();
                    int longueur = Integer.parseInt(longueurField.getText());
                    int hauteur = Integer.parseInt(hauteurField.getText());
                    int profondeur = Integer.parseInt(profondeurField.getText());
                    String description = descriptionField.getText();
                    String refDoc = refDocField.getText();
                    String symbole = symboleField.getText();
                    String refConstructeur = refConstructeurField.getText();
                    float puissanceMax = Float.parseFloat(puissanceMaxField.getText());
                    float alimMin = Float.parseFloat(alimMinField.getText());
                    float alimMax = Float.parseFloat(alimMaxField.getText());
                    float alimNom = Float.parseFloat(alimNomField.getText());
                    double tarif = Double.parseDouble(tarifField.getText());
                    String fixation = (String) fixationField.getSelectedItem();
                    double poids = Double.parseDouble(poidsField.getText());
                    int protectionCC = Integer.parseInt(protectionCCField.getText());
                    String type = (String) typeField.getSelectedItem();

                    query = "UPDATE equipements SET nomEquipt = ?, longueur = ?, hauteur = ?, profondeur = ?, description = ?, refDoc = ?, symbole = ?, RefConstructeur = ?, puissanceMax = ?, alimMin = ?, alimMax = ?, alimNom = ?, tarif = ?, fixation = ?, poids = ?, protectionCC = ?, type = ? WHERE nomEquipt = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(query);
                    updateStatement.setString(1, nouveauNom);
                    updateStatement.setInt(2, longueur);
                    updateStatement.setInt(3, hauteur);
                    updateStatement.setInt(4, profondeur);
                    updateStatement.setString(5, description);
                    updateStatement.setString(6, refDoc);
                    updateStatement.setString(7, symbole);
                    updateStatement.setString(8, refConstructeur);
                    updateStatement.setFloat(9, puissanceMax);
                    updateStatement.setFloat(10, alimMin);
                    updateStatement.setFloat(11, alimMax);
                    updateStatement.setFloat(12, alimNom);
                    updateStatement.setDouble(13, tarif);
                    updateStatement.setString(14, fixation);
                    updateStatement.setDouble(15, poids);
                    updateStatement.setInt(16, protectionCC);
                    updateStatement.setString(17, type);
                    updateStatement.setString(18, nom);
                    updateStatement.executeUpdate();
                    updateStatement.close();
                    connection.close();

                    JOptionPane.showMessageDialog(frame, "Matériel modifié avec succès.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Matériel non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors de la modification du matériel.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void supprimerMateriel() {
        JComboBox<String> dropdown = new JComboBox<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nomEquipt FROM equipements");
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

            int result = JOptionPane.showConfirmDialog(frame, dropdown, "Sélectionner un équipement à supprimer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String nom = (String) dropdown.getSelectedItem();
                supprimerMaterielDetail(nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des équipements.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerMaterielDetail(String nom) {
        if (nom != null && !nom.trim().isEmpty()) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
                String query = "DELETE FROM equipements WHERE nomEquipt = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, nom);
                int rowsDeleted = statement.executeUpdate();
                statement.close();
                connection.close();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(frame, "Matériel supprimé avec succès.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Matériel non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression du matériel.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
