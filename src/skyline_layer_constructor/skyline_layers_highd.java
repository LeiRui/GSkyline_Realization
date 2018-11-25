package skyline_layer_constructor;

import preprocess.Constants;
import preprocess.Point;

import java.util.ArrayList;

public class skyline_layers_highd extends skyline_layers_general {
    public skyline_layers_highd(String datafile, String savefile, int k_point) {
        super(datafile, savefile, k_point);
    }

    // 按照算法构建skyline layers
    protected void construct_skip() {
        ArrayList<Point> points = input.points;
        int n = points.size(); // 总点数
        Point p1 = points.get(0);
        p1.layer = 1;
        maxlayer = 1;
        layers[0].add(p1);

        for (int i = 1; i < n; i++) {
            Point p = points.get(i);
            int j = 0;
            for (; j < maxlayer; j++) { // 从第一层往后找第一个不能dominate点p的层
                if (!IsDominateL(p, j)) {
                    p.layer = j + 1;
                    layers[j].add(p);
                    break;
                }
            }
            if (j == maxlayer) { // 现有的最后一层也dominate点p
                if (maxlayer < k_point_GSkyline_groups) {
                    p.layer = ++maxlayer;
                    layers[p.layer - 1].add(p);
                }
                // else it means p lies outside the first k layers, and we can drop p directly.
            }
        }
    }

    public static void main(String[] args) {
        skyline_layers_highd skyline_layers_in_high_dim
                = new skyline_layers_highd(Constants.my3, Constants.my3_save, 10);
        System.out.println("a");
    }

}
