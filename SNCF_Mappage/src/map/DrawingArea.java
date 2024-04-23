package map;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class DrawingArea extends JPanel {
    private String selectedComponent;
    private Point selectedComponentPosition;
    private int selectedComponentWidth;
    private int selectedComponentHeight;
    private List<List<Object>> equipments;

    public DrawingArea() {
        setBackground(Color.WHITE);
        equipments = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (List<Object> equipment : equipments) {
            drawEquipment(equipment, (Graphics2D) g);
        }
        if (selectedComponent != null && selectedComponentPosition != null) {
            drawComponent(selectedComponent, selectedComponentPosition, selectedComponentWidth, selectedComponentHeight, (Graphics2D) g);
        }
    }

    public void placeSelectedComponent(String componentName, Point position, int width, int height) {
        selectedComponent = componentName;
        selectedComponentPosition = position;
        selectedComponentWidth = width;
        selectedComponentHeight = height;
        repaint();
    }

    public void addEquipment(String componentName, Point position, int width, int height) {
        List<Object> newEquipment = new ArrayList<>();
        newEquipment.add(componentName);
        newEquipment.add(position);
        newEquipment.add(width);
        newEquipment.add(height);
        equipments.add(newEquipment);
        repaint();
    }

    private void drawComponent(String componentName, Point position, int width, int height, Graphics2D g2d) {
        if (componentName != null && position != null) {
            if (width == 0 && height == 0) {
                drawSquare(position, componentName, g2d);
            } else {
                drawRectangle(position, width, height, componentName, g2d);
            }
        }
    }

    private void drawEquipment(List<Object> equipment, Graphics2D g2d) {
        String componentName = (String) equipment.get(0);
        Point position = (Point) equipment.get(1);
        int width = (int) equipment.get(2);
        int height = (int) equipment.get(3);

        if (width == height) {
            drawSquare(position, componentName, g2d);
        } else {
            drawRectangle(position, width, height, componentName, g2d);
        }
    }

    private void drawSquare(Point position, String componentName, Graphics2D g2d) {
        int x = position.x - 30; // Centrer le carré horizontalement
        int y = position.y - 30; // Centrer le carré verticalement
        g2d.setColor(new Color(173, 216, 230)); // Couleur bleu ciel
        g2d.fillRect(x, y, 60, 60);
        g2d.setColor(Color.GREEN); // Bordure verte
        g2d.drawRect(x, y, 60, 60); // Dessiner la bordure
        drawCenteredString(componentName, new Rectangle(x, y, 60, 60), g2d); // Dessiner le nom au centre
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
}
