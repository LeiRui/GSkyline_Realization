package test;
import basis.Constants;
import basis.Point;
import constructor_for_skyline_layer_and_DSG.Constructor_2D;

import java.util.ArrayList;

public class TestConstructor2D {
    public static void main(String[] args) {
        Constructor_2D Constructor_2D
                = new Constructor_2D(Constants.my, Constants.my_save, 4);
        ArrayList<Point>[] layers = Constructor_2D.layers;
        for (int i = 0; i < Constructor_2D.maxlayer; i++) {
            for (Point p : layers[i]) {
                System.out.println("" + p + " " + p.getUnitGroupSize());
            }
        }
    }
}
