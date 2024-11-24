package Matrices;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pract1 {
    public static void main(String[] args) {
        int[][] matrizA = null;
        try {
            matrizA = readMatrix(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] matrizB = null;
        try {
            matrizB = readMatrix(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long startTime = System.nanoTime();
        int[][] resultadoSec = Matrices.multMatrices1(matrizA,matrizB);
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        System.out.println("Tiempo de ejecucion en secuencial: " + (double)time / 1_000_000_000.0 + "sec");
        Matrices m = new Matrices(matrizA, matrizB);
        int numHilos = Integer.parseInt(args[1]);
        List<Thread> listaHilos = new ArrayList<>();
        startTime = System.nanoTime();
        for (int i = 0; i < matrizA.length; i++) {
            Thread t = new Thread(m, ""+i);
            listaHilos.add(t);
            t.start();
            if(listaHilos.size() == numHilos){
                for (Thread thread : listaHilos) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                listaHilos.clear();
            }
        }
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        System.out.println("Tiempo de ejecucion en concurrente: " + (double)time2 / 1_000_000_000.0  + "sec");
        double speedup =  (double)time/(double)time2;
        System.out.println(String.format("Aceleracion obtenida con %d hilos: %f", numHilos, speedup));
        
    }   

    static int[][] readMatrix(String file) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));
        Scanner sc = new Scanner(br);
        String line = sc.nextLine();
        String[] strLine = line.split(" ");
        int[][] lines = new int[strLine.length][strLine.length];
        for (int j = 0; j < lines.length; j++) {
            int[] nbrLine = new int[strLine.length];
            for (int i = 0; i < nbrLine.length; i++) {
                nbrLine[i] = Integer.valueOf(strLine[i]);
            }
            lines[j]= nbrLine;
            if (sc.hasNextLine()) {
                line = sc.nextLine();
                strLine = line.split(" ");
            }
                
        }
        return lines;
    }

    static void printMatrix(int[][] matriz){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println("");
        }
    }
}