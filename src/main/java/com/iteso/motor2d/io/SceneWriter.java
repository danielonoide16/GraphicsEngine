package com.iteso.motor2d.io;

import com.iteso.motor2d.model.shapes.*;

import java.util.List;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

public class SceneWriter {
    private final List<Shape2D> figuras;
    private final String absPath;

    public SceneWriter(List<Shape2D> figuras_lista, String path){
        this.figuras = figuras_lista;
        this.absPath = path;
    }

    public void generateJSON(){
        // Creamos un arreglo JSON con cada figura como objeto (sin id)
        JsonArrayBuilder array = Json.createArrayBuilder();

        for (Shape2D figura: figuras) {
            JsonObjectBuilder gnrlFeatures = Json.createObjectBuilder();
            JsonObjectBuilder uniqueFeatures = Json.createObjectBuilder();

            String clase = figura.getClass().getSimpleName();
            gnrlFeatures.add("Clase", clase);
            gnrlFeatures.add("PosicionX", figura.getX());
            gnrlFeatures.add("PosicionY", figura.getY());

            // Guardamos RGB entero (compatible con new Color(rgb))
            int rgb = figura.getColor().getRGB();
            gnrlFeatures.add("Colores", rgb);

            if (figura instanceof Triangle) {
                Triangle t = (Triangle) figura;
                uniqueFeatures.add("Ancho", t.getWidth());
                uniqueFeatures.add("Altura", t.getHeight());
            } else if (figura instanceof Circle) {
                Circle c = (Circle) figura;
                uniqueFeatures.add("Radio", c.getRadius());
            } else if (figura instanceof Rectangle) {
                Rectangle r = (Rectangle) figura;
                uniqueFeatures.add("Ancho", r.getWidth());
                uniqueFeatures.add("Altura", r.getHeight());
            }

            gnrlFeatures.add("UniqueFeatures", uniqueFeatures.build());
            array.add(gnrlFeatures.build());
        }

        // Escribir en disco (try-with-resources)
        try (OutputStream os = new FileOutputStream(this.absPath);
             JsonWriter writer = Json.createWriter(os)) {
            writer.writeArray(array.build());
        } catch (Exception e) {
            System.out.println("Error al generar el json: " + e.getMessage());
        }
    }
}
