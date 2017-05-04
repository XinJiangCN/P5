/**
 * Class that represents a location
 *
 * @author CS367
 */
public class Location {

	final private String name;

	/**
	 * Constructs a location object
	 *
	 * @param name
	 *            name of the location
	 */
	public Location(String name) {
		this.name = name;
	}

	/**
	 * Getter method for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Compare with another location for equality
	 * 
	 * @param otherLocation
	 *            another location to be compared against
	 * @return true if equal else false
	 */
	public boolean equals(Location otherLocation) {
		if (this == otherLocation) {
			return true;
		} else if (otherLocation == null) {
			return false;
		} else if (!name.equalsIgnoreCase(otherLocation.getName())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "{" + name + "}";
	}
}
