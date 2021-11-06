public class AppLesson5 {

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
    }
    public static void firstMethod(float[] arr) {

        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            //arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            arr[i] = calculator(arr[i], i);
        }

        System.currentTimeMillis();
        System.out.println("a= " + a);
        System.out.println("One thread time: " + (System.currentTimeMillis() - a) + " m.s.");
    }
    public static void secondMethod(float[] arr) {

         for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();
        float [] leftArr = new float[HALF];
        float [] rightArr = new float[HALF];
        System.arraycopy(arr, 0, leftArr, 0, leftArr.length);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < leftArr.length; i++) {
                    leftArr[i] = calculator(leftArr[i], i);
                }
            }
        });
        thread1.start();

        System.arraycopy(arr, HALF, rightArr, 0, HALF);
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0 ; i < rightArr.length; i++) {
                    rightArr[i] = calculator(rightArr[i], i + HALF);
                }
            }
        });
        thread2.start();

        float [] mergedArray = new float[SIZE];
        System.arraycopy(leftArr, 0, mergedArray, 0, leftArr.length);
        System.arraycopy(rightArr, 0, mergedArray, rightArr.length, rightArr.length);
        System.currentTimeMillis();
        System.out.println("Two thread time: " + (System.currentTimeMillis() - a) + " m.s.");
    }
}
