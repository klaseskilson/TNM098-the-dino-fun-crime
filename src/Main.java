import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Reading file...");
        ArrayList<DataLine> entries = CSVReader.readFile("../data/park-movement-Sat.csv");
        System.out.println("Found: " + entries.size());

        // create all visitor entries from the result of the CSV reader,
        // don't care if they get replaced
        HashMap<Integer,Visitor> visitors = new HashMap<Integer,Visitor>();
        System.out.println("Converting to HashMap...");
        for (DataLine dataLine : entries) {
            visitors.put(dataLine.id, new Visitor(dataLine.id));
        }
        // append all data from the entries
        for (DataLine dataLine : entries) {
            visitors.get(dataLine.id).update(new Coordinate(dataLine.x, dataLine.y), dataLine.type, dataLine.timestamp);
        }

        // detect flow change
        System.out.println("Create detect flow anomalies...");
        ChangeDetector flow = new ChangeDetector();
        flow.addData(entries);

        System.out.println("Create LocationMap...");
        LocationMap map = new LocationMap("../web/data/heatMap.csv");
        map.addDataValues(entries);
        map.saveCSV("../web/data/heatMap.csv");

        System.out.println("Sort LocationMap and convert to TreeMap...");
        map.sortMap();
        TreeMap<Integer, Coordinate> treeSortedMap = (TreeMap<Integer, Coordinate>) map.sortedMap;
        System.out.println("Finding people at busy spot...");
        List<Visitor> suspectedVisitors = new ArrayList<Visitor>();
        Visitor v;
        // get people at most busy spot
        for (DataLine entry : entries)
        {
            Coordinate busyCoordinate = treeSortedMap.get(treeSortedMap.firstKey());
            v = visitors.get(entry.id);
            if (v.visitedCoords(busyCoordinate)) {
                suspectedVisitors.add(v);
            }
        }
        System.out.println("Print the time people visited the spots...");
        for (int i = 0; i < 10; i++)
        {
            suspectedVisitors.get(i).printTime(treeSortedMap.get(treeSortedMap.firstKey()));
        }

    }
}
