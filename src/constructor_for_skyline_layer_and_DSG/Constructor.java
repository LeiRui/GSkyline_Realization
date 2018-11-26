package constructor_for_skyline_layer_and_DSG;

import basis.Point;
import basis.PointSet;

import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class Constructor {
    public PointSet input;
    public int k_point_GSkyline_groups; // k-point G-skyline groups

    public int maxlayer; // maxlayer<=k_point_GSkyline_groups
    public ArrayList<Point>[] layers;

    public Constructor(String datafile, int k_point) {
        input = new PointSet(datafile); // already sorted on the first dimension in ascending order
        this.k_point_GSkyline_groups = k_point;
        layers = new ArrayList[k_point_GSkyline_groups];
        for (int i = 0; i < k_point; i++) {
            layers[i] = new ArrayList<>();
        }
        construct_skip(); // construct k skyline layers
        constructDSG(); // build directed skyline graph from the first k skyline layers
    }

    public Constructor(String datafile, String savefile, int k_point) {
        input = new PointSet(datafile); // already sorted on the first dimension in ascending order
        this.k_point_GSkyline_groups = k_point;
        layers = new ArrayList[k_point_GSkyline_groups];
        for (int i = 0; i < k_point; i++) {
            layers[i] = new ArrayList<>();
        }
        construct_skip(); // construct k skyline layers
        save(savefile); // output points' layer information
        constructDSG(); // build directed skyline graph from the first k skyline layers
    }

    // 按照算法构建skyline layers
    protected abstract void construct_skip();

    // 返回true如果layer中存在点p1<=p (p1 dominate or equals to p)，否则返回false
    protected boolean IsDominateL(Point p, int layerId) {
        ArrayList<Point> layer = layers[layerId];
        for (Point pl : layer) {
            if (IsDominateP(pl, p)) { // p1 dominates or equals to p
                return true;
            }
        }
        return false;
    }

    // 返回true如果p1<=p (p1 dominate or equals to p)，否则返回false
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
    NOTE: 这里不区分直接父节点和不直接父节点，统一是父节点
     */
    protected void constructDSG() {
        for (int i = 0; i < maxlayer - 1; i++) {
            ArrayList<Point> layer = layers[i];
            for (int m = i + 1; m < maxlayer; m++) {
                ArrayList<Point> nextLayer = layers[m];
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
}
