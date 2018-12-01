package test;
import basis.Constants;
import constructor_for_skyline_layer_and_DSG.Constructor_2D;
import constructor_for_skyline_layer_and_DSG.Constructor_highD;
import two_algorithms_for_computing_GSkyline_groups.PointWise_Algorithm;
import two_algorithms_for_computing_GSkyline_groups.UnitGroupWise_Algorithm;


public class TestUnitGroupWiseAlgorithm {
    public static void main(String[] args) {
        Constructor_2D constructor
                = new Constructor_2D(Constants.eg, 4);
        UnitGroupWise_Algorithm unitGroupWise_Algorithm=new UnitGroupWise_Algorithm(constructor);
        System.out.println();

        Constructor_highD constructor2
                = new Constructor_highD(Constants.inde_8_very_short, 1);
        unitGroupWise_Algorithm=new UnitGroupWise_Algorithm(constructor2);
    }
}
