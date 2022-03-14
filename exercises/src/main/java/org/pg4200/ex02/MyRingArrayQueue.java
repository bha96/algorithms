package org.pg4200.ex02;

import org.pg4200.les02.queue.MyQueue;

public class MyRingArrayQueue<T> implements MyQueue<T> {
    private int size;
    private int maxSize;
    private T[] contents;
    private int head;
    private int tail;

    public MyRingArrayQueue(int maxSize){
        this.maxSize = maxSize;
        this.size = 0;
        this.head = -1;
        this.tail = -1;
        contents = (T[]) new Object[maxSize];
    }

    public MyRingArrayQueue(){
         this(10);
    }

    @Override
    public void enqueue(T value) {
        //Add to empty array
        if(size == 0){
            tail = 0;
            head = 0;
        //Array is full
        }else if(size == maxSize){
            resize();
        //Tail is at end of array
        }else if (tail == contents.length - 1){
            tail = 0;
        //Normal case
        }else{
            tail++;
        }
        size++;
        contents[tail] = value;
    }

    @Override
    public T dequeue() {
        // empty
        if(this.size == 0) {
            throw new RuntimeException("Queue is already empty");
        }
        T result = contents[head];
        // end of array
        if (head == maxSize - 1){
            if (size == 1){
                head = - 1;
                tail = - 1;
            }else{
                head = 0;

            }
        // Normal case
        }else{
            head++;
        }
        size--;
        return result;

    }

    @Override
    public T peek() {
        return contents[head];
    }

    @Override
    public int size() {
        return size;
    }

    public int getMaxSize(){
        return maxSize;
    }
    private void resize(){
        int newSize = this.maxSize * 2;
        T[] newArray = (T[]) new Object[newSize];

        // Head is 0 and array is full
        if(head < tail){
            for(int i = 0; i < maxSize; i++){
                newArray[i] = contents[i];
            }
            tail++;
        // We have looped around
        }else{
            for (int i = head; i < contents.length; i++){
                newArray[i - head] = contents[i];
            }
            for (int i = 0; i <= tail; i++){
                newArray[contents.length - head + i] = contents[i];
            }
            tail = size;
            head = 0;
        }
        this.maxSize = newSize;
        this.contents = newArray;
    }
}
