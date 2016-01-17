package vorodemo;
import java.util.ArrayList;
import java.util.TreeMap;
import processing.core.PApplet;
public class BeachLine {
	TreeMap<BptNode, Parabola> beachLineTree = new TreeMap<BptNode, Parabola>();
	PApplet p;
	Voronoi voronoi;
	float r = 2; 
	public BeachLine(Voronoi voronoi, PApplet p) {
		this.voronoi = voronoi;
	}
	public void addArc(Site site) {
		if (beachLineTree.isEmpty()) {
			// create boundary nodes
			BptNode newleftNode = new BptNode("leftBound", null, site, site, voronoi.dictx);
			BptNode newrightNode = new BptNode("rightBound", site, null, site, voronoi.dictx);
			// create new arc
			Parabola newArc = new Parabola(voronoi.dictx, site, newleftNode, newrightNode, voronoi.p);
			site.setPara(newArc);
			beachLineTree.put(newleftNode, newArc);
			beachLineTree.put(newrightNode, null);
		}
		else {
			// looking for the existing arc for adding a new one
			BptNode queryNode = new BptNode("query", site);
			BptNode oldLeftNode = beachLineTree.floorKey(queryNode);										
			BptNode oldRightNode = beachLineTree.ceilingKey(queryNode);
			Parabola oldArc = beachLineTree.get(oldLeftNode).clone();
			// create new nodes
			BptNode newLeftNode = new BptNode("left", oldLeftNode.rightSite, site, site, voronoi.dictx);
			BptNode newRightNode = new BptNode("right", site, oldRightNode.leftSite, site, voronoi.dictx);
			// create new arc
			Parabola newArc = new Parabola(voronoi.dictx, site, newLeftNode, newRightNode, voronoi.p);
			beachLineTree.put(newLeftNode, newArc);			
			// modify existing arcs (break)
			beachLineTree.get(oldLeftNode).setRightBptNode(newLeftNode);
			beachLineTree.put(newRightNode, oldArc);
			beachLineTree.get(newRightNode).setLeftBptNode(newRightNode);			
		}
	}
	public void removeArc(BptNode leftBpt, BptNode rightBpt) {
		
	}
	public void draw() {
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			Parabola arc = beachLineTree.get(bptNode);
			bptNode.update();
			System.out.println(bptNode.x + " " + bptNode.y);
			p.ellipse(bptNode.x, bptNode.y, 2, 2);
			if (arc != null) {
				arc.draw();
			}
		}
	}

}
