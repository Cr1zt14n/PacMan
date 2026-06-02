package model;

public class Fantasma extends Entidad {
    private String color; 

    private boolean pausado;
    
    
    public Fantasma(int xInicial, int yInicial, Direccion direccionInicial) {
        super(xInicial, yInicial, direccionInicial);
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
    
}
