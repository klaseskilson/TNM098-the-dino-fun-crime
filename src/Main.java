import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<DataLine> entries = CSVReader.readFile("data/oneperson.csv");
        System.out.println("Found: " + entries.size());
    }
}
