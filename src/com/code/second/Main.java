package com.code.second;

class NumberThread extends Thread {
    private int[] numbers;
    private int startIndex;
    private int endIndex;
    private int sum;

    public NumberThread(int[] numbers, int startIndex, int endIndex) {
        this.numbers = numbers;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.sum = 0;
    }

    @Override
    public void run() {
        for (int i = startIndex; i <= endIndex; i++) {
            sum += numbers[i];
        }
        System.out.println(Thread.currentThread().getName() + ": " + sum);
    }

    public int getSum() {
        return sum;
    }
}

public class Main {
    public static void main(String[] args) {
        int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int startIndex = 0;
        int endIndex = 2;
        int totalSum = 0;

        NumberThread thread1 = new NumberThread(numbers, startIndex, endIndex);
        NumberThread thread2 = new NumberThread(numbers, startIndex + 3, endIndex + 3);
        NumberThread thread3 = new NumberThread(numbers, startIndex + 6, endIndex + 6);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        totalSum = thread1.getSum() + thread2.getSum() + thread3.getSum();
        System.out.println("Total Sum: " + totalSum);
    }
}
