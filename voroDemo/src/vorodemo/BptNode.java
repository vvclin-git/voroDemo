package vorodemo;

public class BptNode implements Comparable<BptNode>{
	Site leftSite;
	Site rightSite;
	Directrix dictx;
	String type; // left, right, query, vertex, leftBound, rightBound
	Edge edge;
	float x, y;	
	public BptNode(String type, Site leftSite, Site rightSite, Site site, Directrix dictx) {
		this.leftSite = leftSite;
		this.rightSite = rightSite;
		this.dictx = dictx;
		this.type = type;		
		this.x = site.x();
		if (type == "leftBound") {
			this.x = Float.NEGATIVE_INFINITY;
			this.y = Float.NEGATIVE_INFINITY;
		}
		if (type == "rightBound") {
			this.x = Float.POSITIVE_INFINITY;
			this.y = Float.POSITIVE_INFINITY;
		}
	}
	public BptNode(String type, Site querySite) { // for query
		this.x = querySite.x();
	}
	public BptNode(String type, BptNode bptNode) { // for transforming a normal node into a vertex
		this.x = bptNode.x();
		this.y = bptNode.y();
		this.type = "vertex";
	}
	public void update() {
		if ((type == "left" | type == "right")) {
			if (leftSite.y() != dictx.y() & rightSite.y() != dictx.y()) {				
				float da, db, dc, h;
				float x1, x2;
				da = a(leftSite) - a(rightSite);
				db = b(leftSite) - b(rightSite);
				dc = c(leftSite) - c(rightSite);
				h = (float) (Math.pow(db, 2) - 4 * da * dc);
				if (h >= 0) {
					x1 = (float) ((-db - (float) Math.sqrt(h)) / da * 0.5);
					x2 = (float) ((-db + (float) Math.sqrt(h)) / da * 0.5);
					//System.out.println(x1 + "," + x2);
					if (type == "left") {
						if (x1 > x2) {
							x = x2;				
						}
						else {
							x = x1;
						}
					}
					else if (type == "right"){
						if (x1 < x2) {
							x = x2;				
						}
						else {
							x = x1;
						}
					}					
				}
			}
			
			y = (float) (a(leftSite) * Math.pow(x, 2) + b(leftSite) * x + c(leftSite));
		}
	}
	private float a(Site site) {
		return (float) (0.5 / (site.y() - dictx.y()));
	}
	private float b(Site site) {
		return -site.x() / (site.y() - dictx.y());
	}
	private float c(Site site) {
		return (float) (0.5 / (site.y() - dictx.y()) * ((Math.pow(site.x(), 2) + Math.pow(site.y(), 2)) - Math.pow(dictx.y(), 2)));
	}
	public float x() {		
		return x;
	}
	public float y() {
		return y;
	}
	public boolean isConverge(BptNode that) {
		float m1, m2, m12;
		float x1, y1, x2, y2;
		float xInt, yInt;
		float c12;
		x1 = this.x();
		y1 = this.y();
		x2 = that.x();
		y2 = that.y();
		m1 = -1 / this.getLeftSite().slopeTo(this.getRightSite());
		m2 = -1 / that.getLeftSite().slopeTo(that.getRightSite());
		m12 = (that.y() - this.y()) / (that.x() - this.y());
		c12 = -this.x() * m12 + this.y();
		xInt = (m1 * x1 - m2 * x2 - y1 + y2) / (m1 - m2);
		yInt = m1 * (m2 * (x1 - x2) + y2) - m2 * y1 / (m1 - m2);
		if (yInt - m12 * xInt - c12 > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	public int compareTo(BptNode that) {		
		//update();		
		that.update();
		if (this.x < that.x) {
			//System.out.println(this.x + ", " + that.x + " larger");
			return -1;
		}
		if (this.x > that.x) {
			//System.out.println(this.x + ", " + that.x + " smaller");
			return 1;
		}
		// tie breaker
		if (this.type == "left" & that.type == "right") {
			//System.out.println("tiebreak -");
			return -1;			
		}
		if (this.type == "right" & that.type == "left") {
			//System.out.println("tiebreak +");
			return 1;
		}
		if (this.type == "query" & that.type == "right") {
			//System.out.println("tiebreak -");
			return -1;			
		}
		if (this.type == "query" & that.type == "left") {
			//System.out.println("tiebreak +");
			return 1;
		}
		// for debugging

		return 0;
	}
	public void setEdge(Edge edge) {
		this.edge = edge;		
	}
	public Edge getEdge() {
		return edge;
	}
	public Site getSharedSite(BptNode that) {
		if (this.getLeftSite() == that.getLeftSite()) {
			return this.getLeftSite();
		}
		if (this.getLeftSite() == that.getRightSite()) {
			return this.getLeftSite();
		}
		if (this.getRightSite() == that.getLeftSite()) {
			return this.getRightSite();
		}
		if (this.getRightSite() == that.getRightSite()) {
			return this.getRightSite();
		}
		return null;
	}
	public Site getLeftSite() {
		return leftSite;
	}
	public Site getRightSite() {
		return rightSite;
	}
	public String getType() {
		return type;
	}
}
