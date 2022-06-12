package util;

import java.util.ArrayList;

public class ArrayListUtils {
    public static <T> void addAllUnique(ArrayList<T> list, ArrayList<T> other) {
        for (T t : other) {
            if (!list.contains(t)) {
                list.add(t);
            }
        }
    }
}
