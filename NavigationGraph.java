import java.util.*;
public class NavigationGraph implements GraphADT<Location, Path> {

    /**
     * Wrapper for the priority queue
     */
    class NodeWrapper implements Comparable<NodeWrapper> {
        private int id; //id of the node
        private double weight; //weight of the edge
        private ArrayList<Integer> destId;

        public NodeWrapper(int id, double weight) {
            this.id = id;
            this.weight = weight;
            destId = new ArrayList<Integer>();
            for(int i = 0; i < graph.get(id).getOutEdges().size(); i++) {
                //add dest id
                destId.add(getGraphNode(graph.get(id).getOutEdges().get(i).getDestination()));
            }
        }

        public double getWeight() {
            return weight;
        }

        public int getId() {
            return id;
        }

        public ArrayList<Integer> getDestId() {
            return destId;
        }

        public int compareTo(NodeWrapper n) {
            return new Double(weight).compareTo(new Double(n.getWeight()));
        }

        public int compare(NodeWrapper n1, NodeWrapper n2) {
            return new Double(n1.getWeight()).compareTo(new Double(n2.getWeight()));
        }
    }

    private ArrayList<GraphNode<Location, Path>> graph; //adjacency list
    private String[] edgePropertyNames;

    public NavigationGraph(String[] edgePropertyNames) {
        graph = new ArrayList<GraphNode<Location, Path>>();
        this.edgePropertyNames = edgePropertyNames;
    }

    /**
     * Adds a vertex to the Graph, NOTE: ID is actually the size of ArrayList
     *
     * @param vertex
     *            vertex to be added
     */
    public void addVertex(Location vertex) {
        GraphNode<Location, Path> newNode = new GraphNode<Location, Path>(vertex, graph.size());
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
            if(graph.get(i).getVertexData().equals(location))
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
        //priority queue for the dijkstra optimization
        PriorityQueue<NodeWrapper> pq = new PriorityQueue<NodeWrapper>();

        for(int i = 0; i < edgePropertyNames.length; i++)
            if(edgePropertyNames[i].equals(edgePropertyName))
                edgePropertyNameId = i;

        //if cannot find edgePropertyName then throw IllegalArgumentException
        if(edgePropertyNameId == -1)
            throw new IllegalArgumentException();

        //prepare dist arry
        double[] dist = new double[graph.size()];
        //prepare the visited array
        int[] visited = new int[graph.size()];
        //record path
        int[] prev = new int[graph.size()];
        //Path list
        ArrayList<Path> path = new ArrayList<Path>();

        //initialize the dist array, set dist to max
        for(int i = 0; i < dist.length; i++) {
            dist[i] = Double.MAX_VALUE;
            //set visited to false
            visited[i] = 0;
            //previous
            prev[i] = -1;
        }

        int srcId = getGraphNode(src);

        //dest from source to source is 0
        dist[srcId] = 0;
        visited[srcId] = 1;

        //push src to the queue
        pq.add(new NodeWrapper(srcId, 0));

        while(!pq.isEmpty()) {
            //poll the node from pq
            NodeWrapper wrapper = pq.poll();
            //get the graph node
            GraphNode<Location, Path> n = graph.get(wrapper.getId());
            //get the out edges from the node
            List<Path> edges = n.getOutEdges();
            //set the node as visited
            visited[wrapper.getId()] = 1;
            //for each edge
            for(int i = 0; i < edges.size(); i++) {
                //get node id
                int destId = wrapper.getDestId().get(i);
                //get edge weight
                double edgeWeight = edges.get(i).getProperties().get(edgePropertyNameId);
                //get new weight
                double newWeight = wrapper.getWeight() + edgeWeight;
                //greedy
                if(newWeight < dist[destId]) {
                    dist[destId] = newWeight;
                    prev[destId] = wrapper.getId();
                    //if not visited set as visited
                    if(visited[destId] == 0) {
                        pq.add(new NodeWrapper(destId, newWeight));
                    }
                }
            }
        }

        //get ids
        int pathDestId  = getGraphNode(dest);
        int pathSrcId = prev[pathDestId];

        //search to the srouce
        while(pathSrcId != -1) {
            Location pathSrc = graph.get(pathSrcId).getVertexData();
            Location pathDest = graph.get(pathDestId).getVertexData();

            path.add(getEdgeIfExists(pathSrc, pathDest));

            pathDestId = pathSrcId;
            pathSrcId = prev[pathDestId];
        }
        //swap the order
        for(int i = 0; i < path.size() / 2; i++) {
            Path tmp = path.get(i);
            path.set(i, path.get(path.size() - i - 1));
            path.set(path.size() - i - 1, tmp);
        }

        return path;
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
        String line = "";
        for(int i = 0; i < graph.size(); i++) {
            //get the node
            GraphNode<Location, Path> node = graph.get(i);
            //Start of the line
            for(int j = 0; j < node.getOutEdges().size(); j++) {
                Path currentPath = node.getOutEdges().get(j);
                line += (j == node.getOutEdges().size() - 1) ? "" + currentPath : currentPath + ",";
            }
            //add
            line += (i == graph.size() - 1) ? "" : "\n";
        }
        return line;
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
        for(int i = 0; i < graph.size(); i++) {
            //if Location name is correct
            if(graph.get(i).getVertexData().getName().equals(name)) {
                //return Location
                return graph.get(i).getVertexData();
            }
        }
        //else return null
        return null;
    }

}
