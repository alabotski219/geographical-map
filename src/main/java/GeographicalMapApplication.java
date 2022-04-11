import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GeographicalMapApplication {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("ERROR PARAMS COUNT!!!");
            System.exit(-1);
        }

        int n = 0, m = 0, k = 0;

        try {
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
            k = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: N, M and K parameters should be numbers");
            System.exit(-2);
        }

        if (n < 0 || m < 0 || k < 0) {
            System.out.println("ERROR: Parameters must be greater than or equal to zero");
            System.exit(-3);
        }

        int[][] result = generateMatrix(n, m, k);
        printMatrix(result, n, m);

        int z = 0;
        Queue<Coordinate> coordinateQueue = new ArrayDeque<>();
        Coordinate currentCoordinate = findNextCountryCoordinate(result, n, m);
        while (currentCoordinate != null) {
            coordinateQueue.add(currentCoordinate);
            while (!coordinateQueue.isEmpty()) {
                Coordinate coordinate = coordinateQueue.poll();
                int x = coordinate.getX();
                int y = coordinate.getY();
                int value = coordinate.getValue();
                result[x][y] = -1;
                if (x + 1 < n && result[x + 1][y] == value) {
                    coordinateQueue.add(new Coordinate(x + 1, y, value));
                }
                if (x - 1 >= 0 && result[x - 1][y] == value) {
                    coordinateQueue.add(new Coordinate(x - 1, y, value));
                }
                if (y + 1 < m && result[x][y + 1] == value) {
                    coordinateQueue.add(new Coordinate(x, y + 1, value));
                }
                if (y - 1 >= 0 && result[x][y - 1] == value) {
                    coordinateQueue.add(new Coordinate(x, y - 1, value));
                }
            }
            z++;
            currentCoordinate = findNextCountryCoordinate(result, n, m);
        }

        System.out.println("Country count: " + z);

    }

    private static void printMatrix(int[][] matrix, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("\t");
    }

    private static int[][] generateMatrix(int n, int m, int k) {
        Random rand = ThreadLocalRandom.current();
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = rand.nextInt(k + 1); // k+1 Т.к. в задании написано что k- количество возможных значений. Но в примере k=2, а принимаемые значения 0,1,2.
            }
        }
        return matrix;
    }

    private static Coordinate findNextCountryCoordinate(int[][] matrix, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] >= 0)
                    return new Coordinate(i, j, matrix[i][j]);
            }
        }
        return null;
    }

    private static class Coordinate {
        private final int x;
        private final int y;
        private final int value;

        public Coordinate(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getValue() {
            return value;
        }
    }
}
