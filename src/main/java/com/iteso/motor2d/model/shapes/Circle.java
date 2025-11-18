package com.iteso.motor2d.model.shapes;

import java.awt.*;

/**
 * Representa un círculo.
 */
public class Circle extends Shape2D {

    private int radius;

    public Circle(int x, int y, int radius, Color color, int id) {
        super(x, y, color, id);
        this.radius = radius;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public boolean intersects(Shape2D other) {
        return false; // Se implementará en CollisionDetector
    }

    @Override
    public Circle clone() {
        return new Circle(x, y, radius, color, id);
    }

    // Getters y setters
    public int getRadius() { return radius; }
    public void setRadius(int radius) { this.radius = radius; }

    @Override
    public String toString() {
        return "Circle2D(id=" + id + ", r=" + radius + ")";
    }
}
