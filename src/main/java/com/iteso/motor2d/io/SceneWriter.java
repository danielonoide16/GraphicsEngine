package com.iteso.motor2d.io;

// Importar la clase SHAPE2D
import com.iteso.motor2d.model.shapes.*;

// Importar dependencias específicas 
import java.util.List;
import java.awt.Color;
import java.io.FileOutputStream;

// Dependencias para crear un json
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonArray;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;


public class SceneWriter {
    private JsonObjectBuilder root;
    
    public SceneWriter(List<Shape2D> figuras_lista){
        root = Json.createObjectBuilder();
        writeJSON(figuras_lista);
    }

    // Iterar y crear el json
    private void writeJSON(List<Shape2D> figuras){

        /*
        ----- Funcion -----
        Llenar el JSonObjectBuilder para generar el archivo JSON
        a partir de una lista de figuras
        Si la lista esta vacía no se ejecuta el codigo
        Sino, entonces agregaran todas las figuras como:

            "idFigura" : {
                        "Clase": clase,
                        "PosicionX": x,
                        "PosicionY": y,
                        "Colores": [RED, BLUE, GREEN],
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

            JsonArrayBuilder colores = Json.createArrayBuilder(); // "Color": [RED, BLUE, GREEN]
            Color color = figura.getColor();
            colores.add(color.getRed());
            colores.add(color.getGreen());
            colores.add(color.getBlue());
            JsonArray arreglo_colores = colores.build();
            gnrlFeatures.add("Colores", arreglo_colores);

            // * Caracteristicas especificas de cada figura
            if(figura instanceof Triangle){ // Triangulo
                Triangle triangulo = (Triangle)figura;
                uniqueFeatures.add("Ancho", triangulo.getWidth());
                uniqueFeatures.add("Altura", triangulo.getHeight());
            }
            else if(figura instanceof Circle){ // Circulo
                Circle circulo = (Circle)figura;
                gnrlFeatures.add("Radio", circulo.getRadius());

            }
            else if(figura instanceof Rectangle){ // Rectangulo
                Rectangle rectangulo = (Rectangle)figura;
                gnrlFeatures.add("Ancho", rectangulo.getWidth());
                gnrlFeatures.add("Altura", rectangulo.getHeight());

            }

            // * Agregar caracteristicas unicas en generales, y agregar las generales en el root
            gnrlFeatures.add("UniqueFeatures",uniqueFeatures.build());
            root.add(Integer.toString(figura.getId()), gnrlFeatures.build());

        }
    }

    public void generateJSON(){
        try{ // Escribir el archivo
            JsonWriter archivo = Json.createWriter(new FileOutputStream("figuras.json"));
            archivo.writeObject(root.build());
            archivo.close();
            
        }
        catch(Exception e){ // Error
            e.printStackTrace();
        }
    }
}
