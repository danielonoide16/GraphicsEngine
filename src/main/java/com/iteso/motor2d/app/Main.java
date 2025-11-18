package com.iteso.motor2d.app;

import com.iteso.motor2d.controller.SceneController;
import com.iteso.motor2d.model.scene.Scene;
import com.iteso.motor2d.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        // Create the main window (Swing UI)
        MainWindow window = new MainWindow();
        Scene scene = new Scene();

        // Create the scene controller and pass the UI reference
        SceneController controller = new SceneController(scene, window);

        // Tell the window who the controller is (for button actions, etc.)
        window.setController(controller);
        
    }
}