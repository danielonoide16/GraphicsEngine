package com.iteso.motor2d.model.shapes;

import java.awt.*;

/**
 * Representa un rectángulo en el motor de física.
 */
public class Rectangle extends Shape2D {

    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height, Color color, int id) 
    {
        super(x, y, color, id);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }

    // Placeholder: la lógica real va en CollisionDetector
    @Override
    public boolean intersects(Shape2D other) {
        return false;
    }

    @Override
    public Rectangle clone() {
        return new Rectangle(x, y, width, height, color, id);
    }

    // Getters y setters
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }

    @Override
    public String toString() {
        return "Rectangle2D(id=" + id + ", w=" + width + ", h=" + height + ")";
    }
}
