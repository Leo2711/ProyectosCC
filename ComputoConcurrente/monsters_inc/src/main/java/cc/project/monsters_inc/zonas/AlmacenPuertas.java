package cc.project.monsters_inc.zonas;

import cc.project.monsters_inc.departamento.Alamacen;
import cc.project.monsters_inc.modelo.Puerta;
import cc.project.monsters_inc.modelo.Puerta.Estado;

// Tener un Almacen por tipo de puertas?

public class AlmacenPuertas implements Alamacen<Puerta> {
        
    private Puerta[] puertas;

    public AlmacenPuertas(int capacidad) {    
        System.out.println("El almacen está en marcha!!");    
        this.puertas = new Puerta[capacidad];      
    }

    @Override
    public Puerta[] verAlmacen() {
        return puertas;
    }

    @Override
    public void almacenar(Puerta puerta) {
        int me = getThreadId();
        this.puertas[me] = puerta;
    }
    
    @Override
    public Puerta solicitar(int lugar) {
        Puerta puerta = this.puertas[lugar];
        puerta.setEstado(Estado.ENUSO);
        return puerta;
    }
    
    @Override
    public void actualizar(int lugar, Estado edo) {        
        this.puertas[lugar].setEstado(edo);
    }

    @Override
    public void limpiar() {
        for (Puerta puerta : puertas) {
            if (puerta == null) { continue; }
            puerta.setEstado(Estado.DISPONIBLE);
        }
    }
    
    private int getThreadId() {
        String t = Thread.currentThread().getName().replaceAll("\\D+", "");
        return t.equals("") ? 0 : Integer.parseInt(t);
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < puertas.length; i++) {
            if (puertas[i] == null) {
                continue;
            }
            s += "PUERTA " + String.valueOf(i) + " { " +puertas[i].toString() + " }\n";
        }
        return s;
    }
}
