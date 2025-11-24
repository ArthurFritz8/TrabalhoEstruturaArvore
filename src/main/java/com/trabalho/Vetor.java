package com.trabalho;

public class Vetor {
    private int[] elementos;
    private int tamanhoAtual;

    public Vetor(int capacidade) {
        elementos = new int[capacidade];
        tamanhoAtual = 0;
    }

    public void inserir(int elemento) {
        if (tamanhoAtual == elementos.length) {
            int[] novoVetor = new int[elementos.length * 2];
            for (int i = 0; i < elementos.length; i++) {
                novoVetor[i] = elementos[i];
            }
            elementos = novoVetor;
        }
        elementos[tamanhoAtual++] = elemento;
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
        return elementos;
    }
    public void ordenar() {
        Ordenacao ordenacao = new Ordenacao();
        int[] tempArray = new int[tamanhoAtual];
        System.arraycopy(elementos, 0, tempArray, 0, tamanhoAtual); 
        ordenacao.mergeSort(tempArray); 
        System.arraycopy(tempArray, 0, elementos, 0, tamanhoAtual); 
    }
}
