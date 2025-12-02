package com.trabalho;

public class ArvoreBinariaBusca {
    No raiz;

    public ArvoreBinariaBusca() {
        raiz = null;
    }

    public void inserir(int valor) {
        raiz = inserirRecursivo(raiz, valor);
    }

    private No inserirRecursivo(No atual, int valor) {
        if (atual == null) {
            return new No(valor);
        }

        if (valor < atual.valor) {
            atual.esquerda = inserirRecursivo(atual.esquerda, valor);
        } else if (valor > atual.valor) {
            atual.direita = inserirRecursivo(atual.direita, valor);
        }
        return atual;
    }

    public boolean buscar(int valor) {
        return buscarRecursivo(raiz, valor);
    }

    private boolean buscarRecursivo(No atual, int valor) {
        if (atual == null) {
            return false;
        }
        if (valor == atual.valor) {
            return true;
        }
        return valor < atual.valor ? buscarRecursivo(atual.esquerda, valor) : buscarRecursivo(atual.direita, valor);
    }

    public void imprimirEmOrdem() {
        System.out.print("In-Ordem: ");
        imprimirEmOrdemRecursivo(raiz);
        System.out.println();
    }

    private void imprimirEmOrdemRecursivo(No no) {
        if (no != null) {
            imprimirEmOrdemRecursivo(no.esquerda);
            System.out.print(no.valor + " ");
            imprimirEmOrdemRecursivo(no.direita);
        }
    }

    public void imprimirPreOrdem() {
        System.out.print("Pré-Ordem: ");
        imprimirPreOrdemRecursivo(raiz);
        System.out.println();
    }

    private void imprimirPreOrdemRecursivo(No no) {
        if (no != null) {
            System.out.print(no.valor + " ");
            imprimirPreOrdemRecursivo(no.esquerda);
            imprimirPreOrdemRecursivo(no.direita);
        }
    }

    public void imprimirPosOrdem() {
        System.out.print("Pós-Ordem: ");
        imprimirPosOrdemRecursivo(raiz);
        System.out.println();
    }

    private void imprimirPosOrdemRecursivo(No no) {
        if (no != null) {
            imprimirPosOrdemRecursivo(no.esquerda);
            imprimirPosOrdemRecursivo(no.direita);
            System.out.print(no.valor + " ");
        }
    }

    public void remover(int valor) {
        raiz = removerRecursivo(raiz, valor);
    }

    private No removerRecursivo(No atual, int valor) {
        if (atual == null) {
            return null;
        }

        if (valor == atual.valor) {
            // Caso 1: Nó folha
            if (atual.esquerda == null && atual.direita == null) {
                return null;
            }
            // Caso 2: Nó com apenas um filho
            if (atual.direita == null) {
                return atual.esquerda;
            }
            if (atual.esquerda == null) {
                return atual.direita;
            }
            // Caso 3: Nó com dois filhos
            int menorValor = encontrarMenorValor(atual.direita);
            atual.valor = menorValor;
            atual.direita = removerRecursivo(atual.direita, menorValor);
            return atual;
        }
        if (valor < atual.valor) {
            atual.esquerda = removerRecursivo(atual.esquerda, valor);
            return atual;
        }
        atual.direita = removerRecursivo(atual.direita, valor);
        return atual;
    }

    private int encontrarMenorValor(No raiz) {
        return raiz.esquerda == null ? raiz.valor : encontrarMenorValor(raiz.esquerda);
    }
}
