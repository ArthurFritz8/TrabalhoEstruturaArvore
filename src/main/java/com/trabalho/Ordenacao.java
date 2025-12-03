package com.trabalho;

import java.io.*;
import java.util.*;

public class Ordenacao {

    public void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean trocou = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    trocou = true;
                }
            }
            // Se nenhuma troca foi feita, o array já está ordenado
            if (!trocou) break;
        }
    }

    public void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    public void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        mergeSort(arr, new int[arr.length], 0, arr.length - 1);
    }

    private void mergeSort(int[] arr, int[] temp, int esquerda, int direita) {
        if (esquerda < direita) {
            int meio = esquerda + (direita - esquerda) / 2;
            mergeSort(arr, temp, esquerda, meio);
            mergeSort(arr, temp, meio + 1, direita);
            merge(arr, temp, esquerda, meio, direita);
        }
    }

    // QuickSort otimizado - versão iterativa para reduzir o uso da pilha de recursão
    public void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSortIterativo(arr, 0, arr.length - 1);
    }

    private void quickSortIterativo(int[] arr, int low, int high) {
        // Criar uma pilha auxiliar para armazenar os limites
        int[] stack = new int[high - low + 1];
        
        // Inicializar o topo da pilha
        int top = -1;
        
        // Empilhar os limites iniciais
        stack[++top] = low;
        stack[++top] = high;
        
        // Continuar até que a pilha esteja vazia
        while (top >= 0) {
            // Desempilhar os limites
            high = stack[top--];
            low = stack[top--];
            
            // Particionar o array
            int pivot = partition(arr, low, high);
            
            // Se há elementos à esquerda do pivô, empilhar os limites
            if (pivot - 1 > low) {
                stack[++top] = low;
                stack[++top] = pivot - 1;
            }
            
            // Se há elementos à direita do pivô, empilhar os limites
            if (pivot + 1 < high) {
                stack[++top] = pivot + 1;
                stack[++top] = high;
            }
        }
    }

    // Versão recursiva do QuickSort (mantida para compatibilidade)
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Otimização: usar insertion sort para arrays pequenos
            if (high - low < 10) {
                insertionSortRange(arr, low, high);
            } else {
                int pi = partition(arr, low, high);
                quickSort(arr, low, pi - 1);
                quickSort(arr, pi + 1, high);
            }
        }
    }

    // Insertion sort para um intervalo específico do array
    private void insertionSortRange(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;
            
            while (j >= low && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private int partition(int[] arr, int low, int high) {
        // Otimização: escolher um pivô melhor (mediana de três)
        int mid = low + (high - low) / 2;
        // Ordenar low, mid e high
        if (arr[mid] < arr[low])
            swap(arr, mid, low);
        if (arr[high] < arr[low])
            swap(arr, high, low);
        if (arr[high] < arr[mid])
            swap(arr, high, mid);
            
        // Colocar o pivô (mediana) no penúltimo elemento
        swap(arr, mid, high - 1);
        int pivot = arr[high - 1];
        
        int i = low;
        for (int j = low; j < high - 1; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high - 1);
        return i;
    }
    
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void merge(int[] arr, int[] temp, int esquerda, int meio, int direita) {
        // Otimização: verificar se já está ordenado
        if (arr[meio] <= arr[meio + 1]) {
            return; // Já está ordenado
        }
        
        // Copiar apenas a parte necessária para o array temporário
        for (int i = esquerda; i <= meio; i++) {
            temp[i] = arr[i];
        }

        int i = esquerda;
        int j = meio + 1;
        int k = esquerda;

        while (i <= meio && j <= direita) {
            if (temp[i] <= arr[j]) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = arr[j++];
            }
        }

        // Copiar o restante do primeiro subarray
        while (i <= meio) {
            arr[k++] = temp[i++];
        }
        // Não é necessário copiar o segundo subarray, pois já está no lugar correto
    }
    
    // Ordenação externa para grandes conjuntos de dados
    public void externalSort(String inputFile, String outputFile, int bufferSize) throws IOException {
        // Fase 1: Dividir o arquivo em partes menores e ordená-las
        List<File> tempFiles = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        int count = 0;
        List<Integer> buffer = new ArrayList<>(bufferSize);
        
        while ((line = reader.readLine()) != null) {
            buffer.add(Integer.parseInt(line.trim()));
            count++;
            
            if (count == bufferSize) {
                File tempFile = sortAndSaveTempFile(buffer);
                tempFiles.add(tempFile);
                buffer.clear();
                count = 0;
            }
        }
        
        if (!buffer.isEmpty()) {
            File tempFile = sortAndSaveTempFile(buffer);
            tempFiles.add(tempFile);
        }
        
        reader.close();
        
        // Fase 2: Mesclar os arquivos temporários
        mergeFiles(tempFiles, outputFile);
        
        // Limpar arquivos temporários
        for (File file : tempFiles) {
            file.delete();
        }
    }
    
    private File sortAndSaveTempFile(List<Integer> buffer) throws IOException {
        // Converter List para array e ordenar
        int[] array = new int[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            array[i] = buffer.get(i);
        }
        
        // Usar o quickSort otimizado
        quickSort(array);
        
        // Salvar em arquivo temporário
        File tempFile = File.createTempFile("sort", ".tmp");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        
        for (int value : array) {
            writer.write(Integer.toString(value));
            writer.newLine();
        }
        
        writer.close();
        return tempFile;
    }
    
    private void mergeFiles(List<File> files, String outputFile) throws IOException {
        PriorityQueue<BufferedReaderWrapper> queue = new PriorityQueue<>();
        
        // Abrir todos os arquivos e adicionar o primeiro valor de cada um à fila
        for (File file : files) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            
            if (line != null) {
                int value = Integer.parseInt(line.trim());
                queue.add(new BufferedReaderWrapper(reader, value));
            } else {
                reader.close();
            }
        }
        
        // Mesclar os arquivos
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        
        while (!queue.isEmpty()) {
            BufferedReaderWrapper wrapper = queue.poll();
            writer.write(Integer.toString(wrapper.getValue()));
            writer.newLine();
            
            String line = wrapper.getReader().readLine();
            if (line != null) {
                int value = Integer.parseInt(line.trim());
                wrapper.setValue(value);
                queue.add(wrapper);
            } else {
                wrapper.getReader().close();
            }
        }
        
        writer.close();
    }
    
    // Classe auxiliar para a fila de prioridade
    private static class BufferedReaderWrapper implements Comparable<BufferedReaderWrapper> {
        private final BufferedReader reader;
        private int value;
        
        public BufferedReaderWrapper(BufferedReader reader, int value) {
            this.reader = reader;
            this.value = value;
        }
        
        public BufferedReader getReader() {
            return reader;
        }
        
        public int getValue() {
            return value;
        }
        
        public void setValue(int value) {
            this.value = value;
        }
        
        @Override
        public int compareTo(BufferedReaderWrapper other) {
            return Integer.compare(this.value, other.value);
        }
    }
}