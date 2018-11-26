package basis;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/*
    给定数据文件路径datafile，读出数据点构造成Point对象
 */
public class PointSet {
    public ArrayList<Point> points;
    private String datafile;

    public PointSet(String datafile) {
        this.datafile = datafile;
        points = new ArrayList<>();
        getPoints();
        Collections.sort(points); //this is an important step: sort the n points on the first dimension in ascending order
    }

    private void getPoints() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(datafile))));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] strs = line.split("\\s+");
                int dim = strs.length;
                double[] v = new double[dim];
                for (int i = 0; i < dim; i++) {
                    v[i] = Double.parseDouble(strs[i]);
                }
                Point point = new Point(dim, v);
                points.add(point);
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    public static void main(String[] args) {
//        PointSet pointSet = new PointSet(Constants.anti_2);
//    }

}
