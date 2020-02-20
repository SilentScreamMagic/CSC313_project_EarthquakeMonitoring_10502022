import java.util.ArrayList;
import java.util.Hashtable;

public class Monitoring {
    public Hashtable<String, Observatory> observations;


    public Monitoring(){

        observations = new Hashtable<String, Observatory>();
    }
    public Hashtable<String, Observatory> getObservations() {
        return observations;
    }

    public int getLargestColour() {
        int highCol=0;
        for (Observatory ob : observations.values()) {

            if (ob.getLargestValue()>highCol){
                highCol=ob.getLargestValue();
            }
        }
        return highCol;
    }

    public double getLargestAvgColour() {
        double avgCol=0;
        for (Observatory ob : observations.values()) {

            if (ob.getAvgValue()>avgCol){
                avgCol=ob.getAvgValue();
            }
        }
        return avgCol;
    }
    public ArrayList<Galamsey> getObservations(int limit) {
        ArrayList<Galamsey> g = new ArrayList<Galamsey>();
        System.out.println("Working");
        for (Observatory o : observations.values()) {

            for (Galamsey galamsey : o.listGalamsey(limit)) {
                System.out.println(galamsey.toString());
                if (galamsey!=null) {
                    System.out.println(g.add(galamsey));


                }
            }
        }
        for (Galamsey galam: g) {
            System.out.println(galam.toString());
        }
        return g;
    }
}