package map;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Ouvrir la page de connexion
            LoginPanel loginPage = new LoginPanel();
            loginPage.setVisible(true);
            // Écouter les événements de connexion réussie depuis la page de connexion
            loginPage.addLoginSuccessListener(new LoginPanel.LoginSuccessListener() {
                @Override
                public void onLoginSuccess() {
                    // Fermer la page de connexion
                    loginPage.dispose();
                    // Ouvrir la fenêtre principale
                    Window mainWindow = new Window();
                    mainWindow.setVisible(true);
                }
            });
        });
    }
}
