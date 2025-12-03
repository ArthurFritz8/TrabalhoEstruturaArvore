package com.trabalho;

import java.util.Stack;

public class ArvoreBinariaBusca {
    No raiz;
    private int tamanho; // Para rastrear o número de nós

    public ArvoreBinariaBusca() {
        raiz = null;
        tamanho = 0;
    }

    // Método de inserção iterativo para economizar memória de pilha
    public void inserir(int valor) {
        if (raiz == null) {
            raiz = new No(valor);
            tamanho++;
            return;
        }

        No atual = raiz;
        No pai = null;
        
        while (atual != null) {
            pai = atual;
            
            if (valor < atual.valor) {
                atual = atual.esquerda;
            } else if (valor > atual.valor) {
                atual = atual.direita;
            } else {
                // Valor já existe, não inserir duplicatas
                return;
            }
        }
        
        // Inserir o novo nó
        No novoNo = new No(valor);
        if (valor < pai.valor) {
            pai.esquerda = novoNo;
        } else {
            pai.direita = novoNo;
        }
        
        tamanho++;
    }

    // Método recursivo mantido para compatibilidade
    private No inserirRecursivo(No atual, int valor) {
        if (atual == null) {
            tamanho++;
            return new No(valor);
        }

        if (valor < atual.valor) {
            atual.esquerda = inserirRecursivo(atual.esquerda, valor);
        } else if (valor > atual.valor) {
            atual.direita = inserirRecursivo(atual.direita, valor);
        }
        return atual;
    }

    // Método de busca iterativo para economizar memória de pilha
    public boolean buscar(int valor) {
        No atual = raiz;
        
        while (atual != null) {
            if (valor == atual.valor) {
                return true;
            }
            
            if (valor < atual.valor) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }
        
        return false;
    }

    // Método recursivo mantido para compatibilidade
    private boolean buscarRecursivo(No atual, int valor) {
        if (atual == null) {
            return false;
        }
        if (valor == atual.valor) {
            return true;
        }
        return valor < atual.valor ? buscarRecursivo(atual.esquerda, valor) : buscarRecursivo(atual.direita, valor);
    }

    // Impressão em ordem iterativa para economizar memória de pilha
    public void imprimirEmOrdem() {
        System.out.print("In-Ordem: ");
        if (raiz == null) {
            System.out.println();
            return;
        }
        
        Stack<No> pilha = new Stack<>();
        No atual = raiz;
        
        while (atual != null || !pilha.isEmpty()) {
            // Alcançar o nó mais à esquerda
            while (atual != null) {
                pilha.push(atual);
                atual = atual.esquerda;
            }
            
            // Visitar o nó atual
            atual = pilha.pop();
            System.out.print(atual.valor + " ");
            
            // Ir para o nó à direita
            atual = atual.direita;
        }
        
        System.out.println();
    }

    // Método recursivo mantido para compatibilidade
    private void imprimirEmOrdemRecursivo(No no) {
        if (no != null) {
            imprimirEmOrdemRecursivo(no.esquerda);
            System.out.print(no.valor + " ");
            imprimirEmOrdemRecursivo(no.direita);
        }
    }

    // Impressão pré-ordem iterativa
    public void imprimirPreOrdem() {
        System.out.print("Pré-Ordem: ");
        if (raiz == null) {
            System.out.println();
            return;
        }
        
        Stack<No> pilha = new Stack<>();
        pilha.push(raiz);
        
        while (!pilha.isEmpty()) {
            No no = pilha.pop();
            System.out.print(no.valor + " ");
            
            // Primeiro empilha direita, depois esquerda para que esquerda seja processada primeiro
            if (no.direita != null) {
                pilha.push(no.direita);
            }
            if (no.esquerda != null) {
                pilha.push(no.esquerda);
            }
        }
        
        System.out.println();
    }

    // Método recursivo mantido para compatibilidade
    private void imprimirPreOrdemRecursivo(No no) {
        if (no != null) {
            System.out.print(no.valor + " ");
            imprimirPreOrdemRecursivo(no.esquerda);
            imprimirPreOrdemRecursivo(no.direita);
        }
    }

    // Impressão pós-ordem iterativa
    public void imprimirPosOrdem() {
        System.out.print("Pós-Ordem: ");
        if (raiz == null) {
            System.out.println();
            return;
        }
        
        Stack<No> pilha1 = new Stack<>();
        Stack<No> pilha2 = new Stack<>();
        
        pilha1.push(raiz);
        
        // Preencher pilha2 na ordem inversa da pós-ordem
        while (!pilha1.isEmpty()) {
            No no = pilha1.pop();
            pilha2.push(no);
            
            if (no.esquerda != null) {
                pilha1.push(no.esquerda);
            }
            if (no.direita != null) {
                pilha1.push(no.direita);
            }
        }
        
        // Imprimir pilha2
        while (!pilha2.isEmpty()) {
            System.out.print(pilha2.pop().valor + " ");
        }
        
        System.out.println();
    }

    // Método recursivo mantido para compatibilidade
    private void imprimirPosOrdemRecursivo(No no) {
        if (no != null) {
            imprimirPosOrdemRecursivo(no.esquerda);
            imprimirPosOrdemRecursivo(no.direita);
            System.out.print(no.valor + " ");
        }
    }

    // Método de remoção iterativo para economizar memória de pilha
    public void remover(int valor) {
        if (raiz == null) {
            return;
        }
        
        // Caso especial: remover a raiz
        if (raiz.valor == valor) {
            if (raiz.esquerda == null) {
                raiz = raiz.direita;
                tamanho--;
                return;
            }
            if (raiz.direita == null) {
                raiz = raiz.esquerda;
                tamanho--;
                return;
            }
            
            // Raiz tem dois filhos
            // Encontrar o sucessor (menor valor na subárvore direita)
            No sucessor = raiz.direita;
            No sucessorPai = raiz;
            
            while (sucessor.esquerda != null) {
                sucessorPai = sucessor;
                sucessor = sucessor.esquerda;
            }
            
            // Substituir o valor da raiz pelo valor do sucessor
            raiz.valor = sucessor.valor;
            
            // Remover o sucessor
            if (sucessorPai == raiz) {
                sucessorPai.direita = sucessor.direita;
            } else {
                sucessorPai.esquerda = sucessor.direita;
            }
            
            tamanho--;
            return;
        }
        
        // Encontrar o nó a ser removido e seu pai
        No atual = raiz;
        No pai = null;
        boolean ehFilhoEsquerdo = false;
        
        while (atual != null && atual.valor != valor) {
            pai = atual;
            
            if (valor < atual.valor) {
                atual = atual.esquerda;
                ehFilhoEsquerdo = true;
            } else {
                atual = atual.direita;
                ehFilhoEsquerdo = false;
            }
        }
        
        // Se o nó não foi encontrado
        if (atual == null) {
            return;
        }
        
        // Caso 1: Nó folha
        if (atual.esquerda == null && atual.direita == null) {
            if (ehFilhoEsquerdo) {
                pai.esquerda = null;
            } else {
                pai.direita = null;
            }
            tamanho--;
            return;
        }
        
        // Caso 2: Nó com apenas um filho
        if (atual.direita == null) {
            if (ehFilhoEsquerdo) {
                pai.esquerda = atual.esquerda;
            } else {
                pai.direita = atual.esquerda;
            }
            tamanho--;
            return;
        }
        
        if (atual.esquerda == null) {
            if (ehFilhoEsquerdo) {
                pai.esquerda = atual.direita;
            } else {
                pai.direita = atual.direita;
            }
            tamanho--;
            return;
        }
        
        // Caso 3: Nó com dois filhos
        // Encontrar o sucessor (menor valor na subárvore direita)
        No sucessor = atual.direita;
        No sucessorPai = atual;
        
        while (sucessor.esquerda != null) {
            sucessorPai = sucessor;
            sucessor = sucessor.esquerda;
        }
        
        // Substituir o valor do nó atual pelo valor do sucessor
        atual.valor = sucessor.valor;
        
        // Remover o sucessor
        if (sucessorPai == atual) {
            sucessorPai.direita = sucessor.direita;
        } else {
            sucessorPai.esquerda = sucessor.direita;
        }
        
        tamanho--;
    }

    // Método recursivo mantido para compatibilidade
    private No removerRecursivo(No atual, int valor) {
        if (atual == null) {
            return null;
        }

        if (valor == atual.valor) {
            tamanho--;
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

    // Método iterativo para encontrar o menor valor
    private int encontrarMenorValor(No raiz) {
        No atual = raiz;
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }
        return atual.valor;
    }
    
    // Métodos adicionais para otimização de memória
    
    // Obter o tamanho da árvore em O(1)
    public int getTamanho() {
        return tamanho;
    }
    
    // Limpar a árvore e liberar memória
    public void limpar() {
        raiz = null;
        tamanho = 0;
        // Sugestão para o GC coletar os nós
        System.gc();
    }
    
    // Método para obter a altura da árvore
    public int getAltura() {
        return calcularAltura(raiz);
    }
    
    private int calcularAltura(No no) {
        if (no == null) {
            return 0;
        }
        return 1 + Math.max(calcularAltura(no.esquerda), calcularAltura(no.direita));
    }
    
    // Método para verificar se a árvore está balanceada
    public boolean estaBalanceada() {
        return verificarBalanceamento(raiz) != -1;
    }
    
    private int verificarBalanceamento(No no) {
        if (no == null) {
            return 0;
        }
        
        int alturaEsquerda = verificarBalanceamento(no.esquerda);
        if (alturaEsquerda == -1) {
            return -1;
        }
        
        int alturaDireita = verificarBalanceamento(no.direita);
        if (alturaDireita == -1) {
            return -1;
        }
        
        if (Math.abs(alturaEsquerda - alturaDireita) > 1) {
            return -1;
        }
        
        return 1 + Math.max(alturaEsquerda, alturaDireita);
    }
}