package vorodemo;
import processing.core.PApplet;

public class Site extends Point {
	Parabola parabola = null;
	boolean processed = false;
	public Site(float x, float y, PApplet p) {
		// TODO Auto-generated constructor stub
		super(x, y, p);
	}	
	Site copy() {
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
	public static void main(String args[]) {
		Site a = new Site(1, 2, null);
		Site b = new Site(1, 2, null);
		System.out.println(a.equalTo(b));
	}
}
