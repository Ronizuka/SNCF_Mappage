package map;
import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Window window = new Window();
                    window.launch();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}



