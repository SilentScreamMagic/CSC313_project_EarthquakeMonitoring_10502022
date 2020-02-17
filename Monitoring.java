package code;


import java.util.ArrayList;
import java.util.Hashtable;

public class Monitoring {
	public Hashtable<String, Observatory> observations;
	private int highCol;
	private double avgCol;
	
	public Monitoring(){
		highCol=0;
		avgCol=0;
		observations = new Hashtable<String, Observatory>();
	}
	public Hashtable<String, Observatory> getObservations() {
		return observations;
	}
	
	public int getLargestColour() {
		return highCol;
	}
	public void addObservatory(Observatory ob) {
		if (ob.getLargestValue()>highCol) {
			highCol= ob.getLargestValue();
		}
		if (ob.getAvgValue()>avgCol) {
			avgCol= ob.getAvgValue();
		}
		observations.put(ob.getName(), ob);
	}
	public double getLargestAvgColour() {
		return avgCol;
	}
	public ArrayList<Galamsey> getObservations(int limit) {
		ArrayList<Galamsey> g = new ArrayList<Galamsey>();
		for (Observatory o : observations.values()) {
			for (Galamsey galamsey : o.listGalamsey(limit)) {
				g.add(galamsey);
			}
		}
		return g;
	}
}
