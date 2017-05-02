import java.util.*;
public class NavigationGraph implements GraphADT<Location, Path> {

    private ArrayList<GraphNode<Location, Path>> graph; //adjacency list
    private String[] edgePropertyNames;

    public NavigationGraph(String[] edgePropertyNames) {
        graph = new ArrayList<GraphNode<Location, Path>>();
    }

    /**
     * Adds a vertex to the Graph, NOTE: ID is actually the size of ArrayList
     *
     * @param vertex
     *            vertex to be added
     */
    public void addVertex(Location vertex) {
        GraphNode<Location, Path> newNode = new GraphNode(vertex, graph.size());
        graph.add(newNode);
    }

    /**
     * Creates a directed edge from src to dest. NOTE: the dest param is unused
     * since we are using adjacency list approach
     *
     * @param src
     *            source vertex from where the edge is outgoing
     * @param dest
     *            destination vertex where the edge is incoming
     * @param edge
     *            edge between src and dest
     */
    public void addEdge(Location src, Location dest, Path edge) {
        //Try to get source id
        int srcId = getGraphNode(src);

        //Throw IllegalArgumentException when source does not exist
        if (srcId == -1)
            throw new IllegalArgumentException();

        //Add edge to that node
        graph.get(srcId).addOutEdge(edge);
    }

    /**
     * Find Graph Node by Location, return -1 when does not
     * find the id, else return id
     *
     * @return id of the node
     */
    private int getGraphNode(Location location) {
        //for-each the graph
        for(int i = 0; i < graph.size(); i++)
            //return id when find the correct location
            if(graph.get(i).equals(location))
                //return id of the graph
                return graph.get(i).getId();

        //return -1 when cannot find the node
        return -1;
    }

    /**
     * Getter method for the vertices
     *
     * @return List of vertices of type V
     */
    public List<Location> getVertices() {
        //NOTE: Experimental

        //create a Location ArrayList
        ArrayList<Location> list = new ArrayList<Location>();

        //for-each nodes in the graph
        for(int i = 0; i < graph.size(); i++) {
            list.add(graph.get(i).getVertexData());
        }
        return list;
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
        //find out ID of the node in the graph
        int srcId = getGraphNode(src);

        //return null when the src does not exist
        if(srcId == -1) {
            return null;
        }

        //get outEdges
        List<Path> outEdges = graph.get(srcId).getOutEdges();

        //for each all the edges to
        for(int i = 0; i < outEdges.size(); i++)
            if(outEdges.get(i).getDestination().equals(dest))
                return outEdges.get(i);

        //return null when cannot find the path
        return  null;
    }

    /**
     * Returns the outgoing edges from a vertex
     *
     * @param src
     *            Source vertex for which the outgoing edges need to be obtained
     * @return List of edges of type E
     */
    public List<Path> getOutEdges(Location src) {
        return graph.get(getGraphNode(src)).getOutEdges();
    }

    /**
     * Returns neighbors of a vertex
     *
     * @param vertex
     *            vertex for which the neighbors are required
     * @return List of vertices(neighbors) of type V
     */
    public List<Location> getNeighbors(Location vertex) {
        if (graph.size() == 0)
            throw new IllegalArgumentException();

        List<Location> neighbors = new ArrayList<Location>();
        for(int i = 0; i < graph.size(); i++) {
            //search for the node
            if(vertex.equals(graph.get(i))) {
                //get the out edges
                List<Path> l = graph.get(i).getOutEdges();
                //add each dest to the list
                for(int j = 0; j < l.size(); j++) {
                   neighbors.add(l.get(i).getDestination());
                }
            }
        }

        //return the list
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
        //find out the position in the properties arry
        int edgePropertyNameId = -1;
        for(int i = 0; i < edgePropertyNames.length; i++)
            if(edgePropertyNames[i].equals(edgePropertyName))
                edgePropertyNameId = i;

        //if cannot find edgePropertyName then throw IllegalArgumentException
        if(edgePropertyNameId == -1)
            throw new IllegalArgumentException();

       
        	PriorityQueue<GraphTable> nodes = new PriorityQueue();
        for (GraphNode node: graph) {
        		GraphTable temp = new GraphTable(node);
        		if (node.getVertexData().equals(src)) {
        			temp.setTotalWeight(0);
        			nodes.add(temp);
        		} else {
        			temp.setTotalWeight(Double.MAX_VALUE);
        			nodes.add(temp);
        		}
        		temp.setPredecessor(null);
        }
        	
        int passedNodes = 0;
        while (!nodes.isEmpty()) {
        		GraphTable smallest = nodes.poll();
        		if (smallest.getNode().getVertexData().equals(dest)) {
        			List<Location> paths = new ArrayList<Location>();
        			for (int i = 0; i < passedNodes - 1; i++) {
        				paths.add(smallest.getNode().getVertexData());
        			}
        		}
        	
        }
        
        
        return null;
    }

    /**
     * Getter method for edge property names
     *
     * @return array of String that denotes the edge property names
     */
    public String[] getEdgePropertyNames() {
        return edgePropertyNames;
    }

    /**
     * Return a string representation of the graph
     *
     * @return String representation of the graph
     */
    public String toString() {
        return "MotherFucker";
    }


    /**
     * Returns a Location object given its name
     *
     * @param name
     *            name of the location
     * @return Location object
     */
    public Location getLocationByName(String name) {
        //for each node
        for(int i = 0; i < graph.size(); i++)
            //if Location name is correct
            if(graph.get(i).getVertexData().getName().equals(name))
                //return Location
                return graph.get(i).getVertexData();
        //else return null
        return null;
    }

}
