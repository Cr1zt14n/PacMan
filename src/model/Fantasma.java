package model;

public class Fantasma extends Entidad implements Runnable {
    private String color; 

    private boolean pausado;
    
    
    public Fantasma(int xInicial, int yInicial, Direccion direccionInicial) {
        super(xInicial, yInicial, direccionInicial);
    }

    @Override
    public void mover(Laberinto laberinto) {
        // Movimiento aleatorio del fantasma
        Direccion[] direcciones = Direccion.values();
        Direccion direccionAleatoria = direcciones[(int) (Math.random() * direcciones.length)];
        int nuevoX = x + direccionAleatoria.getDeltaX();
        int nuevoY = y + direccionAleatoria.getDeltaY();

        puedeMoverse(laberinto, nuevoX, nuevoY);

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
      mover(laberinto);
    }
    
}
