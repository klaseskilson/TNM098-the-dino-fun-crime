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
        int lineNumbers = lineNumbers(fileName);
        int stepSize = lineNumbers / 5;

        ArrayList<DataLine> entries = new ArrayList<>();

        // read line by line
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int count = 0;
            for(String line; (line = br.readLine()) != null; ) {
                ++count;
                // ignore first line containing csv header
                if (count == 1) {
                    continue;
                }
                if (count % stepSize == 0) {
                    System.out.printf("... %.1f%%\n", (float) 100 * count / lineNumbers);
                }

                // append new DataLine entry to entries
                try {
                    DataLine dataLine = new DataLine(line);
                    entries.add(dataLine);
                } catch (Exception e) {}
            }
        } catch (Exception e) {
            System.out.println("Could not read file " + fileName);
            System.out.println(e.toString());
            return null;
        }

        System.out.println("... done!");

        return entries;
    }

    private static int lineNumbers(String fileName) {
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while (br.readLine() != null) ++count;
        } catch (Exception e) {
            System.out.println("Could not count line numbers " + fileName);
            System.out.println(e.toString());
        }

        return count;
    }
}
