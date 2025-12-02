# Análise de Desempenho de Estruturas de Dados em Java

## Objetivo do Projeto

Este trabalho tem como objetivo principal comparar o desempenho de diferentes estruturas de dados em Java: **Vetores**, **Árvores Binárias de Busca (ABB)** e **Árvores AVL**. A análise foca nas operações de inserção e busca, avaliando como o desempenho varia com base no tamanho dos conjuntos de dados e nas diferentes ordens de inserção (ordenada, inversamente ordenada e aleatória). Além disso, para os vetores, são comparados quatro algoritmos de ordenação distintos: Bubble Sort (simples), Insertion Sort (simples), Merge Sort (avançado) e Quick Sort (avançado), juntamente com métodos de busca sequencial e binária.

## Requisitos de Implementação

Todas as estruturas de dados e algoritmos foram implementados do zero, sem o uso de bibliotecas ou funções prontas do sistema, conforme as especificações:

1.  **Estruturas de Dados:**
    *   **Vetor:** Implementa operações de inserção e busca.
    *   **Árvore Binária de Busca (ABB):** Implementa operações de inserção, busca, percursos (in-ordem, pré-ordem, pós-ordem) e remoção de nós.
    *   **Árvore AVL:** Uma árvore binária de busca auto-balanceada, com operações de inserção (incluindo as rotações para balanceamento), busca, percursos (in-ordem, pré-ordem, pós-ordem) e remoção de nós.

2.  **Algoritmos de Ordenação para Vetores:**
    *   **Bubble Sort:** Um algoritmo de ordenação simples.
    *   **Insertion Sort:** Outro algoritmo de ordenação simples.
    *   **Merge Sort:** Um algoritmo de ordenação avançado.
    *   **Quick Sort:** Outro algoritmo de ordenação avançado.

3.  **Métodos de Busca para Vetores:**
    *   **Busca Sequencial:** Funciona para qualquer vetor.
    *   **Busca Binária:** Aplicável apenas a vetores previamente ordenados.

## Metodologia de Testes

A análise de desempenho segue uma metodologia rigorosa para garantir resultados precisos e confiáveis:

1.  **Geração dos Conjuntos de Dados:**
    *   Três tamanhos de conjuntos de dados: 100, 1.000 e 10.000 elementos.
    *   Três ordens de inserção para cada tamanho: Ordenada (crescente), Inversamente Ordenada (decrescente) e Aleatória.

2.  **Medição do Tempo de Inserção:**
    *   O tempo necessário para inserir todos os elementos em cada estrutura é medido e registrado para cada um dos 9 cenários (3 tamanhos x 3 ordens).

3.  **Medição do Tempo de Busca:**
    *   Após a população de cada estrutura, são medidos os tempos de busca para os seguintes elementos: o primeiro inserido, o último inserido, o elemento na posição do meio, três elementos aleatórios que existem na estrutura e um elemento que não existe na estrutura.

4.  **Medição do Tempo de Ordenação (Apenas para Vetores):**
    *   Para os vetores populados, os algoritmos Bubble Sort, Insertion Sort, Merge Sort e Quick Sort são aplicados, e o tempo que cada um leva para ordenar os vetores é medido e registrado.

**Importante:** Cada teste (inserção, busca e ordenação) é executado no mínimo 5 vezes. O tempo registrado para análise é a média dessas 5 execuções para garantir maior precisão. Além disso, para os percursos e remoções em árvores, eles são exibidos apenas para conjuntos de dados de 100 elementos para evitar poluir a saída do console.

## Estrutura de Arquivos e Pastas

O projeto está organizado da seguinte forma:

```
.
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── trabalho/
│                   ├── ArvoreAVL.java
│                   ├── ArvoreBinariaBusca.java
│                   ├── Main.java
│                   ├── No.java
│                   ├── Ordenacao.java
│                   └── Vetor.java
└── README.md
```

### Descrição Detalhada dos Arquivos:

*   `src/main/java/com/trabalho/`: Contém todos os arquivos-fonte Java do projeto.
    *   `ArvoreAVL.java`: Implementa a lógica de uma Árvore AVL, incluindo os métodos para calcular altura, balanceamento, e as rotações (simples à direita, simples à esquerda, dupla à direita e dupla à esquerda) necessárias para manter a árvore balanceada durante as inserções. Também possui o método de busca, métodos para percursos (imprimirEmOrdem, imprimirPreOrdem, imprimirPosOrdem) e o método `remover` para exclusão de nós.
    *   `ArvoreBinariaBusca.java`: Implementa uma Árvore Binária de Busca padrão, com métodos recursivos para inserção de novos valores e busca de valores existentes. Agora também inclui métodos para percursos (imprimirEmOrdem, imprimirPreOrdem, imprimirPosOrdem) e o método `remover` para exclusão de nós.
    *   `Main.java`: A classe principal do projeto. É responsável por:
        *   Definir os tamanhos dos conjuntos de dados (100, 1000, 10000) e as ordens de inserção (Ordenada, Inversamente Ordenada, Aleatória).
        *   Gerar os dados para cada cenário.
        *   Instanciar e testar cada estrutura de dados (Vetor, ABB, AVL).
        *   Medir os tempos de inserção, busca e ordenação (para Vetores, incluindo Bubble Sort, Insertion Sort, Merge Sort e Quick Sort).
        *   Executar os métodos de percurso e remoção para ABB e AVL em conjuntos de dados de 100 elementos.
        *   Executar cada teste múltiplas vezes (5 vezes) e calcular a média dos tempos.
        *   Imprimir os resultados formatados no console.
    *   `No.java`: Uma classe auxiliar que define a estrutura de um nó para as árvores (ABB e AVL). Cada nó contém um `valor` inteiro, referências para os nós `esquerda` e `direita`, e um atributo `altura` (usado especificamente pela Árvore AVL para cálculo de balanceamento).
    *   `Ordenacao.java`: Contém as implementações dos algoritmos de ordenação:
        *   `bubbleSort(int[] arr)`: Implementação do algoritmo Bubble Sort.
        *   `insertionSort(int[] arr)`: Implementação do algoritmo Insertion Sort.
        *   `mergeSort(int[] arr)`: Implementação do algoritmo Merge Sort, incluindo os métodos auxiliares recursivos e o método `merge`.
        *   `quickSort(int[] arr)`: Implementação do algoritmo Quick Sort, incluindo os métodos auxiliares recursivos e o método `partition`.
    *   `Vetor.java`: Implementa uma estrutura de vetor dinâmico. Inclui métodos para:
        *   `inserir(int elemento)`: Adiciona um elemento ao vetor, redimensionando-o se necessário.
        *   `buscarSequencial(int elemento)`: Realiza uma busca linear no vetor.
        *   `buscarBinaria(int elemento)`: Realiza uma busca binária no vetor (pressupõe que o vetor esteja ordenado).
        *   `ordenar()`: Utiliza o Merge Sort da classe `Ordenacao` para ordenar os elementos do vetor.
        *   Métodos `getElemento`, `getTamanhoAtual` e `getElementos` para acesso aos dados.

## Como Compilar e Executar o Projeto

Para compilar e executar este projeto, siga os passos abaixo:

1.  **Certifique-se de ter o Java Development Kit (JDK) instalado.** Este projeto foi desenvolvido e testado com o JDK 21.

2.  **Abra o terminal** na raiz do diretório do projeto (onde o arquivo `README.md` está localizado).

3.  **Compile os arquivos Java** usando o `javac`. Este comando criará um diretório `out` e colocará os arquivos `.class` compilados lá. Certifique-se de usar o caminho completo para o `javac.exe` da sua instalação do JDK 21:

    ```bash
    "C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot\bin\javac.exe" src/main/java/com/trabalho/*.java -d out
    ```

4.  **Execute a aplicação** usando o `java`. O comando irá rodar a classe `Main`, que iniciará a análise de desempenho e exibirá os resultados detalhados no terminal. Certifique-se de usar o caminho completo para o `java.exe` da sua instalação do JDK 21:

    ```bash
    "C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot\bin\java.exe" -cp out com.trabalho.Main
    ```

Após a execução, você verá os tempos médios de inserção, busca e ordenação para cada estrutura de dados, tamanho de conjunto e ordem de inserção, além dos percursos e resultados de remoção das árvores para conjuntos de 100 elementos, permitindo a análise e a criação do relatório técnico.
