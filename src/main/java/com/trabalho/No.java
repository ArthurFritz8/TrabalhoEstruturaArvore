package com.trabalho;

public class No {
    int valor;
    No esquerda;
    No direita;
    int altura;
    
    // Campos adicionais para otimização (podem ser usados em implementações futuras)
    private int tamanhoSubarvore; // Para operações de seleção rápida
    
    public No(int valor) {
        this.valor = valor;
        this.esquerda = null;
        this.direita = null;
        this.altura = 1;
        this.tamanhoSubarvore = 1;
    }
    
    // Método para atualizar o tamanho da subárvore
    public void atualizarTamanhoSubarvore() {
        int tamanhoEsquerda = (esquerda == null) ? 0 : esquerda.tamanhoSubarvore;
        int tamanhoDireita = (direita == null) ? 0 : direita.tamanhoSubarvore;
        tamanhoSubarvore = 1 + tamanhoEsquerda + tamanhoDireita;
    }
    
    // Método para obter o tamanho da subárvore
    public int getTamanhoSubarvore() {
        return tamanhoSubarvore;
    }
    
    // Método para limpar referências e ajudar o GC
    public void limpar() {
        esquerda = null;
        direita = null;
    }
}