package com.iteso.motor2d.model.shapes;

import java.awt.*;

import com.iteso.motor2d.model.collision.CollisionMath;
import com.iteso.motor2d.util.IdGenerator;

/**
 * Representa un círculo.
 */
public class Circle extends Shape2D 
{
    
    private int radius;

    /**
     * 
     * @param x Posición x del centro
     * @param y Posición y del centro
     * @param radius Radio del círculo
     * @param color Color del círculo
     * @param id Identificador del círculo
     */
    public Circle(int x, int y, int radius, Color color, int id) 
    {
        super(x, y, color, id);
        this.radius = radius;
    }

    // Getters y setters
    public int getRadius() 
    { 
        return radius; 
    }

    public void setRadius(int radius) 
    { 
        this.radius = radius; 
    }

    public Rectangle getBoundingBox() 
    {
        return new Rectangle(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public void draw(Graphics g) 
    {
        g.setColor(color);
        g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public boolean intersects(Shape2D other) 
    {
        return other.intersectsWithCircle(this);
    }

    @Override 
    public boolean intersectsWithRectangle(Rectangle r)
    {
        return CollisionMath.intersects(r, this);
    }

    @Override
    public boolean intersectsWithCircle(Circle c)
    {
        return CollisionMath.intersects(c, this);
    }

    @Override
    public boolean intersectsWithTriangle(Triangle t)
    {
        return CollisionMath.intersects(this, t);
    }

    @Override
    public Circle clone() 
    {
        return new Circle(x, y, radius, color, IdGenerator.getInstance().generateId());
    }



    @Override
    public String toString() 
    {
        return "Circle2D(id=" + id + ", r=" + radius + ")";
    }
}
