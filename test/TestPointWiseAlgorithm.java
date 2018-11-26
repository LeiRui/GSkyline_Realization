import basis.Constants;
import basis.GSkylineGroup;
import basis.Point;
import constructor_for_skyline_layer_and_DSG.Constructor_2D;
import constructor_for_skyline_layer_and_DSG.Constructor_highD;
import two_algorithms_for_computing_GSkyline_groups.PointWise_Algorithm;

import java.util.ArrayList;

public class TestPointWiseAlgorithm {
    public static void main(String[] args) {
        Constructor_highD constructor
                = new Constructor_highD(Constants.eg, 4);
        PointWise_Algorithm pointWise_algorithm = new PointWise_Algorithm(constructor);
        pointWise_algorithm.printResult();
    }
}
