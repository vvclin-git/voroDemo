package vorodemo;
import processing.core.PApplet;

public class Site extends Point {
	public Site(float x, float y, PApplet p) {
		// TODO Auto-generated constructor stub
		super(x, y, p);
	}	
	Site copy() {
		return new Site(this.x, this.y, this.p);
	}
}
