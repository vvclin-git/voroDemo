package vorodemo;

public class BptNode implements Comparable<BptNode>{
	Site leftSite;
	Site rightSite;
	Directrix dictx;
	String type; // left, right, query, leftBound, rightBound
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
		if ((type == "left" | type == "right")) {
			update();
		}		
		return x;
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
			return -1;
		}
		if (this.type == "right" & that.type == "left") {
			return 1;
		}
		// for debugging
//		System.out.println(this.x + ", " + that.x + " equal");
		//return 0;
//		else if (this.type != "query") {
//			if (this.equalsTo(that)) {
//				return 0;
//			}
//		}
		//System.out.println(this.x + ", " + that.x + " equal");
		return 0;
	}
	
	public Site getSharedSite(BptNode that) {
		if (this.leftSite == that.leftSite) {
			return this.leftSite;
		}
		if (this.leftSite == that.rightSite) {
			return this.leftSite;
		}
		if (this.rightSite == that.leftSite) {
			return this.rightSite;
		}
		if (this.rightSite == that.rightSite) {
			return this.rightSite;
		}
		return null;
	}	
}
