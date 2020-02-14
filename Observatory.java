package code;

public class Observatory {
	private String name;
	private String country;
	private int year;
	private double areaCovered;
	private Galamsey[] observation;
	private int count;
	private int highCol;
	
	public Observatory(String name, String country, int year, double areaCovered, Galamsey[] observation) {
		super();
		this.name = name;
		this.country = country;
		this.year = year;
		this.areaCovered = areaCovered;
		this.observation = new Galamsey[20];
		this.count=0;
	}
	
	public int getLargestValue() {
		return highCol;
	}
	
	public double getAvgValue() {
		int total=0;
		for (Galamsey galamsey : observation) {
			total=total+galamsey.getColourValue();
		}
		return total/observation.length;
	}
	
	public Galamsey[] listGalamsey(int limit) {
		Galamsey[] g=new Galamsey[observation.length];
		int i =0;
		for (Galamsey galamsey : observation) {
			if (galamsey.getColourValue()>limit) {
				g[i++]=galamsey;
			}
		}
		return g;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getAreaCovered() {
		return areaCovered;
	}

	public void setAreaCovered(double areaCovered) {
		this.areaCovered = areaCovered;
	}

	public Galamsey[] getObservations() {
		return observation;
	}

	public void addGalamsey(Galamsey galamsey) {
		if (count>=observation.length) {
			expand();
		}
		if (galamsey.getColourValue()>highCol) {
			highCol=galamsey.getColourValue();
		}
		observation[count++]=galamsey;
	}
	private void expand() {
		Galamsey[] g = new Galamsey[count*2];
		System.arraycopy(observation, 0, g, 0,observation.length);
		observation=g;
	}
}
