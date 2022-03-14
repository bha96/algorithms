package org.pg4200.ex04;

public class FibonacciImplMemoized implements Fibonacci {

    private int[] alreadyComputedValues = new int[50];;

    @Override
    public int compute(int n) throws IllegalArgumentException {

        if (n < 0){
            throw new IllegalArgumentException();
        }
        if (n >= alreadyComputedValues.length){
            return compute(n-2) + compute(n-1);
        }
        if (n == 0){
            return 0;
        }else if(n == 1){
            return 1;
        }

        if (alreadyComputedValues[n] == 0){
            alreadyComputedValues[n] = compute(n-2) + compute(n-1);
        }
        return alreadyComputedValues[n];
    }
}
