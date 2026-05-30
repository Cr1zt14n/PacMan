package model;

public class Pacman extends Entidad {
    private boolean powerUp;

    public Pacman(int xInicial, int yInicial, Direccion direccionInicial) {
        super(xInicial, yInicial, direccionInicial);
      
    }

    @Override
    public void mover(Laberinto laberinto) {
        // TODO Auto-generated method stub
        int nuevoX = x + direccion.getDeltaX();
        int nuevoY = y + direccion.getDeltaY();

        intentarMoverse(laberinto, nuevoX, nuevoY);
        return 
    }
}
