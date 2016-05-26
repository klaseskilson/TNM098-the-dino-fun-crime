import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Reading file...");
        ArrayList<DataLine> entries = CSVReader.readFile("../data/park-movement-Sun.csv");
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
        System.out.println(visitors.size());
       /*  System.out.println("Running kmeans...");
         int j = 0;
         ArrayList<Kmeans> list = new ArrayList<Kmeans>();
         for (Map.Entry<Integer, Visitor> entry : visitors.entrySet()) {
             Visitor value = entry.getValue();
             Kmeans kMeans = new Kmeans(value);
             list.add(kMeans);
             j++;
             if (j == 1000)
                break;
         }*/
         System.out.println("Comparing");
         for (DataLine dataLine : entries)
         {
            Visitor v = visitors.get(dataLine.id);
            for (DataLine dataLine2 : entries)
            {
                if (v.hasSameMovement(visitors.get(dataLine2.id)));
                {
                    //System.out.println(v.id + " & " + visitors.get())
                }
            }            
         }




        // detect flow change
      /*  System.out.println("Create detect flow anomalies...");
        ChangeDetector flow = new ChangeDetector();
        flow.addData(entries);

        System.out.println("Create LocationMap...");
        LocationMap map = new LocationMap("../web/data/heatMap.csv");
        map.addDataValues(entries);

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
        }*/


    }
}
