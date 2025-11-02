import java.util.Random;

public class MatrixOperations {

    private static final int ROWS = 4;
    private static final int COLS = 4;

    public void run() {
        try {
            // Тип елементів short (C7 = 23 mod 7 = 2)
            short[][] matrixA = generateMatrix(ROWS, COLS);
            short[][] matrixB = generateMatrix(ROWS, COLS);

            System.out.println("Matrix A:");
            printMatrix(matrixA);
            System.out.println("Matrix B:");
            printMatrix(matrixB);

            // C5 = 23 mod 5 = 3 → C = A XOR B
            short[][] matrixC = xorMatrices(matrixA, matrixB);
            System.out.println("Matrix C = A XOR B:");
            printMatrix(matrixC);

            // C11 = 23 mod 11 = 1 → сума мінімальних елементів кожного рядка
            int sumMinRows = sumOfRowMinimums(matrixC);
            System.out.println("Sum of minimum elements in each row of matrix C: " + sumMinRows);

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    // Генерація матриці зі значеннями від 0 до 30
    private short[][] generateMatrix(int rows, int cols) {
        short[][] matrix = new short[rows][cols];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = (short) random.nextInt(31);
            }
        }
        return matrix;
    }

    // Побітове виключне "або" (XOR)
    private short[][] xorMatrices(short[][] A, short[][] B) {
        short[][] C = new short[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                C[i][j] = (short) (A[i][j] ^ B[i][j]);
            }
        }
        return C;
    }

    // Сума мінімальних елементів кожного рядка
    private int sumOfRowMinimums(short[][] matrix) {
        int sum = 0;
        for (int i = 0; i < ROWS; i++) {
            short min = matrix[i][0];
            for (int j = 1; j < COLS; j++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
            }
            sum += min;
        }
        return sum;
    }

    // Виведення матриці
    private void printMatrix(short[][] matrix) {
        for (short[] row : matrix) {
            for (short value : row) {
                System.out.printf("%4d", value);
            }
            System.out.println();
        }
        System.out.println();
    }
}
