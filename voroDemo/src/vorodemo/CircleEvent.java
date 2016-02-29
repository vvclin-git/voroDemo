package vorodemo;



public class CircleEvent extends Event{
	Circle circle;
	public CircleEvent(Voronoi voronoi, Circle circle) {
		super(voronoi, "circle");
		this.y = circle.getLowY();
		this.circle = circle;
	}
	public void eventHandler() {
		if (circle.containProcessedBptNode()) {
			System.out.println("===circle event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());
			voronoi.circles.remove(this.circle);
			System.out.println("circle removed");
			System.out.println("===circle event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());
			return;
		}
		// modify beach line
		System.out.println("===circle event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());
		BptNode leftBptNode = circle.getBptNode1();
		BptNode rightBptNode = circle.getBptNode2();
		voronoi.beachLine.removeArc(leftBptNode, rightBptNode, circle);
		BptNode oldRightBptNode = voronoi.beachLineTree.higherKey(rightBptNode);		
		System.out.println("the left/right bptNodes retreived from the circle: " + leftBptNode + "," + rightBptNode);
		System.out.println("the next right bptNode: " + oldRightBptNode);
		System.out.println("the left/right sites retreived from the circle: " + circle.getLeftSite() + ", " + circle.getRightSite());		
		// create new bptNode and circle event		
		// determine which site produces the latest arc
		// newBptNodeSite1 = the latest site
		Site newBptNodeSite1, newBptNodeSite2;
		BptNode newBptNode;
		if (circle.getLeftSite().compareTo(circle.getRightSite()) == 1) {
			newBptNodeSite1 = circle.getLeftSite();
			newBptNodeSite2 = circle.getRightSite();
		}
		else if (circle.getLeftSite().compareTo(circle.getRightSite()) == -1) {
			newBptNodeSite1 = circle.getRightSite();
			newBptNodeSite2 = circle.getLeftSite();
		}
		else {
			newBptNodeSite1 = circle.getLeftSite();
			newBptNodeSite2 = circle.getRightSite();
		}			
		// new bptNode must be on the latest arc for convergence detection (its type must be exactly right or left)
		if (circle.getCenter().x() > newBptNodeSite1.x()) {
			newBptNode = new BptNode("right", newBptNodeSite1, newBptNodeSite2, newBptNodeSite1, voronoi.dictx);			
		}
		else {
			newBptNode = new BptNode("left", newBptNodeSite2, newBptNodeSite1, newBptNodeSite1, voronoi.dictx);
		}
		newBptNode.update();
		System.out.println("the new bptNode is: " + newBptNode);
		System.out.println("the pos of new bptNode is: " + newBptNode.getXY());
		// create new circle event
		Site sharedSite;
		// create new right circle
		BptNode nextRightBptNode = voronoi.beachLineTree.higherKey(newBptNode);
		System.out.println("new right bptnode is: " + nextRightBptNode.x());
		sharedSite = newBptNode.getSharedSite(nextRightBptNode);			
		if (sharedSite != null) {
			if (nextRightBptNode.getType() != "rightBound") {
				Circle newCircle = new Circle(newBptNode, nextRightBptNode, voronoi.dictx.y(), voronoi.p);
//				System.out.println(voronoi.dictx.y() + ", " + newCircle.y() + ", " + newCircle.getLowY() + " | " + newCircle.containProcessedBptNode() + ", " + newCircle.isConverge());				
//				System.out.println((!newCircle.containProcessedBptNode() & newCircle.isConverge() & newCircle.y() > voronoi.dictx.y()));					
				System.out.print("the new right circle | " + "contain processed bptNode: " + newCircle.containProcessedBptNode());
				System.out.print(", convergence: " + newCircle.isConverge());
				System.out.println(", create new right circle: " + (!newCircle.containProcessedBptNode() & newCircle.isConverge() & newCircle.getLowY() > voronoi.dictx.y()));
				if (!newCircle.containProcessedBptNode() & newCircle.isConverge() & newCircle.getLowY() > voronoi.dictx.y()) {
					voronoi.circles.add(newCircle);
					voronoi.events.add(new CircleEvent(voronoi, newCircle));
				}				
			}
		}
		// create new left circle
		BptNode nextLeftBptNode = voronoi.beachLineTree.lowerKey(newBptNode);
		System.out.println("new left bptnode is: " + nextLeftBptNode.x());
		sharedSite = newBptNode.getSharedSite(nextLeftBptNode);			
		if (sharedSite != null) {
			if (nextLeftBptNode.getType() != "leftBound") {				
				Circle newCircle = new Circle(nextLeftBptNode, newBptNode, voronoi.dictx.y(), voronoi.p);
//				System.out.println(voronoi.dictx.y() + ", " + newCircle.y() + ", " + newCircle.getLowY() + " | " + newCircle.containProcessedBptNode() + ", " + newCircle.isConverge());					
//				System.out.println((!newCircle.containProcessedBptNode() & newCircle.isConverge() & newCircle.y() > voronoi.dictx.y()));
				System.out.print("the new left circle | " + "contain processed bptNode: " + newCircle.containProcessedBptNode());
				System.out.print(", convergence: " + newCircle.isConverge());
				System.out.println(", create new left circle: " + (!newCircle.containProcessedBptNode() & newCircle.isConverge() & newCircle.getLowY() > voronoi.dictx.y()));				
				if (!newCircle.containProcessedBptNode() & newCircle.isConverge() & newCircle.getLowY() > voronoi.dictx.y()) {
					voronoi.circles.add(newCircle);
					voronoi.events.add(new CircleEvent(voronoi, newCircle));
				}				
			}
		}		
		voronoi.beachLineTree.put(newBptNode, new Parabola(voronoi.dictx, rightBptNode.getRightSite(), newBptNode, oldRightBptNode, voronoi.p));		
		voronoi.beachLineTree.lowerEntry(newBptNode).getValue().setRightBptNode(newBptNode);		
		// connecting edges
		BptNode vertex = new BptNode("vertex", newBptNode);
		voronoi.vertices.add(vertex);
		Edge leftEdge = leftBptNode.getEdge();
		Edge rightEdge = rightBptNode.getEdge();
		//System.out.println(leftEdge + "," + rightEdge);
		leftEdge.replaceNode(leftBptNode, vertex);
		rightEdge.replaceNode(rightBptNode, vertex);
		// for recording voronoi cells
//		if (leftEdge.isStatic()) {
//			leftEdge.getSite1().addEdge(leftEdge);
//			leftEdge.getSite2().addEdge(leftEdge);
//		}
//		if (rightEdge.isStatic()) {
//			rightEdge.getSite1().addEdge(rightEdge);
//			rightEdge.getSite2().addEdge(rightEdge);
//		}
		Edge newEdge = new Edge(newBptNode, vertex, newBptNodeSite1, newBptNodeSite2, voronoi.p);
		voronoi.edges.add(newEdge);
		newBptNode.setEdge(newEdge);		
		voronoi.circles.remove(this.circle);
		System.out.println("===circle event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());		
	}

}
