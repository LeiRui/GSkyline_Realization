package two_algorithms_for_computing_GSkyline_groups;

import basis.GSkylineGroup;
import basis.Point;
import constructor_for_skyline_layer_and_DSG.Constructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
input : a DSG and group size k.
output: G-Skyline(k) groups.
 */
public class UnitGroupWise_Algorithm {
    Constructor constructor;

    // 预处理步骤中找到的unit group是k point G-Skyline group的点
    public ArrayList<Point> preprocessGroup;
    
    public int res=0;

    public UnitGroupWise_Algorithm(Constructor constructor) {
        this.constructor = constructor;

        this.preprocessGroup = new ArrayList<>();

        preprocess(constructor.layers);
        process();
    }
    
    //further prune points from the first k skyline layers.
    private void preprocess(ArrayList<Point>[] dsg) {
        preprocessGroup = new ArrayList<>();
        for(ArrayList<Point> l:dsg){
        	for(Point p:l){
        		if(p.layer<=constructor.k_point_GSkyline_groups&&p.getUnitGroupSize()<=constructor.k_point_GSkyline_groups){
        			preprocessGroup.add(p);
        		}
        	}
        }
        for(int i=0;i<preprocessGroup.size();i++){
        	preprocessGroup.get(i).index=i;
        }
    }
    private void process(){
    	int totalPoins = preprocessGroup.size();
    	for (int i = 0;i<totalPoins;i++) {
    		//unit
			Point point = preprocessGroup.get(i);
			GSkylineGroup group=new GSkylineGroup(point);
			if(group.getNumOfPoints()==constructor.k_point_GSkyline_groups){
					res++;
			}
			if(group.getNumOfPoints()>constructor.k_point_GSkyline_groups){
				continue;
			}
			
			//tailset superset prune
			LinkedList<GSkylineGroup> list=new LinkedList<>();
			list.add(group);
			while(!list.isEmpty()){
				GSkylineGroup g=list.remove();
				for (int j:g.getCandidateUnitGroups(totalPoins)) {
					GSkylineGroup next_group=new GSkylineGroup(g);
					next_group.unionUnitGtoup(preprocessGroup.get(j));
					int num_point = next_group.getNumOfPoints();
					if(num_point==constructor.k_point_GSkyline_groups){
						res++;
					}else if (num_point<constructor.k_point_GSkyline_groups) {
						list.add(next_group);
					}
				}
			}	
			//System.out.println(res);
		}
//    	System.out.println("UnitGroupWise的G_skylineGroup数目为:" + res);
    }


}
