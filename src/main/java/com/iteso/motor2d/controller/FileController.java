package com.iteso.motor2d.controller;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;

import com.iteso.motor2d.io.SceneReader;
import com.iteso.motor2d.model.shapes.Shape2D;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.io.FileNotFoundException;

/**
 * Controlador para manejar la lectura de archivos JSON y la generación de figuras
 */
public class FileController {

    private String path; // Ruta del archivo JSON

    public FileController(String path) {
        this.path = path;
    }

    // Lee el JSON como un array
    private JsonArray readJson() {
        try {
            InputStream file = new FileInputStream(this.path);
            JsonReader reader = Json.createReader(file);

            JsonArray jsonArray = reader.readArray();  // 🔥 Leemos un ARREGLO

            reader.close();
            return jsonArray;

        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo: " + this.path);
        }

        return Json.createArrayBuilder().build(); // devuelve array vacío si falla
    }

    // Genera una lista de Shape2D a partir del JSON
    public List<Shape2D> generateFigures() {
        JsonArray jsonArray = readJson();
        SceneReader lector = new SceneReader(jsonArray);
        return lector.createFigures();
    }
}
