package cc.project.monsters_inc.departamento;

import cc.project.monsters_inc.modelo.Puerta.Estado;

public interface Alamacen<T> {

    public T[] verAlmacen();
    
    public void almacenar(T obj);
    
    public T solicitar(int lugar); 
        
    public void actualizar(int lugar, Estado edo);    

    public void limpiar();

}
