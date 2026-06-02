package model;

public class Pacman extends Entidad {
    private boolean powerUp;

    public Pacman(int xInicial, int yInicial, Direccion direccionInicial) {
        super(xInicial, yInicial, direccionInicial);
      
    }

    @Override
    public void mover(Laberinto laberinto) {
        int nuevoX = x + direccion.getDeltaX();
        int nuevoY = y + direccion.getDeltaY();

        puedeMoverse(laberinto, nuevoX, nuevoY);
        
    }
}
