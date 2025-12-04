package com.iteso.motor2d.view;

import javax.swing.*;

import java.awt.*;

import java.util.List;

/**
 * Panel de la barra de herramientas
 */
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

    private JButton btnCloneShape;

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

        btnCloneShape = new JButton("Clone Shape");

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

        add(btnCloneShape);
    }
    
    //Events

    //Se usa Runnable para no usar ActionListener directamente en el controlador
    //y mantener la separación entre vista y controlador
    //Runnable es una interfaz funcional que permite pasar lambdas simples
    public void onAddRectangle(Runnable r)
    {
        btnAddRectangle.addActionListener(e -> r.run());
    }

    public void onAddCircle(Runnable r)
    {
        btnAddCircle.addActionListener(e -> r.run());
    }

    public void onAddTriangle(Runnable r)
    {
        btnAddTriangle.addActionListener(e -> r.run());
    }

    public void onRemoveShape(Runnable r)
    {
        btnRemove.addActionListener(e -> r.run());
    }

    public void onMoveUp(Runnable r)
    {
        btnMoveUp.addActionListener(e -> r.run());
    }

    public void onMoveDown(Runnable r)
    {
        btnMoveDown.addActionListener(e -> r.run());
    }

    public void onMoveLeft(Runnable r)
    {
        btnMoveLeft.addActionListener(e -> r.run());
    }

    public void onMoveRight(Runnable r)
    {
        btnMoveRight.addActionListener(e -> r.run());
    }

    public void onApplySize(Runnable r)
    {
        btnApplySize.addActionListener(e -> r.run());
    }

    public void onGenerateArchive(Runnable r)
    {
        btnGenerateArch.addActionListener(e -> r.run());
    }

    public void onReadArchive(Runnable r)
    {
        btnReadArchive.addActionListener(e -> r.run());
    }

    public void onSelectShape(Runnable r)
    {
        shapeSelector.addActionListener(e -> r.run());
    }   

    public void onCloneShape(Runnable r)
    {
        btnCloneShape.addActionListener(e -> r.run());
    }


    //Getters
    public String getTxtWidth() { return txtWidth.getText(); }
    public String getTxtHeight() { return txtHeight.getText(); }
    public int getSelectedShapeIndex() { return shapeSelector.getSelectedIndex(); }
    public JFileChooser getFileChooser() 
    {
        return fileChooser;
    }


    //Setters
    public void setTxtWidth(String text) { txtWidth.setText(text); }
    public void setTxtHeight(String text) { txtHeight.setText(text); }

    // ---------- ACTUALIZACIÓN SILENCIOSA DEL DROPDOWN ----------
    public void updateShapeList(List<String> names, int selectedIndex) 
    {
        // Guardar y remover listeners para evitar dispararlos durante la actualización
        var listeners = shapeSelector.getActionListeners();
        for (var l : listeners) shapeSelector.removeActionListener(l);

        shapeSelector.removeAllItems();
        for (String s : names) shapeSelector.addItem(s);

        if (selectedIndex >= 0 && selectedIndex < names.size())
            shapeSelector.setSelectedIndex(selectedIndex);

        for (var l : listeners) shapeSelector.addActionListener(l);
    }

}
