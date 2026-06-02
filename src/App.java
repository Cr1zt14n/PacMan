

import model.Juego;
import view.VentanaPrincipal;

public class App {
    public static void main(String[] args) throws Exception {
   javax.swing.SwingUtilities.invokeLater(() -> {
            Juego juego = new Juego();
            new VentanaPrincipal(juego);
        });
    }
}

