import java.util.*;
public class NavigationGraph implements GraphADT<Location, Path> {

    private int id;
    private List<GraphNode> nodes;
    public NavigationGraph(String[] edgePropertyNames) {
        id = 0;
        nodes = new ArrayList<GraphNode>();
    }

    /**
     * Adds a vertex to the Graph
     * 
     * @param vertex
     *            vertex to be added
     */
    public void addVertex(Location vertex) {
        GraphNode newNode = new GraphNode(vertx, id);
        nodes.add(newNode);
        id++;
    }

    /**
     * Creates a directed edge from src to dest
     * 
     * @param src
     *            source vertex from where the edge is outgoing
     * @param dest
     *            destination vertex where the edge is incoming
     * @param edge
     *            edge between src and dest
     */
    public void addEdge(Location src, Location dest, Path edge) {
        GraphNode source = getGraphNode(src);
        if (source == null) 
            throw new IllegalArgumentException();
        source.addOutEdge(edge);

       //TODO FIXIT 
       //TODO HOW TO CONNECT SRC AND DEST
    }


    private GraphNode getGraphNode(Location loc) {
        Iterator itr = nodes.iterator();
        GraphNode temp = null;
        while (itr.hasNext()) {
            temp = itr.next();
            if (temp.getVertexData().equals(loc))
                return temp;
        }
        return null;
    }


    /**
     * Getter method for the vertices
     * 
     * @return List of vertices of type V
     */
    public List<Location> getVertices() {
        return nodes;
    }

    /**
     * Returns edge if there is one from src to dest vertex else null
     * 
     * @param src
     *            Source vertex
     * @param dest
     *            Destination vertex
     * @return Edge of type E from src to dest
     */
    public Path getEdgeIfExists(Location src, Location dest) {
        List<Path> dests = getGraphNode(src).getOutEdges();
        Iterator itr = dests.iterator();
        Path temp;
        while (itr.hasNext()) {
            temp = itr.next();
            if (temp.getDestination().equals(dest))
                return temp;
        }
        return null;
    }

    /**
     * Returns the outgoing edges from a vertex
     * 
     * @param src
     *            Source vertex for which the outgoing edges need to be obtained
     * @return List of edges of type E
     */
    public List<Path> getOutEdges(Location src) {
        return getGraphNode(src).getOutEdges();
    }

    /**
     * Returns neighbors of a vertex
     * 
     * @param vertex
     *            vertex for which the neighbors are required
     * @return List of vertices(neighbors) of type V
     */
    public List<Location> getNeighbors(Location vertex) {
        List<Location> neighbors = new ArrayList<Location>();
        List<Path> paths = this.getOutEdges();
        if (paths.size() == 0) 
            throw new IllegalArgumentException();
        
        
        return neighbors;
    }

    /**
     * Calculate the shortest route from src to dest vertex using
     * edgePropertyName
     * 
     * @param src
     *            Source vertex from which the shortest route is desired
     * @param dest
     *            Destination vertex to which the shortest route is desired
     * @param edgePropertyName
     *            edge property by which shortest route has to be calculated
     * @return List of edges that denote the shortest route by edgePropertyName
     */
    public List<Path> getShortestRoute(Location src, Location dest, String edgePropertyName) {

        return null;
    }

    /**
     * Getter method for edge property names
     * 
     * @return array of String that denotes the edge property names
     */
    public String[] getEdgePropertyNames() {

        return null;
    }

    /**
     * Return a string representation of the graph
     * 
     * @return String representation of the graph
     */
    public String toString() {

        return null;
    }


    /**
     * Returns a Location object given its name
     * 
     * @param name
     *            name of the location
     * @return Location object
     */
    public Location getLocationByName(String name) {
        return null; //TODO: implement correctly. 
    }

}
