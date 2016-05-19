import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<DataLine> entries = CSVReader.readFile("../data/park-movement-Sat.csv");
        System.out.println("Found: " + entries.size());
        HashMap<Integer,Visitor> visitors = new HashMap<Integer,Visitor>();

        for (DataLine dataLine : entries) {
            visitors.put(dataLine.id, new Visitor(dataLine.id));
            visitors.get(dataLine.id).update(new Coordinate(dataLine.x, dataLine.y), dataLine.type, dataLine.timestamp);
        }
        
        LocationMap map = new LocationMap();

        map.addDataValues(entries);
        //map.sortMap();
        //map.saveCSV("../web/data/heatMap.csv");

        map.sortMap();
        Map<Integer, Coordinate> sortedMap = map.sortedMap;
        TreeMap<Integer, Coordinate> treeSortedMap = (TreeMap<Integer, Coordinate>) sortedMap;
        List<Visitor> suspectedVisitors = new ArrayList<Visitor>();
        Visitor v = new Visitor(0);
        // get people at most busy spot
        System.out.println(treeSortedMap.get(treeSortedMap.firstKey()).toString());
        for (int i = 0; i < entries.size(); i++)
        {
            v = visitors.get(entries.get(i).id).getPeopleAtBusyCoords(treeSortedMap.get(treeSortedMap.firstKey()));
            if (v != null)
            {
                suspectedVisitors.add(v);
            }
            
        }
        for (int i = 0; i < suspectedVisitors.size(); i++)
        {
            suspectedVisitors.get(i).printTime(treeSortedMap.get(treeSortedMap.firstKey()));
        }

    }
}
