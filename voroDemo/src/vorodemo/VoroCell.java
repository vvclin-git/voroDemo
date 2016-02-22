package vorodemo;

import java.util.LinkedList;
import processing.core.PApplet;
public class VoroCell {
	Site site;	
//	LinkedList<BptNode> vertices = new LinkedList<BptNode>();
	LinkedList<Edge> edges = new LinkedList<Edge>();
	PApplet p;
	boolean selected = false;
	public VoroCell(Site site, PApplet p) {
		this.site = site;
		this.p = p;
	}
	public void moveSite() {
		
	}
	public boolean inCell(Point point) {
		int i = -1;
		float x = point.x();
		float y = point.y();
		Edge[] tmpEdges = new Edge[2];
		for (Edge edge : edges) {
			if (edge.isIntersect(x)) {
				i += 1;
				tmpEdges[i] = edge;
				i = -1;
			}
		}
		if (tmpEdges[0].getY(x) > tmpEdges[1].getY(x)) {
			return (tmpEdges[1].getY(x) < y & tmpEdges[0].getY(x) > y);
		}
		else {
			return (tmpEdges[0].getY(x) < y & tmpEdges[1].getY(x) > y);
		}		
	}
	public void addEdge(Edge edge) {
		if (edges.isEmpty()) {
			edges.add(edge);
			return;
		}
		if (edges.getFirst().startBptNode == edge.endBptNode) {
			edges.addFirst(edge);
		}
		else if (edges.getFirst().startBptNode == edge.startBptNode) {
			edges.addFirst(edge.twins());
		}
		else if (edges.getLast().endBptNode == edge.startBptNode) {
			edges.addLast(edge);
		}
		else if (edges.getLast().endBptNode == edge.endBptNode) {
			edges.addLast(edge.twins());
		}
	}
	public boolean isEnclosed() {
		if (edges.isEmpty()) {
			return false;
		}
		return (edges.getFirst().startBptNode == edges.getLast().endBptNode);
	}
//	public Point getCM() {
//		float cmX = 0;
//		float cmY = 0;
//		int numVertices = vertices.size();
//		for (BptNode vertex : vertices) {
//			cmX += vertex.x() / numVertices;
//			cmX += vertex.y() / numVertices;
//		}
//		return new Point(cmX, cmY, site.p);
//	}
	public void setSelected() {
		selected = true;
	}
	public void setNotSelected() {
		selected = false;
	}
	public void draw() {
		for (Edge edge : edges) {
			if (selected) {
				p.stroke(255, 0, 0);
			}
			else {
				p.stroke(255, 255, 0);
			}			
			p.strokeWeight(3);
			p.line(edge.startBptNode.x(), edge.startBptNode.y(), edge.endBptNode.x(), edge.endBptNode.y());
		}
		p.strokeWeight(1);
	}

}
