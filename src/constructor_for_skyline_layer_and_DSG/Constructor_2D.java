package constructor_for_skyline_layer_and_DSG;

import basis.Constants;
import basis.Point;

import java.util.ArrayList;

public class Constructor_2D extends Constructor {
    public Constructor_2D(String datafile, String savefile, int k_point) {
        super(datafile, savefile, k_point);
    }

    public Constructor_2D(String datafile, int k_point) {
        super(datafile, k_point);
    }

    protected void construct_skip() {
        ArrayList<Point> points = input.points;
        int n = points.size(); // 总点数
        Point p1 = points.get(0);
        p1.layer = 1;
        maxlayer = 1;
        layers[0].add(p1);

        for (int i = 1; i < n; i++) {
            Point p = points.get(i);
            if (layers[0].get(layers[0].size() - 1).value[1] > p.value[1]) { // the tail point of layer1 cannot dominate p in 2d
                p.layer = 1;
                layers[0].add(p);
            } else if (layers[maxlayer - 1].get(layers[maxlayer - 1].size() - 1).value[1] <= p.value[1]) { // the tail point of layer maxlayer dominate p in 2d
                if (maxlayer < k_point_GSkyline_groups) {
                    p.layer = ++maxlayer;
                    layers[p.layer - 1].add(p);
                }
                // else it means p lies outside the first k layers, and we can drop p directly.
            } else {
                int j = binary_search(p, 0, maxlayer - 1);
                p.layer = j + 1;
                layers[j].add(p);
            }
        }
    }


    /*
    Based on Property 5, the tail points are sorted by y-coordinates in ascending order
    so we can perform a binary search to quickly find the layer that the point belongs to.

    find layer j (low < j <= high，注意下闭上开) that point p belongs to.
    such that the tail point of layer j cannot dominate p and the tail point of layer j−1 dominates p
    i.e., p.value[1] < tail_p.get(j).value[1] && p.value[1] >= tail_p.get(j-1).value
     */
    private int binary_search(Point p, int low, int high) {
        double key = p.value[1]; // 2d
        if (key < layers[low].get(layers[low].size() - 1).value[1]
                || key >= layers[high].get(layers[high].size() - 1).value[1] || low > high) {
            return -1;
        }
        if (high - low == 1) {
            return high;
        }
        int middle = (low + high) / 2; // 初始中间位置
        double middleValue = layers[middle].get(layers[middle].size() - 1).value[1];
        if (key < middleValue) { // 注意上开
            return binary_search(p, low, middle);
        } else { // 注意下闭：key >= middleValue
            return binary_search(p, middle, high);
        }

    }
}

