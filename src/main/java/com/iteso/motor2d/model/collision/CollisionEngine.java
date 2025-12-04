package com.iteso.motor2d.model.collision;

import com.iteso.motor2d.model.shapes.Shape2D;
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
