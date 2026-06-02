

import model.*;
import java.util.Scanner;

public class MainConsola {
    private static Juego juego;
    private static Scanner scanner;

    public static void main(String[] args) {
        juego = new Juego();
        scanner = new Scanner(System.in);
        
        System.out.println("=== PAC-MAN - PRUEBA EN CONSOLA ===");
        System.out.println("Comandos:");
        System.out.println("  w/a/s/d : mover Pacman");
        System.out.println("  f       : mover fantasmas 1 paso");
        System.out.println("  p       : pausar/reanudar");
        System.out.println("  r       : reiniciar juego");
        System.out.println("  i       : iniciar juego (cambiar a JUGANDO)");
        System.out.println("  q       : salir");
        System.out.println("----------------------------------------\n");
        
        // El juego inicia en estado INICIO, el usuario debe presionar 'i' para empezar
        boolean salir = false;
        while (!salir) {
            mostrarEstado();
            System.out.print("> ");
            String comando = scanner.nextLine().trim().toLowerCase();
            
            switch (comando) {
                case "w":
                    moverPacman(Direccion.ARRIBA);
                    break;
                case "s":
                    moverPacman(Direccion.ABAJO);
                    break;
                case "a":
                    moverPacman(Direccion.IZQUIERDA);
                    break;
                case "d":
                    moverPacman(Direccion.DERECHA);
                    break;
                case "f":
                    moverFantasmas();
                    break;
                case "p":
                    pausarReanudar();
                    break;
                case "r":
                    reiniciar();
                    break;
                case "i":
                    iniciarPartida();
                    break;
                case "q":
                    salir = true;
                    System.out.println("Saliendo del simulador...");
                    break;
                default:
                    System.out.println("Comando no reconocido. Usa: w,a,s,d,f,p,r,i,q");
            }
        }
        scanner.close();
    }
    
    private static void moverPacman(Direccion dir) {
        juego.moverPacman(dir);
        System.out.println(">>> Pacman movido hacia " + dir);
    }
    
    private static void moverFantasmas() {
        juego.moverFantasma();
        System.out.println(">>> Fantasmas avanzaron 1 paso");
    }
    
    private static void pausarReanudar() {
        Estadosjuego estado = juego.getEstado();
        if (estado == Estadosjuego.JUGANDO) {
            juego.pausarJuego();
            System.out.println(">>> JUEGO PAUSADO");
        } else if (estado == Estadosjuego.PAUSA) {
            juego.reanudarJuego();
            System.out.println(">>> JUEGO REANUDADO");
        } else {
            System.out.println("No se puede pausar en estado " + estado);
        }
    }
    
    private static void reiniciar() {
        juego.reiniciarJuego();
        System.out.println(">>> JUEGO REINICIADO (presiona 'i' para comenzar)");
    }
    
    private static void iniciarPartida() {
        juego.iniciarJuego();
        System.out.println(">>> PARTIDA INICIADA (estado JUGANDO)");
    }
    
    private static void mostrarEstado() {
        System.out.println("\n--- LABERINTO ---");
        Laberinto lab = juego.getLaberinto();
        Pacman pac = juego.getPacman();
        var fantasmas = juego.getFantasma(); // usa el getter plural
        
        for (int y = 0; y < lab.getAlto(); y++) {
            for (int x = 0; x < lab.getAncho(); x++) {
                // Dibujar Pacman
                if (pac.getX() == x && pac.getY() == y) {
                    System.out.print("C ");
                    continue;
                }
                // Dibujar fantasmas
                boolean hayFantasma = false;
                for (Fantasma f : fantasmas) {
                    if (f.getX() == x && f.getY() == y) {
                        System.out.print("F ");
                        hayFantasma = true;
                        break;
                    }
                }
                if (hayFantasma) continue;
                
                // Dibujar pared, punto o vacío
                if (lab.esPared(x, y)) {
                    System.out.print("# ");
                } else if (lab.esPunto(x, y)) {
                    System.out.print(". ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println(); // nueva línea por fila
        }
        
        System.out.println("Puntaje: " + juego.getPuntaje() +
                           " | Vidas: " + juego.getVidas() +
                           " | Estado: " + juego.getEstado() +
                           " | Puntos restantes: " + lab.getPuntosRestantes());
        System.out.println("----------------------------------------\n");
    }
}