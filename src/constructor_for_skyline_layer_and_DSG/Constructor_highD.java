package constructor_for_skyline_layer_and_DSG;

import basis.Constants;
import basis.Point;

import java.util.ArrayList;

public class Constructor_highD extends Constructor {
    public Constructor_highD(String datafile, String savefile, int k_point) {
        super(datafile, savefile, k_point);
    }

    public Constructor_highD(String datafile, int k_point) {
        super(datafile, k_point);
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

}
