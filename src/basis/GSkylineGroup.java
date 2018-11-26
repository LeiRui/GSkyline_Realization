package basis;

import java.util.ArrayList;
import java.util.HashSet;

public class GSkylineGroup {
    public int k; // k point G-Skyline group
    public int maxIndex; // maxIndex in the tail list
    public int maxLayer; //& Algorithm2 line 9-10
    public ArrayList<Point> points;

    public GSkylineGroup(int k, int maxIndex, int maxLayer, ArrayList<Point> points) {
        this.k = k;
        this.maxIndex = maxIndex;
        this.maxLayer = maxLayer;
        this.points = points;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }


    public ArrayList<Point> getChildern() {
        ArrayList<Point> childern = new ArrayList<>();
        for (Point p : points) {
            childern.addAll(p.childern);
        }
        return childern;
    }

    // Verification of G-Skyline
    public boolean verification() {
        HashSet<Point> uSet = new HashSet<>();
        for (Point p : points) {
            ArrayList<Point> parents = p.parents;
            uSet.addAll(parents);
            uSet.add(p);
        }
        if (uSet.size() == k) {
            return true;
        } else {
            return false;
        }
    }
}
