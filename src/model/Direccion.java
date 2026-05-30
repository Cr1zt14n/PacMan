package model;

public enum Direccion {
    ARRIBA, ABAJO, IZQUIERDA, DERECHA, NINGUNA;

    public int getDeltaX() {
        switch (this) {
            case IZQUIERDA:
                return -1;
            case DERECHA:
                return 1;
            default:
                return 0;
        }
    }

    public int getDeltaY() {
        switch (this) {
            case ARRIBA:
                return -1;
            case ABAJO:
                return 1;
            default:
                return 0;
        }
    }

    
}
