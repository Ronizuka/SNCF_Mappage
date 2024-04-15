package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        JLabel usernameLabel = new JLabel("Votre identifiant : ");
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
                String identifiant = usernameField.getText();
                String motDePasse = new String(passwordField.getPassword());
                if (identifiant.equals("123") && motDePasse.equals("123")) {
                    // Connexion réussie
                    JOptionPane.showMessageDialog(Login.this, "Connexion réussie!");
                    notifyLoginSuccessListeners();
                } else {
                    // Afficher un message d'erreur
                    JOptionPane.showMessageDialog(Login.this, "Identifiant ou mot de passe incorrect.", "Erreur de Connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);
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
