import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<DataLine> entries = CSVReader.readFile("../data/park-movement-Sat.csv");
        System.out.println("Found: " + entries.size());
        HashMap<Integer,Visitor> visitors = new HashMap<Integer,Visitor>();
   		System.out.println(entries.get(1).id);
        for (int i = 0; i < entries.size(); i++)
        { 
        	visitors.put(entries.get(i).id, new Visitor());
        }

        for (int i = 0; i < entries.size(); i++)
        {
        	visitors.get(entries.get(i).id).update(new Coordinate(entries.get(i).x, entries.get(i).y), entries.get(i).type, entries.get(i).timestamp);
        }
        System.out.println(visitors.size());
        visitors.get(entries.get(100).id).print();
        

        LocationMap map = new LocationMap();

        map.addDataValues(entries);
        map.saveCSV("../web/data/heatMap.csv");

        ArrayList<DataLine> heatMapEntries = CSVReader.readFile("../web/data/heatMap.csv");
        for (int i = 0; i < heatMapEntries.size(); i++)
        {
            
        }

    }
}
