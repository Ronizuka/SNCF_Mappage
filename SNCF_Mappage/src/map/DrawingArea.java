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
    private List<Baie> baies;
    private double scale = 1.0;
    private double minScale = 1.0;
    private double offsetX = 0;
    private double offsetY = 0;
    private Point dragStartPoint = null;

    public DrawingArea() {
        setBackground(Color.WHITE);
        baies = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedComponent != null) {
                    Point clickedPoint = adjustPoint(e.getPoint());
                    for (Baie baie : baies) {
                        if (baie.contains(clickedPoint)) {
                            baie.addEquipment(selectedComponent, clickedPoint, selectedComponentWidth, selectedComponentHeight);
                            break;
                        }
                    }
                    selectedComponent = null;
                    selectedComponentPosition = null;
                    selectedComponentWidth = 0;
                    selectedComponentHeight = 0;
                    repaint();
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
                if (e.getPreciseWheelRotation() < 0 && scale < 4.0) {
                    scale *= 1.1;
                } else if (e.getPreciseWheelRotation() > 0 && scale > minScale) {
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

        g2d.translate(offsetX, offsetY);
        g2d.scale(scale, scale);

        for (Baie baie : baies) {
            baie.draw(g2d);
        }
    }

    public void selectComponent(String componentName, int width, int height) {
        selectedComponent = componentName;
        selectedComponentWidth = width;
        selectedComponentHeight = height;
        System.out.println("Component selected: " + componentName + " with width=" + width + " and height=" + height);
    }

    public void addBaies(int nombreBaies) {
        baies.clear();
        int totalWidth = 0;
        int maxHeight = 0;
        for (int i = 0; i < nombreBaies; i++) {
            int baieWidth = 1300;
            int baieHeight = 2000;
            int x = 150 + i * (baieWidth + 50);
            int y = 100;
            baies.add(new Baie(x, y, baieWidth, baieHeight));
            totalWidth = x + baieWidth;
            maxHeight = Math.max(maxHeight, y + baieHeight);
        }
        // Adjust the scale to fit all baies
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        double scaleX = panelWidth / (double) totalWidth;
        double scaleY = panelHeight / (double) maxHeight;
        scale = Math.min(scaleX, scaleY) * 0.9; // Apply a little padding
        minScale = scale;
        offsetX = (panelWidth - totalWidth * scale) / 2;
        offsetY = (panelHeight - maxHeight * scale) / 2;
        repaint();
    }

    private Point adjustPoint(Point p) {
        return new Point((int) ((p.x - offsetX) / scale), (int) ((p.y - offsetY) / scale));
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

    public List<Baie> getBaies() {
        return baies;
    }

    class Baie {
        private int x, y, width, height;
        private List<Equipment> equipments;

        public Baie(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.equipments = new ArrayList<>();
        }

        public void addEquipment(String componentName, Point position, int width, int height) {
            if (width <= 0 || height <= 0) {
                width = 60;
                height = 60;
            }
            equipments.add(new Equipment(componentName, new Point(position.x - x, position.y - y), width, height));
        }

        public boolean contains(Point p) {
            return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
        }

        public void draw(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, width, height);

            for (Equipment equipment : equipments) {
                equipment.draw(g2d, x, y);
            }
        }
    }

    class Equipment {
        private String name;
        private Point position;
        private int width, height;

        public Equipment(String name, Point position, int width, int height) {
            this.name = name;
            this.position = position;
            this.width = width;
            this.height = height;
        }

        public void draw(Graphics2D g2d, int offsetX, int offsetY) {
            int drawX = position.x + offsetX;
            int drawY = position.y + offsetY;

            if (width == 60 && height == 60) {
                g2d.setColor(Color.BLUE); // Couleur bleue pour les équipements avec dimensions par défaut
            } else {
                g2d.setColor(new Color(173, 216, 230)); // Couleur bleu ciel pour les autres équipements
            }
            g2d.fillRect(drawX, drawY, width, height); // Remplir le rectangle avant de dessiner le texte
            g2d.setColor(Color.GREEN); // Bordure verte pour tous les équipements
            g2d.drawRect(drawX, drawY, width, height); // Dessiner la bordure

            FontMetrics metrics = g2d.getFontMetrics();
            int textX = drawX + (width - metrics.stringWidth(name)) / 2;
            int textY = drawY + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
            g2d.setColor(Color.BLACK); // Couleur du texte en noir
            g2d.drawString(name, textX, textY);
        }
    }
}
