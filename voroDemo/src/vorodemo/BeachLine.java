package vorodemo;
import java.util.ArrayList;
import java.util.TreeMap;
import processing.core.PApplet;
public class BeachLine {
	TreeMap<BptNode, Parabola> beachLineTree;
	PApplet p;
	Voronoi voronoi;
	float r = 2; 
	public BeachLine(Voronoi voronoi, PApplet p) {
		this.voronoi = voronoi;
		this.p = p;
		this.beachLineTree = voronoi.beachLineTree;
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
		// remove arcs		
		Parabola nextArc = beachLineTree.get(rightBpt).clone();
		beachLineTree.remove(leftBpt);
		beachLineTree.remove(rightBpt);		
		// generate new arcs
		BptNode newBptNode = new BptNode("left", leftBpt.getLeftSite(), rightBpt.getRightSite(), rightBpt.getRightSite(), voronoi.dictx);
		beachLineTree.put(newBptNode, nextArc);
		beachLineTree.get(newBptNode).setLeftBptNode(newBptNode);
		beachLineTree.floorEntry(leftBpt).getValue().setRightBptNode(newBptNode);
		
	}
	public void update() {
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			bptNode.update();
		}
	}
	public void draw() {
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			Parabola arc = beachLineTree.get(bptNode);			
			p.ellipse(bptNode.x, bptNode.y, r, r);
			if (arc != null) {
				arc.draw();
			}
		}
	}

}
