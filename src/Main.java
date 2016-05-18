import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<DataLine> entries = CSVReader.readFile("../data/park-movement-Sat.csv");
        System.out.println("Found: " + entries.size());
        LocationMap map = new LocationMap();

        map.addDataValues(entries);
        map.saveCSV("../web/data/heatMap.csv");
    }
}
