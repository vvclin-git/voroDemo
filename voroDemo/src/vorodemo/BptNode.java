package vorodemo;

public class BptNode implements Comparable<BptNode>{
	Site leftSite;
	Site rightSite;
	Directrix dictx;
	String type; // left, right, query, leftBound, rightBound
	float x;
	public BptNode(String type, Site leftSite, Site rightSite, Directrix dictx) {
		this.leftSite = leftSite;
		this.rightSite = rightSite;
		this.dictx = dictx;
		this.type = type;		
		update();		
		if (type == "leftBound") {
			this.x = Float.NEGATIVE_INFINITY;
		}
		if (type == "rightBound") {
			this.x = Float.POSITIVE_INFINITY;
		}
	}
	public BptNode(String type, Site querySite) { // for query
		this.x = querySite.x();
	}	
	private void update() {
		if (type == "left" | type == "right") {
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
	public int compareTo(BptNode that) {		
		update();		
		that.update();		
		if (this.x < that.x) {
			return -1;
		}
		if (this.x > that.x) {
			return 1;
		}
		return 0;
	}	

}
