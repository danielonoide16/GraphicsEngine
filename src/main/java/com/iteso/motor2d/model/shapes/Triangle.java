package com.iteso.motor2d.model.shapes;

import java.awt.*;

import com.iteso.motor2d.model.collision.CollisionMath;

/**
 * Representa un triángulo rectángulo definido por 3 vértices.
 */
public class Triangle extends Shape2D 
{

    private int[] px;
    private int[] py;

    /**
     * px[] y py[] contienen los vértices
     * se hace así para facilitar el dibujo con drawPolygon, y para facilitar las colisiones 
     * si fuera solo base y altura, no se podrían rotar los triángulos
     * @param px Arreglo con las coordenadas x de los 3 vértices
     * @param py Arreglo con las coordenadas y de los 3 vértices
     * @param color Color del triángulo
     * @param id Identificador del triángulo
     */
    public Triangle(int[] px, int[] py, Color color, int id) 
    {
        super(px[0], py[0], color, id);
        this.px = px.clone();
        this.py = py.clone();
    }

    public Triangle(int x, int y, int base, int width, Color color, int id)
    {
        super(x, y, color, id);
        this.px = new int[3];
        this.py = new int[3];
        setRightTriangle(base, width);
    }

    @Override
    public void draw(Graphics g) 
    {
        g.setColor(color);
        g.drawPolygon(px, py, 3);
        // int width = getWidth();
        // int height = getHeight();
        //draw triangle using width and height
        //g.drawPolygon(new int[]{x, x + width, x}, new int[]{y, y, y + height}, 3);

    }


    // Getters para los puntos
    public int[] getPx() 
    { 
        return px; 
    }

    public int[] getPy() 
    { 
        return py; 
    }

    public int getWidth()
    {
        int minX = Math.min(Math.min(px[0], px[1]), px[2]);
        int maxX = Math.max(Math.max(px[0], px[1]), px[2]);
        return maxX - minX;
    }

    public int getHeight()
    {
        int minY = Math.min(Math.min(py[0], py[1]), py[2]);
        int maxY = Math.max(Math.max(py[0], py[1]), py[2]);
        return maxY - minY;
    }

    public void setPx(int[] px) 
    { 
        this.px = px.clone(); 
    }

    public void setPy(int[] py) 
    { 
        this.py = py.clone(); 
    }

    public Rectangle getBoundingBox() 
    {
        int minX = Math.min(Math.min(px[0], px[1]), px[2]);
        int maxX = Math.max(Math.max(px[0], px[1]), px[2]);
        int minY = Math.min(Math.min(py[0], py[1]), py[2]);
        int maxY = Math.max(Math.max(py[0], py[1]), py[2]);
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }


    public void setRightTriangle(int base, int height)
    {
        // el primer punto es el origen (x,y)
        px[0] = x;
        py[0] = y;

        px[1] = x + base;
        py[1] = y;

        px[2] = x;
        py[2] = y + height;
    }

    @Override
    public boolean intersects(Shape2D other) 
    {
        return other.intersectsWithTriangle(this);
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
    public Triangle clone() 
    {
        return new Triangle(px, py, color, id);
    }

    @Override
    public String toString() 
    {
        return "Triangle(id=" + id + "w=" + getWidth() + ",h=" + getHeight() + ")";
    }
}
