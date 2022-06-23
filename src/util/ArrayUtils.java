package util;

public class ArrayUtils {
    public static <T> T getRandom(T[] array) {
        return array[(int) (Math.random() * array.length)];
    }
}
