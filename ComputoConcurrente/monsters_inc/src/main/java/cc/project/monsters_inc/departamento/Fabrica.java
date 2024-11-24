package cc.project.monsters_inc.departamento;

public interface Fabrica<T, U> {
    
    public T fabricar(U tipo);

    public void enviarAlmacen(T obj);
}
