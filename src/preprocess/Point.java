package preprocess;


import java.util.ArrayList;

public class Point implements Comparable<Point> {
    public int dimension;
    public int layer = 0; // 0 means invalid
    public double[] value;
    public ArrayList<Point> childern;
    public ArrayList<Point> parents;

    Point(int dim, double[] v) {
        this.dimension = dim;
        this.value = new double[dim];
        for (int i = 0; i < dim; i++) {
            this.value[i] = v[i];
        }
        this.childern = new ArrayList<>();
        this.parents = new ArrayList<>();
    }

    void setLayer(int layer) {
        this.layer = layer;
    }

    public int compareTo(Point p) {
        double res = this.value[0] - p.value[0];
        if (res > 0) {
            return 1;
        } else if (res < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
