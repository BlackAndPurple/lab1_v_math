package labwork;

/**
 * Created by Таня on 08.10.2017.
 */
public class Matrix {

    private double[][] matrix;
    private int size;

    public Matrix(int size, double[][] matrix) {
        this.matrix = matrix;
        this.size = size;
    }

    private void showMatrix(){
        for (int i = 0; i < size; i++){

            for (int j = 0; j < size + 1; j++){
                System.out.format("%5.1f ", matrix[i][j] );
            }
            System.out.println();
        }
        System.out.println();
    }
}
