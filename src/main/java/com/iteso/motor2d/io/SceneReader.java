package com.iteso.motor2d.io;

// Dependencias para manipular jsons
import javax.json.JsonObject;
import javax.json.JsonValue;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

import com.iteso.motor2d.model.shapes.*;

public class SceneReader{
    private JsonObject jsonShape;
    private List<Shape2D> figuras;

    public SceneReader(JsonObject json){
        this.jsonShape = json;
        this.figuras = new ArrayList<>();
    }

    // Dibujar las figuras
    public List<Shape2D> createFigures(){
        for(String key: jsonShape.keySet()){
            // Extrayendo los datos y casteandolos a valores primitivos
            JsonValue metadata = jsonShape.get(key);
            JsonObject objetos = (JsonObject)metadata;  
            String clase = objetos.getString("Clase");
            int posX = objetos.getInt("PosicionX");
            int posY = objetos.getInt("PosicionY");

            int rgb = objetos.getInt("Colores");
            Color color = new Color(rgb);

            JsonObject features = objetos.getJsonObject("UniqueFeatures");
            
            // dibujar las figuras
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
        return figuras;
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
