package com.trabalho;

public class ArvoreAVL {
    No raiz;

    public ArvoreAVL() {
        raiz = null;
    }

    int altura(No n) {
        if (n == null) {
            return 0;
        }
        return n.altura;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    No rotacaoDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    No rotacaoEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    int getBalanceamento(No n) {
        if (n == null) {
            return 0;
        }
        return altura(n.esquerda) - altura(n.direita);
    }

    public void inserir(int valor) {
        raiz = inserir(raiz, valor);
    }

    private No inserir(No no, int valor) {
        if (no == null) {
            return (new No(valor));
        }

        if (valor < no.valor) {
            no.esquerda = inserir(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = inserir(no.direita, valor);
        } else {
            return no;
        }

        no.altura = 1 + max(altura(no.esquerda), altura(no.direita));

        int balanceamento = getBalanceamento(no);

        if (balanceamento > 1 && valor < no.esquerda.valor) {
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && valor > no.direita.valor) {
            return rotacaoEsquerda(no);
        }

        if (balanceamento > 1 && valor > no.esquerda.valor) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && valor < no.direita.valor) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public boolean buscar(int valor) {
        return buscar(raiz, valor);
    }

    private boolean buscar(No no, int valor) {
        if (no == null) {
            return false;
        }
        if (no.valor == valor) {
            return true;
        }
        if (valor < no.valor) {
            return buscar(no.esquerda, valor);
        } else {
            return buscar(no.direita, valor);
        }
    }

    public void imprimirEmOrdem() {
        System.out.print("In-Ordem (AVL): ");
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
        System.out.print("Pré-Ordem (AVL): ");
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
        System.out.print("Pós-Ordem (AVL): ");
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
        raiz = remover(raiz, valor);
    }

    private No remover(No no, int valor) {
        if (no == null) {
            return no;
        }

        if (valor < no.valor) {
            no.esquerda = remover(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = remover(no.direita, valor);
        } else {
            // Nó com no máximo um filho
            if ((no.esquerda == null) || (no.direita == null)) {
                No temp = null;
                if (temp == no.esquerda) {
                    temp = no.direita;
                } else {
                    temp = no.esquerda;
                }

                // Sem filho
                if (temp == null) {
                    no = null;
                } else { // Um filho
                    no = temp;
                }
            } else {
                // Nó com dois filhos: Obtém o sucessor in-ordem (menor na subárvore direita)
                No temp = encontrarMenorValorNo(no.direita);
                no.valor = temp.valor;
                no.direita = remover(no.direita, temp.valor);
            }
        }

        if (no == null) {
            return no;
        }

        no.altura = max(altura(no.esquerda), altura(no.direita)) + 1;

        int balanceamento = getBalanceamento(no);

        // Casos de desbalanceamento e rotações
        // Rotação à direita simples
        if (balanceamento > 1 && getBalanceamento(no.esquerda) >= 0) {
            return rotacaoDireita(no);
        }

        // Rotação à esquerda-direita
        if (balanceamento > 1 && getBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Rotação à esquerda simples
        if (balanceamento < -1 && getBalanceamento(no.direita) <= 0) {
            return rotacaoEsquerda(no);
        }

        // Rotação à direita-esquerda
        if (balanceamento < -1 && getBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private No encontrarMenorValorNo(No no) {
        No atual = no;
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }
        return atual;
    }
}
