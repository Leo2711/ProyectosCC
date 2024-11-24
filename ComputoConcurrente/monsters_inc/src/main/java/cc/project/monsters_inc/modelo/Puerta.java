package cc.project.monsters_inc.modelo;

import java.util.Random;

// Vendría siendo mi StampedSnap

public class Puerta {

    public enum TipoPuerta { NORMAL, ESPECIAL }

    public enum Estado { ENUSO, ENREPARACION, DISPONIBLE, AVERIADO }

    public enum Habitacion { NINO, ADULTO, FIESTA, PIJAMADA}

    private TipoPuerta tipo;
    private Estado estado;
    private Habitacion habitacion;
        

    public Puerta (TipoPuerta tipo) {
        this.tipo = tipo;
        this.estado = Estado.DISPONIBLE;
        this.habitacion = asignarHabitacion();
    }

    public Puerta (TipoPuerta tipo, Estado estado, Habitacion habitacion) {
        this.tipo = tipo;
        this.estado = estado;
        this.habitacion = habitacion;
    }

    public TipoPuerta getTipo() {
        return tipo;
    }

    public void setTipo(TipoPuerta tipo) {
        this.tipo = tipo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    private Habitacion asignarHabitacion() {
        Random random = new Random();
        double proba = random.nextDouble();

        if (proba < 0.5) {
            return Habitacion.NINO;
        } else if (proba < 0.8) {
            return Habitacion.ADULTO;
        } else {
            if (random.nextBoolean()) {
                return Habitacion.FIESTA;
            } else {
                return Habitacion.PIJAMADA;
            }
        }
    }

    @Override
    public String toString() {
        String tipo = this.tipo.name();
        String edo = this.estado.name();
        String habitacion = this.habitacion.name();
        return String.format("TIPO: %s, ESTADO: %s, HABITACION: %s", tipo, edo, habitacion);
    }
}
