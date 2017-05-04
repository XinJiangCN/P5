import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Driver class that reads/parses the input file and creates NavigationGraph
 * object.
 *
 * @author CS367
 *
 */
public class MapApp {

    private NavigationGraph graphObject;

    /**
     * Constructs a MapApp object
     *
     * @param graph
     *            NaviagtionGraph object
     */
    public MapApp(NavigationGraph graph) {
        this.graphObject = graph;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java MapApp <pathToGraphFile>");
            System.exit(1);
        }

        // read the filename from command line argument
        String locationFileName = args[0];
        try {
            NavigationGraph graph = createNavigationGraphFromMapFile(locationFileName);
            MapApp appInstance = new MapApp(graph);
            appInstance.startService();

        } catch (FileNotFoundException e) {
            System.out.println("GRAPH FILE: " + locationFileName + " was not found.");
            System.exit(1);
        } catch (InvalidFileException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }

    /**
     * Displays options to user about the various operations on the loaded graph
     */
    public void startService() {

        System.out.println("Navigation App");
        Scanner sc = new Scanner(System.in);

        int choice = 0;
        do {
            System.out.println();
            System.out.println("1. List all locations");
            System.out.println("2. Display Graph");
            System.out.println("3. Display Outgoing Edges");
            System.out.println("4. Display Shortest Route");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                sc.next();
                System.out.println("Please select a valid option: ");
            }
            choice = sc.nextInt();

            switch (choice) {
            case 1:
                System.out.println(graphObject.getVertices());
                break;
            case 2:
                System.out.println(graphObject.toString());
                break;
            case 3: {
                System.out.println("Enter source location name: ");
                String srcName = sc.next();
                Location src = graphObject.getLocationByName(srcName);

                if (src == null) {
                    System.out.println(srcName + " is not a valid Location");
                    break;
                }

                List<Path> outEdges = graphObject.getOutEdges(src);
                System.out.println("Outgoing edges for " + src + ": ");
                for (Path path : outEdges) {
                    System.out.println(path);
                }
            }
                break;

            case 4:
                System.out.println("Enter source location name: ");
                String srcName = sc.next();
                Location src = graphObject.getLocationByName(srcName);

                System.out.println("Enter destination location name: ");
                String destName = sc.next();
                Location dest = graphObject.getLocationByName(destName);

                if (src == null || dest == null) {
                    System.out.println(srcName + " and/or " + destName + " are not valid Locations in the graph");
                    break;
                }

                if (src == dest) {
                    System.out.println(srcName + " and " + destName + " correspond to the same Location");
                    break;
                }

                System.out.println("Edge properties: ");
                // List Edge Property Names
                String[] propertyNames = graphObject.getEdgePropertyNames();
                for (int i = 0; i < propertyNames.length; i++) {
                    System.out.println("\t" + (i + 1) + ": " + propertyNames[i]);
                }
                System.out.println("Select property to compute shortest route on: ");
                int selectedPropertyIndex = sc.nextInt() - 1;

                if (selectedPropertyIndex >= propertyNames.length) {
                    System.out.println("Invalid option chosen: " + (selectedPropertyIndex + 1));
                    break;
                }

                String selectedPropertyName = propertyNames[selectedPropertyIndex];
                List<Path> shortestRoute = graphObject.getShortestRoute(src, dest, selectedPropertyName);
                for(Path path : shortestRoute) {
                    System.out.print(path.displayPathWithProperty(selectedPropertyIndex)+", ");
                }
                if(shortestRoute.size()==0) {
                    System.out.print("No route exists");
                }
                System.out.println();

                break;

            case 5:
                break;

            default:
                System.out.println("Please select a valid option: ");
                break;

            }
        } while (choice != 5);
        sc.close();
    }

    /**
     * Reads and parses the input file passed as argument create a
     * NavigationGraph object. The edge property names required for
     * the constructor can be got from the first line of the file
     * by ignoring the first 2 columns - source, destination.
     * Use the graph object to add vertices and edges as
     * you read the input file.
     *
     * @param graphFilepath
     *            path to the input file
     * @return NavigationGraph object
     * @throws FileNotFoundException
     *             if graphFilepath is not found
     * @throws InvalidFileException
     *             if header line in the file has < 3 columns or
     *             if any line that describes an edge has different number of properties
     *              than as described in the header or
     *             if any property value is not numeric
     */

    public static NavigationGraph createNavigationGraphFromMapFile(String graphFilepath)
        throws FileNotFoundException, InvalidFileException {
            Scanner input = new Scanner(new File(graphFilepath));
            String tmp = null;
            try {
                //read the next line
                tmp = input.nextLine();
            } catch(Exception e) {
                //throw invalid file when the file is empty
                throw new InvalidFileException(graphFilepath);
            }

            //get properties
            String[] elements = tmp.split(" ");

            if(elements.length < 3) {
                throw new InvalidFileException(graphFilepath);
            }

            String[] graphProperties = new String[elements.length - 2];

            //properties
            for(int i = 2; i < elements.length; i++) {
                graphProperties[i - 2] = elements[i];
            }

            //create the new graph
            NavigationGraph graph = new NavigationGraph(graphProperties);
            //read rest of the file
            while(input.hasNextLine()) {
                //get the line
                String in = input.nextLine();

                //splite the line
                String[] lineElements = in.split(" ");

                //throw Invalid file exception when length does not match
                if(lineElements.length != elements.length) {
                    throw new InvalidFileException(graphFilepath);
                }

                // add source and dest to the graph
                Location source = graph.getLocationByName(lineElements[0]);
                Location dest = graph.getLocationByName(lineElements[1]);

                if(source == null) {
                    source = new Location(lineElements[0]);
                    graph.addVertex(source);
                }

                if(dest == null) {
                    dest = new Location(lineElements[1]);
                    graph.addVertex(dest);
                }

                //add edge
                ArrayList<Double> edgeProperties = new ArrayList<Double>();
                for(int i = 2; i < elements.length; i++) {
                    try {
                        edgeProperties.add(Double.parseDouble(lineElements[i]));
                    } catch(Exception e) {
                        throw new InvalidFileException(graphFilepath);
                    }
                }
                graph.addEdge(source, dest, new Path(source, dest, edgeProperties));
            }
            return graph;
    }
}
