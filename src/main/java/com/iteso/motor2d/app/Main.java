package com.iteso.motor2d.app;

import com.iteso.motor2d.controller.SceneController;
import com.iteso.motor2d.model.scene.Scene;
import com.iteso.motor2d.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        Scene scene = new Scene();

        SceneController controller = new SceneController(scene, window);

        window.setController(controller);

    }
}