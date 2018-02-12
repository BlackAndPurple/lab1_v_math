package labwork;

/**
 * Created by Таня on 08.10.2017.
 */
public class Gauss {

    private int size;
    private double[][] matrix;
    private double A;

    public double getA() {
        return det(matrix);
    }

    public Gauss(int size, double[][] matrix) {
        this.size = size;
        this.matrix = matrix;

        //A = det(this.matrix);

    }

    private double[][] GetMinor(double[][] matrix, int row, int column) {
        int minorLength = matrix.length - 1;
        double[][] minor = new double[minorLength][minorLength];
        int dI = 0;//эти переменные для того, чтобы "пропускать" ненужные нам строку и столбец
        int dJ = 0;
        for (int i = 0; i <= minorLength; i++) {
            dJ = 0;
            for (int j = 0; j <= minorLength; j++) {
                if (i == row) {
                    dI = 1;
                } else {
                    if (j == column) {
                        dJ = 1;
                    } else {
                        minor[i - dI][j - dJ] = matrix[i][j];
                    }
                }
            }
        }

        return minor;

    }


    public double det(double[][] matrix) {
        double Det = 0.0;
        if (matrix.length == 2) {
            Det = matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
        } else {
            int koeff = 1;
            for (int i = 0; i < matrix.length; i++) {
                if (i % 2 == 1) {  //я решил не возводить в степень, а просто поставить условие - это быстрее. Т.к. я раскладываю всегда по первой (читай - "нулевой") строке, то фактически я проверяю на четность значение i+0.
                    koeff = -1;
                } else {
                    koeff = 1;
                }
                ;
                //собственно разложение:
                Det += koeff * matrix[0][i] * this.det(this.GetMinor(matrix, 0, i));
            }
        }

        //возвращаем ответ
        return Det;
    }

    private double getMinor2(double a, double b, double c , double d){
        return (a*d-b*c);
    }

    private int rang(){
        boolean f = false;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size + 1; j++)
                if (matrix[i][j] != 0)
                    f = true;
        if (!f)
            return 0;
        return 1;
    }

    public boolean checkIfHasSolutions() {
        boolean f = true;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++ ){
                if (matrix[i][j] != 0)
                    f = false;
            }
            if (f)
                if (matrix[i][size] != 0)
                    return false;
            f = true;
        }
        return true;
    }

    public double[] getSolutions(){
        double[] sol = new double[size];
        double b = 0;
        for (int i = size - 1; i >= 0; i--){
            if (i == size - 1)
                if ( matrix[i][size - 1] != 0)
                     sol[i] = matrix[i][size] / matrix[i][size - 1];
                 else sol[i] = 0;
            else {
                for (int j = 0; j < size; j++) {
                    if (matrix[i][j] != 0) {
                        for (int k = j + 1; k < size; k++)
                           b += (matrix[i][k]*sol[k]);
                        sol[i] = (matrix[i][size] - b) /  matrix[i][j];
                        //sol[i] = (matrix[i][size] - matrix[i][j + 1] * sol[i + 1]) / matrix[i][j];
                        break;
                    }
                    //b = 0;
                }
                b = 0;
            }
        }
        return sol;
    }

    public boolean infiniteSolutions(double[][] matrix){
        boolean flag = true;
        for (int i = 0; i < size ; i ++){
            for (int j = 0; j < size + 1; j++){
                if (matrix[i][j] != 0)
                    flag = false;

            }
            if (flag)
                return flag;
            flag = true;
        }
        return flag;
    }
    public boolean manySolutions(){
        boolean flag = true;
        for (int j = 0; j < size + 1; j++)
            if (matrix[size - 1][j] != 0){
                flag = false;
                break;
            }
        return flag;
    }

    public double[] getError(double[] sol){
        double[] err = new double[size];
        for (int i = 0; i < size; i++)
            err[i] = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++)
                err[i] += matrix[i][j] * sol[j];
            err[i] -= matrix[i][size];
        }
        return err;
    }


    public double[][] getTriangular() {
        double number;


        for (int k = 0; k < size - 1; k++) {

            number = matrix[k][k];
            if ((k != 0)&&(number == 0)){

                continue;
                //return null;
            }
            for (int i = 0; i < size + 1; i++)
                matrix[k][i] /= number;

            for (int i = k + 1; i < size; i++) {
                number = matrix[i][k];
                for (int j = 0; j < size + 1; j++) {
                    matrix[i][j] -= matrix[k][j] * number;
                }
            }
            if (!checkIfHasSolutions()) {

                return null;
            }
        }
        return matrix;

    }
}
