package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private LoginSuccessListener loginSuccessListener;

    public LoginPanel() {
        setLayout(new BorderLayout());

        // Création du panel pour les champs de connexion
        JPanel loginPanel = new JPanel(new GridBagLayout()); // Utilisation d'un GridBagLayout pour organiser les composants
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(5, 5, 5, 5); // Ajout de marges
        JLabel usernameLabel = new JLabel("Votre identifiiant : ");
        loginPanel.add(usernameLabel, gbc);
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Mot de passe : ");
        loginPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        usernameField = new JTextField(10); // Définir la taille de la zone de texte
        loginPanel.add(usernameField, gbc);
        gbc.gridy++;
        passwordField = new JPasswordField(10); // Définir la taille de la zone de texte
        loginPanel.add(passwordField, gbc);
        add(loginPanel, BorderLayout.CENTER);

        // Création du bouton de connexion
        loginButton = new JButton("Se connecter");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Vérifier les identifiants et mots de passe
                String identifiant = usernameField.getText();
                String motDePasse = new String(passwordField.getPassword());
                if (identifiant.equals("aa") && motDePasse.equals("aa")) {
                    // Connexion réussie
                    if (loginSuccessListener != null) {
                        loginSuccessListener.onLoginSuccess();
                    }
                } else {
                    // Afficher un message d'erreur
                    JOptionPane.showMessageDialog(LoginPanel.this, "Identifiant ou mot de passe incorrect.", "Erreur de Connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addLoginSuccessListener(LoginSuccessListener listener) {
        this.loginSuccessListener = listener;
    }

    public interface LoginSuccessListener {
        void onLoginSuccess();
    }
    
    // Méthode pour fermer la fenêtre de connexion
    public void dispose() {
        Window window = (Window) SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }
}
