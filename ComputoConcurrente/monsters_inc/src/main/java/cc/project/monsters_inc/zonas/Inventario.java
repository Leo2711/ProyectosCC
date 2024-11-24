package cc.project.monsters_inc.zonas;

import cc.project.monsters_inc.modelo.Ingrediente;

public class Inventario {
    public Ingrediente[] ingredientes;

    public Inventario(Ingrediente[] ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Inventario(Integer numIngredientes, Integer capacidad){
        this.ingredientes = new Ingrediente[numIngredientes];
        //Asignamos la capacidad de los ingredientes mediante for
        //ingredientes[i] = new ingrediente(capacidad);
    }
}
