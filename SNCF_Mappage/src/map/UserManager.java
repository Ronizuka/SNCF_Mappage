package map;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private JFrame frame;

    public UserManager(JFrame frame) {
        this.frame = frame;
    }

    public void creerUtilisateur() {
        JTextField mailField = new JTextField();
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField pwdField = new JTextField();
        JTextField hpwdField = new JTextField();
        JTextField fonctionField = new JTextField();
        JComboBox<Integer> droitsField = new JComboBox<>(new Integer[]{0, 1, 2});
        JComboBox<Integer> adminShowPrivateField = new JComboBox<>(new Integer[]{0, 1});
        JTextField nomClientField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(9, 2));
        panel.add(new JLabel("Mail:"));
        panel.add(mailField);
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Prenom:"));
        panel.add(prenomField);
        panel.add(new JLabel("Mot de passe:"));
        panel.add(pwdField);
        panel.add(new JLabel("HMot de passe:"));
        panel.add(hpwdField);
        panel.add(new JLabel("Fonction:"));
        panel.add(fonctionField);
        panel.add(new JLabel("Droits:"));
        panel.add(droitsField);
        panel.add(new JLabel("Admin Show Private:"));
        panel.add(adminShowPrivateField);
        panel.add(new JLabel("Nom Client:"));
        panel.add(nomClientField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Créer un nouvel utilisateur", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (mailField.getText().isEmpty() || nomField.getText().isEmpty() || prenomField.getText().isEmpty() || pwdField.getText().isEmpty() ||
                    hpwdField.getText().isEmpty() || fonctionField.getText().isEmpty() || droitsField.getSelectedItem() == null ||
                    adminShowPrivateField.getSelectedItem() == null || nomClientField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String mail = mailField.getText();
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String pwd = pwdField.getText();
            String hpwd = hpwdField.getText();
            String fonction = fonctionField.getText();
            int droits = (Integer) droitsField.getSelectedItem();
            int adminShowPrivate = (Integer) adminShowPrivateField.getSelectedItem();
            int nomClient = Integer.parseInt(nomClientField.getText());

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
                String query = "INSERT INTO users (mail, nom, prenom, pwd, hpwd, fonction, droits, adminShowPrivate, nomClient) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, mail);
                statement.setString(2, nom);
                statement.setString(3, prenom);
                statement.setString(4, pwd);
                statement.setString(5, hpwd);
                statement.setString(6, fonction);
                statement.setInt(7, droits);
                statement.setInt(8, adminShowPrivate);
                statement.setInt(9, nomClient);
                statement.executeUpdate();
                statement.close();
                connection.close();

                JOptionPane.showMessageDialog(frame, "Utilisateur créé avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la création de l'utilisateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void modifierUtilisateur() {
        JComboBox<String> dropdown = new JComboBox<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT mail FROM users");
            List<String> userNames = new ArrayList<>();
            while (resultSet.next()) {
                userNames.add(resultSet.getString("mail"));
            }
            resultSet.close();
            statement.close();
            connection.close();

            for (String name : userNames) {
                dropdown.addItem(name);
            }

            int result = JOptionPane.showConfirmDialog(frame, dropdown, "Sélectionner un utilisateur à modifier", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String mail = (String) dropdown.getSelectedItem();
                modifierUtilisateurDetail(mail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des utilisateurs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierUtilisateurDetail(String mail) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            String query = "SELECT * FROM users WHERE mail = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                JTextField mailField = new JTextField(resultSet.getString("mail"));
                JTextField nomField = new JTextField(resultSet.getString("nom"));
                JTextField prenomField = new JTextField(resultSet.getString("prenom"));
                JTextField pwdField = new JTextField(resultSet.getString("pwd"));
                JTextField hpwdField = new JTextField(resultSet.getString("hpwd"));
                JTextField fonctionField = new JTextField(resultSet.getString("fonction"));
                JComboBox<Integer> droitsField = new JComboBox<>(new Integer[]{0, 1, 2});
                droitsField.setSelectedItem(resultSet.getInt("droits"));
                JComboBox<Integer> adminShowPrivateField = new JComboBox<>(new Integer[]{0, 1});
                adminShowPrivateField.setSelectedItem(resultSet.getInt("adminShowPrivate"));
                JTextField nomClientField = new JTextField(String.valueOf(resultSet.getInt("nomClient")));

                JPanel panel = new JPanel(new GridLayout(9, 2));
                panel.add(new JLabel("Mail:"));
                panel.add(mailField);
                panel.add(new JLabel("Nom:"));
                panel.add(nomField);
                panel.add(new JLabel("Prenom:"));
                panel.add(prenomField);
                panel.add(new JLabel("Mot de passe:"));
                panel.add(pwdField);
                panel.add(new JLabel("HMot de passe:"));
                panel.add(hpwdField);
                panel.add(new JLabel("Fonction:"));
                panel.add(fonctionField);
                panel.add(new JLabel("Droits:"));
                panel.add(droitsField);
                panel.add(new JLabel("Admin Show Private:"));
                panel.add(adminShowPrivateField);
                panel.add(new JLabel("Nom Client:"));
                panel.add(nomClientField);

                int result = JOptionPane.showConfirmDialog(frame, panel, "Modifier l'utilisateur", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    if (mailField.getText().isEmpty() || nomField.getText().isEmpty() || prenomField.getText().isEmpty() || pwdField.getText().isEmpty() ||
                            hpwdField.getText().isEmpty() || fonctionField.getText().isEmpty() || droitsField.getSelectedItem() == null ||
                            adminShowPrivateField.getSelectedItem() == null || nomClientField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String newMail = mailField.getText();
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
                    String pwd = pwdField.getText();
                    String hpwd = hpwdField.getText();
                    String fonction = fonctionField.getText();
                    int droits = (Integer) droitsField.getSelectedItem();
                    int adminShowPrivate = (Integer) adminShowPrivateField.getSelectedItem();
                    int nomClient = Integer.parseInt(nomClientField.getText());

                    query = "UPDATE users SET mail = ?, nom = ?, prenom = ?, pwd = ?, hpwd = ?, fonction = ?, droits = ?, adminShowPrivate = ?, nomClient = ? WHERE mail = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(query);
                    updateStatement.setString(1, newMail);
                    updateStatement.setString(2, nom);
                    updateStatement.setString(3, prenom);
                    updateStatement.setString(4, pwd);
                    updateStatement.setString(5, hpwd);
                    updateStatement.setString(6, fonction);
                    updateStatement.setInt(7, droits);
                    updateStatement.setInt(8, adminShowPrivate);
                    updateStatement.setInt(9, nomClient);
                    updateStatement.setString(10, mail);
                    updateStatement.executeUpdate();
                    updateStatement.close();
                    connection.close();

                    JOptionPane.showMessageDialog(frame, "Utilisateur modifié avec succès.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Utilisateur non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors de la modification de l'utilisateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void supprimerUtilisateur() {
        JComboBox<String> dropdown = new JComboBox<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT mail FROM users");
            List<String> userNames = new ArrayList<>();
            while (resultSet.next()) {
                userNames.add(resultSet.getString("mail"));
            }
            resultSet.close();
            statement.close();
            connection.close();

            for (String name : userNames) {
                dropdown.addItem(name);
            }

            int result = JOptionPane.showConfirmDialog(frame, dropdown, "Sélectionner un utilisateur à supprimer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String mail = (String) dropdown.getSelectedItem();
                supprimerUtilisateurDetail(mail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des utilisateurs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerUtilisateurDetail(String mail) {
        if (mail != null && !mail.trim().isEmpty()) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
                String query = "DELETE FROM users WHERE mail = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, mail);
                int rowsDeleted = statement.executeUpdate();
                statement.close();
                connection.close();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(frame, "Utilisateur supprimé avec succès.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Utilisateur non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression de l'utilisateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}