package Lesson5;

import static java.util.Arrays.fill;

class Treads {

    private static final int size = 10000000;
    private static final int HALF = size / 2;
    private static float[] arr = new float[size];
    private static float[] a1 = new float[HALF];
    private static float[] a2 = new float[HALF];

    private static void arrCalc(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//            System.out.println(arr[i]);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        fill(arr, 1);
        method1();
        method2();
    }

    private static void method1() {
        long a = System.currentTimeMillis();
        arrCalc(arr);
        System.out.println("Время выполнения метод 1: " + (System.currentTimeMillis() - a) + " миллисекунд");
    }

    private static void method2() throws InterruptedException {
        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        Thread tr1 = new Thread(new MyRunnableClass1());
        tr1.start();
        Thread tr2 = new Thread(new MyRunnableClass2());
        tr2.start();
        while (tr1.isAlive() || tr2.isAlive()) Thread.sleep(1);
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);

        System.out.println("Время выполнения метод 2: " + (System.currentTimeMillis() - a) + " миллисекунд");

    }

    static class MyRunnableClass1 implements Runnable {
        @Override
        public void run() {
            long a = System.currentTimeMillis();
            arrCalc(a1);
            System.out.println("Method2 Thread1 finished");
            System.out.println("Время выполнения Thread1: " + (System.currentTimeMillis() - a) + " миллисекунд");

        }
    }

    static class MyRunnableClass2 implements Runnable {
        @Override
        public void run() {
            long a = System.currentTimeMillis();
            arrCalc(a2);
            System.out.println("Method2 Thread2 finished");
            System.out.println("Время выполнения Thread2: " + (System.currentTimeMillis() - a) + " миллисекунд");
        }
    }
}
