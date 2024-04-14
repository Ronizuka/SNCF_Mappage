package map;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window() {
        setTitle("Application Principale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Taille de la fenêtre principale
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        
        // Création d'un onglet pour le menu
        MenuPanel menuPanel = new MenuPanel();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Menu", menuPanel);

        // Création d'un onglet pour le choix du nombre de baies
        NbChoixBaiesPanel nbchoixBaiesPanel = new NbChoixBaiesPanel();
        tabbedPane.addTab("Choix du Nombre de Baies", nbchoixBaiesPanel);
        
        // Ajout des onglets à la fenêtre principale
        add(tabbedPane);
    }
}
