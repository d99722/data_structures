package cse2010.homework1;

public class test {

	public static void main(String[] args) {
		double[][] m1 = {
            {0, 0, 1.0, 0},
            {1.0, 2.0, 0, 0},
            {0, 0, 0, 3.0}
        };

        double[][] m2 = {
            {1.0, 0, 0, 2.0},
            {0, 3.0, 0, 0},
            {4.0, 0, 5.0, 0}
        };
        
        SparseMatrix sm1 = ArraySparseMatrix.create(m1, 3, 4, 4);
        SparseMatrix sm2 = ArraySparseMatrix.create(m2, 3, 4, 5);
        sm1.add(sm2);


	}

}
