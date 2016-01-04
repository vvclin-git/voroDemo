package vorodemo;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.PriorityQueue;

import processing.core.PApplet;

public class Voronoi extends PApplet{
	PriorityQueue<Event> events = new PriorityQueue<Event>();
	ArrayList<BreakPoint> breakPoints = new ArrayList<BreakPoint>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<Circle> circles = new ArrayList<Circle>();
	ArrayList<Site> sites = new ArrayList<Site>();
	TreeMap<BptNode, BptNode> beachLine = new TreeMap<BptNode, BptNode>();
	TreeMap<BptNode, Parabola> arcs = new TreeMap<BptNode, Parabola>();	
	Directrix dictx;
	PApplet p;
	public Voronoi(float dictY, PApplet p) {		
		this.p = p;
		dictx = new Directrix(dictY, p);
	}
	public void addSite(Site site) {
		sites.add(site);
		//add site event
		events.add(new SiteEvent(this, site));
	}
	public void draw() {
		dictx.draw();
		for (BreakPoint bpt : breakPoints) {
			bpt.draw();
		}
		for (Parabola arc : arcs.values()) {
			arc.draw();
		}
		for (Site site : sites) {
			site.draw();
		}
		for (Circle circle : circles) {
			circle.draw();
		}
	}
	public void update() {
		for (BreakPoint bpt : breakPoints) {
			bpt.update();
		}
	}
}
