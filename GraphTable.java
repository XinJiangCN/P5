class GraphTable<V> {
    private boolean visited;
    private int totalWeight;
    private V predecessor;
    final int MAX_WEIGHT = 65536;

    public GraphTable() {
        visited = false;
        totalWeight = MAX_WEIGHT;    
        predecessor = null;
    }
    
    public int getTotalWeight() {
        return totalWeight;
    }
    
    public V getPredecessor() {
        return predecessor;
    }

    public boolean getVisited() {
        return visited;
    }
    
    public void setVisited() {
        visited = true;
    }

    public void setTotalWeight(int weight) {
        totalWeight = weight;
    }

    public void setPredecessor(V pre) {
        predecessor = pre;
    }
}
