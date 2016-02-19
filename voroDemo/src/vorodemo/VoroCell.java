package vorodemo;

import java.util.LinkedList;
import processing.core.PApplet;
public class VoroCell {
	Site site;	
//	LinkedList<BptNode> vertices = new LinkedList<BptNode>();
	LinkedList<Edge> edges = new LinkedList<Edge>();
	PApplet p;
	public VoroCell(Site site, PApplet p) {
		this.site = site;
		this.p = p;
	}
	public void moveSite() {
		
	}
	public boolean inCell(Point point) {
		return false;
	}
	public void addEdge(Edge edge) {
		if (edges.getFirst().startBptNode == edge.endBptNode) {
			edges.addFirst(edge);
		}
		else if (edges.getFirst().startBptNode == edge.startBptNode) {
			edges.addFirst(edge.twins());
		}
		else if (edges.getLast().endBptNode == edge.startBptNode) {
			edges.addFirst(edge);
		}
		else if (edges.getLast().endBptNode == edge.endBptNode) {
			edges.addFirst(edge.twins());
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
	public void draw() {
		for (Edge edge : edges) {
			p.stroke(255, 255, 0);
			p.strokeWeight(5);
			p.line(edge.startBptNode.x(), edge.startBptNode.y(), edge.endBptNode.x(), edge.endBptNode.y());
		}
	}

}
