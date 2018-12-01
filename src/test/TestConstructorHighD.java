package test;
import basis.Constants;
import basis.Point;
import constructor_for_skyline_layer_and_DSG.Constructor_highD;

import java.util.ArrayList;

public class TestConstructorHighD {
    public static void main(String[] args) {
        Constructor_highD skyline_layers_in_high_dim
                = new Constructor_highD(Constants.my3, Constants.my3_save, 4);
        ArrayList<Point>[] layers = skyline_layers_in_high_dim.layers;
        for (int i = 0; i < skyline_layers_in_high_dim.maxlayer; i++) {
            for (Point p : layers[i]) {
                System.out.println("" + p + " " + p.getUnitGroupSize());
            }
        }
    }
}
