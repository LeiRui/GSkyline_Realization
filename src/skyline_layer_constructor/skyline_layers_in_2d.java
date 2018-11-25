package skyline_layer_constructor;

import preprocess.Constants;
import preprocess.Point;
import preprocess.PointSet;

import java.io.PrintWriter;
import java.util.ArrayList;

/*
    Skyline layers algorithm in two-Ds.
    input : a set of n points in two dimensional space.
    output: l skyline layers.
 */
public class skyline_layers_in_2d {
    public PointSet input;
    private int k_point_GSkyline_groups; // k-point G-skyline groups

    private int maxlayer;
    private ArrayList<Point> tail_p;

    public skyline_layers_in_2d(String datafile, int k_point) {
        input = new PointSet(datafile);
        this.k_point_GSkyline_groups = k_point;
        this.tail_p = new ArrayList<>();
        construct();
//        construct_skip();
        save(Constants.my_save);
    }

    // 按照算法构建skyline layers
    private void construct() {
        ArrayList<Point> points = input.points;
        int n = points.size(); // 总点数
        Point p1 = points.get(0);
        p1.layer = 1;
        maxlayer = 1;
        tail_p.add(p1);

        for (int i = 1; i < n; i++) {
            Point p = points.get(i);
            if (tail_p.get(0).value[1] > p.value[1]) { // the tail point of layer1 cannot dominate p in 2d
                p.layer = 1;
                tail_p.set(0, p);
            } else if (tail_p.get(maxlayer - 1).value[1] <= p.value[1]) { // the tail point of layermaxlayer dominate p in 2d
                p.layer = ++maxlayer;
                tail_p.add(p);
            } else {
                int j = binary_search(p, 0, maxlayer - 1);
                p.layer = j + 1;
                tail_p.set(j, p);
            }
        }
    }

    // 到了k层layers构建跳过后层建设 slightly modify the algorithm
    public void construct_skip() {
        ArrayList<Point> points = input.points;
        int n = points.size(); // 总点数
        Point p1 = points.get(0);
        p1.layer = 1;
        maxlayer = 1;
        tail_p.add(p1);

        for (int i = 1; i < n; i++) {
            Point p = points.get(i);
            if (maxlayer < k_point_GSkyline_groups) {
                if (tail_p.get(0).value[1] > p.value[1]) { // the tail point of layer1 cannot dominate p in 2d
                    p.layer = 1;
                    tail_p.set(0, p);
                } else if (tail_p.get(maxlayer - 1).value[1] <= p.value[1]) { // the tail point of layermaxlayer dominate p in 2d
                    p.layer = ++maxlayer;
                    tail_p.add(p);
                } else {
                    int j = binary_search(p, 0, maxlayer - 1);
                    p.layer = j + 1;
                    tail_p.set(j, p);
                }
            } else { // maxlayer = k_point_GSkyline_groups; the k_th layer is established
                if (tail_p.get(maxlayer - 1).value[1] <= p.value[1]) { // the tail point of layermaxlayer dominate p in 2d
                    /*
                    If p is dominated by it, it means p lies outside the first k layers, and we can drop p directly.
                    The layers of these points remain initial value 0.
                     */
                } else if (tail_p.get(0).value[1] > p.value[1]) { // the tail point of layer1 cannot dominate p in 2d
                    p.layer = 1;
                    tail_p.set(0, p);
                } else {
                    int j = binary_search(p, 0, maxlayer - 1);
                    p.layer = j + 1;
                    tail_p.set(j, p);
                }
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
        if (key < tail_p.get(low).value[1] || key >= tail_p.get(high).value[1] || low > high) {
            return -1;
        }
        if (high - low == 1) {
            return high;
        }
        int middle = (low + high) / 2; // 初始中间位置
        double middleValue = tail_p.get(middle).value[1];
        if (key < middleValue) { // 注意上开
            return binary_search(p, low, middle);
        } else { // 注意下闭：key >= middleValue
            return binary_search(p, middle, high);
        }

    }

    public void save(String savefile) {
        try {
            PrintWriter pw = new PrintWriter(savefile);
            ArrayList<Point> points = input.points;
            for (Point p : points) {
                String line = "";
                for (int i = 0; i < p.dimension; i++) {
                    line += p.value[i];
                    line += " ";
                }
                line += p.layer;
                pw.println(line);
            }
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        skyline_layers_in_2d skyline_layers_in_2d = new skyline_layers_in_2d(Constants.my, 2);
        System.out.println("aa");

        // TODO3: 感兴趣的三维skyline layers图形化效果？
    }

}
