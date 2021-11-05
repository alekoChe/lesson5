public class AppLesson5 {

    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;
    float[] arr = new float[SIZE];

    public AppLesson5() {
        this.arr = arr;
    }

    public static void main(String[] args) {
        AppLesson5 app = new AppLesson5();
        firstMethod(app.arr);  // 1134, 1132, 1132
    }
    public static void firstMethod(float[] arr) {
        /**   2) Заполняют этот массив единицами.
              3) Засекают время выполнения: long a = System.currentTimeMillis().
              4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
         arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
         */
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        /** 5) Проверяется время окончания метода System.currentTimeMillis().
         6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a).
         */
        System.currentTimeMillis();
        System.out.println(System.currentTimeMillis() - a);
    }
}
