package basis;


import java.util.ArrayList;

public class Point implements Comparable<Point> {
    public int dimension;
    public int layer = 0; // 0 means invalid
    public double[] value;
    public ArrayList<Point> childern;
    public ArrayList<Point> parents;

    public Point(int dim, double[] v) {
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

    //TODO 会不会导致set
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

    @Override
    public String toString() {
        String str = "(" + value[0];
        for (int i = 1; i < dimension; i++) {
            str += ",";
            str += value[i];
        }
        str += ")";
        return str;
    }

//    /*
//    Definition 6. (Unit Group). Given a point p in DSG, p and its parents form the unit group for p.
//     */
//    public HashSet<Point> getUnitGroup() {
//        HashSet<Point> res = new HashSet<>();
//        res.add(this);
//        for (Point p : parents) {
//            res.addAll(p.getUnitGroup());
//        }
//        return res;
//    }

    /*
    Definition 6. (Unit Group). Given a point p in DSG, p and its parents form the unit group for p.
     */
    public int getUnitGroupSize() {
        return this.parents.size() + 1;
    }
}
