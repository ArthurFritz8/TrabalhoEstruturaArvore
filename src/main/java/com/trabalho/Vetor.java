package com.trabalho;

import java.util.Arrays;

public class Vetor {
    private int[] elementos;
    private int tamanhoAtual;
    private static final float FATOR_CRESCIMENTO = 1.5f; // Crescimento mais eficiente que dobrar
    private static final int LIMITE_REDUCAO = 4; // Reduzir o tamanho quando estiver 25% ocupado

    public Vetor(int capacidade) {
        elementos = new int[capacidade];
        tamanhoAtual = 0;
    }

    public void inserir(int elemento) {
        if (tamanhoAtual == elementos.length) {
            redimensionar(Math.max(1, (int)(elementos.length * FATOR_CRESCIMENTO)));
        }
        elementos[tamanhoAtual++] = elemento;
    }
    
    // Método para remover elemento (otimizado para liberar memória quando necessário)
    public boolean remover(int elemento) {
        int indice = buscarSequencial(elemento);
        if (indice == -1) {
            return false;
        }
        
        // Deslocar elementos
        System.arraycopy(elementos, indice + 1, elementos, indice, tamanhoAtual - indice - 1);
        tamanhoAtual--;
        elementos[tamanhoAtual] = 0; // Ajuda o GC a liberar memória
        
        // Reduzir o tamanho do array se estiver muito vazio
        if (elementos.length > 20 && tamanhoAtual * LIMITE_REDUCAO < elementos.length) {
            redimensionar(Math.max(10, elementos.length / 2));
        }
        
        return true;
    }
    
    // Método auxiliar para redimensionar o array
    private void redimensionar(int novaCapacidade) {
        elementos = Arrays.copyOf(elementos, novaCapacidade);
    }

    public int buscarSequencial(int elemento) {
        for (int i = 0; i < tamanhoAtual; i++) {
            if (elementos[i] == elemento) {
                return i;
            }
        }
        return -1;
    }

    public int buscarBinaria(int elemento) {
        int inicio = 0;
        int fim = tamanhoAtual - 1;

        while (inicio <= fim) {
            int meio = inicio + (fim - inicio) / 2;
            if (elementos[meio] == elemento) {
                return meio;
            }
            if (elementos[meio] < elemento) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }
        return -1;
    }

    public int getElemento(int indice) {
        if (indice >= 0 && indice < tamanhoAtual) {
            return elementos[indice];
        }
        throw new IndexOutOfBoundsException("Indice fora dos limites");
    }

    public int getTamanhoAtual() {
        return tamanhoAtual;
    }

    public int[] getElementos() {
        // Retornar uma cópia para evitar modificações externas
        return Arrays.copyOf(elementos, tamanhoAtual);
    }
    
    // Obter o array interno para operações de ordenação (evita cópias desnecessárias)
    int[] getArrayInterno() {
        return elementos;
    }
    
    public void ordenar() {
        Ordenacao ordenacao = new Ordenacao();
        // Otimização: Ordenar diretamente o array interno, apenas até o tamanhoAtual
        if (tamanhoAtual > 0) {
            int[] arrayParaOrdenar = Arrays.copyOf(elementos, tamanhoAtual);
            ordenacao.quickSort(arrayParaOrdenar);
            System.arraycopy(arrayParaOrdenar, 0, elementos, 0, tamanhoAtual);
        }
    }
    
    // Método para limpar o vetor e liberar memória
    public void limpar() {
        Arrays.fill(elementos, 0, tamanhoAtual, 0); // Ajuda o GC
        tamanhoAtual = 0;
        
        // Se o array for muito grande, redimensioná-lo para economizar memória
        if (elementos.length > 100) {
            elementos = new int[20]; // Tamanho inicial razoável
        }
    }
    
    // Método para otimizar a capacidade do vetor
    public void otimizarCapacidade() {
        if (tamanhoAtual < elementos.length) {
            elementos = Arrays.copyOf(elementos, tamanhoAtual);
        }
    }
}