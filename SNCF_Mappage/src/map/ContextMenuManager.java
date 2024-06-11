package map;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.*;

public class ContextMenuManager {

    public interface ContextMenuListener {
        void onInformationConnecteurs(int equipmentId);
        void onCreerLiaison(int equipmentId);
    }

    private ContextMenuListener listener;

    public ContextMenuManager(ContextMenuListener listener) {
        this.listener = listener;
    }

    public void showContextMenu(Component component, int x, int y, int equipmentId) {  // Ajoutez l'ID de l'équipement ici
        JPopupMenu contextMenu = new JPopupMenu();

        JMenuItem infoConnecteursItem = new JMenuItem("Informations connecteurs");
        infoConnecteursItem.addActionListener(e -> listener.onInformationConnecteurs(equipmentId));  // Passez l'ID ici
        contextMenu.add(infoConnecteursItem);

        JMenuItem creerLiaisonItem = new JMenuItem("Créer une liaison");
        creerLiaisonItem.addActionListener(e -> listener.onCreerLiaison(equipmentId));  // Passez l'ID ici
        contextMenu.add(creerLiaisonItem);

        contextMenu.show(component, x, y);
    }
}
