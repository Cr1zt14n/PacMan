package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Direccion;
import model.Juego;

public class TecladoController implements KeyListener{

    private Juego juego;

    public TecladoController(Juego juego) {
        this.juego = juego;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (juego.getEstado() != model.Estadosjuego.JUGANDO) {
            return;
        }
        Direccion direccion = Direccion.NINGUNA;
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                // Mover hacia arriba
                break;
                
             case KeyEvent.VK_W:
                direccion = Direccion.ARRIBA;
                break;

            case KeyEvent.VK_DOWN:
                // Mover hacia abajo
                break;

             case KeyEvent.VK_S:
                direccion = Direccion.ABAJO;
                break;

            case KeyEvent.VK_LEFT:
                // Mover hacia la izquierda
                break;

             case KeyEvent.VK_A:
                direccion = Direccion.IZQUIERDA;
                break;

            case KeyEvent.VK_RIGHT:
                // Mover hacia la derecha
                break;

             case KeyEvent.VK_D:
                direccion = Direccion.DERECHA;
                 break;
            case KeyEvent.VK_P:
                if (juego.getEstado() == model.Estadosjuego.JUGANDO) {
                    juego.pausarJuego();
                } else if (juego.getEstado() == model.Estadosjuego.PAUSA) {
                    juego.reanudarJuego();
                }
                break;
            case KeyEvent.VK_R:
                reiniciarJuego();
                break;
        }
    }

    private void reiniciarJuego() {
        juego.reiniciarJuego();
        juego.iniciarJuego();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No se utiliza en este caso
    }
    
}
