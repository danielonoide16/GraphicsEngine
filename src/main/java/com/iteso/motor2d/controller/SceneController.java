package com.iteso.motor2d.controller;

import java.awt.Color;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.iteso.motor2d.io.SceneWriter;
import com.iteso.motor2d.model.exceptions.InvalidFileException;
import com.iteso.motor2d.model.scene.Scene;
import com.iteso.motor2d.model.shapes.Shape2D.ShapeType;
import com.iteso.motor2d.view.MainWindow;
import com.iteso.motor2d.view.ToolbarPanel;

public class SceneController 
{

    private Scene scene; // el modelo
    private MainWindow window; // la vista

    public SceneController(Scene scene, MainWindow window) 
    {
        this.scene = scene;
        this.window = window;
    }

    public void connectToolbar(ToolbarPanel tb) 
    {
        tb.getBtnAddRectangle().addActionListener(e -> {
            if(createShapeWithDialog(ShapeType.RECTANGLE))
                updateUI();
            else
                JOptionPane.showMessageDialog(window, "No se pudo crear el rectángulo", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        });

        tb.getBtnAddCircle().addActionListener(e -> {
            if(createShapeWithDialog(ShapeType.CIRCLE))
                updateUI();
            else
                JOptionPane.showMessageDialog(window, "No se pudo crear el círculo", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        });

        tb.getBtnAddTriangle().addActionListener(e -> {
            if(createShapeWithDialog(ShapeType.TRIANGLE))
                updateUI();
            else
                JOptionPane.showMessageDialog(window, "No se pudo crear el triángulo", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        });

        // Generador del json
        tb.getBtnGenerateArchive().addActionListener(e->{
            JFileChooser menu = window.getToolbarPanel().getFileChooser();
            int estado = menu.showSaveDialog(window);
            if (estado == JFileChooser.APPROVE_OPTION){
                File ruta = menu.getSelectedFile();
                SceneWriter escritor = new SceneWriter(scene.getShapes(), ruta.getAbsolutePath()+".json");
                escritor.generateJSON();    
            }
            
        });

        // Lector de jsons
        tb.getBtnReadArchive().addActionListener(e->{
            JFileChooser menu = window.getToolbarPanel().getFileChooser();
            int estado = menu.showOpenDialog(window);
            if (estado == JFileChooser.APPROVE_OPTION){
                File archivo = menu.getSelectedFile();
                if(!archivo.getName().toLowerCase().endsWith(".json")){
                    throw new InvalidFileException(window, "Selecciona un arhivo .json");

                }
                else{
                    FileController lector = new FileController(archivo.getAbsolutePath());
                    scene.setShape2ds(lector.generateFigures());
                    updateUI();
                }
            }
        });


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
                int w = Integer.parseInt(tb.getTxtWidth().getText());

                String heightText = tb.getTxtHeight().getText();
                
                if(heightText.isEmpty()) {
                    tb.getTxtHeight().setText("" + w);
                }

                int h = Integer.parseInt(tb.getTxtHeight().getText());
                resizeSelected(w, h);
            } catch (Exception ex) {
                // mensaje lo maneja UI, aquí nada
            }
        });
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
        //actualizar el field que muestra la posicion
        window.setPosField(scene.getPosString());
        

        //actualiza lista de nombres de figuras
       window.getToolbarPanel().updateShapeList(
            scene.getShapeNames(),
            scene.getSelectedIndex()
        );

        // redibuja el canvas
        window.getCanvasPanel().setShapes(scene.getShapes());

        // mostrar colisiones
        window.displayCollisions(scene.getCollisionReport());
    }



    private boolean createShapeWithDialog(ShapeType type) 
    {
        // panel dinámico según la figura
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Posición
        JTextField txtX = new JTextField(5);
        JTextField txtY = new JTextField(5);

        panel.add(new JLabel("Posición X:"));
        panel.add(txtX);
        panel.add(new JLabel("Posición Y:"));
        panel.add(txtY);

        // Campos que cambian según la figura
        JTextField txtA = new JTextField(5);
        JTextField txtB = new JTextField(5);

        switch(type) {
            case ShapeType.RECTANGLE:
                panel.add(new JLabel("Ancho:"));
                panel.add(txtA);
                panel.add(new JLabel("Alto:"));
                panel.add(txtB);
                break;

            case ShapeType.CIRCLE:
                panel.add(new JLabel("Radio:"));
                panel.add(txtA);
                break;

            case ShapeType.TRIANGLE:
                panel.add(new JLabel("Base:"));
                panel.add(txtA);
                panel.add(new JLabel("Altura:"));
                panel.add(txtB);
                break;
        }

        // Mostrar el panel
        int result = JOptionPane.showConfirmDialog(window, panel, 
            "Crear " + type, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION)
            return false;

        try {
            int x = Integer.parseInt(txtX.getText());
            int y = Integer.parseInt(txtY.getText());

            // Color
            Color color = JColorChooser.showDialog(window, "Elige color", Color.BLUE);
            if (color == null) return false;
            switch(type) {

                case ShapeType.RECTANGLE: {
                    int w = Integer.parseInt(txtA.getText());
                    int h = Integer.parseInt(txtB.getText());
                    scene.addRectangle(x, y, w, h, color);
                    return true;
                }

                case ShapeType.CIRCLE: {
                    int r = Integer.parseInt(txtA.getText());
                    scene.addCircle(x, y, r, color);
                    return true;
                }

                case ShapeType.TRIANGLE: {
                    int base = Integer.parseInt(txtA.getText());
                    int height = Integer.parseInt(txtB.getText());
                    scene.addTriangle(x, y, base, height, color);
                    return true;
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(window, "Datos inválidos", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return false;
    }


}
