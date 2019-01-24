
//points class that can store doubles

public class Points {

	private double start;
	private double end;
	
	public Points(double start, double end){
		setStart(start);
		setEnd(end);
	}
	
	public double getStart() {
		return start;
	}
	public void setStart(double start) {
		this.start = start;
	}
	public double getEnd() {
		return end;
	}
	public void setEnd(double end) {
		this.end = end;
	}
	
}
