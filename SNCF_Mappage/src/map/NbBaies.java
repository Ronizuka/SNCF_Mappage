package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NbBaies extends JPanel {
    private JSpinner spinnerNombreBaies;
    private JSpinner spinnerNombreChassis;
    private JButton validerButton;

    public NbBaies() {
        setLayout(new BorderLayout());

        // Création du panel pour les champs de sélection
        JPanel selectPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(5, 5, 5, 5); // Ajout de marges

        JLabel nombreBaiesLabel = new JLabel("Nombre de baies : ");
        selectPanel.add(nombreBaiesLabel, gbc);

        gbc.gridy++;
        JLabel nombreChassisLabel = new JLabel("Nombre de châssis : ");
        selectPanel.add(nombreChassisLabel, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;

        // Création du spinner pour sélectionner le nombre de baies
        spinnerNombreBaies = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)); // Valeurs de 1 à 10 avec un pas de 1
        selectPanel.add(spinnerNombreBaies, gbc);

        gbc.gridy++;
        spinnerNombreChassis = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)); // Valeurs de 1 à 10 avec un pas de 1
        selectPanel.add(spinnerNombreChassis, gbc);

        add(selectPanel, BorderLayout.CENTER);

        // Création du bouton Valider
        validerButton = new JButton("Valider");
        validerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Récupérer la valeur sélectionnée dans le spinner
                int nombreBaies = (int) spinnerNombreBaies.getValue();
                int nombreChassis = (int) spinnerNombreChassis.getValue();

                // Ajoutez ici le code à exécuter lorsque le bouton "Valider" est cliqué
                JOptionPane.showMessageDialog(NbBaies.this, "Nombre de baies sélectionné : " + nombreBaies + "\nNombre de châssis sélectionné : " + nombreChassis);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(validerButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
