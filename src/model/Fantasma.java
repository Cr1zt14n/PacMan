package model;

public class Fantasma extends Entidad implements Runnable {
    private volatile boolean corriendo = true;
    private String color; 
    private Juego juego;
    private volatile boolean pausado = false;

    
    public Fantasma(int xInicial, int yInicial, Direccion direccionInicial, String color, Juego juego) {
        super(xInicial, yInicial, direccionInicial);
        this.color = color;
        this.juego = juego;
    }

    @Override
    public void mover(Laberinto laberinto) {
        if (direccion == Direccion.NINGUNA) {
            elegirDireccionAleatoria();
        }
        int nuevoX = x + direccion.getDeltaX();
        int nuevoY = y + direccion.getDeltaY();

        if (!puedeMoverse(laberinto, nuevoX, nuevoY)) {
            Direccion[] direcciones = Direccion.values();
            Direccion direccionAleatoria;

            do {
                direccionAleatoria = direcciones[(int) (Math.random() * direcciones.length)];
            } while (direccionAleatoria == Direccion.NINGUNA);
            this.direccion = direccionAleatoria;

                nuevoX = x + direccion.getDeltaX();
                nuevoY = y + direccion.getDeltaY();
                puedeMoverse(laberinto, nuevoX, nuevoY);
        } 
    }

    private void elegirDireccionAleatoria() {
        Direccion[] direcciones = Direccion.values();
        Direccion direccionAleatoria;

        do {
            direccionAleatoria = direcciones[(int) (Math.random() * direcciones.length)];
        } while (direccionAleatoria == Direccion.NINGUNA);
        this.direccion = direccionAleatoria;
    }

    @Override
    public void run() {
        while (corriendo) {
            try {
                Thread.sleep(500); 
                if (!pausado && juego.getEstado() == Estadosjuego.JUGANDO) {
                    synchronized (juego) {
                        mover(juego.getLaberinto());
                        juego.verificarColisionFantasma();
                    }
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        juego.validarVista();
                });
                }
            } catch (InterruptedException e) {
                corriendo = false;
            }
        }
    }

    public void detener() {
        corriendo = false;
    }
    public void setPausado(boolean pausado) {
        this.pausado = pausado;
    }
    public String getColor() {
        return color;
    }
}
