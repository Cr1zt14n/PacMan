package view;

import model.Juego;
import model.Estadosjuego;
import controller.TecladoController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {
    private Juego juego;
    private PanelJuego panelJuego;
    private JLabel infoLabel;
    private Timer timerFantasmas;
    private Timer repaintTimer;

    public VentanaPrincipal(Juego juego) {
        this.juego = juego;
        setTitle("Pac-Man - Fase 2 Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel del juego
        panelJuego = new PanelJuego(juego);
        add(panelJuego, BorderLayout.CENTER);

        // Panel de información
        JPanel panelInfo = new JPanel();
        infoLabel = new JLabel();
        panelInfo.add(infoLabel);
        add(panelInfo, BorderLayout.SOUTH);

        // Crear barra de menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Juego");
        JMenuItem iniciarItem = new JMenuItem("Iniciar partida");
        JMenuItem pausaItem = new JMenuItem("Pausar/Reanudar");
        JMenuItem reiniciarItem = new JMenuItem("Reiniciar");
        JMenuItem salirItem = new JMenuItem("Salir");

        menu.add(iniciarItem);
        menu.add(pausaItem);
        menu.add(reiniciarItem);
        menu.addSeparator();
        menu.add(salirItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Acciones del menú
        iniciarItem.addActionListener(e -> iniciarJuego());
        pausaItem.addActionListener(e -> togglePausa());
        reiniciarItem.addActionListener(e -> reiniciarJuego());
        salirItem.addActionListener(e -> System.exit(0));

        // Controlador de teclado
        TecladoController teclado = new TecladoController(juego);
        panelJuego.addKeyListener(teclado);
        panelJuego.setFocusable(true);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Los timers se crearán pero no se iniciarán hasta que el usuario pulse "Iniciar partida"
        timerFantasmas = new Timer(300, e -> {
            if (juego.getEstado() == Estadosjuego.JUGANDO) {
                juego.moverFantasma();
                actualizarInfo();
                panelJuego.repaint();
                verificarFinJuego();
            }
        });

        repaintTimer = new Timer(50, e -> {
            if (juego.getEstado() == Estadosjuego.JUGANDO) {
                panelJuego.repaint();
            }
        });

        actualizarInfo();
        // No iniciamos los timers aún
    }

    private void iniciarJuego() {
        if (juego.getEstado() == Estadosjuego.INICIO || juego.getEstado() == Estadosjuego.PAUSA) {
            juego.iniciarJuego();  // cambia a JUGANDO
            timerFantasmas.start();
            repaintTimer.start();
            panelJuego.requestFocusInWindow();  // para que el teclado funcione
        }
    }

    private void togglePausa() {
        if (juego.getEstado() == Estadosjuego.JUGANDO) {
            juego.pausarJuego();
            timerFantasmas.stop();
            repaintTimer.stop();
        } else if (juego.getEstado() == Estadosjuego.PAUSA) {
            juego.reanudarJuego();
            timerFantasmas.start();
            repaintTimer.start();
        }
        actualizarInfo();
        panelJuego.repaint();
    }

    private void reiniciarJuego() {
        // Detener timers si estaban corriendo
        timerFantasmas.stop();
        repaintTimer.stop();
        juego.reiniciarJuego();
        // El estado queda en INICIO, no se inicia automáticamente
        actualizarInfo();
        panelJuego.repaint();
        // No llamamos a iniciarJuego() aquí, para que el usuario decida
    }

    private void verificarFinJuego() {
        if (juego.getEstado() == Estadosjuego.GANADO) {
            JOptionPane.showMessageDialog(this, "¡Ganaste!");
            timerFantasmas.stop();
            repaintTimer.stop();
        } else if (juego.getEstado() == Estadosjuego.PERDIDO) {
            JOptionPane.showMessageDialog(this, "Game Over");
            timerFantasmas.stop();
            repaintTimer.stop();
        }
    }

    public void actualizarInfo() {
        infoLabel.setText("Puntaje: " + juego.getPuntaje() + "   Vidas: " + juego.getVidas());
    }
}