package com.iteso.motor2d.model.shapes;

import java.awt.*;

/**
 * Representa un triángulo definido por 3 vértices.
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

    @Override
    public boolean intersects(Shape2D other) 
    {
        return false; // Colisiones se implementan después
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
