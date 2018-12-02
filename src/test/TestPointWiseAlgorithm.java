package test;
import basis.Constants;
import constructor_for_skyline_layer_and_DSG.Constructor_2D;
import constructor_for_skyline_layer_and_DSG.Constructor_highD;
import two_algorithms_for_computing_GSkyline_groups.PointWise_Algorithm;


public class TestPointWiseAlgorithm {
    public static void main(String[] args) {
    	/*
    	long startTime0=System.currentTimeMillis(); 
        Constructor_2D constructor
                = new Constructor_2D(Constants.anti2, 3);
        PointWise_Algorithm pointWise_algorithm = new PointWise_Algorithm(constructor);
        pointWise_algorithm.printResult();
        long endTime0=System.currentTimeMillis();
        System.out.println("point_two_cost time is:"+(endTime0-startTime0)+"ms");
        throw new IllegalArgumentException("point_two_cost time is:"+(endTime0-startTime0)+"ms");
    	*/
    	 
        long startTime=System.currentTimeMillis(); 
        Constructor_highD constructor2
                = new Constructor_highD(Constants.anti4, 3);
        PointWise_Algorithm pointWise_algorithm2 = new PointWise_Algorithm(constructor2);
        pointWise_algorithm2.printResult();
        long endTime=System.currentTimeMillis();
        System.out.println("point_high_cost time is:"+(endTime-startTime)+"ms");
        throw new IllegalArgumentException("point_high_cost time is:"+(endTime-startTime)+"ms");
        
    }
}
