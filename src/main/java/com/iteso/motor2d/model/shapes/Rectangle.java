package com.iteso.motor2d.model.shapes;

import java.awt.*;

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

    // Placeholder: la lógica real va en CollisionDetector
    @Override
    public boolean intersects(Shape2D other) 
    {
        if(other instanceof Rectangle)
        {
            Rectangle otherR = (Rectangle)other;
            return this.x < otherR.x + otherR.width &&
                this.x + this.width > otherR.x &&
                this.y < otherR.y + otherR.height &&
                this.y + this.height > otherR.y;
        }


        if(other instanceof Circle)
        {
            Circle circle = (Circle)other;
            double closestX = Math.clamp(circle.x, this.x, this.x + this.width);
            double closestY = Math.clamp(circle.y, this.y, this.y + this.height);

            double dx = circle.x - closestX;
            double dy = circle.y - closestY;

            return dx*dx + dy*dy < circle.getRadius() * circle.getRadius();
        }

        return false;

        // if(other instanceof Triangle)
        // {
            
        // }
        
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
