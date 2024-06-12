package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Cette classe représente un panneau permettant de sélectionner le nombre, la largeur et la hauteur des châssis.
 */
public class NbChassis extends JPanel {
    /**
     * Spinner pour sélectionner le nombre de châssis.
     */
    private JSpinner spinnerNombreChassis;

    /**
     * Spinner pour sélectionner la largeur des châssis.
     */
    private JSpinner spinnerLargeurChassis;

    /**
     * Spinner pour sélectionner la hauteur des châssis.
     */
    private JSpinner spinnerHauteurChassis;

    /**
     * Bouton pour valider la sélection.
     */
    private JButton validerButton;

    /**
     * Référence à la fenêtre principale de l'application.
     */
    private Window window;

    /**
     * Constructeur de la classe NbChassis.
     *
     * @param window la fenêtre principale de l'application
     */
    public NbChassis(Window window) {
        this.window = window;
        setLayout(new BorderLayout());

        JPanel selectPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(5, 5, 5, 5); // Ajout de marges

        JLabel nombreChassisLabel = new JLabel("Nombre de châssis : ");
        selectPanel.add(nombreChassisLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START;

        // Création du spinner pour sélectionner le nombre de châssis
        spinnerNombreChassis = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)); // Valeurs de 1 à 10 avec un pas de 1
        selectPanel.add(spinnerNombreChassis, gbc);

        // Création du label et du spinner pour la largeur des châssis
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel largeurChassisLabel = new JLabel("Largeur des châssis (px) : ");
        selectPanel.add(largeurChassisLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START;
        spinnerLargeurChassis = new JSpinner(new SpinnerNumberModel(1300, 100, 5000, 50)); // Valeurs ajustables pour la largeur
        selectPanel.add(spinnerLargeurChassis, gbc);

        // Création du label et du spinner pour la hauteur des châssis
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel hauteurChassisLabel = new JLabel("Hauteur des châssis (px) : ");
        selectPanel.add(hauteurChassisLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START;
        spinnerHauteurChassis = new JSpinner(new SpinnerNumberModel(2000, 100, 5000, 50)); // Valeurs ajustables pour la hauteur
        selectPanel.add(spinnerHauteurChassis, gbc);

        add(selectPanel, BorderLayout.CENTER);

        // Création du bouton Valider
        validerButton = new JButton("Valider");
        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nombreChassis = (int) spinnerNombreChassis.getValue();
                int largeurChassis = (int) spinnerLargeurChassis.getValue();
                int hauteurChassis = (int) spinnerHauteurChassis.getValue();
                window.addChassis(nombreChassis, largeurChassis, hauteurChassis);
                SwingUtilities.getWindowAncestor(NbChassis.this).dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(validerButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
