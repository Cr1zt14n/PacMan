package view;

import model.Juego;
import model.Estadosjuego;
import controller.TecladoController;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private Juego juego;
    private PanelJuego panelJuego;
    private JLabel infoLabel;
    private Timer timerFantasmas;
    private Timer repaintTimer;

    public VentanaPrincipal(Juego juego) {
        this.juego = juego;
        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelJuego = new PanelJuego(juego);
        add(panelJuego, BorderLayout.CENTER);

        JPanel panelInfo = new JPanel();
        JPanel panelBotones = new JPanel();
        JButton btnIniciar = new JButton("Iniciar");
        JButton btnPausa = new JButton("Pausar");
        JButton btnReiniciar = new JButton("Reiniciar");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnIniciar);
        panelBotones.add(btnPausa);
        panelBotones.add(btnReiniciar);
        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.NORTH);
        btnIniciar.addActionListener(e -> iniciarJuego());
        btnPausa.addActionListener(e -> togglePausa());
        btnReiniciar.addActionListener(e -> reiniciarJuego());
        btnSalir.addActionListener(e -> System.exit(0));
        infoLabel = new JLabel("Presiona 'Juego → Iniciar partida' para comenzar");
        panelInfo.add(infoLabel);
        add(panelInfo, BorderLayout.SOUTH);

        // Barra de menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Juego");
        JMenuItem iniciarItem = new JMenuItem("Iniciar partida");
        JMenuItem pausaItem  = new JMenuItem("Pausar / Reanudar");
        JMenuItem reiniciarItem = new JMenuItem("Reiniciar");
        JMenuItem salirItem  = new JMenuItem("Salir");
        menu.add(iniciarItem);
        menu.add(pausaItem);
        menu.add(reiniciarItem);
        menu.addSeparator();
        menu.add(salirItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

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

 
        TecladoController teclado = new TecladoController(juego);
        panelJuego.addKeyListener(teclado);
        panelJuego.setFocusable(true);

        // Acciones del menú
        iniciarItem.addActionListener(e -> iniciarJuego());
        pausaItem.addActionListener(e -> togglePausa());
        reiniciarItem.addActionListener(e -> reiniciarJuego());
        salirItem.addActionListener(e -> System.exit(0));
        System.out.println("MENU CREADO: " + menuBar.getMenuCount());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        actualizarInfo();
    }

    private void iniciarJuego() {
        Estadosjuego estado = juego.getEstado();
        if (estado == Estadosjuego.INICIO) {
            juego.iniciarJuego();
            timerFantasmas.start();
            repaintTimer.start();
            panelJuego.requestFocusInWindow();
            actualizarInfo();
            panelJuego.repaint();
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
            panelJuego.requestFocusInWindow();
        }
        actualizarInfo();
        panelJuego.repaint();
    }

    private void reiniciarJuego() {
        timerFantasmas.stop();
        repaintTimer.stop();
        juego.reiniciarJuego();
        actualizarInfo();
        panelJuego.repaint();
        infoLabel.setText("Presiona 'Juego → Iniciar partida' para comenzar");
    }

    private void verificarFinJuego() {
        if (juego.getEstado() == Estadosjuego.GANADO) {
            timerFantasmas.stop();
            repaintTimer.stop();
            JOptionPane.showMessageDialog(this,
                "¡Ganaste! Puntaje final: " + juego.getPuntaje(), 
                "Victoria", JOptionPane.INFORMATION_MESSAGE);
        } else if (juego.getEstado() == Estadosjuego.PERDIDO) {
            timerFantasmas.stop();
            repaintTimer.stop();
            JOptionPane.showMessageDialog(this,
                "Game Over. Puntaje: " + juego.getPuntaje(),
                "Fin del juego", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarInfo() {
        infoLabel.setText("Puntaje: " + juego.getPuntaje() + 
                          "   Vidas: " + juego.getVidas() + 
                          "   Estado: " + juego.getEstado());
    }
}