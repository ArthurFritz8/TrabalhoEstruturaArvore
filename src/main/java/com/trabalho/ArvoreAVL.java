package com.trabalho;

import java.util.Stack;

public class ArvoreAVL {
    No raiz;
    private int tamanho; // Para rastrear o número de nós

    public ArvoreAVL() {
        raiz = null;
        tamanho = 0;
    }

    int altura(No n) {
        return (n == null) ? 0 : n.altura;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    No rotacaoDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;

        // Rotação
        x.direita = y;
        y.esquerda = T2;

        // Atualizar alturas
        y.altura = max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    No rotacaoEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;

        // Rotação
        y.esquerda = x;
        x.direita = T2;

        // Atualizar alturas
        x.altura = max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    int getBalanceamento(No n) {
        return (n == null) ? 0 : altura(n.esquerda) - altura(n.direita);
    }

    public void inserir(int valor) {
        raiz = inserir(raiz, valor);
    }

    private No inserir(No no, int valor) {
        // Inserção normal BST
        if (no == null) {
            tamanho++;
            return (new No(valor));
        }

        if (valor < no.valor) {
            no.esquerda = inserir(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = inserir(no.direita, valor);
        } else {
            // Valor duplicado não é inserido
            return no;
        }

        // Atualizar altura do nó atual
        no.altura = 1 + max(altura(no.esquerda), altura(no.direita));

        // Verificar balanceamento e realizar rotações se necessário
        int balanceamento = getBalanceamento(no);

        // Casos de desbalanceamento
        // Caso Esquerda-Esquerda
        if (balanceamento > 1 && valor < no.esquerda.valor) {
            return rotacaoDireita(no);
        }

        // Caso Direita-Direita
        if (balanceamento < -1 && valor > no.direita.valor) {
            return rotacaoEsquerda(no);
        }

        // Caso Esquerda-Direita
        if (balanceamento > 1 && valor > no.esquerda.valor) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Caso Direita-Esquerda
        if (balanceamento < -1 && valor < no.direita.valor) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    // Busca iterativa para economizar memória de pilha
    public boolean buscar(int valor) {
        No atual = raiz;
        
        while (atual != null) {
            if (atual.valor == valor) {
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
    private boolean buscarRecursivo(No no, int valor) {
        if (no == null) {
            return false;
        }
        if (no.valor == valor) {
            return true;
        }
        if (valor < no.valor) {
            return buscarRecursivo(no.esquerda, valor);
        } else {
            return buscarRecursivo(no.direita, valor);
        }
    }

    // Impressão em ordem iterativa para economizar memória de pilha
    public void imprimirEmOrdem() {
        System.out.print("In-Ordem (AVL): ");
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
        System.out.print("Pré-Ordem (AVL): ");
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

    // Impressão pós-ordem iterativa (mais complexa)
    public void imprimirPosOrdem() {
        System.out.print("Pós-Ordem (AVL): ");
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
            // Nó a ser removido encontrado
            
            // Nó com no máximo um filho
            if ((no.esquerda == null) || (no.direita == null)) {
                No temp = (no.esquerda != null) ? no.esquerda : no.direita;

                // Sem filho
                if (temp == null) {
                    temp = no;
                    no = null;
                } else { // Um filho
                    no = temp; // Copiar o conteúdo do filho
                }
                
                tamanho--; // Decrementar o tamanho
            } else {
                // Nó com dois filhos: Obtém o sucessor in-ordem (menor na subárvore direita)
                No temp = encontrarMenorValorNo(no.direita);
                
                // Copiar o valor do sucessor para este nó
                no.valor = temp.valor;
                
                // Remover o sucessor
                no.direita = remover(no.direita, temp.valor);
            }
        }

        // Se a árvore tinha apenas um nó, retornar
        if (no == null) {
            return no;
        }

        // Atualizar altura
        no.altura = max(altura(no.esquerda), altura(no.direita)) + 1;

        // Verificar balanceamento
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
        
        // Loop para encontrar o nó mais à esquerda
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }
        
        return atual;
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
    
    // Método para verificar se a árvore está balanceada
    public boolean estaBalanceada() {
        return verificarBalanceamento(raiz);
    }
    
    private boolean verificarBalanceamento(No no) {
        if (no == null) {
            return true;
        }
        
        int balanceamento = getBalanceamento(no);
        
        // Se o fator de balanceamento não estiver entre -1 e 1
        if (balanceamento < -1 || balanceamento > 1) {
            return false;
        }
        
        // Verificar recursivamente as subárvores
        return verificarBalanceamento(no.esquerda) && verificarBalanceamento(no.direita);
    }
}