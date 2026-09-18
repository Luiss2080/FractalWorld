package Capa_Logica.geometria;

/**
 * Fractales disponibles con su rango de niveles válido.
 *
 * <p>El máximo acota el trabajo: el número de figuras crece de forma exponencial
 * con el nivel (p. ej. 4^n en el copo de Koch) y más allá de estos niveles el
 * detalle ya es menor que un píxel, por lo que solo se bloquearía la interfaz.</p>
 */
public enum Fractal {
    COPO_DE_NIEVE("Copo de nieve de Koch", 7),
    ARBOL("Árbol binario", 12),
    SIERPINSKI("Triángulo de Sierpinski", 9),
    LEVY_C("Curva de Lévy C", 16),
    HILBERT("Curva de Hilbert", 7),
    ANILLOS("Fractal de anillos", 7),
    PENTAGONOS("Fractal de pentágonos", 6);

    public static final int NIVEL_MIN = 1;

    private final String nombre;
    private final int nivelMax;

    Fractal(String nombre, int nivelMax) {
        this.nombre = nombre;
        this.nivelMax = nivelMax;
    }

    public String nombre() {
        return nombre;
    }

    public int nivelMax() {
        return nivelMax;
    }

    /** @throws IllegalArgumentException si el nivel está fuera de [NIVEL_MIN, nivelMax]. */
    public void validar(int nivel) {
        if (nivel < NIVEL_MIN || nivel > nivelMax) {
            throw new IllegalArgumentException(
                    "El nivel para \"" + nombre + "\" debe estar entre " + NIVEL_MIN + " y " + nivelMax + ".");
        }
    }

    /**
     * Convierte el texto de un campo en un nivel válido.
     *
     * @throws IllegalArgumentException con un mensaje apto para el usuario.
     */
    public int parsearNivel(String texto) {
        int nivel;
        try {
            nivel = Integer.parseInt(texto == null ? "" : texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Ingrese un número entero entre " + NIVEL_MIN + " y " + nivelMax + ".");
        }
        validar(nivel);
        return nivel;
    }
}
