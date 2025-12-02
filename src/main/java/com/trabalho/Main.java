package com.trabalho;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    private static final int[] TAMANHOS = {100, 1000, 10000};
    private static final String[] ORDENS = {"Ordenada", "Inversamente Ordenada", "Aleatoria"};
    private static final int NUM_EXECUCOES = 5;
    private static final List<String[]> resultados = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Iniciando analise de desempenho de estruturas de dados em Java.");
        System.out.println("----------------------------------------------------------------");

        // Adicionar cabeçalho CSV
        resultados.add(new String[]{"Estrutura", "Tamanho", "Ordem", "Operacao", "TempoMedio_ms"});

        for (int tamanho : TAMANHOS) {
            for (String ordem : ORDENS) {
                System.out.println("Tamanho: " + tamanho + ", Ordem: " + ordem);
                System.out.println("----------------------------------------------------------------");

                List<Integer> dados = gerarDados(tamanho, ordem);

                // Teste Vetor
                testarVetor(tamanho, ordem, dados);

                // Teste Arvore Binaria de Busca
                testarArvoreBinariaBusca(tamanho, ordem, dados);

                // Teste Arvore AVL
                testarArvoreAVL(tamanho, ordem, dados);

                System.out.println("----------------------------------------------------------------");
            }
        }

        exportarResultadosParaCSV("resultados_desempenho.csv");
        System.out.println("Analise de desempenho concluida. Resultados exportados para resultados_desempenho.csv");
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

    private static void testarVetor(int tamanho, String ordem, List<Integer> dados) {
        System.out.println("Testando Vetor...");
        long tempoInsercaoTotal = 0;
        long tempoBuscaSequencialTotal = 0;
        long tempoBuscaBinariaTotal = 0;
        long tempoBubbleSortTotal = 0;
        long tempoInsertionSortTotal = 0;
        long tempoMergeSortTotal = 0;
        long tempoQuickSortTotal = 0;

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

            Vetor vetorInsertion = new Vetor(tamanho);
            for (int dado : dados) {
                vetorInsertion.inserir(dado);
            }
            long inicioInsertion = System.nanoTime();
            new Ordenacao().insertionSort(vetorInsertion.getElementos()); 
            long fimInsertion = System.nanoTime();
            tempoInsertionSortTotal += (fimInsertion - inicioInsertion);

            Vetor vetorMerge = new Vetor(tamanho);
            for (int dado : dados) {
                vetorMerge.inserir(dado);
            }
            long inicioMerge = System.nanoTime();
            new Ordenacao().mergeSort(vetorMerge.getElementos()); 
            long fimMerge = System.nanoTime();
            tempoMergeSortTotal += (fimMerge - inicioMerge);

            Vetor vetorQuick = new Vetor(tamanho);
            for (int dado : dados) {
                vetorQuick.inserir(dado);
            }
            long inicioQuick = System.nanoTime();
            new Ordenacao().quickSort(vetorQuick.getElementos()); 
            long fimQuick = System.nanoTime();
            tempoQuickSortTotal += (fimQuick - inicioQuick);

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
        double tempoInsercaoVetor = (double) tempoInsercaoTotal / NUM_EXECUCOES / 1_000_000.0;
        double tempoBuscaSequencialVetor = (double) tempoBuscaSequencialTotal / NUM_EXECUCOES / 1_000_000.0;
        double tempoBuscaBinariaVetor = (double) tempoBuscaBinariaTotal / NUM_EXECUCOES / 1_000_000.0;
        double tempoBubbleSortVetor = (double) tempoBubbleSortTotal / NUM_EXECUCOES / 1_000_000.0;
        double tempoInsertionSortVetor = (double) tempoInsertionSortTotal / NUM_EXECUCOES / 1_000_000.0;
        double tempoMergeSortVetor = (double) tempoMergeSortTotal / NUM_EXECUCOES / 1_000_000.0;
        double tempoQuickSortVetor = (double) tempoQuickSortTotal / NUM_EXECUCOES / 1_000_000.0;

        System.out.printf("Tempo medio de insercao (Vetor): %.3f ms%n", tempoInsercaoVetor);
        System.out.printf("Tempo medio de busca sequencial (Vetor): %.3f ms%n", tempoBuscaSequencialVetor);
        System.out.printf("Tempo medio de busca binaria (Vetor): %.3f ms%n", tempoBuscaBinariaVetor);
        System.out.printf("Tempo medio de Bubble Sort (Vetor): %.3f ms%n", tempoBubbleSortVetor);
        System.out.printf("Tempo medio de Insertion Sort (Vetor): %.3f ms%n", tempoInsertionSortVetor);
        System.out.printf("Tempo medio de Merge Sort (Vetor): %.3f ms%n", tempoMergeSortVetor);
        System.out.printf("Tempo medio de Quick Sort (Vetor): %.3f ms%n", tempoQuickSortVetor);

        resultados.add(new String[]{"Vetor", String.valueOf(tamanho), ordem, "Insercao", String.format("%.3f", tempoInsercaoVetor)});
        resultados.add(new String[]{"Vetor", String.valueOf(tamanho), ordem, "Busca Sequencial", String.format("%.3f", tempoBuscaSequencialVetor)});
        resultados.add(new String[]{"Vetor", String.valueOf(tamanho), ordem, "Busca Binaria", String.format("%.3f", tempoBuscaBinariaVetor)});
        resultados.add(new String[]{"Vetor", String.valueOf(tamanho), ordem, "Bubble Sort", String.format("%.3f", tempoBubbleSortVetor)});
        resultados.add(new String[]{"Vetor", String.valueOf(tamanho), ordem, "Insertion Sort", String.format("%.3f", tempoInsertionSortVetor)});
        resultados.add(new String[]{"Vetor", String.valueOf(tamanho), ordem, "Merge Sort", String.format("%.3f", tempoMergeSortVetor)});
        resultados.add(new String[]{"Vetor", String.valueOf(tamanho), ordem, "Quick Sort", String.format("%.3f", tempoQuickSortVetor)});
    }

    private static void testarArvoreBinariaBusca(int tamanho, String ordem, List<Integer> dados) {
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
        double tempoInsercaoABB = (double) tempoInsercaoTotal / NUM_EXECUCOES / 1_000_000.0;
        double tempoBuscaABB = (double) tempoBuscaTotal / NUM_EXECUCOES / 1_000_000.0;

        System.out.printf("Tempo medio de insercao (ABB): %.3f ms%n", tempoInsercaoABB);
        System.out.printf("Tempo medio de busca (ABB): %.3f ms%n", tempoBuscaABB);

        resultados.add(new String[]{"Arvore Binaria de Busca", String.valueOf(tamanho), ordem, "Insercao", String.format("%.3f", tempoInsercaoABB)});
        resultados.add(new String[]{"Arvore Binaria de Busca", String.valueOf(tamanho), ordem, "Busca", String.format("%.3f", tempoBuscaABB)});
        
        if (tamanho == 100) { // Apenas para tamanhos pequenos para não poluir a saída
            ArvoreBinariaBusca abbParaPercurso = new ArvoreBinariaBusca();
            for (int dado : dados) {
                abbParaPercurso.inserir(dado);
            }
            abbParaPercurso.imprimirEmOrdem();
            abbParaPercurso.imprimirPreOrdem();
            abbParaPercurso.imprimirPosOrdem();

            System.out.println("Testando remocao (ABB):");
            int valorParaRemover = dados.get(tamanho / 2); // Remover o elemento do meio
            System.out.println("Removendo " + valorParaRemover + " da ABB...");
            abbParaPercurso.remover(valorParaRemover);
            System.out.println("Busca por " + valorParaRemover + " apos remocao: " + abbParaPercurso.buscar(valorParaRemover));
            abbParaPercurso.imprimirEmOrdem();
        }
    }

    private static void testarArvoreAVL(int tamanho, String ordem, List<Integer> dados) {
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
        double tempoInsercaoAVL = (double) tempoInsercaoTotal / NUM_EXECUCOES / 1_000_000.0;
        double tempoBuscaAVL = (double) tempoBuscaTotal / NUM_EXECUCOES / 1_000_000.0;

        System.out.printf("Tempo medio de insercao (AVL): %.3f ms%n", tempoInsercaoAVL);
        System.out.printf("Tempo medio de busca (AVL): %.3f ms%n", tempoBuscaAVL);

        resultados.add(new String[]{"Arvore AVL", String.valueOf(tamanho), ordem, "Insercao", String.format("%.3f", tempoInsercaoAVL)});
        resultados.add(new String[]{"Arvore AVL", String.valueOf(tamanho), ordem, "Busca", String.format("%.3f", tempoBuscaAVL)});

        if (tamanho == 100) { // Apenas para tamanhos pequenos para não poluir a saída
            ArvoreAVL avlParaPercurso = new ArvoreAVL();
            for (int dado : dados) {
                avlParaPercurso.inserir(dado);
            }
            avlParaPercurso.imprimirEmOrdem();
            avlParaPercurso.imprimirPreOrdem();
            avlParaPercurso.imprimirPosOrdem();

            System.out.println("Testando remocao (AVL):");
            int valorParaRemover = dados.get(tamanho / 2); // Remover o elemento do meio
            System.out.println("Removendo " + valorParaRemover + " da AVL...");
            avlParaPercurso.remover(valorParaRemover);
            System.out.println("Busca por " + valorParaRemover + " apos remocao: " + avlParaPercurso.buscar(valorParaRemover));
            avlParaPercurso.imprimirEmOrdem();
        }
    }

    private static void exportarResultadosParaCSV(String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            for (String[] linha : resultados) {
                writer.append(String.join(",", linha));
                writer.append("\n");
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao exportar resultados para CSV: " + e.getMessage());
        }
    }
}
