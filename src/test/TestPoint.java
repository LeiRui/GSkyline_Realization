package test;

import basis.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class TestPoint {
    public static void main(String[] args) {
        Point point1 = new Point(2, new double[]{1, 2});
        Point point2 = new Point(2, new double[]{1, 3});
//        TreeSet treeSet = new TreeSet();
//        treeSet.add(point1);
//        treeSet.add(point2);
        HashSet hashSet = new HashSet();
        hashSet.add(point1);
        hashSet.add(point1);
        System.out.println("");

        ArrayList<Point> arrayList = new ArrayList<>();
        arrayList.add(point1);
        arrayList.add(point2);
        if(arrayList.contains(point1)) {
            System.out.println("yes");
        }
    }
}
