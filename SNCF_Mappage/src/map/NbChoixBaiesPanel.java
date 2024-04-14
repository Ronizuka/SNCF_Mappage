package map;

import javax.swing.*;
import java.awt.*;

public class NbChoixBaiesPanel extends JPanel {
    public NbChoixBaiesPanel() {
        setName("Choix du Nombre de Châssis et de Baies");
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ne pas fermer l'application lors de la fermeture de cette fenêtre
        setSize(800, 600); // Taille de la fenêtre
        setLocation(null); // Centrer la fenêtre sur l'écran

        // Création du panel principal avec un layout en grille
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 0, 10)); // 3 lignes, 1 colonne, 10 pixels de marge entre les composants

        // Panel pour le choix du nombre de châssis
        JPanel chassisPanel = new JPanel(new GridBagLayout()); // GridBagLayout pour aligner les composants
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END; // Aligner à droite
        gbc.insets = new Insets(10, 10, 5, 0); // Espacement entre les composants (haut, gauche, bas, droite)
        JLabel chassisLabel = new JLabel("Nombre de Châssis: ");
        chassisPanel.add(chassisLabel, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START; // Aligner à gauche
        gbc.insets = new Insets(10, 0, 5, 10); // Espacement entre les composants (haut, gauche, bas, droite)
        JSpinner chassisSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)); // Création du spinner avec une plage de 1 à 10 et incrémentation de 1
        chassisPanel.add(chassisSpinner, gbc);
        mainPanel.add(chassisPanel);

        // Panel pour le choix du nombre de baies
        JPanel baiesPanel = new JPanel(new GridBagLayout()); // GridBagLayout pour aligner les composants
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END; // Aligner à droite
        gbc.insets = new Insets(10, 10, 5, 0); // Espacement entre les composants (haut, gauche, bas, droite)
        JLabel baiesLabel = new JLabel("Nombre de Baies: ");
        baiesPanel.add(baiesLabel, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START; // Aligner à gauche
        gbc.insets = new Insets(10, 0, 5, 10); // Espacement entre les composants (haut, gauche, bas, droite)
        JSpinner baiesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)); // Création du spinner avec une plage de 1 à 10 et incrémentation de 1
        baiesPanel.add(baiesSpinner, gbc);
        mainPanel.add(baiesPanel);

        // Ajout du bouton "Valider"
        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> {
            // Ajouter ici la logique de validation
            JOptionPane.showMessageDialog(this, "Validation en cours...");
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // FlowLayout pour centrer le bouton
        buttonPanel.add(validerButton);
        mainPanel.add(buttonPanel);

        // Ajouter le panel principal à la fenêtre
        add(mainPanel);
    }
}
