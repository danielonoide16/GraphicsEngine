package com.iteso.motor2d.controller;

// Dependencias para mainpular JSON
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

// Dependencias internas
import com.iteso.motor2d.io.SceneReader;
import com.iteso.motor2d.model.shapes.Shape2D;

// Dependencias de la API especificas
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.io.FileNotFoundException;

public class FileController{
    private String path; // Ruta para leer el documento
    private JsonObject jsonShape;

    public FileController(String path){
        this.path = path;
        generateFigures();
    }

    // Funcion que lee el json apartir del path
    private void readJson(){
        try{
            InputStream file = new FileInputStream(this.path);
            JsonReader reader = Json.createReader(file);
            jsonShape = reader.readObject();
            reader.close();

        } catch(FileNotFoundException e){
            System.out.println("No se encuentra el error");
        };
    }

    // Funcion que genera una lista de shapes2D apartir del json leido
    public List<Shape2D> generateFigures(){
        readJson();
        SceneReader lector = new SceneReader(jsonShape);
        return lector.createFigures();
    }

}
