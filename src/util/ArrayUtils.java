package util;

import java.util.ArrayList;

public class ArrayUtils {
    public static <T> T getRandom(T[] array) {
        return array[(int) (Math.random() * array.length)];
    }

    public static <T> ArrayList<T> copyOf(ArrayList<T> list) {
        ArrayList<T> newList = new ArrayList<>();
        for (T item : list) {
            newList.add(item);
        }
        return newList;
    }
}
