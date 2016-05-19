import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVReader {

    /**
     * parse fileName to ArrayList of DataLines
     * @param fileName
     * @return
     */
    public static ArrayList<DataLine> readFile(String fileName) {
        // create arraylist for data
        ArrayList<DataLine> entries = new ArrayList<>();

        // read line by line
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            boolean first = false;
            for(String line; (line = br.readLine()) != null; ) {
                // ignore first line containing csv header
                if (!first) {
                    first = true;
                    continue;
                }

                // append new DataLine entry to entries
                entries.add(new DataLine(line));
            }
        } catch (Exception e) {
            System.out.println("Could not read file " + fileName);
            System.out.println(e.toString());
            return null;
        }

        return entries;
    }
}
