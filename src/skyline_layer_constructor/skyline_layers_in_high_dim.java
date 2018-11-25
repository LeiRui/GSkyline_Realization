package skyline_layer_constructor;

import preprocess.Constants;
import preprocess.Point;
import preprocess.PointSet;

import java.io.PrintWriter;
import java.util.ArrayList;

public class skyline_layers_in_high_dim {
    public PointSet input;
    private int k_point_GSkyline_groups; // k-point G-skyline groups

    private int maxlayer;
    private ArrayList<Point>[] layers;

    public skyline_layers_in_high_dim(String datafile, String savefile, int k_point) {
        input = new PointSet(datafile);
        this.k_point_GSkyline_groups = k_point;
        layers = new ArrayList[k_point_GSkyline_groups];
        for (int i = 0; i < k_point; i++) {
            layers[i] = new ArrayList<>();
        }
        construct_skip();
        save(savefile);
    }

    // 按照算法构建skyline layers
    private void construct_skip() {
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

    // 返回true如果layer中存在点p1<=p，否则返回false
    private boolean IsDominateL(Point p, int layerId) {
        ArrayList<Point> layer = layers[layerId];
        for (Point pl : layer) {
            if (IsDominateP(pl, p)) { // p1 dominates or equals to p
                return true;
            }
        }
        return false;
    }

    // 返回true如果p1<=p: p1 dominate or equals to p
    private boolean IsDominateP(Point p1, Point p) {
        for (int i = 0; i < p1.dimension; i++) {
            if (p1.value[i] > p.value[i]) {
                return false;
            }
        }
        return true;
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
        skyline_layers_in_high_dim skyline_layers_in_high_dim
                = new skyline_layers_in_high_dim(Constants.my3, Constants.my3_save, 10);
        System.out.println("a");
    }

}
