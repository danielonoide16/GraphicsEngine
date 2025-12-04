package com.iteso.motor2d.model.shapes;

import java.awt.*;
import java.util.Objects;

/**
 * Clase abstracta que representa cualquier figura 2D del motor.
 * Implementa atributos comunes y métodos base.
 */
public abstract class Shape2D implements Cloneable 
{
    public static enum ShapeType 
    {
        CIRCLE,
        RECTANGLE,
        TRIANGLE
    }


    protected int x;      // posición X (centro o punto de referencia)
    protected int y;      // posición Y
    protected Color color; // color por defecto
    protected final int id;  // ID inmutable y unico 

    public Shape2D(int x, int y, Color color, int id) 
    {
        this.x = x;
        this.y = y;
        this.color = color;
        this.id = id;
    }

    // --------- Métodos abstractos (cada figura los implementa) ---------

    /** dibuja la figura en el canvas */
    public abstract void draw(Graphics g);

    /** retoran copia de la figura */
    public abstract Shape2D clone();

    /** devuelve true si colisiona con otra figura */
    public abstract boolean intersects(Shape2D other);


    // second dispatch
    protected abstract boolean intersectsWithCircle(Circle c);
    protected abstract boolean intersectsWithRectangle(Rectangle r);
    protected abstract boolean intersectsWithTriangle(Triangle t);

    // --------- Getters y Setters ---------

    public int getX()
    {
        return x; 
    }

    public int getY() 
    {
        return y; 
    }

    public void setX(int x) 
    { 
        this.x = x; 
    }

    public void setY(int y) 
    {
        this.y = y; 
    }

    public Color getColor()
    {
        return color; 
    }
    
    public void setColor(Color color) 
    { 
        this.color = color; 
    }

    public int getId() 
    {
        return id; 
    }

    // --------- Métodos sobreescritos ---------

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (!(o instanceof Shape2D)) return false;
        Shape2D shape = (Shape2D) o;
        return id == shape.id;
    }

    
    /**
     * Genera un hash code basado en el ID de la figura para que dos figuras
     * con el mismo ID tengan el mismo hash code y se puedan usar en colecciones.
     */
    @Override
    public int hashCode() 
    {
        return Objects.hash(id);
    }

    @Override
    public String toString() 
    {
        return getClass().getSimpleName() + "(id=" + id + ")";
    }
}
