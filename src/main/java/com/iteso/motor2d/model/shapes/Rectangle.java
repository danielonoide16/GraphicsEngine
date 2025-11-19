package com.iteso.motor2d.model.shapes;

import java.awt.*;

import com.iteso.motor2d.model.collision.CollisionMath;

/**
 * Representa un rectángulo
 */
public class Rectangle extends Shape2D 
{

    private int width;
    private int height;

    /**
     * Constructor del rectángulo
     * @param x Posición x del rectángulo
     * @param y Posición y del rectángulo
     * @param width Ancho del rectángulo
     * @param height Alto del rectángulo
     * @param color Color del rectángulo
     * @param id Identificador del rectángulo
     */

    public Rectangle(int x, int y, int width, int height) 
    {
        this(x, y, width, height, Color.BLACK, -1); 
    }
    
    public Rectangle(int x, int y, int width, int height, Color color, int id) 
    {
        super(x, y, color, id);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) 
    {
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }

    @Override
    public boolean intersects(Shape2D other) 
    {
        return other.intersectsWithRectangle(this);
    }

    @Override 
    public boolean intersectsWithRectangle(Rectangle r)
    {
        return CollisionMath.intersects(r, this);
    }

    @Override
    public boolean intersectsWithCircle(Circle c)
    {
        return CollisionMath.intersects(this, c);
    }

    @Override
    public boolean intersectsWithTriangle(Triangle t)
    {
        return CollisionMath.intersects(this, t);
    }

    @Override
    public Rectangle clone() 
    {
        return new Rectangle(x, y, width, height, color, id);
    }

    // Getters y setters
    public int getWidth() 
    { 
        return width; 
    }

    public int getHeight() 
    { 
        return height; 
    }

    public void setWidth(int width) 
    { 
        this.width = width; 
    }

    public void setHeight(int height) 
    { 
        this.height = height; 
    }

    @Override
    public String toString() 
    {
        return "Rectangle2D(id=" + id + ", w=" + width + ", h=" + height + ")";
    }
}
