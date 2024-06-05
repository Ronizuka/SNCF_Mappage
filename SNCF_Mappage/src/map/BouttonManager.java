package map;
import java.awt.*;
import javax.swing.*;
import java.util.List;

public class BouttonManager {

    public static void disableMenuItemsAndButtons(JFrame frame, List<JMenuItem> disabledMenuItems, List<JButton> disabledButtons) {
        JMenuBar menuBar = frame.getJMenuBar();
        for (Component menuComponent : menuBar.getComponents()) {
            if (menuComponent instanceof JMenu) {
                JMenu menu = (JMenu) menuComponent;
                for (Component menuItemComponent : menu.getMenuComponents()) {
                    if (menuItemComponent instanceof JMenuItem) {
                        JMenuItem menuItem = (JMenuItem) menuItemComponent;
                        String text = menuItem.getText();
                        if (!text.equals("Nouveau plan") && !text.equals("Importer plan") && !text.equals("Quitter") && !text.equals("Déconnexion")) {
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

    public static void enableMenuItemsAndButtons(List<JMenuItem> disabledMenuItems, List<JButton> disabledButtons) {
        for (JMenuItem menuItem : disabledMenuItems) {
            menuItem.setEnabled(true);
        }
        for (JButton button : disabledButtons) {
            button.setEnabled(true);
        }
    }
}
