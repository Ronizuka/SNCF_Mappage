package map;

import java.awt.*;
import javax.swing.*;
import java.util.List;

/**
 * Cette classe gère l'activation et la désactivation des éléments de menu et des boutons.
 */
public class BouttonManager {

    /**
     * Constructeur par défaut de la classe BouttonManager.
     * 
     * Ce constructeur est implicite et n'effectue aucune opération.
     */
    public BouttonManager() {
        // Constructeur par défaut
    }

    /**
     * Désactive les éléments de menu et les boutons spécifiés, à l'exception de certains éléments de menu spécifiques.
     * 
     * @param frame la fenêtre principale de l'application
     * @param disabledMenuItems la liste des éléments de menu désactivés
     * @param disabledButtons la liste des boutons désactivés
     */
    public static void disableMenuItemsAndButtons(JFrame frame, List<JMenuItem> disabledMenuItems, List<JButton> disabledButtons) {
        JMenuBar menuBar = frame.getJMenuBar();
        for (Component menuComponent : menuBar.getComponents()) {
            if (menuComponent instanceof JMenu) {
                JMenu menu = (JMenu) menuComponent;
                for (Component menuItemComponent : menu.getMenuComponents()) {
                    if (menuItemComponent instanceof JMenuItem) {
                        JMenuItem menuItem = (JMenuItem) menuItemComponent;
                        String text = menuItem.getText();
                        if (!text.equals("Nouveau plan") && !text.equals("Ouvrir plan") && !text.equals("Quitter") && !text.equals("Déconnexion")&& !text.equals("Créer un utilisateur")&& !text.equals("Modifier un utilisateur")&& !text.equals("Supprimer un utilisateur")) {
                            menuItem.setEnabled(false);
                            disabledMenuItems.add(menuItem);
                        }
                    }
                }
            }
        }
        for (JButton button : disabledButtons) {
            button.setEnabled(false);
        }
    }

    /**
     * Active les éléments de menu et les boutons spécifiés.
     * 
     * @param disabledMenuItems la liste des éléments de menu à réactiver
     * @param disabledButtons la liste des boutons à réactiver
     */
    public static void enableMenuItemsAndButtons(List<JMenuItem> disabledMenuItems, List<JButton> disabledButtons) {
        for (JMenuItem menuItem : disabledMenuItems) {
            menuItem.setEnabled(true);
        }
        for (JButton button : disabledButtons) {
            button.setEnabled(true);
        }
    }
}
