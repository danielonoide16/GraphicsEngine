package com.iteso.motor2d.controller;

import com.iteso.motor2d.model.scene.Scene;
import com.iteso.motor2d.view.MainWindow;
import com.iteso.motor2d.view.ToolbarPanel;

public class SceneController 
{

    private Scene scene;
    private MainWindow window;

    public SceneController(Scene scene, MainWindow window) 
    {
        this.scene = scene;
        this.window = window;
    }

    public void connectToolbar(ToolbarPanel tb) 
    {
        // Agregar figuras
        tb.getBtnAddRectangle().addActionListener(e -> addRectangle());
        tb.getBtnAddCircle().addActionListener(e -> addCircle());
        tb.getBtnAddTriangle().addActionListener(e -> addTriangle());

        // Selección
        tb.getShapeSelector().addActionListener(e -> {
            int i = tb.getShapeSelector().getSelectedIndex();
            selectShape(i);
        });

        //Remover figuras
        tb.getBtnRemove().addActionListener(e -> 
        {
            scene.removeShape(scene.getSelectedShape());

            int newIndex = tb.getShapeSelector().getSelectedIndex() - 1;

            if(newIndex < 0 && scene.getShapes().size() > 0) 
                {
                newIndex = 0; // select first if we removed the first one
            }

            scene.selectShape(newIndex);
            updateUI();

        });

        final int STEP = 20;
        // Movimientos
        tb.getBtnMoveUp().addActionListener(e -> moveSelected(0, -STEP));
        tb.getBtnMoveDown().addActionListener(e -> moveSelected(0, STEP));
        tb.getBtnMoveLeft().addActionListener(e -> moveSelected(-STEP, 0));
        tb.getBtnMoveRight().addActionListener(e -> moveSelected(STEP, 0));

        // Redimensionar
        tb.getBtnApplySize().addActionListener(e -> {
            try {
                double w = Double.parseDouble(tb.getTxtWidth().getText());
                double h = Double.parseDouble(tb.getTxtHeight().getText());
                resizeSelected(w, h);
            } catch (Exception ex) {
                // mensaje lo maneja UI, aquí nada
            }
        });
    }

    // Metodos de control

    public void addRectangle() 
    {
        System.out.println("Adding Rectangle");
        scene.addRectangle();
        updateUI();
    }

    public void addCircle() 
    {
        System.out.println("Adding Circle");
        scene.addCircle();
        updateUI();
    }

    public void addTriangle() 
    {
        System.out.println("Adding Triangle");
        scene.addTriangle();
        updateUI();
    }

    public void selectShape(int index) 
    {
        System.out.println("Shape selected, index: " + index);
        scene.selectShape(index);
        updateUI();
    }

    public void moveSelected(int dx, int dy) 
    {
        System.out.println("Moving shape: " + scene.getSelectedShape());
        scene.moveSelected(dx, dy);
        updateUI();
    }

    public void resizeSelected(double w, double h) 
    {
        scene.resizeSelected(w, h);
        updateUI();
    }

    private void updateUI() 
    {
        // 1) Actualiza lista de nombres de figuras
       window.getToolbarPanel().updateShapeList(
            scene.getShapeNames(),
            scene.getSelectedIndex()
        );

        // 2) Redibuja el canvas
        window.getCanvasPanel().setShapes(scene.getShapes());

        // 3) Muestra colisiones
        window.displayCollisions(scene.getCollisionReport());
    }
}
