import java.text.DecimalFormat;
import java.util.List;

/**
 * Class that creates a directed edge from one location to another
 * 
 * @author CS367
 *
 */
public class Path {

	private List<Double> pathProperties;

	// TODO: check at end if we use the below two necessary
	private Location source;
	private Location destination;

	/**
	 * Creates a Path object from source to destination with properties
	 * 
	 * @param source
	 *            source location for the edge
	 * @param destination
	 *            destination location for the edge
	 * @param pathProperties
	 *            list of property values associated with the edge (Note: length
	 *            of this list must agree with the propertynames in the graph)
	 */
	public Path(Location source, Location destination, List<Double> pathProperties) {
		if (source == null || destination == null || source == destination) {
			throw new IllegalArgumentException("Src: " + source + ", Dest: " + destination);
		}
		this.source = source;
		this.destination = destination;
		this.pathProperties = pathProperties;
	}

	/**
	 * Getter method for destination location
	 * 
	 * @return destination location
	 */
	public Location getDestination() {
		return destination;
	}

	/**
	 * Getter method for source location
	 * 
	 * @return source location
	 */
	public Location getSource() {
		return source;
	}

	/**
	 * Getter method for path properties
	 * 
	 * @return list of properties
	 */
	public List<Double> getProperties() {
		return this.pathProperties;
	}


    /**
     * Similar to toString, but only displays the specified property.
     *
     * @param propertyIndex
     *            The Index of the edgeProperty to be displayed.
     * @return
     *            String representation of the Path with only specified property value.
     */
	public String displayPathWithProperty(int propertyIndex) {
        DecimalFormat df = new DecimalFormat("0.00");
        String outputString = source + " ";

        double property = this.pathProperties.get(propertyIndex);
        outputString += df.format(property) + " ";

        outputString += destination;
        return outputString;
    }

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		String outputString = source + " ";

		for (Double property : this.pathProperties) {
			outputString += df.format(property) + " ";
		}

		outputString += destination;
		return outputString;
	}

}
