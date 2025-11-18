package com.iteso.motor2d.model.shapes;

import java.awt.*;
import java.util.Objects;

/**
 * Clase abstracta Shape2D:
 * Representa cualquier figura 2D del motor.
 * Implementa atributos comunes y métodos base.
 */
public abstract class Shape2D implements Cloneable {

    protected int x;      // Posición X (centro o punto de referencia)
    protected int y;      // Posición Y
    protected Color color;
    protected final int id;  // ID único e inmutable

    /**
     * Constructor principal
     */
    public Shape2D(int x, int y, Color color, int id) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.id = id;
    }

    // --------- Métodos abstractos (cada figura los implementa) ---------

    /** Dibuja la figura en el canvas */
    public abstract void draw(Graphics g);

    /** Retorna una copia de la figura */
    public abstract Shape2D clone();

    /** Devuelve true si colisiona con otra figura */
    public abstract boolean intersects(Shape2D other);

    // --------- Getters y Setters ---------

    public int getX() { return x; }
    public int getY() { return y; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public int getId() { return id; }

    // --------- Métodos estándar ---------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shape2D)) return false;
        Shape2D shape = (Shape2D) o;
        return id == shape.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(id=" + id + ")";
    }
}
