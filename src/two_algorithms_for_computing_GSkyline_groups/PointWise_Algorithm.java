package two_algorithms_for_computing_GSkyline_groups;

import basis.GSkylineGroup;
import basis.Point;
import constructor_for_skyline_layer_and_DSG.Constructor;

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

    // 把preprocess步骤和mainprocess步骤找到的k-point G-Skyline groups合并起来成最终结果
    public ArrayList<ArrayList<Point>> getResult() {
        ArrayList<ArrayList<Point>> res = new ArrayList<>();
        for (Point p : preprocessGroup) {
            ArrayList<Point> points = p.parents;
            points.add(p);
            res.add(points);
        }
        if (nodes.size() <= constructor.k_point_GSkyline_groups) {
            return res;
        } else {
            ArrayList<GSkylineGroup> gSkylineGroups = nodes.get(constructor.k_point_GSkyline_groups);
            for (GSkylineGroup gSkylineGroup : gSkylineGroups) {
                res.add(gSkylineGroup.points);
            }
        }
        return res;
    }

    public void printResult() {
        ArrayList<ArrayList<Point>> res = getResult();
        System.out.println("find " + res.size() + " " + constructor.k_point_GSkyline_groups + "-point G-Skyline groups:");
        int cnt = 0;
        for (ArrayList<Point> group : res) {
            cnt++;
            String line = "";
            for (Point p : group) {
                line += p;
            }
            System.out.println("G-Skyline group" + cnt + ":" + line);
        }
    }

}
