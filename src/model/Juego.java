package model;

import java.util.ArrayList;
import java.util.List;

public class Juego {
    private Laberinto laberinto;
    private Pacman pacman;
    private ArrayList<Fantasma>  fantasmas;
    private int puntaje;
    private int vidas;
    private int puntajeRestante;
    private Estadosjuego estado;
    private Runnable callBackVista;

    
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
        this.fantasmas = new ArrayList<>();
        this.fantasmas.add(new Fantasma(5, 1, Direccion.NINGUNA, "Rojo", this));
        this.fantasmas.add(new Fantasma(5, 3, Direccion.NINGUNA, "Azul", this));
        this.fantasmas.add(new Fantasma(5, 5, Direccion.NINGUNA, "Rosa", this));

        this.puntaje = 0;
        this.vidas = 3;     
        this.estado = Estadosjuego.INICIO;
        this.puntajeRestante = this.laberinto.getTotalPuntos();
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
        this.pacman = new Pacman(1, 1, Direccion.NINGUNA);
        this.fantasmas.clear();
        this.fantasmas.add(new Fantasma(5, 1, Direccion.NINGUNA, "Rojo", this));
        this.fantasmas.add(new Fantasma(5, 3, Direccion.NINGUNA, "Azul", this));
        this.fantasmas.add(new Fantasma(5, 5, Direccion.NINGUNA, "Rosa", this));
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
            return; 
        }
        pacman.setDireccion(direccion);
        pacman.mover(laberinto);

        if (laberinto.esPunto(pacman.getX(), pacman.getY())) {
            laberinto.comerPunto(pacman.getX(), pacman.getY());
            puntaje += 10;
            puntajeRestante--;
            if (puntajeRestante == 0) {
                estado = Estadosjuego.GANADO;
                return;
            }
        }

        verificarColision();


    }

    public void moverFantasma() {
        if (estado != Estadosjuego.JUGANDO) {
            return; 
        }
        for (Fantasma f : fantasmas) {
            f.mover(laberinto);
        }
        verificarColision();

    }

    private void verificarColision() {
        for (Fantasma f : fantasmas) {
            if (f.getX() == pacman.getX() && f.getY() == pacman.getY()) {
                perderVida(); 
                break;        
            }
        }
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
        return fantasmas;
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

    public void validarVista() {
        if (callBackVista != null) {
            callBackVista.run();
        }
    }
    public void setCallBackVista (Runnable callback) {
        this.callBackVista = callback;
    }
    public void iniciarHilosFantasma() {
        for (Fantasma f : fantasmas) {
            Thread t =new Thread(f);
            t.setDaemon(true);
            t.start();
        }
     }
     public void verificarColisionFantasma() {
     verificarColision();
     }
}

