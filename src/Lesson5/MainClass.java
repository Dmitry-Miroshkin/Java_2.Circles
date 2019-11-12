package Lesson5;

import static java.util.Arrays.fill;

public class MainClass {

    private static final int size = 100;
    private static final int HALF = size / 2;
    private static float[] arr = new float[size];
    private static float[] a1 = new float[HALF];
    private static float[] a2 = new float[HALF];


    private static void arrCalc(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            System.out.println(arr[i]);
        }
    }

    public static void main(String[] args) {
        fill(arr, 1);
        method1();
        method2();
    }

    private static void method1() {
        long a = System.currentTimeMillis();
        arrCalc(arr);
        System.out.println("Время выполнения метод 1: " + (System.currentTimeMillis() - a) + " миллисекунд");
    }

    private static void method2() {
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        System.out.println("время разделения массива:" + (System.currentTimeMillis() - a));
        new Thread(new MyRunnableClass1()).start();
        new Thread(new MyRunnableClass2()).start();
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);
        System.out.println("Время выполнения метод 2: " + (System.currentTimeMillis() - a) + " миллисекунд");

    }

    static class MyRunnableClass1 implements Runnable {
        @Override
        public void run() {
            arrCalc(MainClass.a1);
        }
    }

    static class MyRunnableClass2 implements Runnable {
        @Override
        public void run() {
            arrCalc(MainClass.a2);
        }
    }
}
