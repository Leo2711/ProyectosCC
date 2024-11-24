package cc.project.monsters_inc.zonas;

import cc.project.monsters_inc.departamento.Fabrica;
import cc.project.monsters_inc.modelo.Puerta;
import cc.project.monsters_inc.modelo.Puerta.TipoPuerta;

/*
 * Diseñadores de puertas:
 * 1 por puerta
 * + si es puerta especial
 */

public class FabricaPuertas implements Fabrica<Puerta, TipoPuerta> {    
    
    private AlmacenPuertas almacen;

    public FabricaPuertas(int capacidad) {
        System.out.println("Las luces de la fabrica se han encendido!!");
        this.almacen = new AlmacenPuertas(capacidad);
    }

    @Override
    public Puerta fabricar(TipoPuerta tipo) {
        Puerta nueva = new Puerta(tipo);
        enviarAlmacen(nueva);
        return nueva;
    }

    @Override
    public void enviarAlmacen(Puerta puerta) {
        System.out.println("Almacenando puerta...");
        this.almacen.almacenar(puerta);
    }

    public AlmacenPuertas getAlmacen() {
        return almacen;
    }

    public void setAlmacen(AlmacenPuertas almacen) {
        this.almacen = almacen;
    }    
}
