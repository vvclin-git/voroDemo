package vorodemo;
import processing.core.PApplet;

public class Site extends Point {
	Parabola parabola = null;
	boolean processed = false;
	VoroCell voroCell = new VoroCell(this, p);
//	ArrayList<Edge> edges;
	public Site(float x, float y, PApplet p) {		
		super(x, y, p);
	}	
	public Site copy() {
		return new Site(this.x, this.y, this.p);
	}
	public void setPara(Parabola parabola) {
		this.parabola = parabola;
	}
	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed() {
		processed = true;
	}
	public int compareTo(Site that) {
		if (this.y < that.y) {
			return -1;
		}    
		if (this.y > that.y) {
			return 1;
		}		
		return 0;
	}
	public void addEdge(Edge edge) {
		voroCell.addEdge(edge);
	}
	public String toOutString() {
		return x + "," + y;
	}
	public VoroCell getVoroCell() {
		return voroCell;
	}
	public static void main(String args[]) {
		Site a = new Site(1, 2, null);
		Site b = new Site(1, 2, null);
		System.out.println(a.equalTo(b));
	}
}
