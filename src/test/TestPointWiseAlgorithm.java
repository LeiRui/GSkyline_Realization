package test;

import basis.Constants;
import constructor_for_skyline_layer_and_DSG.Constructor_highD;
import two_algorithms_for_computing_GSkyline_groups.PointWise_Algorithm;

import java.io.PrintWriter;


public class TestPointWiseAlgorithm {
    public static void main(String[] args) throws Exception {
        String[] family = new String[9];
        family[0] = Constants.inde2;
        family[1] = Constants.inde4;
        family[2] = Constants.inde6;
        family[3] = Constants.corr2;
        family[4] = Constants.corr4;
        family[5] = Constants.corr6;
        family[6] = Constants.anti2;
        family[7] = Constants.anti4;
        family[8] = Constants.anti6;

        PrintWriter pw = new PrintWriter("algo_p_fixk_3_varing_d.txt");
        pw.println("fileName,outputGroupNumber,elapseTime(ms)");
        int fixk = 3;

        for (int i = 0; i < family.length; i++) {
            long startTime = System.currentTimeMillis();
            Constructor_highD constructor2
                    = new Constructor_highD(family[i], fixk);
            PointWise_Algorithm pointWise_algorithm2 = new PointWise_Algorithm(constructor2);
            long endTime = System.currentTimeMillis();
            System.out.println(family[i]);
            int outputSize = pointWise_algorithm2.printResult();
            long elapse = endTime - startTime;
            System.out.println("point_high_cost time is:" + elapse + "ms");
            pw.println(family[i] + "," + outputSize + "," + elapse);
        }

        pw.close();
    }
}
