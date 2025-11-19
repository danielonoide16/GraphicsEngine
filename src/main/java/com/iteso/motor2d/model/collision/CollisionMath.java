package com.iteso.motor2d.model.collision;

import com.iteso.motor2d.model.shapes.Circle;
import com.iteso.motor2d.model.shapes.Rectangle;
import com.iteso.motor2d.model.shapes.Triangle;

public class CollisionMath 
{
    public static boolean intersects(Rectangle a, Rectangle b) 
    {
        return a.getX() < b.getX() + b.getWidth() &&
               a.getX() + a.getWidth() > b.getX() &&
               a.getY() < b.getY() + b.getHeight() &&
               a.getY() + a.getHeight() > b.getY();
    }

    public static boolean intersects(Circle a, Circle b) 
    {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double sum = a.getRadius() + b.getRadius();
        return dx * dx + dy * dy <= sum * sum;
    }

    public static boolean intersects(Rectangle r, Circle c) 
    {
        double closestX = Math.clamp(c.getX(), r.getX(), r.getX() + r.getWidth());
        double closestY = Math.clamp(c.getY(), r.getY(), r.getY() + r.getHeight());

        double dx = c.getX() - closestX;
        double dy = c.getY() - closestY;

        return dx * dx + dy * dy <= c.getRadius() * c.getRadius();
    }

    public static boolean intersects(Rectangle rect, Triangle tri) 
    {

        // 1. Si el rectángulo intersecta el bounding-box del triángulo
        if (!intersectsBoundingBox(rect, tri)) return false;

        // 2. Si cualquier vértice del rect está dentro del triángulo
        if (pointInTriangleRect(rect.getX(), rect.getY(), tri)) return true;
        if (pointInTriangleRect(rect.getX() + rect.getWidth(), rect.getY(), tri)) return true;
        if (pointInTriangleRect(rect.getX(), rect.getY() + rect.getHeight(), tri)) return true;
        if (pointInTriangleRect(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight(), tri)) return true;

        // 3. Si cualquier vértice del triángulo cae dentro del rectángulo
        for (double[] p : triVertices(tri)) {
            if (pointInRect(p[0], p[1], rect)) return true;
        }

        // 4. Checar intersección entre los segmentos del triángulo y los del rectángulo
        return edgesIntersect(rect, tri);
    }


    public static boolean intersects(Circle c, Triangle t) 
    {
        double cx = c.getX();
        double cy = c.getY();
        double r  = c.getRadius();

        double tx = t.getX();
        double ty = t.getY();
        double w  = t.getWidth();
        double h  = t.getHeight();

        // 1) Si el bounding box no choca, no hay colisión
        if (!intersects(c.getBoundingBox(), t.getBoundingBox())) return false;

        // 2) Si el centro del círculo está dentro del triángulo → colisión
        if (pointInTriangleRect(cx, cy, t)) return true;

        // 3) Distancia del círculo a los lados rectos del triángulo
        // Lado horizontal inferior:   y = ty  con  tx <= x <= tx + w
        if (cy >= ty && cy <= ty && cx >= tx && cx <= tx + w) {
            if (Math.abs(cy - ty) <= r) return true;
        }

        // Lado vertical izquierdo:  x = tx  con  ty <= y <= ty + h
        if (cx >= tx && cx <= tx && cy >= ty && cy <= ty + h) {
            if (Math.abs(cx - tx) <= r) return true;
        }

        // 4) Checar distancia de los 3 vértices del triángulo al centro del círculo
        double[][] verts = triVertices(t);
        for (double[] p : verts) {
            if (dist2(cx, cy, p[0], p[1]) <= r*r) return true;
        }

        // 5) Checar distancia mínima del círculo a la hipotenusa
        if (distancePointToSegment(cx, cy,          // punto = centro del círculo
                                tx + w, ty,      // hipotenusa: (x+w, y)
                                tx,     ty + h)  //             (x,   y+h)
            <= r)
            return true;

        return false;
    }



    public static boolean intersects(Triangle a, Triangle b) 
    {

        // 1. Checar bounding boxes
        if (!intersectsBoundingBox(a, b)) return false;

        // 2. Un vértice dentro del otro triángulo
        for (double[] p : triVertices(a)) {
            if (pointInTriangleRect(p[0], p[1], b)) return true;
        }
        for (double[] p : triVertices(b)) {
            if (pointInTriangleRect(p[0], p[1], a)) return true;
        }

        // 3. Checar intersección entre lados
        for (double[] ea : triEdges(a)) {
            for (double[] eb : triEdges(b)) {
                if (segmentsIntersect(ea, eb)) return true;
            }
        }

        return false;
    }


    public static double dist2(double x1, double y1, double x2, double y2) 
    {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return dx * dx + dy * dy;
    }


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

        // Aristas del triángulo (usa tu helper triEdges)
        double[][] triangleEdges = triEdges(tri);

        // Compara cada par de aristas
        for (double[] re : rectEdges) {
            for (double[] te : triangleEdges) {
                if (segmentsIntersect(re, te)) return true;
            }
        }
        return false;
    }



     private static boolean pointInRect(double px, double py, Rectangle r) {
        return px >= r.getX() && px <= r.getX() + r.getWidth() &&
               py >= r.getY() && py <= r.getY() + r.getHeight();
    }

    private static boolean intersectsBoundingBox(Rectangle r, Triangle t) {
        return intersects(r, t.getBoundingBox());
    }

    private static boolean intersectsBoundingBox(Triangle a, Triangle b) {
        return intersects(a.getBoundingBox(), b.getBoundingBox());
    }

    // ----------------------------
    // TRIÁNGULO RECTO – AUXILIARES
    // ----------------------------

    private static boolean pointInTriangleRect(double px, double py, Triangle t) {

        double x = t.getX();
        double y = t.getY();
        double w = t.getWidth();
        double h = t.getHeight();

        // Dentro del bounding box
        if (px < x || px > x + w || py < y || py > y + h) return false;

        // Ecuación de la hipotenusa: va de (x,y+h) a (x+w,y)
        // Paramétrica: (x+h-t) ??? No.
        // Usamos la ecuación explícita:

        double slope = -h / w; // pendiente
        double intercept = y + h; // intersección Y

        // Línea: Y = slope*(X - x) + intercept

        double yLine = slope * (px - x) + intercept;

        return py <= yLine;
    }

    private static double[][] triVertices(Triangle t) {
        return new double[][] {
            { t.getX(), t.getY() },                   // inferior izquierdo
            { t.getX() + t.getWidth(), t.getY() },    // inferior derecho
            { t.getX(), t.getY() + t.getHeight() }    // vértice superior
        };
    }

    private static double[][] triEdges(Triangle t) 
    {
        double[][] v = triVertices(t);
        return new double[][] {
            { v[0][0], v[0][1], v[1][0], v[1][1] }, // base
            { v[0][0], v[0][1], v[2][0], v[2][1] }, // lado vertical
            { v[1][0], v[1][1], v[2][0], v[2][1] }  // hipotenusa
        };
    }

    private static boolean segmentsIntersect(double[] s1, double[] s2) {
        double x1 = s1[0], y1 = s1[1];
        double x2 = s1[2], y2 = s1[3];
        double x3 = s2[0], y3 = s2[1];
        double x4 = s2[2], y4 = s2[3];

        double d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
        if (d == 0) return false;

        double t = ((x1-x3)*(y3-y4) - (y1-y3)*(x3-x4)) / d;
        double u = ((x1-x3)*(y1-y2) - (y1-y3)*(x1-x2)) / d;

        return t >= 0 && t <= 1 && u >= 0 && u <= 1;
    }
    
}
