import java.util.Arrays;
import java.util.concurrent.*;

// 64050278 Aschawit keanprakobg
public class Lab_MatrixMul {
    public static void main(String[] args) {

        int[][] inputA = { { 5, 6, 7 }, { 4, 8, 9 } };
        int[][] inputB = { { 6, 4 }, { 5, 7 }, { 1, 1 } };
        MyData matA = new MyData(inputA);
        MyData matB = new MyData(inputB);
        int matC_r = matA.data.length;
        int matC_c = matB.data[0].length;
        MyData matC = new MyData(matC_r, matC_c);
        // Q4
        if (matA.data[0].length != matB.data.length) {// จำนวนหลักของเมทริกซ์ตัวหน้าต้อง เท่ากับ
                                                      // จำนวนแถวของเมทริกซ์ตัวหลัง
            System.out.println("error inputing matrix multiplication");
            System.exit(0);
        }
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < matC.data.length; i++) {
            for (int j = 0; j < matC.data[0].length; j++) {
                pool.execute(new MatrixMulThread(i, j, matA, matB, matC));
            }
        }
        // Q5
        pool.shutdown();
        while (!pool.isTerminated()) {
            // System.out.println("wait");
        }
        matC.show();
    }
}

class MatrixMulThread implements Runnable {
    private int processinf_row = 0;
    int processing_col = 0;
    MyData datA;
    MyData datB;
    MyData datC;

    public MatrixMulThread(int tRow, int tCol, MyData a, MyData b, MyData c) {// q1
        processinf_row = tRow;
        processing_col = tCol;
        datA = new MyData(a.data);
        datB = new MyData(b.data);
        datC = new MyData(c.data);

    }// q2

    public void run() {// q3
        int SumRow = 0;
        int SumCol = 0;
        int ans = 0;
        for (int i = 0; i < datB.data.length; i++) {
            ans = ans + (datA.data[processinf_row][SumRow++]
                    * datB.data[SumCol++][processing_col]);
        }
        datC.data[processinf_row][processing_col] = ans;
        System.out.println(
                "Thread ID: "
                        + Thread.currentThread().getId() + " ans : " + ans);

    }

}

class MyData {
    int[][] data;

    MyData(int[][] m) {
        data = m;
    }

    MyData(int r, int c) {
        data = new int[r][c];
        for (int[] aRow : data) {
            Arrays.fill(aRow, 9);
        }
    }

    void show() {
        System.out.println(Arrays.deepToString(data));
    }
}
/*
 * 1.6 print บรรทัดที่ 20 x value is 3
 */
