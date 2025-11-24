package com.trabalho;

public class Ordenacao {

    public void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
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

    private void merge(int[] arr, int[] temp, int esquerda, int meio, int direita) {
        for (int i = esquerda; i <= direita; i++) {
            temp[i] = arr[i];
        }

        int i = esquerda;
        int j = meio + 1;
        int k = esquerda;

        while (i <= meio && j <= direita) {
            if (temp[i] <= temp[j]) {
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
            }
            k++;
        }

        while (i <= meio) {
            arr[k] = temp[i];
            i++;
            k++;
        }
    }
}
