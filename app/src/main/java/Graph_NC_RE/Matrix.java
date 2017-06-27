package Graph_NC_RE;

import java.util.Random;

/******************************************************************************
 *  Compilation:  javac Matrix.java
 *  Execution:    java Matrix
 *
 *  A bare-bones collection of static methods for manipulating
 *  matrices.
 *
 ******************************************************************************/

public class Matrix {

    // return a random m-by-n matrix with values between 0 and 1
    public static double[][] random(int m, int n) {
        double[][] a = new double[m][n];
        Random r = new Random();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = r.nextDouble();//(0.0, 1.0);
        return a;
    }

    // return n-by-n identity matrix I
    public static double[][] identity(int n) {
        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++)
            a[i][i] = 1;
        return a;
    }

    // return x^T y
    public static double dot(double[] x, double[] y) {
        if (x.length != y.length) throw new RuntimeException("Illegal vector dimensions.");
        double sum = 0.0;
        for (int i = 0; i < x.length; i++)
            sum += x[i] * y[i];
        return sum;
    }

    // return B = A^T
    public static double[][] transpose(double[][] a) {
        int m = a.length;
        int n = a[0].length;
        double[][] b = new double[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                b[j][i] = a[i][j];
        return b;
    }

    // return c = a + b
    public static double[][] add(double[][] a, double[][] b) {
        int m = a.length;
        int n = a[0].length;
        double[][] c = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] + b[i][j];
        return c;
    }

    // return c = a - b
    public static double[][] subtract(double[][] a, double[][] b) {
        int m = a.length;
        int n = a[0].length;
        double[][] c = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] - b[i][j];
        return c;
    }

    // return c = a * b
    public static double[][] multiply(double[][] a, double[][] b) {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        double[][] c = new double[m1][n2];
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

    // matrix-vector multiplication (y = A * x)
    public static double[] multiply(double[][] a, double[] x) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += a[i][j] * x[j];
        return y;
    }


    // vector-matrix multiplication (y = x^T A)
    public static double[] multiply(double[] x, double[][] a) {
        int m = a.length;
        int n = a.length;
        if (x.length != m) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[n];
        for (int j = 0; j < n; j++)
            for (int i = 0; i < m; i++)
                y[j] += a[i][j] * x[i];
        return y;
    }
    
    // vector-vector multiplication
    public static double multiply(double[] a, double[] that) {
        if (a.length!= that.length)
            throw new IllegalArgumentException("dimensions disagree");
        double sum = 0.0;
        int n = a.length;
        for (int i = 0; i < n; i++)
            sum = sum + (a[i] * that[i]);
        return sum;
    }
    
    // vector-scalar multiplication
    public static double[] scalarMultiply(double[] array, double scale){
		for( int i=0; i<array.length; i++){
			array[i] = (array[i]) * scale; 
		}
		
		return array;
    }
    
    public static double[][] getSubMatrix(double[][] a, int[] r, int[] c) {
    	double[][] X = new double[r.length][c.length];
//        double[][] B = X.getArray();
        try {
           for (int i = 0; i < r.length; i++) {
              for (int j = 0; j < c.length; j++) {
                 X[i][j] = a[r[i]][c[j]];
              }
           }
        } catch(ArrayIndexOutOfBoundsException e) {
           throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }
    
    
}
