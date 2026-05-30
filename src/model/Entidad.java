package model;

public abstract class Entidad {
    protected int x;
    protected int y;
    protected Direccion direccion;

    public Entidad(int xInicial, int yInicial, Direccion direccionInicial) {
        this.x = xInicial;
        this.y = yInicial;
        this.direccion = direccionInicial;
    }

    public abstract void mover(Laberinto laberinto);

    protected boolean puedeMoverse(Laberinto laberinto, int nuevoX, int nuevoY) {
        if (!laberinto.esPared(nuevoX, nuevoY)) {
            this.x = nuevoX;
            this.y = nuevoY;
            return true;
        }
        return false;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
     
    public void setY(int y) {
        this.y = y;
    }

    public Direccion getDireccion() {
        return direccion;
    }
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    void reiniciarPosicion(int x, int y) {
        this.x = x;
        this.y = y;
        this.direccion = Direccion.NINGUNA;
    }

}