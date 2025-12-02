package com.iteso.motor2d.model.exceptions;

import com.iteso.motor2d.view.MainWindow;
import javax.swing.JOptionPane;


public class InvalidFileException extends RuntimeException{
    private MainWindow ventana;
    private String texto;
    public InvalidFileException(MainWindow ventana, String mensaje){
        super("Archivo incorrecto");
        this.ventana =  ventana;
        this.texto = mensaje;
        throwError();
    }

    public String toString(){
        return getMessage();
    }
    
    private void throwError(){
        JOptionPane.showMessageDialog(ventana, texto);
    }
}
