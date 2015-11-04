package vorodemo;
import processing.core.PApplet;

public class BreakPoint extends Point{
	Parabola paraLeft, paraRight;
	String type;
	public BreakPoint(float x, float y, String type, Parabola paraLeft, Parabola paraRight, PApplet p) {
		// TODO Auto-generated constructor stub
		super(x, y, p);
		this.type = type;
		this.paraLeft = paraLeft;
		this.paraRight = paraRight;
	}
	BreakPoint copy() {
		return new BreakPoint(this.x, this.y, this.type, this.paraLeft, this.paraRight, this.p);
	}
	void update() {
	    if (type == "left") {
	      this.x = paraLeft.leftInterPt(paraRight).x();
	      this.y = paraLeft.y(this.x);	      
	    }
	    else if (type == "right"){
	      this.x = paraRight.rightInterPt(paraLeft).x();
	      this.y = paraRight.y(this.x);	      
	    }
	    else if (type == "leftBound") {
	    	this.x = 0;
	    	this.y = paraRight.y(this.x);
	    }
	    else if (type == "rightBound") {
	    	this.x = p.width;
	    	this.y = paraLeft.y(this.x);
	    }
	  }

}
