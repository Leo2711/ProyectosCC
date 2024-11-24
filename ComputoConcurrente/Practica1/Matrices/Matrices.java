package Matrices;

public class Matrices implements Runnable {
    int[][] matrizA;
    int[][] matrizB;
    static int[][] resultado;
    
    public Matrices(int[][] matrizA, int[][]matrizB){
        this.matrizA = matrizA;
        this.matrizB = matrizB;
        resultado = new int[matrizA.length][matrizB[0].length];
    }

    public static int[][] multMatrices1(int[][] matrizA, int[][]matrizB){
        int[][] resultado = new int[matrizA.length][matrizB[0].length];
        int suma = 0;
        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizA[i].length; j++) {
                for (int j2 = 0; j2 < matrizB.length; j2++) {
                    suma += matrizA[i][j2] * matrizB[j2][j];
                }
                resultado[i][j] = suma;
                suma = 0;
            }
        }
        return resultado;
    }

    public void multMatrices2(int[][] matrizA, int[][] matrizB, int fila){
        int suma = 0;
        for (int j = 0; j < matrizA[fila].length; j++) {
            for (int j2 = 0; j2 < matrizB.length; j2++) {
                suma += matrizA[fila][j2] * matrizB[j2][j];
            }
            resultado[fila][j] = suma;
            suma = 0;
        } 
    }

    @Override
    public void run() {
        int fila = Integer.parseInt(Thread.currentThread().getName());
        multMatrices2(this.matrizA,this.matrizB,fila);
    }
}