/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LerArquivo {

    private Scanner scan;
    private FileReader arq;
    private String linha;
    private ArrayList<Integer> vetor = new ArrayList<>();
    private double[][] matriz;

    public int tamanho;

    @SuppressWarnings("empty-statement")
    public LerArquivo(String file) throws FileNotFoundException, IOException {

        this.scan = new Scanner(new File("../arquivos/" + file + ".txt"));
        while (!scan.next().equals("DIMENSION:"));
        tamanho = Integer.parseInt(scan.next());
        while (!scan.next().equals("EDGE_WEIGHT_SECTION"));
        matriz = generateMatrix(file);
    }

    public double[][] generateMatrix(String file) {
        double[][] matriz = new double[tamanho][tamanho];
        int linha = -1;
        int coluna = -1;
        if (file.equals("pa561")) {
            linha = 0;
            coluna = 0;
            while (scan.hasNext()) {
                String coisa = scan.next();
                if (coisa.equals("DISPLAY_DATA_SECTION")) {
                    break;
                }
                if (coisa.equals("0")) {
                    matriz[linha][coluna] = Integer.parseInt(coisa);
                    linha++;
                    coluna = 0;
                } else {
                    matriz[linha][coluna] = Integer.parseInt(coisa);
                    coluna++;
                }
            }
        } else {
            while (scan.hasNext()) {
                String coisa = scan.next();
                if (coisa.equals("EOF")) {
                    break;
                }
                if (coisa.equals("0")) {
                    linha++;
                    coluna = linha;
                    matriz[linha][coluna] = Integer.parseInt(coisa);
                } else {
                    coluna++;
                    matriz[linha][coluna] = Integer.parseInt(coisa);
                }
            }
        }
        return matriz;
    }

    public int getDimensao() {
        return tamanho;
    }

    public void printArray() {
        for (int k = 0; k < vetor.size(); k++) {
            System.out.print(vetor.get(k) + " ");
        }
        System.out.println();
        System.out.println(vetor.size());
    }

    public double[][] getMatriz() {
        return matriz;
    }

}
