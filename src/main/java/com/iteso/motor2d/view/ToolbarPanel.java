package com.iteso.motor2d.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ToolbarPanel extends JPanel {

    private JButton btnAddRectangle;
    private JButton btnAddCircle;
    private JButton btnAddTriangle;

    private JComboBox<String> shapeSelector;

    private JButton btnMoveUp;
    private JButton btnMoveDown;
    private JButton btnMoveLeft;
    private JButton btnMoveRight;

    private JTextField txtWidth;
    private JTextField txtHeight;

    private JButton btnApplySize;

    // Guardamos el listener para poder desactivarlo temporalmente
    private ActionListener comboListener;

    public ToolbarPanel() {

        setLayout(new FlowLayout(FlowLayout.LEFT));

        btnAddRectangle = new JButton("Add Rectangle");
        btnAddCircle = new JButton("Add Circle");
        btnAddTriangle = new JButton("Add Triangle");

        shapeSelector = new JComboBox<>();
        shapeSelector.setPreferredSize(new Dimension(150, 25));

        btnMoveUp = new JButton("↑");
        btnMoveDown = new JButton("↓");
        btnMoveLeft = new JButton("←");
        btnMoveRight = new JButton("→");

        txtWidth = new JTextField(5);
        txtHeight = new JTextField(5);
        btnApplySize = new JButton("Apply Size");

        add(new JLabel("Add:"));
        add(btnAddRectangle);
        add(btnAddCircle);
        add(btnAddTriangle);

        add(new JLabel("   Select Shape:"));
        add(shapeSelector);

        add(new JLabel("   Move:"));
        add(btnMoveUp);
        add(btnMoveDown);
        add(btnMoveLeft);
        add(btnMoveRight);

        add(new JLabel("   Size W/H:"));
        add(txtWidth);
        add(txtHeight);
        add(btnApplySize);
    }

    // ---------- Getters ----------
    public JButton getBtnAddRectangle() { return btnAddRectangle; }
    public JButton getBtnAddCircle() { return btnAddCircle; }
    public JButton getBtnAddTriangle() { return btnAddTriangle; }

    public JComboBox<String> getShapeSelector() { return shapeSelector; }

    public JButton getBtnMoveUp() { return btnMoveUp; }
    public JButton getBtnMoveDown() { return btnMoveDown; }
    public JButton getBtnMoveLeft() { return btnMoveLeft; }
    public JButton getBtnMoveRight() { return btnMoveRight; }

    public JTextField getTxtWidth() { return txtWidth; }
    public JTextField getTxtHeight() { return txtHeight; }
    public JButton getBtnApplySize() { return btnApplySize; }


    // ---------- Registro del Controller ----------
    public void setComboBoxListener(ActionListener listener) {
        this.comboListener = listener;
        shapeSelector.addActionListener(listener);
    }


    // ---------- ACTUALIZACIÓN SILENCIOSA DEL DROPDOWN ----------
    public void updateShapeList(java.util.List<String> names, int selectedIndex) {

        // Desactivar listener temporalmente
        if (comboListener != null)
            shapeSelector.removeActionListener(comboListener);

        // Repoblar sin disparar eventos
        shapeSelector.removeAllItems();
        for (String s : names) shapeSelector.addItem(s);

        // Restaurar selección
        if (selectedIndex >= 0 && selectedIndex < names.size())
            shapeSelector.setSelectedIndex(selectedIndex);

        // Restaurar listener
        if (comboListener != null)
            shapeSelector.addActionListener(comboListener);
    }
}
