package com.iteso.motor2d.io;

// Dependencias para manipular jsons
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

// Dependencias extra
import java.util.List;
import java.awt.Color;

// Dependencias internas
import com.iteso.motor2d.model.shapes.*;
import com.iteso.motor2d.view.MainWindow;

public class SceneReader{
    private JsonObject jsonShape;
    private List<Shape2D> figuras;
    private MainWindow window;

    // Constructor
    public SceneReader(MainWindow window, JsonObject json){
        this.window = window;
        this.jsonShape = json;
    }

    // Dibujar las figuras
    public void createFigures(){
        for(String key: jsonShape.keySet()){
            JsonValue metadata = jsonShape.get(key);
            JsonObject objetos = (JsonObject)metadata;  
            String clase = objetos.getString("Clase");
            int posX = objetos.getInt("PosicionX");
            int posY = objetos.getInt("PosicionY");

            JsonArray colores = objetos.getJsonArray("Colores");
            int red = colores.getInt(0);
            int blue = colores.getInt(1);
            int green = colores.getInt(2);
            Color color = new Color(red, green, blue);

            JsonObject features = objetos.getJsonObject("UniqueFeatures");
            
            // * Dibujar las figuras
            switch (clase) {
                case "Circle":
                    int radius = features.getInt("Radio");
                    figuras.add(createCircle(posX, posY, radius, Integer.parseInt(key), color));
                    break;
                case "Triangle":
                    int ancho = features.getInt("Ancho");
                    int alto = features.getInt("Altura");
                    figuras.add(createTriangle(posX, posY, ancho, alto, Integer.parseInt(key), color));
                    break;
                case "Rectangle":
                    int width = features.getInt("Ancho");
                    int height = features.getInt("Altura");
                    figuras.add(createRectangle(posX, posY, width, height, Integer.parseInt(key), color));
                    break;
                default:
                    System.out.println("Error al imprimir");
                    System.out.println(clase.toString());
                    break;
            }
        }
        window.getCanvasPanel().setShapes(figuras);
    }
    
    private Circle createCircle(int posX, int posY, int radius, int id, Color color){
        Circle circulo = new Circle(posX, posY ,radius , color, id);
        return circulo;
    }

    private Triangle createTriangle(int posX, int posY, int width, int height, int id, Color color){
        Triangle triangulo = new Triangle(posX, posY, width, height, color, id);
        return triangulo;
    } 

    private Rectangle createRectangle(int posX, int posY, int width, int height, int id, Color color){
        Rectangle rectangulo = new Rectangle(posX, posY, width, height, color, id);
        return rectangulo;
    }
}
