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
    private double offsetX = 0;
    private double offsetY = 0;
    private Point dragStartPoint = null;

    public DrawingArea() {
        setBackground(Color.WHITE);
        equipments = new ArrayList<>();

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

            @Override
            public void mousePressed(MouseEvent e) {
                dragStartPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragStartPoint = null;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragStartPoint != null) {
                    Point dragEndPoint = e.getPoint();
                    offsetX += dragEndPoint.x - dragStartPoint.x;
                    offsetY += dragEndPoint.y - dragStartPoint.y;
                    dragStartPoint = dragEndPoint;
                    repaint();
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
                offsetX = mousePoint.x - (mousePoint.x - offsetX) * (scale / prevScale);
                offsetY = mousePoint.y - (mousePoint.y - offsetY) * (scale / prevScale);

                revalidate();
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
        System.out.println("Component selected: " + componentName + " with width=" + width + " and height=" + height);
    }

    public void addEquipment(String componentName, Point position, int width, int height) {
        if (position != null) {
            Point adjustedPosition = adjustPoint(position);
            // Vérifiez que l'équipement n'existe pas déjà à cette position ajustée
            for (List<Object> equipment : equipments) {
                String existingComponentName = (String) equipment.get(0);
                Point existingPosition = (Point) equipment.get(1);
                if (existingComponentName.equals(componentName) && existingPosition.equals(adjustedPosition)) {
                    System.out.println("Equipment already exists at this position, not adding again.");
                    return;
                }
            }

            List<Object> newEquipment = new ArrayList<>();
            newEquipment.add(componentName);
            newEquipment.add(new Point(adjustedPosition)); // Ajouter la position ajustée
            newEquipment.add(width);
            newEquipment.add(height);
            equipments.add(newEquipment);
            System.out.println("Added equipment: " + componentName + " at " + adjustedPosition + " with width=" + width + ", height=" + height);
            repaint();
        } else {
            System.out.println("Position is null, not adding equipment.");
        }
    }

    private void drawEquipment(List<Object> equipment, Graphics2D g2d) {
        String componentName = (String) equipment.get(0);
        Point position = (Point) equipment.get(1);
        int width = (int) equipment.get(2);
        int height = (int) equipment.get(3);

        if (position != null) {
            Point adjustedPosition = new Point((int) (position.x * scale + offsetX), (int) (position.y * scale + offsetY));
            if (width == 0 && height == 0) {
                drawSquare(adjustedPosition, componentName, g2d);
            } else {
                drawRectangle(adjustedPosition, width, height, componentName, g2d);
            }
        } else {
            System.out.println("Position is null while drawing equipment.");
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
        System.out.println("Original point: " + p + ", Adjusted point: " + adjusted);
        return adjusted;
    }

    public double getScale() {
        return scale;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }
}
