package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Login extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private List<LoginSuccessListener> loginSuccessListeners = new ArrayList<>();

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
                if (authenticate(email, password)) {
                    // Connexion réussie
                    JOptionPane.showMessageDialog(Login.this, "Connexion réussie!");
                    notifyLoginSuccessListeners();
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

    private boolean authenticate(String email, String password) {
        boolean isAuthenticated = false;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            String query = "SELECT * FROM users WHERE mail = ? AND pwd = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isAuthenticated = true;
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }

    public void addLoginSuccessListener(LoginSuccessListener listener) {
        loginSuccessListeners.add(listener);
    }

    private void notifyLoginSuccessListeners() {
        for (LoginSuccessListener listener : loginSuccessListeners) {
            listener.onLoginSuccess();
        }
    }

    // Interface pour écouter les événements de connexion réussie
    public interface LoginSuccessListener {
        void onLoginSuccess();
    }
}