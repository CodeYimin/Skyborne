package util;

import java.io.File;
import java.util.Scanner;

public class FileUtils {
    public static int getLineWidth(File file) {
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (Exception e) {
            return -1;
        }

        int width = scanner.nextLine().length();
        scanner.close();
        return width;
    }

    public static int getLineElementCount(File file) {
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (Exception e) {
            return -1;
        }
        String line = scanner.nextLine();
        scanner.close();
        return line.split(" ").length;
    }

    public static int getLineCount(File file) {
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (Exception e) {
            return -1;
        }

        int count = 0;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            count++;
        }
        scanner.close();
        return count;
    }

    public static int[][] processIntGrid(File file) {
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (Exception e) {
            return null;
        }

        int[][] grid = new int[getLineCount(file)][getLineElementCount(file)];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = scanner.nextInt();
            }
        }

        scanner.close();
        return grid;
    }
}
