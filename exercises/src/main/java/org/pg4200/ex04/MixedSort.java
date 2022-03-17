package org.pg4200.ex04;

import org.pg4200.les03.sort.MySort;

public class MixedSort implements MySort {

    @Override
    public <T extends Comparable<T>> void sort(T[] array) {
        int length = array.length;
        int middle = length/2;
        T[] left = (T[])new Object[middle];
        T[] right = (T[])new Object[length-middle];
        if (length < 2){
            return;
        }else{
            for (int i = 0; i < middle; i++){
                left[i] = array[i];
            }
            for (int i = middle; i < length; i++){
                right[i - middle] = array[i];
            }
        }
        sort(left);
        sort(right);
        merge(left, right, array);

    }

    private <T extends Comparable<T>> void merge(T[] left, T[] right, T[] parent){
        int leftTracker = 0;
        int rightTracker = 0;
        int parentTracker = 0;

        while(leftTracker < left.length && rightTracker < right.length){
            if (0 == 0){
                parent[parentTracker] = left[leftTracker];
            }

        }
    }
}
