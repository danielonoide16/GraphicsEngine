package com.iteso.motor2d.model.shapes;

import java.awt.*;

/**
 * Representa un triángulo definido por 3 vértices.
 */
public class Triangle extends Shape2D {

    private int[] px;
    private int[] py;

    /**
     * px[] y py[] contienen los vértices
     */
    public Triangle(int[] px, int[] py, Color color, int id) {
        super(px[0], py[0], color, id);
        this.px = px.clone();
        this.py = py.clone();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawPolygon(px, py, 3);
    }

    @Override
    public boolean intersects(Shape2D other) {
        return false; // Colisiones se implementan después
    }

    @Override
    public Triangle clone() {
        return new Triangle(px, py, color, id);
    }

    // Getters para los puntos
    public int[] getPx() { return px; }
    public int[] getPy() { return py; }

    public void setPx(int[] px) { this.px = px.clone(); }
    public void setPy(int[] py) { this.py = py.clone(); }

    @Override
    public String toString() {
        return "Triangle(id=" + id + ")";
    }
}
