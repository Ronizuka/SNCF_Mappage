package map;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

class DrawingArea extends JPanel {
    private String selectedComponent;
    private Point selectedComponentPosition;
    private int selectedComponentWidth;
    private int selectedComponentHeight;
    private List<List<Object>> equipments;
    private double scale = 1.0;
    private Dimension screenSize;
    private double offsetX = 0;
    private double offsetY = 0;

    public DrawingArea() {
        setBackground(Color.WHITE);
        equipments = new ArrayList<>();

        // Obtenez la taille de l'écran
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize); // Utilisez la taille de l'écran comme taille préférée initiale

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedComponent != null) {
                    Point clickedPoint = adjustPoint(e.getPoint());
                    System.out.println("Mouse clicked at: " + e.getPoint() + ", adjusted to: " + clickedPoint);
                    addEquipment(selectedComponent, clickedPoint, selectedComponentWidth, selectedComponentHeight);
                    selectedComponent = null;
                    selectedComponentPosition = null;
                    selectedComponentWidth = 0;
                    selectedComponentHeight = 0;
                }
            }
        });

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                Point mousePoint = e.getPoint();
                double prevScale = scale;
                if (e.getPreciseWheelRotation() < 0) {
                    scale *= 1.1;
                } else {
                    scale /= 1.1;
                }

                // Adjust offset to keep mouse position consistent
                offsetX = mousePoint.x - scale * (mousePoint.x - offsetX) / prevScale;
                offsetY = mousePoint.y - scale * (mousePoint.y - offsetY) / prevScale;

                revalidate(); // Réajuste la taille de la zone de dessin
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Appliquez l'échelle et le décalage
        g2d.translate(offsetX, offsetY);
        g2d.scale(scale, scale);

        // Dessinez les équipements
        for (List<Object> equipment : equipments) {
            drawEquipment(equipment, g2d);
        }
    }

    public void selectComponent(String componentName, int width, int height) {
        selectedComponent = componentName;
        selectedComponentWidth = width;
        selectedComponentHeight = height;
    }

    public void addEquipment(String componentName, Point position, int width, int height) {
        if (position != null) {
            List<Object> newEquipment = new ArrayList<>();
            newEquipment.add(componentName);
            newEquipment.add(position);
            newEquipment.add(width);
            newEquipment.add(height);
            equipments.add(newEquipment);
            System.out.println("Added equipment: " + componentName + " at " + position + " with width=" + width + ", height=" + height);
            repaint();
        }
    }

    private void drawEquipment(List<Object> equipment, Graphics2D g2d) {
        String componentName = (String) equipment.get(0);
        Point position = (Point) equipment.get(1);
        int width = (int) equipment.get(2);
        int height = (int) equipment.get(3);

        if (position != null) {
            if (width == 0 && height == 0) {
                drawSquare(position, componentName, g2d);
            } else {
                drawRectangle(position, width, height, componentName, g2d);
            }
        }
    }

    private void drawSquare(Point position, String componentName, Graphics2D g2d) {
        int x = position.x - 50; // Centrer le carré horizontalement
        int y = position.y - 50; // Centrer le carré verticalement
        g2d.setColor(new Color(173, 216, 230)); // Couleur bleu ciel
        g2d.fillRect(x, y, 100, 100);
        g2d.setColor(Color.GREEN); // Bordure verte
        g2d.drawRect(x, y, 100, 100); // Dessiner la bordure
        drawCenteredString(componentName, new Rectangle(x, y, 100, 100), g2d); // Dessiner le nom au centre
    }

    private void drawRectangle(Point position, int width, int height, String componentName, Graphics2D g2d) {
        int x = position.x - (width / 2);
        int y = position.y - (height / 2);
        g2d.setColor(new Color(173, 216, 230)); // Couleur bleu ciel
        g2d.fillRect(x, y, width, height);
        g2d.setColor(Color.BLUE); // Bordure bleue
        g2d.drawRect(x, y, width, height); // Dessiner la bordure
        drawCenteredString(componentName, new Rectangle(x, y, width, height), g2d); // Dessiner le nom au centre
    }

    private void drawCenteredString(String text, Rectangle rect, Graphics2D g2d) {
        FontMetrics metrics = g2d.getFontMetrics();
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.setColor(Color.BLACK); // Couleur du texte en noir
        g2d.drawString(text, x, y);
    }

    private Point adjustPoint(Point p) {
        Point adjusted = new Point((int) ((p.x - offsetX) / scale), (int) ((p.y - offsetY) / scale));
        System.out.println("Adjusted point: " + adjusted);
        return adjusted;
    }

    public double getScale() {
        return scale;
    }
}
