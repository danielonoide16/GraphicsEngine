package com.iteso.motor2d.util;

/**
 * IdGenerator
 * ----------------
 * Singleton encargado de generar IDs únicos para cada Shape2D.
 *
 * - Usa un contador interno que incrementa cada vez que se solicita un nuevo ID.
 * - Es thread-safe gracias al uso de la palabra clave 'synchronized'.
 * - El constructor es privado para asegurar el patrón Singleton.
 */
public final class IdGenerator {

    // Instancia única del Singleton (inmutable)
    private static final IdGenerator INSTANCE = new IdGenerator();

    // Contador interno de IDs (comienza en 1)
    private int counter = 1;

    // Constructor privado: evita instanciación externa
    private IdGenerator() { }

    /**
     * Obtiene la única instancia del generador.
     */
    public static IdGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * Genera un nuevo ID único.
     *
     * Comentario en español:
     * El método es synchronized para que, en caso de que múltiples hilos
     * lo usen simultáneamente, el contador no genere valores duplicados.
     */
    public synchronized int generateId() {
        return counter++;
    }

    /**
     * Reinicia el generador (solo si el proyecto lo necesita).
     * No es obligatorio, pero puede ser útil en pruebas.
     */
    public synchronized void reset() {
        counter = 1;
    }
}
