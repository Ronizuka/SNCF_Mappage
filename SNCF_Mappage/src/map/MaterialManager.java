package map;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe gère les opérations de création, modification et suppression des matériels.
 */
public class MaterialManager {

    /**
     * La fenêtre principale de l'application.
     */
    private JFrame frame;

    /**
     * Constructeur de la classe MaterialManager.
     *
     * @param frame la fenêtre principale de l'application
     */
    public MaterialManager(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Crée un nouveau matériel et l'ajoute à la base de données.
     */
    public void creerMateriel() {
        JTextField nomField = new JTextField();
        JTextField longueurField = createNumberField();
        JTextField hauteurField = createNumberField();
        JTextField profondeurField = createNumberField();
        JTextField descriptionField = new JTextField();
        JTextField refDocField = new JTextField();
        JTextField symboleField = new JTextField();
        JTextField refConstructeurField = new JTextField();
        JTextField puissanceMaxField = createFloatField();
        JTextField alimMinField = createFloatField();
        JTextField alimMaxField = createFloatField();
        JTextField alimNomField = createFloatField();
        JTextField tarifField = createDoubleField();
        JComboBox<String> fixationField = new JComboBox<>(new String[]{"ND", "R", "F", "PT", "PF", "PS", "Dv"});
        JTextField poidsField = createDoubleField();
        JTextField protectionCCField = createNumberField();
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

    /**
     * Ouvre une boîte de dialogue pour sélectionner un matériel à modifier.
     */
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

    /**
     * Modifie les détails d'un matériel sélectionné.
     * 
     * @param nom le nom du matériel à modifier
     */
    private void modifierMaterielDetail(String nom) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            String query = "SELECT * FROM equipements WHERE nomEquipt = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                JTextField nomField = new JTextField(resultSet.getString("nomEquipt"));
                JTextField longueurField = createNumberField(resultSet.getInt("longueur"));
                JTextField hauteurField = createNumberField(resultSet.getInt("hauteur"));
                JTextField profondeurField = createNumberField(resultSet.getInt("profondeur"));
                JTextField descriptionField = new JTextField(resultSet.getString("description"));
                JTextField refDocField = new JTextField(resultSet.getString("refDoc"));
                JTextField symboleField = new JTextField(resultSet.getString("symbole"));
                JTextField refConstructeurField = new JTextField(resultSet.getString("RefConstructeur"));
                JTextField puissanceMaxField = createFloatField(resultSet.getFloat("puissanceMax"));
                JTextField alimMinField = createFloatField(resultSet.getFloat("alimMin"));
                JTextField alimMaxField = createFloatField(resultSet.getFloat("alimMax"));
                JTextField alimNomField = createFloatField(resultSet.getFloat("alimNom"));
                JTextField tarifField = createDoubleField(resultSet.getDouble("tarif"));
                JComboBox<String> fixationField = new JComboBox<>(new String[]{"ND", "R", "F", "PT", "PF", "PS", "Dv"});
                fixationField.setSelectedItem(resultSet.getString("fixation"));
                JTextField poidsField = createDoubleField(resultSet.getDouble("poids"));
                JTextField protectionCCField = createNumberField(resultSet.getInt("protectionCC"));
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

    /**
     * Ouvre une boîte de dialogue pour sélectionner un matériel à supprimer.
     */
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

    /**
     * Supprime un matériel sélectionné de la base de données.
     * 
     * @param nom le nom du matériel à supprimer
     */
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

    /**
     * Crée un champ de texte pour entrer des nombres entiers avec un filtre pour n'accepter que les chiffres.
     * 
     * @return un JTextField configuré pour les nombres entiers
     */
    private JTextField createNumberField() {
        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new NumberFilter());
        return field;
    }

    /**
     * Crée un champ de texte pré-rempli avec une valeur pour entrer des nombres entiers avec un filtre pour n'accepter que les chiffres.
     * 
     * @param value la valeur initiale du champ de texte
     * @return un JTextField configuré pour les nombres entiers
     */
    private JTextField createNumberField(int value) {
        JTextField field = new JTextField(String.valueOf(value));
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new NumberFilter());
        return field;
    }

    /**
     * Crée un champ de texte pour entrer des nombres flottants avec un filtre pour n'accepter que les chiffres et un point décimal.
     *
     * @return un JTextField configuré pour les nombres flottants
     */
    private JTextField createFloatField() {
        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new FloatFilter());
        return field;
    }

    /**
     * Crée un champ de texte pré-rempli avec une valeur pour entrer des nombres flottants avec un filtre pour n'accepter que les chiffres et un point décimal.
     *
     * @param value la valeur initiale du champ de texte
     * @return un JTextField configuré pour les nombres flottants
     */
    private JTextField createFloatField(float value) {
        JTextField field = new JTextField(String.valueOf(value));
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new FloatFilter());
        return field;
    }

    /**
     * Crée un champ de texte pour entrer des nombres doubles avec un filtre pour n'accepter que les chiffres et un point décimal.
     *
     * @return un JTextField configuré pour les nombres doubles
     */
    private JTextField createDoubleField() {
        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DoubleFilter());
        return field;
    }

    /**
     * Crée un champ de texte pré-rempli avec une valeur pour entrer des nombres doubles avec un filtre pour n'accepter que les chiffres et un point décimal.
     *
     * @param value la valeur initiale du champ de texte
     * @return un JTextField configuré pour les nombres doubles
     */
    private JTextField createDoubleField(double value) {
        JTextField field = new JTextField(String.valueOf(value));
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DoubleFilter());
        return field;
    }

    /**
     * Filtre pour n'accepter que les chiffres dans un champ de texte.
     */
    private class NumberFilter extends DocumentFilter {
        /**
         * Constructeur par défaut de la classe NumberFilter.
         */
        public NumberFilter() {
            super();
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
            if (string.matches("\\d*")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
            if (text.matches("\\d*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    /**
     * Filtre pour n'accepter que les chiffres et un point décimal dans un champ de texte pour les nombres flottants.
     */
    private class FloatFilter extends DocumentFilter {
        /**
         * Constructeur par défaut de la classe FloatFilter.
         */
        public FloatFilter() {
            super();
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
            if (string.matches("\\d*\\.?\\d*")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
            if (text.matches("\\d*\\.?\\d*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    /**
     * Filtre pour n'accepter que les chiffres et un point décimal dans un champ de texte pour les nombres doubles.
     */
    private class DoubleFilter extends DocumentFilter {
        /**
         * Constructeur par défaut de la classe DoubleFilter.
         */
        public DoubleFilter() {
            super();
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
            if (string.matches("\\d*\\.?\\d*")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
            if (text.matches("\\d*\\.?\\d*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
