package test;

import basis.Constants;
import constructor_for_skyline_layer_and_DSG.Constructor_highD;
import two_algorithms_for_computing_GSkyline_groups.PointWise_Algorithm;

import java.io.PrintWriter;


public class TestPointWiseAlgorithm2 {
    public static void main(String[] args) throws Exception {
        String[] family = new String[3];
        family[2] = Constants.anti2;
        family[1] = Constants.corr2;
        family[0] = Constants.inde2;


        PrintWriter pw = new PrintWriter("algo_p_fixd_2_varing_k.txt");
        pw.println("fileName,group size k,outputGroupNumber,elapseTime(ms)");
        int[] varying_k = new int[8];
        varying_k[0] = 2;
        varying_k[1] = 4;
        varying_k[2] = 6;
        varying_k[3] = 8;
        varying_k[4] = 10;
        varying_k[5] = 12;
        varying_k[6] = 14;
        varying_k[7] = 16;

        for (int i = 0; i < family.length; i++) {
            for (int j = 0; j < varying_k.length; j++) {
                long startTime = System.currentTimeMillis();
                Constructor_highD constructor2
                        = new Constructor_highD(family[i], varying_k[j]);
                PointWise_Algorithm pointWise_algorithm2 = new PointWise_Algorithm(constructor2);
                long endTime = System.currentTimeMillis();
                System.out.println(family[i]);
                int outputSize = pointWise_algorithm2.printResult();
                long elapse = endTime - startTime;
                System.out.println("point_high_cost time is:" + elapse + "ms");
                pw.println(family[i] + "," + varying_k[j] + "," + outputSize + "," + elapse);
            }
        }

        pw.close();
    }
}
