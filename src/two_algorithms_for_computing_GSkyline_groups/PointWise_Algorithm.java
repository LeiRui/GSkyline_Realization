package two_algorithms_for_computing_GSkyline_groups;

import basis.Constants;
import basis.GSkylineGroup;
import basis.Point;
import constructor_for_skyline_layer_and_DSG.Constructor;
import constructor_for_skyline_layer_and_DSG.Constructor_2D;
import constructor_for_skyline_layer_and_DSG.Constructor_highD;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
input : a DSG and group size k.
output: G-Skyline(k) groups.
 */
public class PointWise_Algorithm {
    Constructor constructor;

    // 预处理步骤中找到的unit group是k point G-Skyline group的点
    public ArrayList<Point> preprocessGroup;

    private ArrayList<Point> FullTailList; // 排布顺序是按层递增、层内以第一维递增

    public ArrayList<ArrayList<GSkylineGroup>> nodes; // Each node in the set enumeration tree is a candidate group.

    public PointWise_Algorithm(Constructor constructor) {
        this.constructor = constructor;
        this.FullTailList = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.preprocessGroup = new ArrayList<>();
        preprocess();
        mainprocess();
    }

    //further prune points from the first k skyline layers.
    private void preprocess() {
        ArrayList<Point>[] layers = constructor.layers;
        for (int i = 0; i < constructor.maxlayer; i++) {
            ArrayList<Point> layer = layers[i];
            for (int j = layer.size() - 1; j >= 0; j--) { // 倒叙删
                Point p = layer.get(j);
                int num = p.getUnitGroupSize();
                if (num > constructor.k_point_GSkyline_groups) {
                    // remove p directly from the DSG without having to consider it
                    layer.remove(j);
                } else if (num == constructor.k_point_GSkyline_groups) {
                    // output p as one of the G-Skyline groups and p
                    preprocessGroup.add(p);
                    layer.remove(j);
                }
            }
        }

        for (int i = 0; i < constructor.maxlayer; i++) {
            FullTailList.addAll(layers[i]);
        }
    }

    public void mainprocess() {
        ArrayList<GSkylineGroup> G0 = new ArrayList<>();
        G0.add(new GSkylineGroup(0, -1, 0, new ArrayList<>()));
        nodes.add(G0);

        for (int l = 1; l <= constructor.k_point_GSkyline_groups; l++) {
            for (GSkylineGroup gSkylineGroup : nodes.get(l - 1)) {
                // tail list prune
                ArrayList<Point> Childern = gSkylineGroup.getChildern();
                List<Point> tailList = FullTailList.subList(gSkylineGroup.maxIndex + 1, FullTailList.size());// 注意只读不修改
                List<Point> prunedTailList = new ArrayList<>();
                for (Point p : tailList) {
                    if ((Childern.contains(p) || p.layer == 1) && p.layer - gSkylineGroup.maxLayer < 2) {
                        prunedTailList.add(p);
                    }
                }

                for (Point p : prunedTailList) {
                    int index = FullTailList.indexOf(p);
                    ArrayList<Point> points = new ArrayList<>();
                    points.addAll(gSkylineGroup.points);
                    points.add(p);
                    GSkylineGroup gSkylineGroup1 = new GSkylineGroup(l, index, p.layer, points);
                    if (gSkylineGroup1.verification()) { // subtree prune
                        if (nodes.size() <= l) {
                            ArrayList<GSkylineGroup> G = new ArrayList<>();
                            G.add(gSkylineGroup1);
                            nodes.add(G);
                        } else {
                            nodes.get(l).add(gSkylineGroup1);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Constructor_2D constructor_2D
                = new Constructor_2D(Constants.eg, 4);
        PointWise_Algorithm pointWise_algorithm = new PointWise_Algorithm(constructor_2D);
        System.out.println("");
        ArrayList<GSkylineGroup> G = pointWise_algorithm.nodes.get(constructor_2D.k_point_GSkyline_groups);
        System.out.println("find " + G.size() + " " + constructor_2D.k_point_GSkyline_groups + "-point G-Skyline groups:");
        int cnt = 0;
        for (GSkylineGroup gSkylineGroup : G) {
            cnt++;
            String line = "";
            for (Point p : gSkylineGroup.points) {
                line += p;
            }
            System.out.println("G-Skyline group" + cnt + ":" + line);
        }
    }

}
