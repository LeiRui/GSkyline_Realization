package basis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class GSkylineGroup {
	public int k; // k point G-Skyline group
	public int maxIndex; // maxIndex in the tail list
	public int maxLayer; // & Algorithm2 line 9-10
	public ArrayList<Point> points;
	public HashSet<Integer> poIntegers;

	public GSkylineGroup(int k, int maxIndex, int maxLayer, ArrayList<Point> points) {
		this.k = k;
		this.maxIndex = maxIndex;
		this.maxLayer = maxLayer;
		this.points = points;
	}
	public GSkylineGroup(Point p) {
		points=new ArrayList<>(Arrays.asList(p));
		poIntegers=new HashSet<>(Arrays.asList(p.index));
		for(Point parent:p.parents){
			poIntegers.add(parent.index);
		}
		
	}
	public GSkylineGroup(GSkylineGroup g) {
		points = new ArrayList<>();
		poIntegers = new HashSet<>();
		for (Integer i : g.poIntegers) {
			poIntegers.add(i);
		}
		for (Point p : g.points) {
			points.add(p);
		}
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public HashSet<Integer> getPoIntegers() {
		return poIntegers;
	}

	public int getNumOfPoints() {
		return poIntegers.size();

	}

	public int getNumOfUnitPoints() {
		return points.size();
	}

	public ArrayList<Point> getChildern() {
		ArrayList<Point> childern = new ArrayList<>();
		for (Point p : points) {
			childern.addAll(p.childern);
		}
		return childern;
	}

	public HashSet<Integer> getIndexOfParents() {
		HashSet<Integer> parents = new HashSet<>();
		for (Point p : points) {
			for (Point parent : p.parents) {
				parents.add(parent.index);
			}
		}
		return parents;
	}

	public HashSet<Integer> getIndexsOfChildren() {
		HashSet<Integer> children = new HashSet<>();
		for (Point p : points) {
			for (Point parent : p.childern) {
				children.add(parent.index);
			}
		}
		return children;
	}
	//Algorithm3 line 15-16: form new candidate group G''
	public ArrayList<Integer> getCandidateUnitGroups(int totalPoints) {
		int max = Integer.MIN_VALUE;
		for (Point p : points) {
			//get max index
			max = Math.max(max, p.index);
		}
		ArrayList<Integer> candidate = new ArrayList<>();
		HashSet<Integer> children = getIndexsOfChildren();
		for (int i = max + 1; i <totalPoints; i++) {
			if (!children.contains(i)) {
				//Algorithm3 line 16
				candidate.add(i);
			}
		}
		return candidate;
	}
	
	//get unit group = p + parents [definition 2.1.6]
	public GSkylineGroup unionUnitGtoup(Point p) {
		points.add(p);
		poIntegers.add(p.index);
		//Algorithm3 line 11-14
		for (Point parent : p.parents) {
			poIntegers.add(parent.index);
		}
		return this;
	}
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
