package labwork;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static void showMatrix(int size, double[][] matrix, String outputString) {
        if (matrix != null) {
            System.out.println(outputString);
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size + 1; j++)
                    matrix[i][j] += 0.0;

            for (int i = 0; i < size; i++) {

                for (int j = 0; j < size + 1; j++) {
                    System.out.format("%6.2f ", matrix[i][j]);

                    if (j == size - 1)
                        System.out.print("|");
                }
                System.out.println();
            }
            System.out.println();
        }
        }

    static void showSolutions(int size, double[] sol, String str){


        System.out.println(str);
        for (int i = 0; i < size; i ++){
            System.out.format("x[%d] = %6.3f\n", i, sol[i]);
        }
    }

    static void  showError(int size, double[] err){
        System.out.println("\nErrors: ");
        for (int i = 0; i < size; i++)
            System.out.println("[" + i + "] = " + err[i]);
    }

    static int inputSize(){
        int a = 0;
        int size = 0;
        Scanner in = new Scanner(System.in);
        do  {

            System.out.print("How to input system size?\n 1 - Keyboard input\n 2 - From file\nChoose option: ");
            a = in.nextInt();

        } while ((a < 1) | (a > 2));

        switch (a){
            case 1: System.out.print("Input system size: ");
                size = in.nextInt();
                break;
            case 2: System.out.print("Input file name:  ");
                Scanner inn = new Scanner(System.in);
                String filename = inn.nextLine();
                size = sizeFromFile(filename);
                break;
        }

        return size;
    }

    static int sizeFromFile(String filename){
        String size = "";
        try{
            FileInputStream fstream = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            if ((size = br.readLine()) != null){
                return Integer.parseInt(size);
            } else throw new Exception();
        }catch (Exception e){
            System.out.println("Invalid value");
        }
        return 0;
    }

    static double[][] inputMatrix(int size){

        Scanner in = new Scanner(System.in);
        double[][] matrix = new double[size][size + 1];
        int a = 0;

        do  {

            System.out.print("\nHow to input matrix values?\n 1 - Keyboard input\n 2 - From file\nChoose option: ");
            a = in.nextInt();

        } while ((a < 1) | (a > 2));

        switch (a){
            case 1: System.out.println("Input values: ");
                    Scanner inn = new Scanner(System.in);
                    for (int i = 0; i < size; i++)
                        for (int j = 0; j < size + 1; j++){
                            System.out.print("matrix[" + i + "][" + j + "] = ");
                            matrix[i][j] = inn.nextDouble();
                        }
                break;
            case 2:
                System.out.print("Input file name: ");
                Scanner innn = new Scanner(System.in);
                String filename = innn.nextLine();
                matrix = matrixFromFile(size, filename);
                break;
        }
        return matrix;
    }

    static double[][] matrixFromFile(int size, String filename){


       double[][] matrix = new double[size][size + 1];
        for (int i = 0; i < size; i ++)
            for (int j = 0; j < 0; j ++)
                matrix[i][j] = 0;
        try{
            FileInputStream fstream = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            int lineCount = 0;
            String line = "";
            while ((line = br.readLine()) != null)
            {
                String[] numbers = line.split(" ");
                for ( int j = 0 ; j < size + 1 ; j++)
                    matrix[lineCount][j] = Double.parseDouble(numbers[j]);

                lineCount++;
            }

        }catch (Exception e){
            System.out.println("Invalid value");
        }
        return matrix;


    }


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int a = 0;
        do  {

            System.out.print("Menu: \n 1 - Input matrix\n 2 - Test\n 3 - Random\nChoose option: ");
            a = in.nextInt();

        } while ((a < 1) | (a > 3));

        int size = 0;
        double[][] matrix = null;

        switch (a){
            case 1: size = inputSize();
                    matrix = inputMatrix(size);
                break;
            case 2:
                size = 5;
                matrix = matrixFromFile(size, "test.txt");
                break;
            case 3:
                Random random = new Random();
                size = (random.nextInt(20) + 1);
                //size = 12;
                matrix = new double[size][size + 1];
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size + 1; j++)
                        matrix[i][j] = random.nextInt(20);
                break;
        }


        showMatrix(size, matrix, "\nMatrix:");
	    Gauss Count = new Gauss(size, matrix);

        if (!Count.checkIfHasSolutions()) {
            System.out.println("There is no solutions");
        }else{
            double[][] Triangmatrix = Count.getTriangular();
            showMatrix(size, Triangmatrix, "\nTriangular: ");


            if (Count.checkIfHasSolutions()) {
                if (Count.infiniteSolutions(Triangmatrix) || Count.manySolutions())
                    showSolutions(size, Count.getSolutions(), "There are an infinite number of solutions.\nParticular solution: ");
                else showSolutions(size, Count.getSolutions(), "Solutions: ");
                showError(size, Count.getError(Count.getSolutions()));
            }
            else System.out.println("System has no solutions");
        }



        System.out.println("\nDet = " + Count.getA());
    }
}
