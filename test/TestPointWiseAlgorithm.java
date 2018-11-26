import basis.Constants;
import constructor_for_skyline_layer_and_DSG.Constructor_2D;
import constructor_for_skyline_layer_and_DSG.Constructor_highD;
import two_algorithms_for_computing_GSkyline_groups.PointWise_Algorithm;


public class TestPointWiseAlgorithm {
    public static void main(String[] args) {
        Constructor_2D constructor
                = new Constructor_2D(Constants.eg, 4);
        PointWise_Algorithm pointWise_algorithm = new PointWise_Algorithm(constructor);
        pointWise_algorithm.printResult();
        System.out.println();

        Constructor_highD constructor2
                = new Constructor_highD(Constants.inde_8_very_short, 1);
        PointWise_Algorithm pointWise_algorithm2 = new PointWise_Algorithm(constructor2);
        pointWise_algorithm2.printResult();
    }
}
