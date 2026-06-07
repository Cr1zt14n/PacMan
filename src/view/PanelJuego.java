package view;

import model.*;
import javax.swing.*;
import java.awt.*;

public class PanelJuego extends JPanel {
    private Juego juego;
    private final int CELDA_SIZE = 40; 

    public PanelJuego(Juego juego) {
        this.juego = juego;
        setPreferredSize(new Dimension(
            juego.getLaberinto().getAncho() * CELDA_SIZE,
            juego.getLaberinto().getAlto() * CELDA_SIZE
        ));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Laberinto lab = juego.getLaberinto();
        int ancho = lab.getAncho();
        int alto = lab.getAlto();

        // Dibujar celdas
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int screenX = x * CELDA_SIZE;
                int screenY = y * CELDA_SIZE;
                if (lab.esPared(x, y)) {
                    g.setColor(Color.BLUE);
                    g.fillRect(screenX, screenY, CELDA_SIZE, CELDA_SIZE);
                } else if (lab.esPunto(x, y)) {
                    g.setColor(Color.WHITE);
                    g.fillOval(screenX + CELDA_SIZE/2 - 3, screenY + CELDA_SIZE/2 - 3, 6, 6);
                }
            }
        }

        for (Fantasma f : juego.getFantasma()) {
            int x = f.getX() * CELDA_SIZE;
            int y = f.getY() * CELDA_SIZE;
            switch (f.getColor()) {
                case "Rojo": g.setColor(Color.RED); break;
                case "Azul": g.setColor(Color.CYAN); break;
                case "Rosa": g.setColor(Color.PINK); break;
                default: g.setColor(Color.GRAY);
            }
            g.fillOval(x + 5, y + 5, CELDA_SIZE - 10, CELDA_SIZE - 10);
            // Ojos simples
            g.setColor(Color.WHITE);
            g.fillOval(x + 12, y + 12, 6, 6);
            g.fillOval(x + 22, y + 12, 6, 6);
        }

        Pacman p = juego.getPacman();
        int x = p.getX() * CELDA_SIZE;
        int y = p.getY() * CELDA_SIZE;
        g.setColor(Color.YELLOW);
  
        int startAngle = 0;
        int arcAngle = 360;
        switch (p.getDireccion()) {
            case DERECHA: startAngle = 30; arcAngle = 300; break;
            case IZQUIERDA: startAngle = 210; arcAngle = 300; break;
            case ARRIBA: startAngle = 120; arcAngle = 300; break;
            case ABAJO: startAngle = 300; arcAngle = 300; break;
            default: startAngle = 30; arcAngle = 300;
        }
        g.fillArc(x + 5, y + 5, CELDA_SIZE - 10, CELDA_SIZE - 10, startAngle, arcAngle);
    }
}