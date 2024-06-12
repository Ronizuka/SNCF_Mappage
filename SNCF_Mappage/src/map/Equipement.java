package map;

import java.awt.*;

public class Equipement {
    private String name;
    private Point position;
    private int width;
    private int height;
    private int id;

    public Equipement(String name, Point position, int width, int height, int id) {
        this.name = name;
        this.position = position;
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Point getCenter() {
        return new Point(position.x + width / 2, position.y + height / 2);
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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

    public Point getConnectorPosition(String connector) {
        switch (connector) {
            case "top":
                return new Point(position.x + width / 2, position.y);
            case "bottom":
                return new Point(position.x + width / 2, position.y + height);
            case "left":
                return new Point(position.x, position.y + height / 2);
            case "right":
                return new Point(position.x + width, position.y + height / 2);
            default:
                return new Point(position.x + width / 2, position.y + height / 2);
        }
    }
}
