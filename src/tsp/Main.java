package tsp;

import java.io.IOException;
import java.util.*;

public class Main {

    private static int MAX_CIDADES;
    private static double[][] distancias;
    private static List<Cidade> cidades;
    private static List<Rota> ForcaBrutaPermRotas = new ArrayList<Rota>();
    private static double ForcaBrutaMenorCusto = Double.MAX_VALUE;
    private static Rota ForcaBrutaMenorRota;
    private static List<String> nomeArquivos;

    public static void geraMatrixAleatoria(int dimensoes) {
        MAX_CIDADES = dimensoes;
        distancias = new double[MAX_CIDADES][MAX_CIDADES];
        Random rnd = new Random(System.currentTimeMillis());
        int j;
        for (int i = 0; i < MAX_CIDADES; i++) {
            for (j = 0; j < MAX_CIDADES; j++) {
                if (i == j) {
                    distancias[i][j] = 0;
                } else {
                    distancias[i][j] = (rnd.nextInt(100) + rnd.nextDouble());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {

        long tempo1 = 0;
        long tempo2 = 0;

        int numIterations = 12;
        LerArquivo arquivo;

        for (int i = 2; i < numIterations; i++) {
            ForcaBrutaPermRotas = new ArrayList<Rota>();
            ForcaBrutaMenorCusto = Double.MAX_VALUE;
            geraMatrixAleatoria(i);
            long tempo = System.currentTimeMillis();
            ForcaBruta();
            System.out.println("\tTime:" + (System.currentTimeMillis() - tempo) + "ms");
            tempo = System.currentTimeMillis();
            vizinhoMaisProximo();
            System.out.println("\tTime:" + (System.currentTimeMillis() - tempo) + "ms");

        }
        nomeArquivos = new ArrayList<String>();
        nomeArquivos.add("si535");
        nomeArquivos.add("si1032");
        nomeArquivos.add("pa561");
        for (int vizProx = 0; vizProx < 3; vizProx++) {

            arquivo = new LerArquivo(nomeArquivos.get(vizProx));
            distancias = arquivo.getMatriz();
            MAX_CIDADES = arquivo.getDimensao();
            ForcaBrutaPermRotas = new ArrayList<Rota>();
            ForcaBrutaMenorCusto = Double.MAX_VALUE;
            long tempo = System.currentTimeMillis();

            vizinhoMaisProximo();
            System.out.println("\tTime:" + (System.currentTimeMillis() - tempo) + "ms");

        }
    }

    public static void ForcaBruta() {
        System.out.println("ForcaBruta:");
        resetaListas();
        List<Integer> cidadeNums = new ArrayList<Integer>();
        for (int i = 0; i < MAX_CIDADES - 1; i++) {
            cidadeNums.add(i);
        }
        permutar(new Rota(), cidadeNums, true);
        System.out.println("\tPermutacoes: " + ForcaBrutaPermRotas.size());
        encontraMenorPermutacao(ForcaBrutaPermRotas);
    }

    private static void vizinhoMaisProximo() {
        System.out.println("vizinhoMaisProximo:");
        resetaListas();
        double custoRota = 0;
        Rota rotaMaisProxima = new Rota(cidades.get(MAX_CIDADES - 1));
        while (rotaMaisProxima.getRota().size() != cidades.size()) {
            Cidade cidadeVizinha = null;
            double distanciaDoVizinho = Double.MAX_VALUE;
            for (int i = 0; i < MAX_CIDADES - 1; i++) {
                // If closer and not self and not visited
                if (distancias[rotaMaisProxima.getCidadeAtual().getID()][i] < distanciaDoVizinho
                        && distancias[rotaMaisProxima.getCidadeAtual().getID()][i] != 0
                        && cidades.get(i).isFoiVisitada() == false) {

                    // Update closest neighbour
                    cidadeVizinha = cidades.get(i);
                    distanciaDoVizinho = distancias[rotaMaisProxima.getCidadeAtual().getID()][i];
                }
            }
            if (cidadeVizinha != null) {

                rotaMaisProxima.getRota().add(cidadeVizinha);
                rotaMaisProxima.setCidadeAtual(cidadeVizinha);
                cidadeVizinha.setFoiVisitada(true);

                custoRota += distanciaDoVizinho;
            }
        }
        custoRota += distancias[rotaMaisProxima.getStartCity().getID()][rotaMaisProxima.getCidadeAtual().getID()];
        rotaMaisProxima.getRota().add(cidades.get(MAX_CIDADES - 1));
        System.out.println("\t" + rotaMaisProxima.toString() + "\n\tDistanciaPercorrida: " + custoRota);
    }

    private static void resetaListas() {
        ForcaBrutaPermRotas = new ArrayList<Rota>();
        cidades = new ArrayList<Cidade>();
        for (int o = 0; o < MAX_CIDADES; o++) {
            if (o != (MAX_CIDADES - 1)) {
                cidades.add(new Cidade("" + o, o, false));
            } else {
                cidades.add(new Cidade("" + o, o, true));
            }
        }
    }

    private static void permutar(Rota r, List<Integer> naoVisitados, boolean isForcaBruta) {
        if (!naoVisitados.isEmpty()) {

            for (int i = 0; i < naoVisitados.size(); i++) {
                int temp = naoVisitados.remove(0);
                Rota newRoute = new Rota();
                for (Cidade c1 : r.getRota()) {
                    newRoute.getRota().add(c1);
                }
                newRoute.getRota().add(cidades.get(temp));
                if (isForcaBruta) {

                    permutar(newRoute, naoVisitados, true);
                }
                naoVisitados.add(temp);
            }
        } else if (isForcaBruta) {
            ForcaBrutaPermRotas.add(r);
        } else {

            r.getRota().add(0, cidades.get(MAX_CIDADES - 1));
            r.getRota().add(cidades.get(MAX_CIDADES - 1));
        }
    }

    private static void encontraMenorPermutacao(List<Rota> listaRotas) {
        for (Rota r : listaRotas) {
            defineCidadeFinal(r);
            if (getCustoDaRota(r) < ForcaBrutaMenorCusto) {
                ForcaBrutaMenorCusto = getCustoDaRota(r);
                ForcaBrutaMenorRota = r;
            }
        }
        System.out.println("\t" + ForcaBrutaMenorRota.toString() + "\n\tCost: " + ForcaBrutaMenorCusto);
    }

    private static void defineCidadeFinal(Rota r) {
        r.getRota().add(0, cidades.get(MAX_CIDADES - 1));
        r.getRota().add(cidades.get(MAX_CIDADES - 1));
    }

    private static Double getCustoDaRota(Rota r) {
        double temp = 0;
        for (int i = 0; i < r.getRota().size() - 1; i++) {
            temp += distancias[r.getRota().get(i).getID()][r.getRota().get(i + 1).getID()];
        }
        return temp;
    }
}
