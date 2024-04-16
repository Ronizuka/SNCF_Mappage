package map;
import map.Login.LoginSuccessListener;
import javax.swing.*;





public class Main {

    public static void main(String[] args) {
        // Créer et afficher la fenêtre de connexion au démarrage de l'application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Login loginWindow = new Login();
                loginWindow.addLoginSuccessListener(new LoginSuccessListener() {
                    @Override
                    public void onLoginSuccess() {
                        // Une fois que l'utilisateur est connecté, fermer la fenêtre de connexion
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(loginWindow);
                        if (frame != null) {
                            frame.dispose();
                        }

                        // Lancer l'application principale
                        Window window = new Window();
                        window.launch();
                    }
                });
                JFrame frame = new JFrame("SNCF Mappage - Connexion");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(loginWindow);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}

