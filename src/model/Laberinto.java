package model;

public class Laberinto {
    private int[][] mapa;

    private int ancho;
    private int alto;
    private int totalPuntos;
    public Laberinto(int[][] mapa) {
        this.mapa = mapa;
        this.alto = mapa.length;
        this.ancho = mapa[0].length;
        this.totalPuntos = calcularTotalPuntos();
    }

    private int calcularTotalPuntos() {
        int puntos = 0;
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                if (mapa[y][x] == 2) {
                    puntos++;
                }
            }
        }
        return puntos;
    }

    public boolean esPared(int x, int y) {
        return mapa[y][x] == 1;
    }

    public boolean esPunto(int x, int y) {
        return mapa[y][x] == 2;
    }

    public void comerPunto(int x, int y) {
        if (esPunto(x, y)) {
            mapa[y][x] = 0; 
        }
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public int getTotalPuntos() {
        return totalPuntos;
    }

    public int getPuntosRestantes() {
        int puntosRestantes = 0;
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                if (esPunto(x, y)) {
                    puntosRestantes++;
                }
            }
        }
        return puntosRestantes;
    }
    

}
