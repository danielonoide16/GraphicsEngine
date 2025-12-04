package com.iteso.motor2d.model.collision;

import com.iteso.motor2d.model.shapes.Circle;
import com.iteso.motor2d.model.shapes.Rectangle;
import com.iteso.motor2d.model.shapes.Triangle;

public class CollisionMath 
{

    /**
     * AABB vs AABB
     * @param a First rectangle
     * @param b Second rectangle
     * @return true if they intersect, false otherwise
     * It's named AABB because it uses Axis-Aligned Bounding Boxes (no rotation)
     * An AABB is defined by its top-left corner (x,y), width and height.
     * Axis-Aligned means the edges are parallel to the axes.
     */
    public static boolean intersects(Rectangle a, Rectangle b) 
    {
        return a.getX() < b.getX() + b.getWidth() &&
               a.getX() + a.getWidth() > b.getX() &&
               a.getY() < b.getY() + b.getHeight() &&
               a.getY() + a.getHeight() > b.getY();
    }

    /**
     * Circle vs Circle
     * @param a, is first circle
     * @param b, is second circle
     * @return true if they intersect, false otherwise
     */
    public static boolean intersects(Circle a, Circle b) 
    {
        if(a.equals(b)) return true; // mismo objeto, colisionan
        //this works by comparing the squared distance between centers to the squared sum of radii
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double sum = a.getRadius() + b.getRadius();
        return dx * dx + dy * dy <= sum * sum;
    }

    public static boolean intersects(Rectangle r, Circle c) 
    {
        //Esto funciona encontrando el punto en el rectángulo más cercano al centro del círculo
        double closestX = Math.clamp(c.getX(), r.getX(), r.getX() + r.getWidth()); //clamp es un método que limita un valor entre un mínimo y un máximo
        double closestY = Math.clamp(c.getY(), r.getY(), r.getY() + r.getHeight()); //clamp se usa aquí para asegurar que el punto más cercano esté dentro de los límites del rectángulo

        //Luego calculamos la distancia entre ese punto y el centro del círculo
        double dx = c.getX() - closestX;
        double dy = c.getY() - closestY;

        //Finalmente, comparamos la distancia al radio del círculo

        return dx * dx + dy * dy <= c.getRadius() * c.getRadius();
    }

    //Estos metodos funcionan solo para triangulos rectos definidos con setRightTriangle
    public static boolean intersects(Rectangle rect, Triangle tri)  
    {
        // 1. Si el rectángulo intersecta el bounding-box del triangulo
        //if (!intersectsBoundingBox(rect, tri)) return false;
        if(!intersects(rect, tri.getBoundingBox())) return false;

        // 2. Si cualquier vértice del rect está dentro del triángulo
        if (pointInTriangle(rect.getX(), rect.getY(), tri)) return true;
        if (pointInTriangle(rect.getX() + rect.getWidth(), rect.getY(), tri)) return true;
        if (pointInTriangle(rect.getX(), rect.getY() + rect.getHeight(), tri)) return true;
        if (pointInTriangle(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight(), tri)) return true;

        // 3. Si cualquier vértice del triángulo cae dentro del rectángulo
        for (double[] p : triVertices(tri)) {
            if (pointInRect(p[0], p[1], rect)) return true;
        }

        // 4. Checar intersección entre los segmentos del triángulo y los del rectángulo
        return edgesIntersect(rect, tri);
    }


    public static boolean intersects(Circle c, Triangle t) 
    {

        // 1. Checar bounding boxes
        if (!intersects(c.getBoundingBox(), t.getBoundingBox()))
            return false;

        // Centro y radio del círculo
        double cx = c.getX(), cy = c.getY(), r = c.getRadius();

        // 2. Centro dentro del triángulo
        if (pointInTriangle(cx, cy, t)) return true;

        // 3. Distancia del centro a cada arista del triángulo
        for (double[] e : triEdges(t))
            if (distancePointToSegment(cx, cy, e[0], e[1], e[2], e[3]) <= r)
                return true;

        // 4. Vértices del triángulo dentro del círculo
        for (double[] p : triVertices(t)) {
            double dx = cx - p[0];
            double dy = cy - p[1];
            if (dx*dx + dy*dy <= r*r) return true;
        }

        return false;
    }


    public static boolean intersects(Triangle a, Triangle b) 
    {
        if(a.equals(b)) return true; // mismo objeto, colisionan

        // 1. Checar bounding boxes
        if(!intersects(a.getBoundingBox(), b.getBoundingBox())) return false;

        // 2. Un vértice dentro del otro triángulo
        for (double[] p : triVertices(a)) {
            if (pointInTriangle(p[0], p[1], b)) return true;
        }
        for (double[] p : triVertices(b)) {
            if (pointInTriangle(p[0], p[1], a)) return true;
        }

        // 3. Checar intersección entre lados
        for (double[] ea : triEdges(a)) 
        {
            for (double[] eb : triEdges(b)) 
            {
                if (segmentsIntersect(ea, eb)) return true;
            }
        }

        return false;
    }

    /**
     * Calcula la distancia al cuadrado entre dos puntos
     * @param x1 coordenada x del primer punto
     * @param y1 coordenada y del primer punto
     * @param x2 coordenada x del segundo punto
     * @param y2 coordenada y del segundo punto
     * @return
     */
    public static double dist2(double x1, double y1, double x2, double y2) 
    {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return dx * dx + dy * dy;
    }

    /**
     * Calcula la distancia mínima entre un punto y un segmento
     * @param px coordenada x del punto
     * @param py coordenada y del punto
     * @param x1 coordenada x del primer extremo del segmento
     * @param y1 coordenada y del primer extremo del segmento
     * @param x2 coordenada x del segundo extremo del segmento
     * @param y2 coordenada y del segundo extremo del segmento
     * @return
     */
    public static double distancePointToSegment(double px, double py, double x1, double y1, double x2, double y2) 
    {
        // Vector del segmento
        double vx = x2 - x1;
        double vy = y2 - y1;

        // Vector del punto a un extremo
        double wx = px - x1;
        double wy = py - y1;

        double len2 = vx * vx + vy * vy; // largo^2 del segmento

        // Caso degenerado: el segmento es un punto
        if (len2 == 0.0) {
            return Math.sqrt(dist2(px, py, x1, y1));
        }

        // Proyección escalar del punto sobre el segmento (factor t)
        double t = (wx * vx + wy * vy) / len2;

        if (t < 0) {
            // Más cerca del punto A
            return Math.sqrt(dist2(px, py, x1, y1));
        } else if (t > 1) {
            // Más cerca del punto B
            return Math.sqrt(dist2(px, py, x2, y2));
        }

        // Proyección interna: punto perpendicular dentro del segmento
        double projX = x1 + t * vx;
        double projY = y1 + t * vy;

        return Math.sqrt(dist2(px, py, projX, projY));
    }


    /**
    * Comprueba si alguna arista del rectángulo intersecta alguna arista del triángulo.
    * @param rect es el rectángulo
    * @param tri es el triángulo
    * @return true si alguna arista intersecta, false en caso contrario
    */
    private static boolean edgesIntersect(Rectangle rect, Triangle tri) 
    {
        // Aristas del rectángulo (x1,y1,x2,y2)
        double rx = rect.getX();
        double ry = rect.getY();
        double rw = rect.getWidth();
        double rh = rect.getHeight();

        double[][] rectEdges = new double[][] {
            { rx,       ry,       rx + rw, ry       }, // top
            { rx + rw,  ry,       rx + rw, ry + rh  }, // right
            { rx + rw,  ry + rh,  rx,      ry + rh  }, // bottom
            { rx,       ry + rh,  rx,      ry       }  // left
        };

        // Aristas del triángulo (x1,y1,x2,y2)
        double[][] triangleEdges = triEdges(tri);

        // Compara cada par de aristas
        for (double[] re : rectEdges) 
        {
            for (double[] te : triangleEdges) 
            {
                if (segmentsIntersect(re, te)) return true;
            }
        }
        return false;
    }



     private static boolean pointInRect(double px, double py, Rectangle r) 
     {
        //funciona verificando si las coordenadas del punto están dentro de los límites del rectángulo
        return px >= r.getX() && px <= r.getX() + r.getWidth() &&
               py >= r.getY() && py <= r.getY() + r.getHeight();
    }

    /**
     * Checa si un punto está dentro de un triángulo
     * @param px coordenada x del punto
     * @param py coordenada y del punto
     * @param t es el triángulo
     * @return
     */
    private static boolean pointInTriangle(double px, double py, Triangle t) 
    {
        double x1 = t.getPx()[0], y1 = t.getPy()[0];
        double x2 = t.getPx()[1], y2 = t.getPy()[1];
        double x3 = t.getPx()[2], y3 = t.getPy()[2];

        // Signos de las áreas (cross products)
        //estos productos cruzados determinan de qué lado de cada arista del triángulo se encuentra el punto
        boolean b1 = cross(px, py, x1, y1, x2, y2) < 0.0;
        boolean b2 = cross(px, py, x2, y2, x3, y3) < 0.0;
        boolean b3 = cross(px, py, x3, y3, x1, y1) < 0.0;

        // Si todos los signos son iguales → dentro
        return (b1 == b2) && (b2 == b3);
    }

    private static double cross(double px, double py, double x1, double y1, double x2, double y2)
    {
        return (x2 - x1) * (py - y1) - (y2 - y1) * (px - x1);
    }


    /**
     * Obtiene los vértices del triángulo como puntos (x,y)
     * @param t triangulo
     * @return un arreglo de puntos [[x1,y1],[x2,y2],[x3,y3]]
     */
    private static double[][] triVertices(Triangle t) 
    {
        return new double[][] {
            { t.getPx()[0], t.getPy()[0] },
            { t.getPx()[1], t.getPy()[1] },
            { t.getPx()[2], t.getPy()[2] }
        };
    }

    /**
     * Obtiene las aristas del triángulo como segmentos (x1,y1,x2,y2)
     * @param t es el triángulo
     * @return un arreglo de segmentos [[x1,y1,x2,y2],...]
     */
    private static double[][] triEdges(Triangle t) 
    {
        int[] px = t.getPx();
        int[] py = t.getPy();
        return new double[][] {
            {px[0], py[0], px[1], py[1]},
            {px[1], py[1], px[2], py[2]},
            {px[2], py[2], px[0], py[0]}
        };
    }

    /**
     * Checa si dos segmentos se intersectan
     * @param s1 es el primer segmento (x1,y1,x2,y2)
     * @param s2 es el segundo segmento (x1,y1,x2,y2)
     * @return
     */
    private static boolean segmentsIntersect(double[] s1, double[] s2) 
    {
        //primero calcula el determinante para ver si los segmentos son paralelos
        double x1 = s1[0], y1 = s1[1];
        double x2 = s1[2], y2 = s1[3];
        double x3 = s2[0], y3 = s2[1];
        double x4 = s2[2], y4 = s2[3];

        // Determinante
        double d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
        if (d == 0) return false; // Paralelos

        // Aquí calcula los parámetros t y u para ver si la intersección está dentro de ambos segmentos
        //los parametros t y u representan las posiciones relativas a lo largo de los segmentos donde ocurre la intersección
        double t = ((x1-x3)*(y3-y4) - (y1-y3)*(x3-x4)) / d;
        double u = ((x1-x3)*(y1-y2) - (y1-y3)*(x1-x2)) / d;

        // Si t y u están entre 0 y 1, los segmentos se intersectan
        return t >= 0 && t <= 1 && u >= 0 && u <= 1;
    }
    
}
