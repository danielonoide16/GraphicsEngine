package com.iteso.motor2d.model.collision;

import com.iteso.motor2d.model.shapes.Shape2D;
import com.iteso.motor2d.model.shapes.Rectangle;
import com.iteso.motor2d.model.shapes.Circle;
import com.iteso.motor2d.model.shapes.Triangle;

import java.awt.Shape;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * CollisionEngine
 * Motor singleton encargado de detectar colisiones entre Shape2D.
 *
 * Esta clase construye objetos java.awt.Shape
 * a partir de las clases del modelo (Rectangle, Circle, Triangle)
 * y usa java.awt.geom.Area para comprobar intersecciones geométricas.
 */
public final class CollisionEngine 
{

    // Instancia única (singleton)
    private static final CollisionEngine INSTANCE = new CollisionEngine();

    // Constructor privado para evitar instanciación externa
    private CollisionEngine() {}

    // Método para obtener la instancia singleton
    public static CollisionEngine getInstance() 
    {
        return INSTANCE;
    }

    /**
     * Detecta todas las colisiones entre figuras de la lista.
     *
     * @param shapes lista de figuras (no debe ser null)
     * @return lista con pares de figuras que colisionan
     * @throws IllegalArgumentException si shapes es null o contiene nulls
     */

    public List<CollisionPair> detectCollisions(List<? extends Shape2D> shapes) 
    {
        if (shapes == null) 
        {
            throw new IllegalArgumentException("Shape list cannot be null.");
        }

        List<CollisionPair> collisions = new ArrayList<>();

        //usando awt shapes y areas
        // Comparación de todos contra todos (i < j)
        // for (int i = 0; i < shapes.size(); i++) 
        // {
        //     Shape2D a = shapes.get(i);
        //     if (a == null) 
        //     {
        //         throw new IllegalArgumentException("Shape element cannot be null.");
        //     }

        //     Shape awtShape = toAwtShape(a);
        //     if(awtShape == null) 
        //     {
        //         continue; 
        //     }

        //     for(int j = i + 1; j < shapes.size(); j++) 
        //     {
        //         Shape2D b = shapes.get(j);
        //         if(b == null) 
        //         {
        //             throw new IllegalArgumentException("Shape element cannot be null.");
        //         }

        //         Shape bwtShape = toAwtShape(b);

        //         if(bwtShape == null) 
        //         {
        //             continue;
        //         }

        //         if(checkIntersection(awtShape, bwtShape)) 
        //         {
        //             collisions.add(new CollisionPair(a, b));
        //         }
        //     }
        // }

        //usando CollisionMath

        for (int i = 0; i < shapes.size(); i++) 
        {
            Shape2D a = shapes.get(i);
            if (a == null) 
            {
                throw new IllegalArgumentException("Shape element cannot be null.");
            }

            for(int j = i + 1; j < shapes.size(); j++) 
            {
                Shape2D b = shapes.get(j);
                if(b == null) 
                {
                    throw new IllegalArgumentException("Shape element cannot be null.");
                }

                if(a.intersects(b)) 
                {
                    collisions.add(new CollisionPair(a, b));
                }
            }
        }

        return collisions;
    }

    /**
     * Convierte una Shape2D del modelo a un java.awt.Shape adecuado.
     *
     * @param s figura del modelo
     * @return java.awt.Shape o null si no es representable
     */
    private Shape toAwtShape(Shape2D s) 
    {
        if (s instanceof Rectangle rect) 
        {
            // En nuestro modelo Rectangle guarda x,y,width,height (x,y como esquina superior izquierda)
            return new Rectangle2D.Double(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        } 
        else if (s instanceof Circle circ) 
        {
            // Circle guarda x,y como centro (según definición previa), radius
            double r = circ.getRadius();
            return new Ellipse2D.Double(circ.getX() - r, circ.getY() - r, r * 2, r * 2);
        } 
        else if (s instanceof Triangle tri) 
        {
            int[] px = tri.getPx();
            int[] py = tri.getPy();
            if (px == null || py == null || px.length != 3 || py.length != 3) return null;
            return new Polygon(px, py, 3);
        } 
        else 
        {
            // Si hay nuevas figuras no soportadas, retornar null
            return null;
        }
    }

    /**
     * Comprueba si dos java.awt.Shape se intersectan usando Area.
     *
     * @param a primer shape
     * @param b segundo shape
     * @return true si intersectan
     */
    private boolean checkIntersection(Shape a, Shape b) 
    {
        try {
            Area areaA = new Area(a);
            Area areaB = new Area(b);
            areaA.intersect(areaB);
            return !areaA.isEmpty();
        } catch (Exception ex) {
            // En caso de error geométrico, lo registramos y devolvemos false
            System.err.println("CollisionEngine.checkIntersection error: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Clase estática que representa un par de figuras en colisión.
     */
    public static class CollisionPair 
    {
        private final Shape2D first;
        private final Shape2D second;

        public CollisionPair(Shape2D first, Shape2D second) 
        {
            this.first = first;
            this.second = second;
        }

        public Shape2D getFirst() 
        { 
            return first; 
        }

        public Shape2D getSecond() 
        { 
            return second; 
        }

        @Override
        public String toString() 
        {
            return first.toString() + " <-> " + second.toString();
        }
    }
}
