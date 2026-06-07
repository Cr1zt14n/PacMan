package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.Direccion;
import model.Estadosjuego;
import model.Juego;

public class TecladoController implements KeyListener {
    private Juego juego;

    public TecladoController(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        Estadosjuego estado = juego.getEstado();
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_P) {
            if (estado == Estadosjuego.JUGANDO) {
                juego.pausarJuego();
            } else if (estado == Estadosjuego.PAUSA) {
                juego.reanudarJuego();
            }
            return;
        }
        if (keyCode == KeyEvent.VK_R) {
            juego.reiniciarJuego();
            juego.iniciarJuego();
            return;
        }

        // Movimiento solo si está jugando
        if (estado != Estadosjuego.JUGANDO) return;

        Direccion direccion = null;

        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                direccion = Direccion.ARRIBA;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                direccion = Direccion.ABAJO;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                direccion = Direccion.IZQUIERDA;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                direccion = Direccion.DERECHA;
                break;
        }

        if (direccion != null) {
            juego.moverPacman(direccion);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}