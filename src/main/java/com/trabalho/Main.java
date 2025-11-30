package com.trabalho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    private static final int[] TAMANHOS = {100, 1000, 10000};
    private static final String[] ORDENS = {"Ordenada", "Inversamente Ordenada", "Aleatoria"};
    private static final int NUM_EXECUCOES = 5;

    public static void main(String[] args) {
        System.out.println("Iniciando analise de desempenho de estruturas de dados em Java.");
        System.out.println("----------------------------------------------------------------");

        for (int tamanho : TAMANHOS) {
            for (String ordem : ORDENS) {
                System.out.println("Tamanho: " + tamanho + ", Ordem: " + ordem);
                System.out.println("----------------------------------------------------------------");

                List<Integer> dados = gerarDados(tamanho, ordem);

                // Teste Vetor
                testarVetor(tamanho, dados);

                // Teste Arvore Binaria de Busca
                testarArvoreBinariaBusca(tamanho, dados);

                // Teste Arvore AVL
                testarArvoreAVL(tamanho, dados);

                System.out.println("----------------------------------------------------------------");
            }
        }
    }

    private static List<Integer> gerarDados(int tamanho, String ordem) {
        List<Integer> dados = new ArrayList<>();
        for (int i = 1; i <= tamanho; i++) {
            dados.add(i);
        }

        if (ordem.equals("Inversamente Ordenada")) {
            Collections.reverse(dados);
        } else if (ordem.equals("Aleatoria")) {
            Collections.shuffle(dados, new Random());
        }
        return dados;
    }

    private static void testarVetor(int tamanho, List<Integer> dados) {
        System.out.println("Testando Vetor...");
        long tempoInsercaoTotal = 0;
        long tempoBuscaSequencialTotal = 0;
        long tempoBuscaBinariaTotal = 0;
        long tempoBubbleSortTotal = 0;
        long tempoMergeSortTotal = 0;

        for (int k = 0; k < NUM_EXECUCOES; k++) {
            Vetor vetor = new Vetor(tamanho);
            long inicio = System.nanoTime();
            for (int dado : dados) {
                vetor.inserir(dado);
            }
            long fim = System.nanoTime();
            tempoInsercaoTotal += (fim - inicio);

            inicio = System.nanoTime();
            vetor.buscarSequencial(dados.get(0)); 
            vetor.buscarSequencial(dados.get(tamanho - 1)); 
            vetor.buscarSequencial(dados.get(tamanho / 2)); 
            Random rand = new Random(); 
            for (int i = 0; i < 3; i++) {
                vetor.buscarSequencial(dados.get(rand.nextInt(tamanho))); 
            }
            vetor.buscarSequencial(tamanho + 1); 
            fim = System.nanoTime();
            tempoBuscaSequencialTotal += (fim - inicio);

            Vetor vetorBubble = new Vetor(tamanho);
            for (int dado : dados) {
                vetorBubble.inserir(dado);
            }
            long inicioBubble = System.nanoTime();
            new Ordenacao().bubbleSort(vetorBubble.getElementos()); 
            long fimBubble = System.nanoTime();
            tempoBubbleSortTotal += (fimBubble - inicioBubble);

            Vetor vetorMerge = new Vetor(tamanho);
            for (int dado : dados) {
                vetorMerge.inserir(dado);
            }
            long inicioMerge = System.nanoTime();
            new Ordenacao().mergeSort(vetorMerge.getElementos()); 
            long fimMerge = System.nanoTime();
            tempoMergeSortTotal += (fimMerge - inicioMerge);

            vetor.ordenar(); 
            inicio = System.nanoTime();
            vetor.buscarBinaria(dados.get(0)); 
            vetor.buscarBinaria(dados.get(tamanho - 1)); 
            vetor.buscarBinaria(dados.get(tamanho / 2)); 
            for (int i = 0; i < 3; i++) {
                vetor.buscarBinaria(dados.get(rand.nextInt(tamanho))); 
            }
            vetor.buscarBinaria(tamanho + 1); 
            fim = System.nanoTime();
            tempoBuscaBinariaTotal += (fim - inicio);
        }

        System.out.printf("Tempo medio de insercao (Vetor): %.3f ms%n", (double) tempoInsercaoTotal / NUM_EXECUCOES / 1_000_000.0);
        System.out.printf("Tempo medio de busca sequencial (Vetor): %.3f ms%n", (double) tempoBuscaSequencialTotal / NUM_EXECUCOES / 1_000_000.0);
        System.out.printf("Tempo medio de busca binaria (Vetor): %.3f ms%n", (double) tempoBuscaBinariaTotal / NUM_EXECUCOES / 1_000_000.0);
        System.out.printf("Tempo medio de Bubble Sort (Vetor): %.3f ms%n", (double) tempoBubbleSortTotal / NUM_EXECUCOES / 1_000_000.0);
        System.out.printf("Tempo medio de Merge Sort (Vetor): %.3f ms%n", (double) tempoMergeSortTotal / NUM_EXECUCOES / 1_000_000.0);
    }

    private static void testarArvoreBinariaBusca(int tamanho, List<Integer> dados) {
        System.out.println("Testando Arvore Binaria de Busca...");
        long tempoInsercaoTotal = 0;
        long tempoBuscaTotal = 0;

        for (int k = 0; k < NUM_EXECUCOES; k++) {
            ArvoreBinariaBusca abb = new ArvoreBinariaBusca();
            long inicio = System.nanoTime();
            for (int dado : dados) {
                abb.inserir(dado);
            }
            long fim = System.nanoTime();
            tempoInsercaoTotal += (fim - inicio);

            inicio = System.nanoTime();
            abb.buscar(dados.get(0)); 
            abb.buscar(dados.get(tamanho - 1)); 
            abb.buscar(dados.get(tamanho / 2)); 
            Random rand = new Random();
            for (int i = 0; i < 3; i++) {
                abb.buscar(dados.get(rand.nextInt(tamanho))); 
            }
            abb.buscar(tamanho + 1); 
            fim = System.nanoTime();
            tempoBuscaTotal += (fim - inicio);
        }
        System.out.printf("Tempo medio de insercao (ABB): %.3f ms%n", (double) tempoInsercaoTotal / NUM_EXECUCOES / 1_000_000.0);
        System.out.printf("Tempo medio de busca (ABB): %.3f ms%n", (double) tempoBuscaTotal / NUM_EXECUCOES / 1_000_000.0);
    }

    private static void testarArvoreAVL(int tamanho, List<Integer> dados) {
        System.out.println("Testando Arvore AVL...");
        long tempoInsercaoTotal = 0;
        long tempoBuscaTotal = 0;

        for (int k = 0; k < NUM_EXECUCOES; k++) {
            ArvoreAVL avl = new ArvoreAVL();
            long inicio = System.nanoTime();
            for (int dado : dados) {
                avl.inserir(dado);
            }
            long fim = System.nanoTime();
            tempoInsercaoTotal += (fim - inicio);

            inicio = System.nanoTime();
            avl.buscar(dados.get(0)); 
            avl.buscar(dados.get(tamanho - 1)); 
            avl.buscar(dados.get(tamanho / 2)); 
            Random rand = new Random();
            for (int i = 0; i < 3; i++) {
                avl.buscar(dados.get(rand.nextInt(tamanho))); 
            }
            avl.buscar(tamanho + 1); 
            fim = System.nanoTime();
            tempoBuscaTotal += (fim - inicio);
        }
        System.out.printf("Tempo medio de insercao (AVL): %.3f ms%n", (double) tempoInsercaoTotal / NUM_EXECUCOES / 1_000_000.0);
        System.out.printf("Tempo medio de busca (AVL): %.3f ms%n", (double) tempoBuscaTotal / NUM_EXECUCOES / 1_000_000.0);
    }
}

// iniciar projeto : javac src/main/java/com/trabalho/*.java -d out && java -cp out com.trabalho.Main ou
// java -cp src/main/java com.trabalho.Main
// "C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot\bin\javac.exe" src/main/java/com/trabalho/*.java -d out && "C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot\bin\java.exe" -cp out com.trabalho.Main


