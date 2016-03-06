package vorodemo;

public class BptNode implements Comparable<BptNode>{
	Site leftSite;
	Site rightSite;
	Site site;
//	Point outPoint;
	Directrix dictx;
	String type; // left, right, query, vertex, leftBound, rightBound, single
	Edge edge;	
	float x, y;
	boolean processed = false;
	public BptNode(String type, Site leftSite, Site rightSite, Site site, Directrix dictx) {		
		this.leftSite = leftSite;
		this.rightSite = rightSite;
		this.dictx = dictx;
		this.type = type;		
		this.site = site;
		this.x = site.x();		
		if (type !=  "leftBound" & type != "rightBound") {
			if (leftSite.y() == dictx.y()) {
				this.y = (float) (a(rightSite) * Math.pow(x, 2) + b(rightSite) * x + c(rightSite));				
			}
			if (rightSite.y() == dictx.y()) {
				this.y = (float) (a(leftSite) * Math.pow(x, 2) + b(leftSite) * x + c(leftSite));				
			}
		}		
		
		if (type == "leftBound") {
			this.x = Float.NEGATIVE_INFINITY;
			this.y = Float.NEGATIVE_INFINITY;
		}
		if (type == "rightBound") {
			this.x = Float.POSITIVE_INFINITY;
			this.y = Float.POSITIVE_INFINITY;
		}
//		this.outPoint = new Point(this.x, this.y, site.p);
	}
	public BptNode(String type, Site querySite) { // for query
		this.x = querySite.x();
	}
	public BptNode(String type, BptNode bptNode) { // for transforming a normal node into a vertex
		this.x = bptNode.x();
		this.y = bptNode.y();
		this.leftSite = bptNode.getLeftSite();
		this.rightSite = bptNode.getRightSite();
		this.type = "vertex";
	}
	public void update() {		
		if ((type == "left" | type == "right")) {
			if (leftSite.y() != dictx.y() & rightSite.y() != dictx.y()) {
				if (leftSite.y() != rightSite.y()) {					
					float da, db, dc, h;
					float x1, x2;
					da = a(leftSite) - a(rightSite);
					db = b(leftSite) - b(rightSite);
					dc = c(leftSite) - c(rightSite);
					h = (float) (Math.pow(db, 2) - 4 * da * dc);
					if (h >= 0) {
						x1 = (float) ((-db - (float) Math.sqrt(h)) / da * 0.5);
						x2 = (float) ((-db + (float) Math.sqrt(h)) / da * 0.5);					
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
				else { // special case (both left and right site share the same y-coordinate)
					x = (leftSite.x() + rightSite.x()) / 2;
				}
				
			}
			if (type == "right") {
				y = (float) (a(rightSite) * Math.pow(x, 2) + b(rightSite) * x + c(rightSite));
			}			
			if (type == "left") {
				y = (float) (a(leftSite) * Math.pow(x, 2) + b(leftSite) * x + c(leftSite));
			}			
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
	public String getXY() {
		return "(" + x + ", " + y + ")";
	}
	public void setProcessed() {
		processed = true;
	}
	public boolean isProcessed() {
		return processed;
	}
	public int getSiteOrient() {
		// return its main site orientation to alternative site
		if (type == "left") {
			// main site = rightSite, alter site = leftSite
			if (rightSite.x() < leftSite.x()) {
				return -1;
			}
			else if (rightSite.x() > leftSite.x()) {
				return 1;
			}
			else {
				return 0;
			}
		}
		if (type == "right") {
			// main site = leftSite, alter site = rightSite
			if (leftSite.x() < rightSite.x()) {
				return -1;
			}
			else if (leftSite.x() > rightSite.x()) {
				return 1;
			}
			else {
				return 0;
			}
		}
		return 2;
	}
	public int compareTo(BptNode that) {		
		//update();		
		that.update();
		if (this.x < that.x) {
//			System.out.println(this.x + ", " + that.x + " larger");
			return -1;
		}
		if (this.x > that.x) {
//			System.out.println(this.x + ", " + that.x + " smaller");
			return 1;
		}
		// tie breaker
		if (this.type == "left" & that.type == "right") {
//			System.out.println("tiebreak -");
			return -1;			
		}
		if (this.type == "right" & that.type == "left") {
//			System.out.println("tiebreak +");
			return 1;
		}
		if (this.type == "query" & that.type == "right") {
//			System.out.println("tiebreak -");
			return -1;			
		}
		if (this.type == "query" & that.type == "left") {
//			System.out.println("tiebreak +");
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
	public Site getNotSharedSite(BptNode that) {
		if (this.getLeftSite() == that.getLeftSite()) {
			if (this.getRightSite() != that.getRightSite()) {
				return this.getRightSite();
			}			
		}
		if (this.getLeftSite() == that.getRightSite()) {
			if (this.getRightSite() != that.getLeftSite()) {
				return this.getRightSite();
			}			
		}
		if (this.getRightSite() == that.getLeftSite()) {
			if (this.getLeftSite() != that.getRightSite()) {
				return this.getLeftSite();
			}			
		}
		if (this.getRightSite() == that.getRightSite()) {
			if (this.getLeftSite() != that.getLeftSite()) {
				return this.getLeftSite();
			}			
		}
		return null;
	}
	public Site getLeftSite() {
		return leftSite;
	}
	public Site getRightSite() {
		return rightSite;
	}
	public Site getSite() {
		return site;
	}
	public String getType() {
		return type;
	}
	public float distSqrTo(Point that) {
		return (float) (Math.pow((x - that.x()), 2) + Math.pow((y - that.y()), 2));
	}
	public BptNode getTwin() {
		if (this == edge.startBptNode) {
			return edge.endBptNode;
		}
		else {
			return edge.startBptNode;
		}
	}
//	public Point toPoint() {		
//		return this.outPoint;
//	}
//	public float distSqrToSite(Site site) {
//		return (float) this.outPoint.distSqrTo(site);
//	}
	
}
