package map;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.*;

/**
 * Cette classe gère les menus contextuels pour les équipements.
 */
public class ContextMenuManager {

    /**
     * Interface pour gérer les actions du menu contextuel.
     */
    public interface ContextMenuListener {
        /**
         * Appelée lorsque l'utilisateur demande des informations sur les connecteurs.
         *
         * @param equipmentId l'ID de l'équipement
         */
        void onInformationConnecteurs(int equipmentId);

        /**
         * Appelée lorsque l'utilisateur demande de créer une liaison.
         *
         * @param equipmentId l'ID de l'équipement
         */
        void onCreerLiaison(int equipmentId);
    }

    /**
     * Le listener pour les actions du menu contextuel.
     */
    private ContextMenuListener listener;

    /**
     * Constructeur de la classe ContextMenuManager.
     * 
     * @param listener le listener pour les actions du menu contextuel
     */
    public ContextMenuManager(ContextMenuListener listener) {
        this.listener = listener;
    }

    /**
     * Affiche le menu contextuel à la position spécifiée pour l'équipement donné.
     * 
     * @param component le composant sur lequel afficher le menu contextuel
     * @param x la position x où afficher le menu
     * @param y la position y où afficher le menu
     * @param equipmentId l'ID de l'équipement pour lequel afficher le menu
     */
    public void showContextMenu(Component component, int x, int y, int equipmentId) {
        JPopupMenu contextMenu = new JPopupMenu();

        JMenuItem infoConnecteursItem = new JMenuItem("Informations connecteurs");
        infoConnecteursItem.addActionListener(e -> listener.onInformationConnecteurs(equipmentId));
        contextMenu.add(infoConnecteursItem);

        JMenuItem creerLiaisonItem = new JMenuItem("Créer une liaison");
        creerLiaisonItem.addActionListener(e -> listener.onCreerLiaison(equipmentId));
        contextMenu.add(creerLiaisonItem);

        contextMenu.show(component, x, y);
    }
}
