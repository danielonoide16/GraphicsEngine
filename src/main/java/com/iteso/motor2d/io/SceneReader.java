package com.iteso.motor2d.io;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

import com.iteso.motor2d.model.shapes.*;
import com.iteso.motor2d.util.IdGenerator;

public class SceneReader {
    private final JsonArray jsonArray;
    private final List<Shape2D> figuras;

    public SceneReader(JsonArray jsonArray) {
        this.jsonArray = jsonArray;
        this.figuras = new ArrayList<>();
    }

    public List<Shape2D> createFigures() {
        for (JsonValue v : jsonArray) {
            if (v.getValueType() != JsonValue.ValueType.OBJECT) continue;
            JsonObject obj = v.asJsonObject();

            String clase = obj.getString("Clase");
            int posX = obj.getInt("PosicionX");
            int posY = obj.getInt("PosicionY");

            int rgb = obj.getInt("Colores");
            Color color = new Color(rgb, true);

            JsonObject features = obj.getJsonObject("UniqueFeatures");

            switch (clase) {
                case "Circle": {
                    int radius = features.getInt("Radio");
                    figuras.add(createCircle(posX, posY, radius, color));
                    break;
                }
                case "Triangle": {
                    int ancho = features.getInt("Ancho");
                    int alto = features.getInt("Altura");
                    figuras.add(createTriangle(posX, posY, ancho, alto, color));
                    break;
                }
                case "Rectangle": {
                    int width = features.getInt("Ancho");
                    int height = features.getInt("Altura");
                    figuras.add(createRectangle(posX, posY, width, height, color));
                    break;
                }
                default:
                    System.out.println("Figura desconocida: " + clase);
            }
        }
        return figuras;
    }

    private Circle createCircle(int posX, int posY, int radius, Color color){
        return new Circle(posX, posY, radius, color, IdGenerator.getInstance().generateId());
    }

    private Triangle createTriangle(int posX, int posY, int width, int height, Color color){
        return new Triangle(posX, posY, width, height, color, IdGenerator.getInstance().generateId());
    }

    private Rectangle createRectangle(int posX, int posY, int width, int height, Color color){
        return new Rectangle(posX, posY, width, height, color, IdGenerator.getInstance().generateId());
    }
}
