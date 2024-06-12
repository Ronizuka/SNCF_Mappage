package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe gère l'interface de connexion pour l'application.
 */
public class Login extends JPanel {
    /**
     * Champ de texte pour le nom d'utilisateur (adresse mail).
     */
    private JTextField usernameField;
    /**
     * Champ de texte pour le mot de passe.
     */
    private JPasswordField passwordField;
    /**
     * Bouton de connexion.
     */
    private JButton loginButton;
    /**
     * Liste des écouteurs de succès de connexion.
     */
    private List<LoginSuccessListener> loginSuccessListeners = new ArrayList<>();

    /**
     * Constructeur de la classe Login.
     * Initialise les composants de l'interface utilisateur et ajoute les écouteurs nécessaires.
     */
    public Login() {
        setLayout(new BorderLayout());

        // Création du panel pour les champs de connexion
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel usernameLabel = new JLabel("Adresse mail : ");
        loginPanel.add(usernameLabel, gbc);
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Mot de passe : ");
        loginPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        usernameField = new JTextField(20);
        loginPanel.add(usernameField, gbc);
        gbc.gridy++;
        passwordField = new JPasswordField(20);
        loginPanel.add(passwordField, gbc);
        add(loginPanel, BorderLayout.CENTER);

        // Création du bouton de connexion
        loginButton = new JButton("Se connecter");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = usernameField.getText();
                String password = new String(passwordField.getPassword());
                int userRights = authenticate(email, password);
                if (userRights >= 0) {
                    // Connexion réussie
                    JOptionPane.showMessageDialog(Login.this, "Connexion réussie!");
                    notifyLoginSuccessListeners(userRights);
                } else {
                    // Afficher un message d'erreur
                    JOptionPane.showMessageDialog(Login.this, "Adresse mail ou mot de passe incorrect.", "Erreur de Connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Authentifie l'utilisateur avec l'adresse mail et le mot de passe fournis.
     * 
     * @param email l'adresse mail de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @return les droits de l'utilisateur si l'authentification réussit, -1 sinon
     */
    private int authenticate(String email, String password) {
        int userRights = -1;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            String query = "SELECT droits FROM users WHERE mail = ? AND pwd = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userRights = resultSet.getInt("droits");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userRights;
    }

    /**
     * Ajoute un écouteur pour les événements de connexion réussie.
     * 
     * @param listener l'écouteur à ajouter
     */
    public void addLoginSuccessListener(LoginSuccessListener listener) {
        loginSuccessListeners.add(listener);
    }

    /**
     * Notifie tous les écouteurs enregistrés de l'événement de connexion réussie.
     * 
     * @param userRights les droits de l'utilisateur authentifié
     */
    private void notifyLoginSuccessListeners(int userRights) {
        for (LoginSuccessListener listener : loginSuccessListeners) {
            listener.onLoginSuccess(userRights);
        }
    }

    /**
     * Interface pour écouter les événements de connexion réussie.
     */
    public interface LoginSuccessListener {
        /**
         * Appelée lorsque la connexion est réussie.
         * 
         * @param userRights les droits de l'utilisateur authentifié
         */
        void onLoginSuccess(int userRights);
    }
}
