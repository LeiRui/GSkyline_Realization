package skyline_layer_constructor;

import preprocess.Constants;
import preprocess.Point;
import preprocess.PointSet;

import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class skyline_layers_general {
    protected PointSet input;
    protected int k_point_GSkyline_groups; // k-point G-skyline groups

    protected int maxlayer;
    protected ArrayList<Point>[] layers;

    public skyline_layers_general(String datafile, String savefile, int k_point) {
        input = new PointSet(datafile);
        this.k_point_GSkyline_groups = k_point;
        layers = new ArrayList[k_point_GSkyline_groups];
        for (int i = 0; i < k_point; i++) {
            layers[i] = new ArrayList<>();
        }
        construct_skip();
        save(savefile);
        constructDSG();
    }

    // 按照算法构建skyline layers
    protected abstract void construct_skip();

    // 返回true如果layer中存在点p1<=p，否则返回false
    protected boolean IsDominateL(Point p, int layerId) {
        ArrayList<Point> layer = layers[layerId];
        for (Point pl : layer) {
            if (IsDominateP(pl, p)) { // p1 dominates or equals to p
                return true;
            }
        }
        return false;
    }

    // 返回true如果p1<=p: p1 dominate or equals to p
    protected boolean IsDominateP(Point p1, Point p) {
        for (int i = 0; i < p1.dimension; i++) {
            if (p1.value[i] > p.value[i]) {
                return false;
            }
        }
        return true;
    }

    // 把每个点及其所属的skyline layer输出到savefile
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

    /*
    For each point pi, we scan all points in the previous layers and find those points that dominate pi,
    add pi to their children list, and add those points that dominate pi as pi’s parents.
     */
    protected void constructDSG() {
        for (int i = 0; i < maxlayer - 1; i++) {
            ArrayList<Point> layer = layers[i];
            ArrayList<Point> nextLayer = layers[i + 1];
            int n1 = layer.size();
            int n2 = nextLayer.size();
            for (int j = 0; j < n1; j++) {
                Point p1 = layer.get(j);
                for (int k = 0; k < n2; k++) {
                    Point p2 = nextLayer.get(k);
                    if (IsDominateP(p1, p2)) { // p1<=p2
                        /*
                        TODO 这里论文中没说equals的时候算不算 我这里当作算处理
                        理由是每一层的点至少都被前一层的一个点dominate，
                        那么如果刚好P1P2完全相同的两个点，P1先进了前面的层，然后判断P2是作为P1后面的层的，而且应该是被dominate
                         */
                        p1.childern.add(p2);
                        p2.parents.add(p1);
                    }
                }
            }
        }

    }
}
