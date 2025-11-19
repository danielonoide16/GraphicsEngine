package com.iteso.motor2d.view;

import com.iteso.motor2d.model.shapes.Shape2D;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * CanvasPanel:
 * Panel donde se dibujan todas las figuras.
 * Recibe una lista de figuras y las pinta.
 */
public class CanvasPanel extends JPanel 
{

    private List<Shape2D> shapes;

    public CanvasPanel() 
    {
        setBackground(Color.WHITE);
    }

    // Setter para actualizar la lista de figuras del canvas
    public void setShapes(List<Shape2D> shapes) 
    {
        this.shapes = shapes;
        repaint(); // Redibuja el canvas
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        //System.out.println("Paint component");
        super.paintComponent(g);

        if (shapes == null) return;

        for (Shape2D shape : shapes) 
        {
            shape.draw(g); 
        }
    }
}
