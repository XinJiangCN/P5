import java.util.ArrayList;
import java.util.List;

/**
 * Generic class to represent a Graph Node.
 * 
 * @param <V>
 *            Generic data type for Vertex
 * @param <E>
 *            Generic data type for Edge
 * 
 * DO NOT MODIFY THIS CLASS.
 * 
 * @author CS367
 */
public class GraphNode<V, E> {

	private int id;
	private V vertexData;
	private List<E> outEdges;

	/**
	 * Constructor to create a GraphNode object
	 * 
	 * @param vertexData
	 *            data associated with the node
	 * @param id
	 *            unique integer to identify the node
	 */
	public GraphNode(V vertexData, int id) {
		this.id = id;
		this.vertexData = vertexData;
		this.outEdges = new ArrayList<E>();
	}

	/**
	 * Constructor to create a GraphNode object
	 * 
	 * @param vertexData
	 *            data associated with the node
	 * @param outEdges
	 *            list of edges originating from this node
	 * @param id
	 *            unique integer to identify the node
	 */
	public GraphNode(V vertexData, List<E> outEdges, int id) {
		this.id = id;
		this.vertexData = vertexData;
		this.outEdges = outEdges;
	}

	/**
	 * Getter method for id
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter method for data
	 * 
	 * @return vertexData
	 */
	public V getVertexData() {
		return vertexData;
	}

	/**
	 * Setter method for data
	 * 
	 * @param vertexData
	 *            data to be set as vertexData
	 */
	public void setVertexData(V vertexData) {
		this.vertexData = vertexData;
	}

	/**
	 * Getter method for outgoing edges
	 * 
	 * @return list of outgoing edges
	 */
	public List<E> getOutEdges() {
		return outEdges;
	}

	/**
	 * Setter method for outgoing edges
	 * 
	 * @param outEdges
	 *            list of outgoing edges
	 */
	public void setOutEdges(List<E> outEdges) {
		this.outEdges = outEdges;
	}

	/**
	 * Adds an outgoing edge
	 * 
	 * @param outEdge
	 *            edge to be added to the list of outgoing edges
	 */
	public void addOutEdge(E outEdge) {
		this.outEdges.add(outEdge);
	}

	@Override
	public String toString() {
		return outEdges.toString();
	}

}
