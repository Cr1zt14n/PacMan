package model;

import java.util.List;

public class Juego {
    private Laberinto laberinto;
    private Pacman pacman;
    private List<Fantasma>  fantasma;
    private int puntaje;
    private int vidas;
    private int puntajeRestante = laberinto.getTotalPuntos();
    private Estadosjuego estado;

    
    public Juego() {
        this.laberinto = new Laberinto(new int[][] {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 2, 0, 0, 0, 2, 1},
            {1, 0, 1, 1, 0, 0, 1},
            {1, 0, 0, 0, 2, 0, 1},
            {1, 2, 0, 1, 0, 2, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1}
        });
        this.pacman = new Pacman(1, 1, Direccion.NINGUNA);
        this.fantasma = List.of(
            new Fantasma(5, 1, Direccion.NINGUNA),
            new Fantasma(5, 3, Direccion.NINGUNA),
            new Fantasma(5, 5, Direccion.NINGUNA)
        );
        this.puntaje = 0;
        this.vidas = 3;     
        this.estado = Estadosjuego.INICIO;
        this.puntajeRestante = laberinto.getTotalPuntos();
    }

    public void iniciarJuego() {
        estado = Estadosjuego.JUGANDO;
    }

    public void pausarJuego() {
        if (estado == Estadosjuego.JUGANDO) {
            estado = Estadosjuego.PAUSA;
        }
    }

    public void reanudarJuego() {
        if (estado == Estadosjuego.PAUSA) {
            estado = Estadosjuego.JUGANDO;
        }
    }

    public void reiniciarJuego() {
        this.laberinto = new Laberinto(new int[][] {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 2, 0, 0, 0, 2, 1},
            {1, 0, 1, 1, 0, 0, 1},
            {1, 0, 0, 0, 2, 0, 1},
            {1, 2, 0, 1, 0, 2, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1}
        });
        this.pacman = new Pacman(1, 1, null);
        this.fantasma = List.of(
            new Fantasma(5, 1, null),
            new Fantasma(5, 3, null),
            new Fantasma(5, 5, null)
        );
        this.puntaje = 0;
        this.vidas = 3;     
        this.estado = Estadosjuego.INICIO;
        this.puntajeRestante = laberinto.getTotalPuntos();
    }
    public void terminarJuego() {
        estado = Estadosjuego.PERDIDO;
    }
    public void moverPacman(Direccion direccion) {
        if (estado != Estadosjuego.JUGANDO) {
            return; // No mover si el juego no está activo
        }
        pacman.setDireccion(direccion);
        pacman.mover(laberinto);

        if (laberinto.esPunto(pacman.getX(), pacman.getY())) {
            laberinto.comerPunto(pacman.getX(), pacman.getY());
            puntaje += 10;
            puntajeRestante--;
            if (puntajeRestante == 0) {
                estado = Estadosjuego.GANADO;
            }
        }

        verificarColision();


    }

    public void moverFantasma() {
        if (estado != Estadosjuego.JUGANDO) {
            return; // No mover si el juego no está activo
        }
        for (Fantasma f : fantasma) {
            f.mover(laberinto);
            if (f.getX() == pacman.getX() && f.getY() == pacman.getY()) {
                vidas--;
                if (vidas <= 0) {
                    estado = Estadosjuego.PERDIDO;
                } else {
                    pacman.reiniciarPosicion(1, 1);
                }
            }
        }
        verificarColision();

    }

    private void verificarColision() {
        for (Fantasma f : fantasma) {
            if (f.getX() == pacman.getX() && f.getY() == pacman.getY()) {
                vidas--;
                if (vidas <= 0) {
                    estado = Estadosjuego.PERDIDO;
                } else {
                    pacman.reiniciarPosicion(1, 1);
                }
            }
        }
        perderVida();
    }

    private void perderVida() {
        vidas--;
        if (vidas <= 0) {
            estado = Estadosjuego.PERDIDO;
        } else {
            pacman.reiniciarPosicion(1, 1);
        }
    }


    public Laberinto getLaberinto() {
        return laberinto;
    }

    public Pacman getPacman() {
        
        return pacman;
    }

    public List<Fantasma> getFantasma() {
        return fantasma;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getVidas() {
        return vidas;
    }

    public Estadosjuego getEstado() {
        return estado;
    }

}

