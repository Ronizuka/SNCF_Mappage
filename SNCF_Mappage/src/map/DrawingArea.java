package map;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class DrawingArea extends JPanel {
    private int zoomFactor = 1; // Facteur de zoom initial
    private int xOffset = 0; // Décalage horizontal initial
    private int yOffset = 0; // Décalage vertical initial

    public DrawingArea() {
        setBackground(Color.WHITE);

        // Ajouter un écouteur d'événements de souris pour détecter les mouvements de la molette
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // Récupérer la position de la souris avant le zoom
                Point originalMousePos = e.getPoint();

                // Vérifier si la molette de la souris a été déplacée vers le haut (zoom avant)
                if (e.getWheelRotation() < 0) {
                    zoomIn(originalMousePos);
                } 
                // Vérifier si la molette de la souris a été déplacée vers le bas (zoom arrière)
                else {
                    zoomOut(originalMousePos);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Appliquer le zoom et le décalage
        g2d.scale(zoomFactor, zoomFactor);
        g2d.translate(-xOffset, -yOffset);

        // Dessiner des formes ici
        g2d.setColor(Color.BLACK);
        g2d.fillRect(100, 100, 50, 50); // Exemple de dessin d'un rectangle
    }

    // Méthode pour changer le facteur de zoom
    public void setZoom(int zoomFactor) {
        this.zoomFactor = zoomFactor;
        repaint(); // Redessiner la zone de dessin avec le nouveau facteur de zoom
    }

    // Méthode pour effectuer un zoom avant centré sur la position de la souris
    public void zoomIn(Point mousePos) {
        int oldZoomFactor = zoomFactor;
        zoomFactor++;
        adjustOffsets(mousePos, oldZoomFactor);
        repaint();
    }

    // Méthode pour effectuer un zoom arrière centré sur la position de la souris
    public void zoomOut(Point mousePos) {
        if (zoomFactor > 1) {
            int oldZoomFactor = zoomFactor;
            zoomFactor--;
            adjustOffsets(mousePos, oldZoomFactor);
            repaint();
        }
    }

    // Méthode pour ajuster les décalages en fonction du nouveau facteur de zoom
    private void adjustOffsets(Point mousePos, int oldZoomFactor) {
        // Calculer le décalage nécessaire pour maintenir la position de la souris inchangée
        int deltaX = (int) ((mousePos.x - xOffset) * (zoomFactor - oldZoomFactor));
        int deltaY = (int) ((mousePos.y - yOffset) * (zoomFactor - oldZoomFactor));
        
        // Appliquer le décalage
        xOffset -= deltaX;
        yOffset -= deltaY;

        // Limiter les décalages pour éviter les valeurs négatives
        xOffset = Math.max(0, xOffset);
        yOffset = Math.max(0, yOffset);
    }
}