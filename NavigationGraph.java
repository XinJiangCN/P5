import java.util.*;
public class NavigationGraph implements GraphADT<Location, Path> {
    final private static Double INFINITE = Double.MAX_VALUE;
    private ArrayList<GraphNode<Location, Path>> graph; //adjacency list
    private String[] edgePropertyNames;
    private int id;

    public NavigationGraph(String[] edgePropertyNames) {
        graph =  new ArrayList<GraphNode<Location,Path>>();
        id = 0;
    }

    /**
     * Adds a vertex to the Graph, NOTE: ID is actually the size of ArrayList
     *
     * @param vertex
     *            vertex to be added
     */
    public void addVertex(Location vertex) {
    	GraphNode newNode = new GraphNode(vertex, id);
        graph.add(newNode);
        id++;
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
    	GraphNode source = getGraphNode(src);
    	 if (source == null)
    		 throw new IllegalArgumentException();
         source.addOutEdge(edge);
    }

    /**
     * Find Graph Node by Location, return -1 when does not
     * find the id, else return id
     *
     * @return id of the node
     */
    private GraphNode getGraphNode(Location loc) {
    	for (GraphNode temp : graph) {
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
    	List<Path> dests = getGraphNode(src).getOutEdges();
        for (Path d : dests) {
        	if (d.getDestination().equals(dest))
        		return d;
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
        return graph.get(getGraphNode(src).getId()).getOutEdges();
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
        //prepare dist arry
        Map<Location,Double> distances = new HashMap<Location,Double>();
        Map<Location,GraphTable> previous = new HashMap<Location,GraphTable>();

        PriorityQueue<GraphTable> nodes = new PriorityQueue();

        for (GraphNode gn: graph) {
            if (getGraphNode(src).equals(gn.getVertexData())) {
                distances.put((Location)gn.getVertexData(), 0.0);
                nodes.add(new GraphTable((Location)gn.getVertexData(), 0));
            } else {
                distances.put((Location)gn.getVertexData(), INFINITE);
                nodes.add(new GraphTable((Location)gn.getVertexData(),INFINITE));
            }    
            previous.put((Location)gn.getVertexData(), null);
        }

        while (!nodes.isEmpty()) {
            GraphTable nearest = nodes.poll();
            if (nearest.getLocation().equals(dest)) {
                List<Path> paths = new ArrayList();
                while (previous.get(nearest.getLocation()) != null) {
                    paths.add(getEdgeIfExists(previous.get(nearest.getLocation()).getLocation(),nearest.getLocation()));
                    nearest = previous.get(nearest.getLocation());
                } 
                return paths;

            } 
            if (distances.get(nearest.getLocation()).equals(INFINITE))
                break;

            //get the correspond GraphTable with Location
            List<Location> neighborsLoc = getNeighbors(nearest.getLocation());
            List<GraphTable> neighbors = new ArrayList();
            for (int i = 0; i < nodes.size(); i++) {
                neighbors.add((GraphTable)nodes.toArray()[i]);
            }
            for (GraphTable table : neighbors) {
                Double newWeight = distances.get(nearest.getLocation()) + table.getDist();
                if (newWeight < distances.get(table.getLocation()))
                    distances.put(table.getLocation(), newWeight);
                previous.put(table.getLocation(), nearest);
tableRenewloop:
                for (GraphTable n : nodes) {
                    if (n.getLocation().equals(table.getLocation())) {
                        nodes.remove(n);
                        n.setDist(newWeight);
                        nodes.add(n);
                        break tableRenewloop;
                    }
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
