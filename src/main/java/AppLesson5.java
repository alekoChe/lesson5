public class AppLesson5 {
    // попытка сделать пулл реквест
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;
    float[] arr = new float[SIZE];

    public AppLesson5() {
        this.arr = arr;
    }

    public static float calculator(float number, int i) {
        return (float)(number * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
    }

    public static void main(String[] args) {
        AppLesson5 app = new AppLesson5();
        firstMethod(app.arr);  // 1134, 1132, 1132
        secondMethod(app.arr);
        System.out.println("///////////////////////////////////////////////////////////");
        app.run();
    }
    public static void firstMethod(float[] arr) {

        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            arr[i] = calculator(arr[i], i);
        }

        System.currentTimeMillis();
        System.out.println("a= " + a);
        System.out.println("One thread time: " + (System.currentTimeMillis() - a) + " m.s.");
    }
    public static void secondMethod(float[] arr) {
        /** метод с разделением и последующей склейкой массива (по условию задания) */
         for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();
        float [] leftArr = new float[HALF];
        float [] rightArr = new float[HALF];
        System.arraycopy(arr, 0, leftArr, 0, leftArr.length);
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < leftArr.length; i++) {
//                    leftArr[i] = calculator(leftArr[i], i);
//                }
//            }
//        });
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < leftArr.length; i++) {
                leftArr[i] = calculator(leftArr[i], i);
            }
        });

        System.arraycopy(arr, HALF, rightArr, 0, HALF);
        Thread thread2 = new Thread(() -> {
            for (int i = 0 ; i < rightArr.length; i++) {
                rightArr[i] = calculator(rightArr[i], i + HALF);
            }
        });

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        float [] mergedArray = new float[SIZE];
        System.arraycopy(leftArr, 0, mergedArray, 0, leftArr.length);
        System.arraycopy(rightArr, 0, mergedArray, rightArr.length, rightArr.length);
        System.currentTimeMillis();
        System.out.println("Two thread time: " + (System.currentTimeMillis() - a) + " m.s.");
    }
    public  void run() {
        /** Метод одновременной обработки одного массива разными потоками (как на лекции) */
        AppLesson5 app = new AppLesson5();
        ThreadCalculator calc1 = new ThreadCalculator(app.arr, 0, SIZE / 4);
        ThreadCalculator calc2 = new ThreadCalculator(app.arr, SIZE / 4, SIZE / 2);
        ThreadCalculator calc3 = new ThreadCalculator(app.arr, SIZE / 2, (SIZE / 2 + SIZE / 4));
        ThreadCalculator calc4 = new ThreadCalculator(app.arr, (SIZE / 2 + SIZE / 4), SIZE);
        long a = System.currentTimeMillis();
        calc1.start();
        calc2.start();
        calc3.start();
        calc4.start();

        try {
            calc1.join();
            calc2.join();
            calc3.join();
            calc4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Four thread time: " + (System.currentTimeMillis() - a) + " m.s.");
    }
    public class ThreadCalculator extends Thread{
        int start;
        int finish;
        float[] arr;

        public ThreadCalculator(float[] arr, int start, int finish) {
            this.arr = arr;
            this.start = start;
            this.finish = finish;
        }

        @Override
        public void run() {
            for (int i = start; i < finish; i++) {
                arr[i] = AppLesson5.calculator(arr[i], i);
            }
        }
    }

}
