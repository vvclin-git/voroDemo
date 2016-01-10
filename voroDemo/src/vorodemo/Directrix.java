package vorodemo;
import processing.core.PApplet;
// draw a horizontal line in PApplet
public class Directrix{
	float y;
	PApplet p;
	Directrix (float y, PApplet p) {
		this.y = y;
		this.p = p;
	}
	public void draw() {
		p.stroke(255);
		p.line(0, y, p.width, y);
	}
	void move(float dist) {
		y += dist;
	}
	float y() {
		return y;
	}
	void setY(float y) {
		this.y = y;
	}
}
