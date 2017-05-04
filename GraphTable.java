
class GraphTable implements Comparable<GraphTable> {
    
    private Double distance;
    private Location loc;

    public GraphTable(Location loc, double distance) {
        this.distance = distance;
        this.loc = loc;
    }


    public Double getDist() {
        return distance;
    }
    
    public Location getLocation() {
    	return loc;
    }
    
    public void setDist(double dist) {
        distance = dist;
    }
    
    


    @Override
    public int compareTo(GraphTable n) {
       if (distance < n.getDist())
           return -1;
       if (distance > n.getDist()) {
            return 1;
       }
        return 0;
    }
}
