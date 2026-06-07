

import model.Juego;
import view.VentanaPrincipal;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            Juego juego = new Juego();
            new VentanaPrincipal(juego);
        });
    }
}

