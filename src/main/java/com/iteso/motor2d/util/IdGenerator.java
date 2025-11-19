package com.iteso.motor2d.util;

/**
 * Singleton encargado de generar IDs únicos para cada Shape2D.
 *
 * Usa un contador interno que incrementa cada vez que se solicita un nuevo ID.
 * El constructor es privado para asegurar el patrón Singleton.
 */
public final class IdGenerator {

    // instancia única del Singleton (final para que sea inmutable)
    private static final IdGenerator INSTANCE = new IdGenerator();

    // contador interno de IDs (comienza en 1)
    private int counter = 1;

    // constructor privado que evita instanciación externa
    private IdGenerator(){}

    /**
     * Obtiene la única instancia del generador.
     */
    public static IdGenerator getInstance() 
    {
        return INSTANCE;
    }

    /**
     * Genera un nuevo ID único.
     *
     * El método es synchronized para que, en caso de que múltiples hilos
     * lo usen simultáneamente, el contador no genere valores duplicados
     * probablemente no necesario en este proyecto, pero es buena practica.
     */
    public synchronized int generateId() 
    {
        return counter++;
    }

    /**
     * Reinicia el generador 
     * No es obligatorio, pero puede ser útil en pruebas.
     */
    public synchronized void reset() 
    {
        counter = 1;
    }
}
