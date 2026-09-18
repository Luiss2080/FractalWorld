package Capa_Logica.geometria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class GeneradorFractalesTest {

    private static final int W = 600;
    private static final int H = 500;
    private static final double EPS = 1e-6;

    private static int pow(int base, int exp) {
        int r = 1;
        for (int i = 0; i < exp; i++) {
            r *= base;
        }
        return r;
    }

    // ---------- validación de niveles (evita StackOverflowError y cuelgues) ----------

    @Test
    void nivelCeroONegativoSeRechazaEnTodosLosFractales() {
        for (int n : new int[]{0, -1, Integer.MIN_VALUE}) {
            assertThrows(IllegalArgumentException.class, () -> GeneradorFractales.copoDeNieve(n));
            assertThrows(IllegalArgumentException.class, () -> GeneradorFractales.arbol(n, W, H));
            assertThrows(IllegalArgumentException.class, () -> GeneradorFractales.sierpinski(n));
            assertThrows(IllegalArgumentException.class, () -> GeneradorFractales.curvaLevyC(n, W, H));
            assertThrows(IllegalArgumentException.class, () -> GeneradorFractales.curvaHilbert(n, W, H));
            assertThrows(IllegalArgumentException.class, () -> GeneradorFractales.anillos(n, W, H));
            assertThrows(IllegalArgumentException.class, () -> GeneradorFractales.pentagonos(n, W, H));
        }
    }

    @Test
    void nivelPorEncimaDelMaximoSeRechaza() {
        assertThrows(IllegalArgumentException.class,
                () -> GeneradorFractales.copoDeNieve(Fractal.COPO_DE_NIEVE.nivelMax() + 1));
        assertThrows(IllegalArgumentException.class, () -> GeneradorFractales.arbol(1000, W, H));
        assertThrows(IllegalArgumentException.class, () -> GeneradorFractales.curvaHilbert(31, W, H));
        assertThrows(IllegalArgumentException.class, () -> GeneradorFractales.anillos(Integer.MAX_VALUE, W, H));
    }

    @Test
    @Timeout(20)
    void nivelMaximoDeCadaFractalTerminaRapidoYSinDesbordarLaPila() {
        assertFalse(GeneradorFractales.copoDeNieve(Fractal.COPO_DE_NIEVE.nivelMax()).isEmpty());
        assertFalse(GeneradorFractales.arbol(Fractal.ARBOL.nivelMax(), W, H).isEmpty());
        assertFalse(GeneradorFractales.sierpinski(Fractal.SIERPINSKI.nivelMax()).isEmpty());
        assertFalse(GeneradorFractales.curvaLevyC(Fractal.LEVY_C.nivelMax(), W, H).isEmpty());
        assertFalse(GeneradorFractales.curvaHilbert(Fractal.HILBERT.nivelMax(), W, H).isEmpty());
        assertFalse(GeneradorFractales.anillos(Fractal.ANILLOS.nivelMax(), W, H).isEmpty());
        assertFalse(GeneradorFractales.pentagonos(Fractal.PENTAGONOS.nivelMax(), W, H).isEmpty());
    }

    @Test
    void parsearNivelAceptaEnterosValidosConEspacios() {
        assertEquals(5, Fractal.SIERPINSKI.parsearNivel(" 5 "));
        assertEquals(1, Fractal.HILBERT.parsearNivel("1"));
    }

    @Test
    void parsearNivelRechazaTextoVacioNoNumericoDecimalYFueraDeRango() {
        for (String texto : new String[]{null, "", "  ", "abc", "3.5", "1e2", "0", "-2", "99999999999999999999"}) {
            assertThrows(IllegalArgumentException.class, () -> Fractal.SIERPINSKI.parsearNivel(texto), String.valueOf(texto));
        }
        assertThrows(IllegalArgumentException.class, () -> Fractal.SIERPINSKI.parsearNivel("10"));
    }

    // ---------- copo de Koch ----------

    @Test
    void kochNivel1EsUnTrianguloCerrado() {
        List<Segmento> s = GeneradorFractales.copoDeNieve(1);
        assertEquals(3, s.size());
        assertEquals(s.get(0).x1(), s.get(2).x2(), EPS);
        assertEquals(s.get(0).y1(), s.get(2).y2(), EPS);
    }

    @Test
    void kochTieneTresPorCuatroALaNMenosUnoSegmentosYElPerimetroCreceEnCuatroTercios() {
        double anterior = 0;
        for (int n = 1; n <= 6; n++) {
            List<Segmento> s = GeneradorFractales.copoDeNieve(n);
            assertEquals(3 * pow(4, n - 1), s.size());
            double total = s.stream().mapToDouble(Segmento::longitud).sum();
            if (n > 1) {
                // tolerancia: el triángulo inicial (50,400)-(250,54)-(450,400) no es exactamente equilátero
                assertEquals(4.0 / 3.0, total / anterior, 1e-6);
            }
            anterior = total;
        }
    }

    @Test
    void kochEsUnaPolilineaContinua() {
        List<Segmento> s = GeneradorFractales.copoDeNieve(4);
        for (int i = 1; i < s.size(); i++) {
            assertEquals(s.get(i - 1).x2(), s.get(i).x1(), EPS);
            assertEquals(s.get(i - 1).y2(), s.get(i).y1(), EPS);
        }
    }

    // ---------- árbol ----------

    @Test
    void arbolNivel1EsSoloElTroncoVertical() {
        List<Segmento> s = GeneradorFractales.arbol(1, W, H);
        assertEquals(1, s.size());
        assertEquals(W / 2.0, s.get(0).x1(), EPS);
        assertEquals(H, s.get(0).y1(), EPS);
        assertEquals(W / 2.0, s.get(0).x2(), EPS);
        assertEquals(H - 10, s.get(0).y2(), EPS);
    }

    @Test
    void arbolTieneDosALaNMenosUnoRamasYCadaRamaNaceDondeTerminaSuPadre() {
        for (int n = 1; n <= 10; n++) {
            assertEquals(pow(2, n) - 1, GeneradorFractales.arbol(n, W, H).size());
        }
        List<Segmento> s = GeneradorFractales.arbol(4, W, H);
        // Preorden: el hijo izquierdo del tronco empieza en el final del tronco.
        assertEquals(s.get(0).x2(), s.get(1).x1(), EPS);
        assertEquals(s.get(0).y2(), s.get(1).y1(), EPS);
        // Longitudes: tronco = 4*10, hijos = 3*10
        assertEquals(40, s.get(0).longitud(), EPS);
        assertEquals(30, s.get(1).longitud(), EPS);
    }

    @Test
    void arbolNoEmpiezaFueraDelPanel() {
        for (Segmento seg : GeneradorFractales.arbol(6, W, H)) {
            assertTrue(seg.x1() >= 0 && seg.x1() <= W);
            assertTrue(seg.y1() <= H);
        }
    }

    // ---------- Sierpinski ----------

    @Test
    void sierpinskiTieneTresALaNSegmentosPorTriangulo() {
        for (int n = 1; n <= 7; n++) {
            assertEquals(3 * pow(3, n - 1), GeneradorFractales.sierpinski(n).size());
        }
    }

    @Test
    void sierpinskiNivel1EsElTrianguloInicial() {
        List<Segmento> s = GeneradorFractales.sierpinski(1);
        assertEquals(new Segmento(250, 50, 50, 450), s.get(0));
        assertEquals(new Segmento(50, 450, 450, 450), s.get(1));
        assertEquals(new Segmento(450, 450, 250, 50), s.get(2));
    }

    @Test
    void sierpinskiSubtriangulosCaenDentroDelTrianguloInicial() {
        for (Segmento s : GeneradorFractales.sierpinski(5)) {
            for (double[] p : new double[][]{{s.x1(), s.y1()}, {s.x2(), s.y2()}}) {
                assertTrue(p[0] >= 50 - EPS && p[0] <= 450 + EPS);
                assertTrue(p[1] >= 50 - EPS && p[1] <= 450 + EPS);
            }
        }
    }

    // ---------- Lévy C ----------

    @Test
    void levyTieneDosALaNMenosUnoSegmentosYEsContinua() {
        for (int n = 1; n <= 12; n++) {
            List<Segmento> s = GeneradorFractales.curvaLevyC(n, W, H);
            assertEquals(pow(2, n - 1), s.size());
            for (int i = 1; i < s.size(); i++) {
                assertEquals(s.get(i - 1).x2(), s.get(i).x1(), EPS);
                assertEquals(s.get(i - 1).y2(), s.get(i).y1(), EPS);
            }
        }
    }

    @Test
    void levyMantieneLosExtremosDeLaBaseYDividePorRaizDeDosLaLongitud() {
        List<Segmento> s = GeneradorFractales.curvaLevyC(8, W, H);
        assertEquals(W / 2.0 - 100, s.get(0).x1(), EPS);
        assertEquals(H / 2.0, s.get(0).y1(), EPS);
        assertEquals(W / 2.0 + 100, s.get(s.size() - 1).x2(), EPS);
        assertEquals(H / 2.0, s.get(s.size() - 1).y2(), EPS);
        double esperada = 200 / Math.pow(Math.sqrt(2), 7);
        for (Segmento seg : s) {
            assertEquals(esperada, seg.longitud(), 1e-6);
        }
    }

    // ---------- Hilbert ----------

    @Test
    void hilbertVisitaCadaCeldaUnaVezConPasosUnitariosYSinRamificaciones() {
        for (int n = 1; n <= 6; n++) {
            int celdas = 1 << n;
            Set<Integer> visitadas = new HashSet<>();
            int[] prev = null;
            for (int d = 0; d < celdas * celdas; d++) {
                int[] p = GeneradorFractales.hilbertXY(celdas, d);
                assertTrue(p[0] >= 0 && p[0] < celdas && p[1] >= 0 && p[1] < celdas);
                assertTrue(visitadas.add(p[0] * celdas + p[1]), "celda repetida en n=" + n);
                if (prev != null) {
                    assertEquals(1, Math.abs(p[0] - prev[0]) + Math.abs(p[1] - prev[1]), "paso no unitario");
                }
                prev = p;
            }
            assertEquals(celdas * celdas, visitadas.size());
        }
    }

    @Test
    void hilbertGeneraUnaSolaPolilineaDeCuatroALaNMenosUnoSegmentos() {
        for (int n = 1; n <= 5; n++) {
            List<Segmento> s = GeneradorFractales.curvaHilbert(n, W, H);
            assertEquals(pow(4, n) - 1, s.size());
            for (int i = 1; i < s.size(); i++) {
                assertEquals(s.get(i - 1).x2(), s.get(i).x1(), EPS);
                assertEquals(s.get(i - 1).y2(), s.get(i).y1(), EPS);
            }
            double paso = 270.0 / ((1 << n) - 1);
            for (Segmento seg : s) {
                assertEquals(paso, seg.longitud(), 1e-6);
            }
        }
    }

    @Test
    void hilbertQuedaCentradaYDentroDelCuadradoDe270() {
        for (Segmento s : GeneradorFractales.curvaHilbert(4, W, H)) {
            for (double[] p : new double[][]{{s.x1(), s.y1()}, {s.x2(), s.y2()}}) {
                assertTrue(p[0] >= W / 2.0 - 135 - EPS && p[0] <= W / 2.0 + 135 + EPS);
                assertTrue(p[1] >= H / 2.0 - 135 - EPS && p[1] <= H / 2.0 + 135 + EPS);
            }
        }
    }

    // ---------- anillos ----------

    @Test
    void anillosTieneCuatroALaNMenosUnoSobreTresCirculos() {
        for (int n = 1; n <= 6; n++) {
            assertEquals((pow(4, n) - 1) / 3, GeneradorFractales.anillos(n, W, H).size());
        }
    }

    @Test
    void anillosNivel1EsUnCirculoCentradoDe200() {
        List<Circulo> c = GeneradorFractales.anillos(1, W, H);
        assertEquals(List.of(new Circulo(W / 2.0, H / 2.0, 200)), c);
    }

    @Test
    void anillosNivel2AgregaCuatroCirculosDe100EnLosLados() {
        List<Circulo> c = GeneradorFractales.anillos(2, W, H);
        assertEquals(5, c.size());
        Set<Circulo> esperados = Set.of(
                new Circulo(W / 2.0 - 100, H / 2.0, 100), new Circulo(W / 2.0 + 100, H / 2.0, 100),
                new Circulo(W / 2.0, H / 2.0 - 100, 100), new Circulo(W / 2.0, H / 2.0 + 100, 100));
        assertTrue(new HashSet<>(c.subList(1, 5)).equals(esperados));
    }

    // ---------- pentágonos ----------

    @Test
    void pentagonosTieneCincoLadosPorPentagonoYCincoALaNMenosUnoSobreCuatroPentagonos() {
        for (int n = 1; n <= 5; n++) {
            assertEquals(5 * (pow(5, n) - 1) / 4, GeneradorFractales.pentagonos(n, W, H).size());
        }
    }

    @Test
    void pentagonoRegularTieneLadosIgualesYSeCierra() {
        List<Segmento> s = GeneradorFractales.pentagonos(1, W, H);
        double lado = 2 * 200 * Math.sin(Math.PI / 5);
        for (Segmento seg : s) {
            assertEquals(lado, seg.longitud(), 1e-6);
        }
        assertEquals(s.get(0).x1(), s.get(4).x2(), EPS);
        assertEquals(s.get(0).y1(), s.get(4).y2(), EPS);
        // primer vértice arriba del centro
        assertEquals(W / 2.0, s.get(0).x1(), EPS);
        assertEquals(H / 2.0 - 200, s.get(0).y1(), EPS);
    }

    @Test
    void variosNivelesDanResultadosDistintos() {
        assertNotEquals(GeneradorFractales.sierpinski(2), GeneradorFractales.sierpinski(3));
    }
}
