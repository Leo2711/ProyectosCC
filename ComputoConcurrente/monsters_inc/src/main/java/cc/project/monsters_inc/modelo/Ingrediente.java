package cc.project.monsters_inc.modelo;

public class Ingrediente {
    private String nombre;
    private Integer posicion;//Con este valor accedemos directamente a la posicion del arreglo
    private volatile Integer cantidad; //Este si esta en lista de platillos, cuantas unidades 
    //usa (si no lo quieren usar que valga 0, pero solo es valido en lista de platillos), en el
    //Inventario ese atributo nos dira cuantos hay de sobrante, para que se vea la concurrencia
    //En teoria se usa un Lock adicional para que se acceda, pero como tenemos 2 intancias diferentes y similares
    //Recomiendo usar herencia
}
