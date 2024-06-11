package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingArea extends JPanel {
    public static final int ACTION_NONE = 0;
    public static final int ACTION_MOVE = 1;
    public static final int ACTION_DELETE = 2;

    private String selectedComponent;
    private Point selectedComponentPosition;
    private int selectedComponentWidth;
    private int selectedComponentHeight;
    private int selectedComponentId;
    private List<Baie> baies;
    private double scale = 1.0;
    private double minScale = 1.0;
    private double offsetX = 0;
    private double offsetY = 0;
    private Point dragStartPoint = null;
    private Equipment draggedEquipment = null;
    private int currentAction = ACTION_NONE;

    private ContextMenuManager contextMenuManager;

    public DrawingArea(ContextMenuManager contextMenuManager) {
        this.contextMenuManager = contextMenuManager;
        setBackground(Color.WHITE);
        baies = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Point clickedPoint = adjustPoint(e.getPoint());
                    dragStartPoint = e.getPoint();

                    if (currentAction == ACTION_MOVE) {
                        for (Baie baie : baies) {
                            if (baie.contains(clickedPoint)) {
                                Equipment equipment = baie.getEquipmentAt(clickedPoint);
                                if (equipment != null) {
                                    draggedEquipment = equipment;
                                    baie.removeEquipment(equipment);
                                    repaint();
                                    return;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (currentAction == ACTION_MOVE && draggedEquipment != null) {
                        Point releasePoint = adjustPoint(e.getPoint());
                        for (Baie baie : baies) {
                            if (baie.contains(releasePoint)) {
                                baie.addEquipment(draggedEquipment);
                                draggedEquipment = null;
                                break;
                            }
                        }
                        repaint();
                    }
                    dragStartPoint = null;
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickedPoint = adjustPoint(e.getPoint());
                if (SwingUtilities.isRightMouseButton(e)) {
                    for (Baie baie : baies) {
                        if (baie.contains(clickedPoint)) {
                            Equipment equipment = baie.getEquipmentAt(clickedPoint);
                            if (equipment != null) {
                                contextMenuManager.showContextMenu(DrawingArea.this, e.getX(), e.getY(), equipment.getId());
                                return;
                            }
                        }
                    }
                } else if (currentAction == ACTION_NONE && selectedComponent != null) {
                    for (Baie baie : baies) {
                        if (baie.contains(clickedPoint)) {
                            Equipment equipmentToRemove = baie.getEquipmentAt(clickedPoint);
                            if (equipmentToRemove != null) {
                                baie.removeEquipment(equipmentToRemove);
                            }

                            Equipment newEquipment = new Equipment(selectedComponent, new Point(clickedPoint.x - selectedComponentWidth / 2, clickedPoint.y - selectedComponentHeight / 2), selectedComponentWidth, selectedComponentHeight, selectedComponentId);
                            baie.addEquipment(newEquipment);
                            selectedComponent = null;
                            selectedComponentPosition = null;
                            selectedComponentWidth = 0;
                            selectedComponentHeight = 0;
                            selectedComponentId = 0;
                            repaint();
                            break;
                        }
                    }
                } else if (currentAction == ACTION_DELETE) {
                    for (Baie baie : baies) {
                        if (baie.contains(clickedPoint)) {
                            Equipment equipmentToRemove = baie.getEquipmentAt(clickedPoint);
                            if (equipmentToRemove != null) {
                                baie.removeEquipment(equipmentToRemove);
                                repaint();
                                return;
                            }
                        }
                    }
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (currentAction == ACTION_MOVE && draggedEquipment != null) {
                        Point dragEndPoint = adjustPoint(e.getPoint());
                        if (dragStartPoint != null && dragEndPoint != null) {
                            draggedEquipment.position = dragEndPoint;
                            repaint();
                        }
                    } else if (dragStartPoint != null) {
                        Point dragEndPoint = e.getPoint();
                        offsetX += dragEndPoint.x - dragStartPoint.x;
                        offsetY += dragEndPoint.y - dragStartPoint.y;
                        dragStartPoint = dragEndPoint;
                        repaint();
                    }
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

        if (currentAction == ACTION_MOVE && draggedEquipment != null) {
            draggedEquipment.draw(g2d, 0, 0);
        }
    }

    public void selectComponent(String componentName, int width, int height, int id) {
        selectedComponent = componentName;
        selectedComponentWidth = width;
        selectedComponentHeight = height;
        selectedComponentId = id;
        System.out.println("Component selected: " + componentName + " with width=" + width + " and height=" + height + " and ID=" + id);
    }

    public void addChassis(int nombreChassis, int largeurChassis, int hauteurChassis) {
        baies.clear();
        int totalWidth = 0;
        int maxHeight = 0;
        for (int i = 0; i < nombreChassis; i++) {
            int baieWidth = largeurChassis;
            int baieHeight = hauteurChassis;
            int x = 150 + i * (baieWidth + 50);
            int y = 100;
            baies.add(new Baie(x, y, baieWidth, baieHeight));
            totalWidth = x + baieWidth;
            maxHeight = Math.max(maxHeight, y + baieHeight);
        }

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        double scaleX = panelWidth / (double) totalWidth;
        double scaleY = panelHeight / (double) maxHeight;
        scale = Math.min(scaleX, scaleY) * 0.9;
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

    public void setBaies(List<Baie> baies) {
        this.baies = baies;
        fitToScreen();
    }

    public void setCurrentAction(int action) {
        this.currentAction = action;
    }

    public void fitToScreen() {
        if (baies.isEmpty()) {
            return;
        }

        int totalWidth = 0;
        int maxHeight = 0;
        for (Baie baie : baies) {
            totalWidth = Math.max(totalWidth, baie.x + baie.width);
            maxHeight = Math.max(maxHeight, baie.y + baie.height);
        }

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        double scaleX = panelWidth / (double) totalWidth;
        double scaleY = panelHeight / (double) maxHeight;
        scale = Math.min(scaleX, scaleY) * 0.9;
        minScale = scale;
        offsetX = (panelWidth - totalWidth * scale) / 2;
        offsetY = (panelHeight - maxHeight * scale) / 2;
        repaint();
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

        public void addEquipment(String componentName, Point position, int width, int height, int id) {
            if (width <= 0 || height <= 0) {
                width = 60;
                height = 60;
            }
            equipments.add(new Equipment(componentName, new Point(position.x - x, position.y - y), width, height, id));
        }

        public void addEquipment(Equipment equipment) {
            equipment.position = new Point(equipment.position.x - x, equipment.position.y - y);
            equipments.add(equipment);
        }

        public void removeEquipment(Equipment equipment) {
            equipments.remove(equipment);
        }

        public boolean contains(Point p) {
            return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
        }

        public Equipment getEquipmentAt(Point p) {
            for (Equipment equipment : equipments) {
                if (equipment.contains(new Point(p.x - x, p.y - y))) {
                    return equipment;
                }
            }
            return null;
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
        private int id;

        public Equipment(String name, Point position, int width, int height, int id) {
            this.name = name;
            this.position = position;
            this.width = width;
            this.height = height;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void move(int dx, int dy) {
            position.translate(dx, dy);
        }

        public boolean contains(Point p) {
            return p.x >= position.x && p.x <= position.x + width && p.y >= position.y && p.y <= position.y + height;
        }

        public void draw(Graphics2D g2d, int offsetX, int offsetY) {
            int drawX = position.x + offsetX;
            int drawY = position.y + offsetY;

            if (width == 60 && height == 60) {
                g2d.setColor(Color.BLUE);
            } else {
                g2d.setColor(new Color(173, 216, 230));
            }
            g2d.fillRect(drawX, drawY, width, height);
            g2d.setColor(Color.GREEN);
            g2d.drawRect(drawX, drawY, width, height);

            FontMetrics metrics = g2d.getFontMetrics();
            int textX = drawX + (width - metrics.stringWidth(name)) / 2;
            int textY = drawY + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
            g2d.setColor(Color.BLACK);
            g2d.drawString(name, textX, textY);
        }
    }
}
