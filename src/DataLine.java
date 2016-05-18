/**
 * Created by kalas on 2016-05-18.
 */
public class DataLine {
    public String timestamp, type;
    public int id, x, y;

    /**
     * construct a data line entry from a string
     * @param line a line containing the data
     */
    public DataLine(String line) {
        String[] content = line.split(",");

        // the data is stored as: Timestamp,id,type,X,Y
        timestamp = content[0];
        id = Integer.parseInt(content[1]);
        type = content[2];
        x = Integer.parseInt(content[3]);
        y = Integer.parseInt(content[4]);
    }
}
