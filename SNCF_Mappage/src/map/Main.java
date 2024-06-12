package map;

import map.Login.LoginSuccessListener;
import javax.swing.*;
import java.sql.*;

/**
 * La classe principale qui lance l'application.
 */
public class Main {

    /**
     * Constructeur par défaut de la classe Main.
     * Ce constructeur est utilisé pour lancer l'application.
     */
    public Main() {
        // Default constructor
    }

    /**
     * Le point d'entrée principal de l'application.
     * 
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        // Créer et afficher la fenêtre de connexion au démarrage de l'application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Login loginWindow = new Login();
                loginWindow.addLoginSuccessListener(new LoginSuccessListener() {
                    @Override
                    public void onLoginSuccess(int userRights) {
                        // Une fois que l'utilisateur est connecté, fermer la fenêtre de connexion
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(loginWindow);
                        if (frame != null) {
                            frame.dispose();
                        }

                        // Lancer l'application principale
                        Window window = new Window(userRights);
                        window.launch();
                    }
                });
                JFrame frame = new JFrame("SNCF Mappage - Connexion");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(loginWindow);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                // Connexion à la base de données
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
                    if (connection != null) {
                        System.out.println("Connexion à la base de données établie !");
                        // Vous pouvez faire d'autres opérations avec la connexion ici si nécessaire
                    } else {
                        System.out.println("Impossible de se connecter à la base de données.");
                    }
                } catch (SQLException e) {
                    System.out.println("Erreur : Échec de la connexion à la base de données !");
                    e.printStackTrace();
                }
            }
        });
    }
}
