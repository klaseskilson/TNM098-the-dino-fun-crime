import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<DataLine> entries = CSVReader.readFile("/data/park-movement-Fri-FIXED-2.0.csv");
        System.out.println("Found: " + entries.size());
        LocationMap map = new LocationMap();

        for(DataLine data : entries) {
        	map.addDataValue(data);
        }

        //map.printHeatMap();
        map.printMaxValue();
    }
}
