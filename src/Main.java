import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Reading file...");
        ArrayList<DataLine> entries = CSVReader.readFile("../data/park-movement-Sun.csv");
        System.out.println("Found: " + entries.size());

        // create all visitor entries from the result of the CSV reader,
        // don't care if they get replaced, the id of each person is the key
        HashMap<Integer,Visitor> visitors = new HashMap<Integer,Visitor>();
        System.out.println("Converting to HashMap...");
        for (DataLine dataLine : entries) {
            visitors.put(dataLine.id, new Visitor(dataLine.id));
        }
        // append all data from the entries
        for (DataLine dataLine : entries) {
            visitors.get(dataLine.id).update(new Coordinate(dataLine.x, dataLine.y), dataLine.type, dataLine.timestamp);
        }

         System.out.println("Comparing");
        Iterator it = visitors.entrySet().iterator();
        HashMap<Integer, Visitor> visitorsCopy = new HashMap<Integer,Visitor>(visitors);
        System.out.println(visitors.size());
        ArrayList<Integer> removeList = new ArrayList<Integer>();
        while (it.hasNext()) 
        {
            Map.Entry entry = (Map.Entry)it.next();
            Visitor v = (Visitor)entry.getValue();
            Iterator it2 = visitorsCopy.entrySet().iterator();
            while(it2.hasNext())
            {
                Map.Entry entry2= (Map.Entry)it2.next();
                Visitor v2 = (Visitor)entry2.getValue();
                if (v.hasSameMovement(v2))
                {
                    v.addToGroup(v2);
                    removeList.add((Integer)entry2.getKey());
                }
            }
            for (int i = 0; i < removeList.size(); i++)
            {
                visitorsCopy.remove(removeList.get(i));
            }
            removeList.clear();
            
         }
         System.out.println(visitors.size());
         System.out.println("Comparing done");

         for (Map.Entry<Integer, Visitor> entry : visitors.entrySet()) 
         {
             entry.getValue().printGroup();
         }
        for (Visitor visitor : visitors.values()) {
            MovementAnalyser analyser = new MovementAnalyser(visitor);
            List<MovementAnalyser.Movement> movements = analyser.detectNonMoving();
            int sum = analyser.sumWaiting();
            if (sum > 50000) {
                System.out.println("waiting: " + analyser.sumWaiting());
            }
        }

        // reduce data amount by summarizing over time
        System.out.println("Create LocationMap...");
        LocationMap map = new LocationMap("../web/data/heatMap.csv");
        map.addDataValues(entries);

         



      /*  System.out.println("Create detect flow anomalies...");
        System.out.println("Detecting flow anomalies...");
        ChangeDetector flow = new ChangeDetector();
        flow.addData(entries);
        flow.printBox(30, 40, 30, 40);


        System.out.println("Running kmeans...");
        int j = 0;
        for (Map.Entry<Integer, Visitor> entry : visitors.entrySet()) {
            Visitor value = entry.getValue();

            Kmeans kMeans = new Kmeans(value);
            j++;
            if (j == 3000)
               break;
        }

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
