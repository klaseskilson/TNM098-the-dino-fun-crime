import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataLine {
    public String type;
    public Date timestamp;
    public int id, x, y;

    private static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * construct a data line entry from a string
     * @param line a line containing the data
     */
    public DataLine(String line) {
        String[] content = line.split(",");

        // the data is stored as: Timestamp,id,type,X,Y
        try {
            timestamp = FORMATTER.parse(content[0]);
            id = Integer.parseInt(content[1]);
            type = content[2];
            x = Integer.parseInt(content[3]);
            y = Integer.parseInt(content[4]);
        } catch (Exception e) {
            System.out.println("Could not parse data line: " + line);
            System.out.println(e.toString());
        }
    }
}
