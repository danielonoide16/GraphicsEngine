package com.iteso.motor2d.view;

import javax.swing.*;
import java.awt.*;
import com.iteso.motor2d.controller.SceneController;

public class MainWindow extends JFrame {

    private ToolbarPanel toolbarPanel;
    private CanvasPanel canvasPanel;
    private JTextArea collisionArea;

    public MainWindow() {
        setTitle("Physics Engine - Collision Demo");
        setSize(1980, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        toolbarPanel = new ToolbarPanel();
        canvasPanel = new CanvasPanel();

        collisionArea = new JTextArea(5, 30);
        collisionArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(collisionArea);

        add(toolbarPanel, BorderLayout.NORTH);
        add(canvasPanel, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);

        setVisible(true);
    }

    public CanvasPanel getCanvasPanel() { return canvasPanel; }
    public ToolbarPanel getToolbarPanel() { return toolbarPanel; }

    public void displayCollisions(String text) {
        collisionArea.setText(text);
    }

    public void setController(SceneController controller) {
        controller.connectToolbar(toolbarPanel);
    }
}
