package com.iteso.motor2d.view;

import javax.swing.*;
import java.awt.*;
import com.iteso.motor2d.controller.SceneController;

public class MainWindow extends JFrame 
{
    private ToolbarPanel toolbarPanel;
    private CanvasPanel canvasPanel;
    private JTextArea collisionArea;
    private JTextField posField;

    public MainWindow() 
    {
        setTitle("Game engine");
        setSize(1980, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        posField = new JTextField("");
        posField.setEditable(false);
        posField.setBorder(null);
        posField.setBackground(new Color(240,240,240));
        posField.setVisible(false); // oculto al inicio

        toolbarPanel = new ToolbarPanel();

        // contenedor con toolbar y label de posición
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(toolbarPanel, BorderLayout.NORTH);
        topContainer.add(posField, BorderLayout.SOUTH);

        canvasPanel = new CanvasPanel();

        collisionArea = new JTextArea(5, 30);
        collisionArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(collisionArea);

        add(topContainer, BorderLayout.NORTH);
        add(canvasPanel, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);

        setVisible(true);

        // actualizar el layout del toolbar al inicio para que acomode bien los botones
        SwingUtilities.invokeLater(() -> {
            toolbarPanel.revalidate(); // actualizar el layout 
            toolbarPanel.repaint(); // repintar el panel
        });
    }

    public void setPosField(String text)
    {
        if (text == null || text.isEmpty()) 
        {
            posField.setVisible(false);
        } 
        else
        {
            posField.setText(text);
            posField.setVisible(true);
        }
    }

    public CanvasPanel getCanvasPanel() 
    { 
        return canvasPanel; 
    }

    public ToolbarPanel getToolbarPanel() 
    { 
        return toolbarPanel; 
    }

    public void displayCollisions(String text) 
    {
        collisionArea.setText(text);
    }

    public void setController(SceneController controller) 
    {
        controller.connectToolbar(toolbarPanel);
    }
}
