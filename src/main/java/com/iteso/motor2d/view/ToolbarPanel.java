package com.iteso.motor2d.view;

import javax.swing.*;

import java.awt.*;

public class ToolbarPanel extends JPanel 
{

    private JButton btnAddRectangle;
    private JButton btnAddCircle;
    private JButton btnAddTriangle;

    private JComboBox<String> shapeSelector;
    private JButton btnRemove;
    private JFileChooser fileChooser;

    private JButton btnMoveUp;
    private JButton btnMoveDown;
    private JButton btnMoveLeft;
    private JButton btnMoveRight;

    private JTextField txtWidth;
    private JTextField txtHeight;

    private JButton btnApplySize;
    private JButton btnGenerateArch;
    private JButton btnReadArchive;

    public ToolbarPanel() 
    {
        //setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setLayout(new WrapLayout(FlowLayout.LEFT, 0, 0));

        btnAddRectangle = new JButton("Add Rectangle");
        btnAddCircle = new JButton("Add Circle");
        btnAddTriangle = new JButton("Add Triangle");

        shapeSelector = new JComboBox<>();
        shapeSelector.setPreferredSize(new Dimension(150, 25));

        btnRemove = new JButton("Remove");

        btnMoveUp = new JButton("↑");
        btnMoveDown = new JButton("↓");
        btnMoveLeft = new JButton("←");
        btnMoveRight = new JButton("→");

        txtWidth = new JTextField(5);
        txtHeight = new JTextField(5);
        btnApplySize = new JButton("Apply Size");
        btnGenerateArch = new JButton("Generate Archive");
        btnReadArchive = new JButton("Read Archive");

        fileChooser = new JFileChooser();

        add(new JLabel("Add:"));
        add(btnAddRectangle);
        add(btnAddCircle);
        add(btnAddTriangle);

        add(new JLabel("   Select Shape:"));
        add(shapeSelector);

        add(btnRemove);

        add(new JLabel("   Move:"));
        add(btnMoveUp);
        add(btnMoveDown);
        add(btnMoveLeft);
        add(btnMoveRight);

        add(new JLabel("   Size W/H:"));
        add(txtWidth);
        add(txtHeight);
        add(btnApplySize);

        add(new JLabel("Manipulate Archives"));
        add(btnGenerateArch);
        add(btnReadArchive);

        //resize the panel to fit the window of the computer
        this.setPreferredSize(new Dimension(800, 30));


        
    }

    // ---------- Getters ----------
    public JButton getBtnAddRectangle() { return btnAddRectangle; }
    public JButton getBtnAddCircle() { return btnAddCircle; }
    public JButton getBtnAddTriangle() { return btnAddTriangle; }

    public JComboBox<String> getShapeSelector() { return shapeSelector; }
    public JFileChooser getFileChooser(){ return fileChooser;}

    public JButton getBtnRemove() { return btnRemove; }
    public JButton getBtnMoveUp() { return btnMoveUp; }
    public JButton getBtnMoveDown() { return btnMoveDown; }
    public JButton getBtnMoveLeft() { return btnMoveLeft; }
    public JButton getBtnMoveRight() { return btnMoveRight; }

    public JTextField getTxtWidth() { return txtWidth; }
    public JTextField getTxtHeight() { return txtHeight; }
    public JButton getBtnApplySize() { return btnApplySize; }
    public JButton getBtnGenerateArchive(){return btnGenerateArch;}
    public JButton getBtnReadArchive(){return btnReadArchive;}


    // ---------- ACTUALIZACIÓN SILENCIOSA DEL DROPDOWN ----------
    public void updateShapeList(java.util.List<String> names, int selectedIndex) 
    {
        // Repoblar sin disparar eventos
        shapeSelector.removeAllItems();
        for (String s : names) 
        {
            shapeSelector.addItem(s);
        }

        // Restaurar selección
        if (selectedIndex >= 0 && selectedIndex < names.size())
        {
            shapeSelector.setSelectedIndex(selectedIndex);
        }
    }
}
