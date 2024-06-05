package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NbBaies extends JPanel {
    private JSpinner spinnerNombreBaies;
    private JButton validerButton;
    private Window window;

    public NbBaies(Window window) {
        this.window = window;
        setLayout(new BorderLayout());

        JPanel selectPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(5, 5, 5, 5); // Ajout de marges

        JLabel nombreBaiesLabel = new JLabel("Nombre de baies : ");
        selectPanel.add(nombreBaiesLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_START;

        // Création du spinner pour sélectionner le nombre de baies
        spinnerNombreBaies = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)); // Valeurs de 1 à 10 avec un pas de 1
        selectPanel.add(spinnerNombreBaies, gbc);

        add(selectPanel, BorderLayout.CENTER);

        // Création du bouton Valider
        validerButton = new JButton("Valider");
        validerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int nombreBaies = (int) spinnerNombreBaies.getValue();
                window.addBaies(nombreBaies);
                SwingUtilities.getWindowAncestor(NbBaies.this).dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(validerButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}