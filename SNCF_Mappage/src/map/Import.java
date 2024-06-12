package map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Cette classe gère l'importation et la sauvegarde des plans en format JSON.
 */
public class Import {

    /**
     * Constructeur par défaut de la classe Import.
     * 
     * Ce constructeur est implicite et n'effectue aucune opération.
     */
    public Import() {
        // Constructeur par défaut
    }

    /**
     * Sauvegarde le plan actuel dans un fichier JSON.
     * 
     * @param frame la fenêtre principale de l'application
     * @param baies la liste des baies à sauvegarder
     */
    public static void savePlan(JFrame frame, List<DrawingArea.Baie> baies) {
        FileDialog fileDialog = new FileDialog(frame, "Sauvegarder le plan", FileDialog.SAVE);
        fileDialog.setFile("*.json");
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String file = fileDialog.getFile();

        if (directory != null && file != null) {
            String filePath = directory + file;
            if (!filePath.toLowerCase().endsWith(".json")) {
                filePath += ".json";
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(baies);
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(json);
                JOptionPane.showMessageDialog(frame, "Plan sauvegardé avec succès!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la sauvegarde du plan.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Charge un plan à partir d'un fichier JSON et l'affiche dans la DrawingArea.
     * 
     * @param frame la fenêtre principale de l'application
     * @param drawingArea la zone de dessin où afficher le plan importé
     * @param disabledMenuItems la liste des éléments de menu désactivés
     * @param disabledButtons la liste des boutons désactivés
     */
    public static void loadPlan(JFrame frame, DrawingArea drawingArea, List<JMenuItem> disabledMenuItems, List<JButton> disabledButtons) {
        FileDialog fileDialog = new FileDialog(frame, "Importer un plan", FileDialog.LOAD);
        fileDialog.setFile("*.json");
        fileDialog.setFilenameFilter(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".json");
            }
        });
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String file = fileDialog.getFile();

        if (directory != null && file != null) {
            String filePath = directory + file;
            try (FileReader reader = new FileReader(filePath)) {
                Gson gson = new Gson();
                Type baieListType = new TypeToken<List<DrawingArea.Baie>>() {}.getType();
                List<DrawingArea.Baie> baies = gson.fromJson(reader, baieListType);
                drawingArea.setBaies(baies);
                drawingArea.repaint();
                JOptionPane.showMessageDialog(frame, "Plan importé avec succès!");
                BouttonManager.enableMenuItemsAndButtons(disabledMenuItems, disabledButtons); // Réactiver les éléments après l'importation
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de l'importation du plan.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
