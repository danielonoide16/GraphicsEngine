package com.iteso.motor2d.io;

import com.iteso.motor2d.model.shapes.*;

import java.util.List;
import java.awt.Color;
import java.io.FileOutputStream;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;


public class SceneWriter {
    private JsonObjectBuilder root;
    private String absPath;
    
    public SceneWriter(List<Shape2D> figuras_lista, String path){
        root = Json.createObjectBuilder();
        this.absPath = path;
        writeJSON(figuras_lista);
        
    }

    // Iterar y crear el json
    private void writeJSON(List<Shape2D> figuras){

        /*
        ----- Funcion -----
        Llenar el JSonObjectBuilder para generar el archivo JSON
        a partir de una lista de figuras
        Si la lista esta vacía no se ejecuta el codigo
        Sino, entonces se agregaran todas las figuras como:

            "idFigura" : {
                        "Clase": clase,
                        "PosicionX": x,
                        "PosicionY": y,
                        "Colores": [RED, BLUE, GREEN, ALPHA],
                        "UniqueFeatures": {Caracteristicas especificas de cada figura}
            }
        */
        
        if(figuras.isEmpty()){ // Lista vacia
            return;
        } 

        for(Shape2D figura: figuras){
            JsonObjectBuilder gnrlFeatures = Json.createObjectBuilder(); // Caracteristicas generales
            JsonObjectBuilder uniqueFeatures = Json.createObjectBuilder(); // Caracteristicas unicas
            
            // * Caracteristicas generales
            String clase = figura.getClass().getSimpleName();
            gnrlFeatures.add("Clase", clase);
            gnrlFeatures.add("PosicionX", figura.getX());
            gnrlFeatures.add("PosicionY", figura.getY());

            Color color = figura.getColor();
            int rgb = color.getRGB();
            gnrlFeatures.add("Colores", rgb);

            // * Caracteristicas especificas de cada figura
            if(figura instanceof Triangle){ // Triangulo
                Triangle triangulo = (Triangle)figura;
                uniqueFeatures.add("Ancho", triangulo.getWidth());
                uniqueFeatures.add("Altura", triangulo.getHeight());
            }
            else if(figura instanceof Circle){ // Circulo
                Circle circulo = (Circle)figura;
                uniqueFeatures.add("Radio", circulo.getRadius());

            }
            else if(figura instanceof Rectangle){ // Rectangulo
                Rectangle rectangulo = (Rectangle)figura;
                uniqueFeatures.add("Ancho", rectangulo.getWidth());
                uniqueFeatures.add("Altura", rectangulo.getHeight());

            }
            else{
                System.out.println("Error agregando unique features");
            }

            // * Agregar caracteristicas unicas en generales, y agregar las generales en el root
            gnrlFeatures.add("UniqueFeatures",uniqueFeatures.build());
            root.add(Integer.toString(figura.getId()), gnrlFeatures.build());

        }
    }

    public void generateJSON(){
        try{ // Escribir el archivo
            JsonWriter archivo = Json.createWriter(new FileOutputStream(this.absPath));
            archivo.writeObject(root.build());
            archivo.close();
            
        }
        catch(Exception e){ // Error
            System.out.println("Error al generar el json");
            root = Json.createObjectBuilder();
        }
    }
}
