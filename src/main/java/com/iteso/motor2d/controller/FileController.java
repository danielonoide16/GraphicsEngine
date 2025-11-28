package com.iteso.motor2d.controller;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.iteso.motor2d.io.SceneReader;
import com.iteso.motor2d.view.MainWindow;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;

public class FileController{
    private String path;
    private JsonObject jsonShape;
    private MainWindow window;

    public FileController(String path, MainWindow window){
        this.path = path;
        this.window = window;
        createFigures();
    }

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
    public void createFigures(){
        readJson();
        SceneReader lector = new SceneReader(window, jsonShape);
        lector.createFigures();
    }

}
