package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {
    public MenuPanel() {
        setLayout(new BorderLayout());
        JButton createPlanButton = new JButton("Créer un plan");
        add(createPlanButton, BorderLayout.CENTER);
        
        // Ajouter un écouteur au bouton "Créer un plan" pour ouvrir ChoixNbBaies
        createPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir la fenêtre ChoixNbBaies
                NbChoixBaiesPanel choixNbBaies = new NbChoixBaiesPanel();
                choixNbBaies.setVisible(true);
            }
        });
    }
}
