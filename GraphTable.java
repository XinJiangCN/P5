
class GraphTable implements Comparable<GraphTable> {
    private double totalWeight;
    private GraphNode predecessor;
    private boolean isVisited;
    private GraphNode node;
    
    public GraphTable(GraphNode graphGraphNodeode) {
    		totalWeight = 0.0;
    		node = graphGraphNodeode;
    		isVisited = false;
    		predecessor = null;
    }
    public void setVisited() {
    		isVisited = true;
    }
    public void setTotalWeight(double tw) {
    		totalWeight = tw;
    }
    public void setPredecessor(GraphNode pre) {
    		predecessor = pre;
    }
    public GraphNode getPredecessor() {
    		return predecessor;
    }
    public boolean getisVisited() {
    		return isVisited;
    }
    public double gettotalWeight() {
    		return totalWeight;
    }
    public GraphNode getNode() {
    		return node;
    }
	@Override
	public int compareTo(GraphTable o) {
		if (totalWeight < o.totalWeight)
			return -1;
		if (totalWeight > o.totalWeight)
			return 1;
		return 0;
	}
    
}
