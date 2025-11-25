package com.iteso.motor2d.model.scene;

import com.iteso.motor2d.model.shapes.Circle;
import com.iteso.motor2d.model.shapes.Rectangle;
import com.iteso.motor2d.model.shapes.Shape2D;
import com.iteso.motor2d.model.shapes.Triangle;
import com.iteso.motor2d.model.collision.CollisionEngine;
import com.iteso.motor2d.util.IdGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Scene
 * 
 * Contenedor de las figuras (es parte del modelo). 
 * 
 * Esta clase crea figuras por defecto usando
 * IdGenerator para asignar IDs y Color constantes. Mantiene la lista de
 * Shape2D y la figura seleccionada.
 */
public class Scene 
{

    private final List<Shape2D> shapes;
    private Shape2D selectedShape;

    public Scene() 
    {
        shapes = new ArrayList<>();
    }

    /**
     * Crea y agrega un Rectangle por defecto (con color e id).
     */
    public void addRectangle(int x, int y, int width, int height, Color color) 
    {
        int id = IdGenerator.getInstance().generateId();
        //Rectangle r = new Rectangle(50, 50, 120, 80, Color.BLUE, id);
        Rectangle r = new Rectangle(x, y, width, height, color, id);
        shapes.add(r);
        selectedShape = r;
    }

    /**
     * Crea y agrega un Circle por defecto (con color e id).
     */
    public void addCircle(int x, int y, int radius, Color color) 
    {
        int id = IdGenerator.getInstance().generateId();
        //Circle c = new Circle(200, 200, 40, Color.RED, id);
        Circle c = new Circle(x, y, radius, color, id);
        shapes.add(c);
        selectedShape = c;
    }

    /**
     * Crea y agrega un Triangle por defecto (con color e id).
     * Triángulo definido por 3 vértices.
     */
    public void addTriangle(int x, int y, int width, int height, Color color) 
    {
        int id = IdGenerator.getInstance().generateId();
        // int[] px = {300, 340, 260};
        // int[] py = {300, 360, 360};
        
        //Triangle t = new Triangle(300, 300, 100, 50, Color.GREEN, id);
        Triangle t = new Triangle(x, y, width, height, color, id);
        shapes.add(t);
        selectedShape = t;
    }

    /**
     * Selecciona una figura por índice (índice del dropdown).
     */
    public void selectShape(int index) 
    {
        if (index < 0 || index >= shapes.size()) 
        {
            selectedShape = null;
        } 
        else 
        {
            System.out.println("Shape selected: " + shapes.get(index));
            selectedShape = shapes.get(index);
        }
    }

    /**
     * Mueve la figura seleccionada sumando dx, dy a su posición.
     * Usa los setters de Shape2D (setX/setY).
     */
    public void moveSelected(int dx, int dy) 
    {
        if (selectedShape == null) 
        {
            return;
        }

        selectedShape.setX(selectedShape.getX() + dx);
        selectedShape.setY(selectedShape.getY() + dy);

        // Si la figura es Triangle, además mover sus vértices coherentemente
        if (selectedShape instanceof Triangle tri) 
        {
            int[] px = tri.getPx();
            int[] py = tri.getPy();
            for (int i = 0; i < px.length; i++) px[i] += dx;
            for (int i = 0; i < py.length; i++) py[i] += dy;
            tri.setPx(px);
            tri.setPy(py);
        }
    }

    /**
     * Redimensiona la figura seleccionada.
     * Rectangle: setWidth/setHeight
     * Circle: setRadius (usa w como nuevo radio)
     * Triangle: escala los vértices respecto al centro (factor relativo)
     *
     * Nota: w,h pueden ser valores no enteros, para los setters se convierten a int.
     */
    public void resizeSelected(double w, double h) 
    {
        if (selectedShape == null) 
        {
            return;
        }

        if (selectedShape instanceof Rectangle rect) 
        {
            rect.setWidth((int) Math.max(1, Math.round(w)));
            rect.setHeight((int) Math.max(1, Math.round(h)));
        } 
        else if (selectedShape instanceof Circle circ) 
        {
            circ.setRadius((int) Math.max(1, Math.round(w))); // usar w como radio
        } 
        else if (selectedShape instanceof Triangle tri) 
        {
            // escalado simple, se escala respecto al centro promedio de los vértices
            int[] px = tri.getPx().clone();
            int[] py = tri.getPy().clone();
            double cx = (px[0] + px[1] + px[2]) / 3.0;
            double cy = (py[0] + py[1] + py[2]) / 3.0;

            // calcular factores de escala relativos: tratamos w/h como nuevos "anchos"
            // pero aquí interpretamos w como factor X, h como factor Y si >0,
            // si se pasa 0 o 1 manejamos como factor= w/avgWidth (simplificación).
            // Para simplicidad, aplicamos factores relativos basados en w,h respecto a current bounding box.
            int minX = Math.min(px[0], Math.min(px[1], px[2]));
            int maxX = Math.max(px[0], Math.max(px[1], px[2]));
            int minY = Math.min(py[0], Math.min(py[1], py[2]));
            int maxY = Math.max(py[0], Math.max(py[1], py[2]));
            double currentW = Math.max(1, maxX - minX);
            double currentH = Math.max(1, maxY - minY);

            double scaleX = (w > 0) ? (w / currentW) : 1.0;
            double scaleY = (h > 0) ? (h / currentH) : 1.0;

            for (int i = 0; i < px.length; i++) 
            {
                double relX = px[i] - cx;
                double relY = py[i] - cy;
                int newX = (int) Math.round(cx + relX * scaleX);
                int newY = (int) Math.round(cy + relY * scaleY);
                px[i] = newX;
                py[i] = newY;
            }

            tri.setPx(px);
            tri.setPy(py);
            // actualizar la posición de referencia (x,y) al primer vértice
            tri.setX(px[0]);
            tri.setY(py[0]);
        }
    }

    /**
     * Devuelve la lista de nombres para poblar el dropdown (toString de cada figura).
     */
    public List<String> getShapeNames() 
    {
        List<String> names = new ArrayList<>();
        for (Shape2D s : shapes) {
            names.add(s.toString());
        }
        return names;
    }

    /**
     * Construye un reporte de colisiones usando CollisionEngine.
     * Retorna un String con líneas "Figure id (Type) collides with Figure id (Type)".
     */
    public String getCollisionReport() 
    {
        if (shapes.size() < 2) 
        {
            return "No collisions.";
        }

        StringBuilder sb = new StringBuilder();

        List<CollisionEngine.CollisionPair> collisions = CollisionEngine.getInstance().detectCollisions(shapes);

        if (collisions.isEmpty()) 
        {
            return "No collisions.";
        }

        for (CollisionEngine.CollisionPair pair : collisions) 
        {
            Shape2D a = pair.getFirst();
            Shape2D b = pair.getSecond();
            sb.append("Figure ").append(a.getId())
              .append(" (").append(a.getClass().getSimpleName()).append(")")
              .append(" collides with Figure ").append(b.getId())
              .append(" (").append(b.getClass().getSimpleName()).append(")")
              .append("\n");
        }

        return sb.toString();
    }

    /**
     * Agrega una figura dada (si se crea fuera de los helper addX()).
     */
    public void addShape(Shape2D shape) 
    {
        if (shape == null) throw new IllegalArgumentException("Shape cannot be null.");
        shapes.add(shape);
    }

    /**
     * Retorna la lista inmutable de figuras (para que la vista las dibuje).
     */
    public List<Shape2D> getShapes() 
    {
        return Collections.unmodifiableList(shapes);
    }

    public Shape2D getSelectedShape() 
    {
        return selectedShape;
    }

    public void setSelectedShape(Shape2D shape) 
    {
        this.selectedShape = shape;
    }

    public int getSelectedIndex() 
    {
        if (selectedShape == null) return -1;
        return shapes.indexOf(selectedShape);
    }


    public Shape2D getShapeById(int id) 
    {
        for (Shape2D s : shapes) if (s.getId() == id) return s;
        return null;
    }

    public Shape2D getShapeByString(String text) 
    {
        if(text == null) return null;
        for (Shape2D s : shapes) if (s.toString().equals(text)) return s;
        return null;
    }

    public String getPosString()
    {
        if(selectedShape == null)
        {
            return "";
        }

        return "x: " + selectedShape.getX() + " y: " + selectedShape.getY();
    }

    public void removeShape(Shape2D shape) 
    {
        if(shape == null) 
        {
            return;
        }

        shapes.remove(shape);
        
        if(selectedShape == shape) 
        {
            selectedShape = null;
        }
    }

    public void clear() 
    {
        shapes.clear();
        selectedShape = null;
    }
}
