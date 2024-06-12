package map;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

/**
 * Classe qui gère les baies.
 */
public class BaieManager {

    /**
     * La fenêtre principale de l'application.
     */
    private JFrame frame;

    /**
     * Constructeur de la classe BaieManager.
     *
     * @param frame la fenêtre principale de l'application
     */
    public BaieManager(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Interface pour gérer les callbacks de création de Baie.
     */
    public interface TrainCreationCallback {
        /**
         * Méthode appelée lorsque la baie est créée.
         */
        void onTrainCreated();
    }

    /**
     * Crée une nouvelle Baie avec les informations fournies par l'utilisateur.
     *
     * @param callback le callback appelé après la création de la baie
     */
    public void creerTrain(TrainCreationCallback callback) {
        JTextField nomField = new JTextField();
        JTextField budgetField = createFloatField();
        JTextField debutTravauxField = createDateField();
        JTextField finTravauxField = createDateField();
        JTextField nbRisquesField = createNumberField();
        JTextField construitEnField = new JTextField();
        JTextField renoveEnField = new JTextField();
        JTextField descrProjetField = new JTextField();
        JTextField noAffaireField = new JTextField();
        JTextField codeProjetField = new JTextField();
        JTextField codeActiviteField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(12, 2));
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Budget (float):"));
        panel.add(budgetField);
        panel.add(new JLabel("Début Travaux (date YYYY-MM-DD):"));
        panel.add(debutTravauxField);
        panel.add(new JLabel("Fin Travaux (date YYYY-MM-DD):"));
        panel.add(finTravauxField);
        panel.add(new JLabel("Nombre de Risques (int):"));
        panel.add(nbRisquesField);
        panel.add(new JLabel("Construit En:"));
        panel.add(construitEnField);
        panel.add(new JLabel("Rénové En:"));
        panel.add(renoveEnField);
        panel.add(new JLabel("Description Projet:"));
        panel.add(descrProjetField);
        panel.add(new JLabel("Numéro Affaire:"));
        panel.add(noAffaireField);
        panel.add(new JLabel("Code Projet:"));
        panel.add(codeProjetField);
        panel.add(new JLabel("Code Activité:"));
        panel.add(codeActiviteField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Créer une nouvelle Baie", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (nomField.getText().isEmpty() || budgetField.getText().isEmpty() ||
                    debutTravauxField.getText().isEmpty() || finTravauxField.getText().isEmpty() || nbRisquesField.getText().isEmpty() ||
                    construitEnField.getText().isEmpty() || renoveEnField.getText().isEmpty() || descrProjetField.getText().isEmpty() ||
                    noAffaireField.getText().isEmpty() || codeProjetField.getText().isEmpty() || codeActiviteField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nom = nomField.getText();
            float budget = Float.parseFloat(budgetField.getText());
            Date debutTravaux = Date.valueOf(debutTravauxField.getText());
            Date finTravaux = Date.valueOf(finTravauxField.getText());
            int nbRisques = Integer.parseInt(nbRisquesField.getText());
            String construitEn = construitEnField.getText();
            String renoveEn = renoveEnField.getText();
            String descrProjet = descrProjetField.getText();
            String noAffaire = noAffaireField.getText();
            String codeProjet = codeProjetField.getText();
            String codeActivite = codeActiviteField.getText();

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
                // Vérifier si le nom existe déjà
                String checkQuery = "SELECT COUNT(*) FROM trains WHERE nom = ?";
                PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                checkStatement.setString(1, nom);
                ResultSet resultSet = checkStatement.executeQuery();
                resultSet.next();
                if (resultSet.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(frame, "Une Baie avec ce nom existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    resultSet.close();
                    checkStatement.close();
                    return;
                }
                resultSet.close();
                checkStatement.close();

                String query = "INSERT INTO trains (nom, budget, debutTravaux, finTravaux, nbRisques, construitEn, renoveEn, descrProjet, noAffaire, codeProjet, codeActivite) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, nom);
                statement.setFloat(2, budget);
                statement.setDate(3, debutTravaux);
                statement.setDate(4, finTravaux);
                statement.setInt(5, nbRisques);
                statement.setString(6, construitEn);
                statement.setString(7, renoveEn);
                statement.setString(8, descrProjet);
                statement.setString(9, noAffaire);
                statement.setString(10, codeProjet);
                statement.setString(11, codeActivite);
                statement.executeUpdate();
                statement.close();
                connection.close();

                JOptionPane.showMessageDialog(frame, "Baie créée avec succès.");
                callback.onTrainCreated();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la création de la Baie.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Crée un champ de texte pour les nombres.
     *
     * @return un champ de texte pour les nombres
     */
    private JTextField createNumberField() {
        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new NumberFilter());
        return field;
    }

    /**
     * Crée un champ de texte pour les nombres à virgule flottante.
     *
     * @return un champ de texte pour les nombres à virgule flottante
     */
    private JTextField createFloatField() {
        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new FloatFilter());
        return field;
    }

    /**
     * Crée un champ de texte pour les dates.
     *
     * @return un champ de texte pour les dates
     */
    private JTextField createDateField() {
        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DateFilter());
        return field;
    }

    /**
     * Filtre pour les nombres dans les documents.
     */
    private class NumberFilter extends DocumentFilter {
        /**
         * Constructeur par défaut de NumberFilter.
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
     * Filtre pour les nombres à virgule flottante dans les documents.
     */
    private class FloatFilter extends DocumentFilter {
        /**
         * Constructeur par défaut de FloatFilter.
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
     * Filtre pour les dates dans les documents.
     */
    private class DateFilter extends DocumentFilter {
        /**
         * Constructeur par défaut de DateFilter.
         */
        public DateFilter() {
            super();
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
            if (string.matches("\\d{0,4}-?\\d{0,2}-?\\d{0,2}")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
            if (text.matches("\\d{0,4}-?\\d{0,2}-?\\d{0,2}")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
