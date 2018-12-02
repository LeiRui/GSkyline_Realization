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
    	//Algorithm3 line 2
    	for (int i = 0;i<totalPoins;i++) {
    		//unit
			Point point = preprocessGroup.get(i);			
			GSkylineGroup group=new GSkylineGroup(point);
			//Algorithm3 line 3-5
			if(group.getNumOfPoints()==constructor.k_point_GSkyline_groups){
					res++;
			}
			//Algorithm3 line 6-7
			if(group.getNumOfPoints()>constructor.k_point_GSkyline_groups){
				continue;
			}
			
			//tailset and superset prune
			LinkedList<GSkylineGroup> list=new LinkedList<>();
			list.add(group);//|G′|u 
			//Algorithm3 line 9-10: candidate group G' & G'u is not empty
			while(!list.isEmpty()){
				GSkylineGroup g=list.remove();
				//Algorithm3 line 10  getCandidateUnitGroups = each G′
				//Algorithm3 line 15-16: form candidateUnitGroups G''
				for (int j:g.getCandidateUnitGroups(totalPoins)) {
					GSkylineGroup next_group=new GSkylineGroup(g);
					//Algorithm3 line 11-14 form unit group
					next_group.unionUnitGtoup(preprocessGroup.get(j));
					int num_point = next_group.getNumOfPoints();
					if(num_point==constructor.k_point_GSkyline_groups){
						//Algorithm3 line 18  not prune superset--->output
						res++;
					}else if (num_point<constructor.k_point_GSkyline_groups) {
						//Algorithm3 line 19  prune superset
						list.add(next_group);
					}
				}
			}	
			//System.out.println(res);
		}
//    	System.out.println("UnitGroupWise的G_skylineGroup数目为:" + res);
    }


}
