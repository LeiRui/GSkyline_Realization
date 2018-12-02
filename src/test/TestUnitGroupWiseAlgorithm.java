package test;
import basis.Constants;
import constructor_for_skyline_layer_and_DSG.Constructor_2D;
import constructor_for_skyline_layer_and_DSG.Constructor_highD;
import two_algorithms_for_computing_GSkyline_groups.PointWise_Algorithm;
import two_algorithms_for_computing_GSkyline_groups.UnitGroupWise_Algorithm;


public class TestUnitGroupWiseAlgorithm {
    public static void main(String[] args) {
    	/*
        Constructor_2D constructor
                = new Constructor_2D(Constants.anti2, 3);
        long startTime=System.currentTimeMillis(); 
        UnitGroupWise_Algorithm unitGroupWise_Algorithm=new UnitGroupWise_Algorithm(constructor);
        long endTime=System.currentTimeMillis();
        System.out.println("unit_two_cost time is:"+(endTime-startTime)+"ms");
        System.out.println();
        throw new IllegalArgumentException("unit_two_cost time is:"+(endTime-startTime)+"ms");
    	*/
        
    	
        long startTime=System.currentTimeMillis(); 
        Constructor_highD constructor2
                = new Constructor_highD(Constants.anti4, 3);
        UnitGroupWise_Algorithm unitGroupWise_Algorithm=new UnitGroupWise_Algorithm(constructor2);
        long endTime=System.currentTimeMillis();
        System.out.println("unit_high_cost time is:"+(endTime-startTime)+"ms");
        throw new IllegalArgumentException("unit_high_cost time is:"+(endTime-startTime)+"ms");
         
    }
}
