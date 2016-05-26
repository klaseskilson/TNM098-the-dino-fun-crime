import java.lang.reflect.Array;
import java.util.*;

public class ChangeDetector {
    private class Change {
        public int amount;
        public Date timestamp;

        public Change(int amount, Date timestamp) {
            this.amount = amount;
            this.timestamp = timestamp;
        }

        public String toString() {
            return this.amount + " @ " + this.timestamp;
        }
    }

    // how large should the anomaly be compared to the mean?
    private final static int ANOMALY_FACTOR = 2;

    // how many parts should each hour be divided into?
    private final static int STEPS_PER_HOUR = 6;
    private final static int HOUR_IN_MS = 1000 * 60 * 60;
    private final static int DELIMETER = HOUR_IN_MS / STEPS_PER_HOUR;

    HashMap<String, ArrayList<Date>> timestamps;
    HashMap<String, HashMap<Integer, Integer>> checkins;
    HashMap<String, ArrayList<Change>> anomalies;

    public ChangeDetector() {
        timestamps = new HashMap<>(1000);
        anomalies = new HashMap<>();
    }

    /**
     * add data and store it in timestamps
     * @param data list containing DataLines
     */
    public void addData(ArrayList<DataLine> data) {
        // read data line by line and save the time stamps in hashmap based on the coordinates
        for (DataLine entry : data) {
            // group checkins to analyse larger areas
            double reductionFactor = 1.0;
            int x = (int) Math.round(entry.x / reductionFactor),
                y = (int) Math.round(entry.y / reductionFactor);

            // store data with keys in the format "x,y"
            String key = x + "," + y;
            ArrayList<Date> datesForPosition = timestamps.getOrDefault(key, new ArrayList<Date>());
            datesForPosition.add(entry.timestamp);
            timestamps.put(key, datesForPosition);
        }
        calculatePerHour();
        detectLargeChanges();
    }

    public void calculatePerHour() {
        checkins = new HashMap<>(timestamps.size());
        for (String key : timestamps.keySet()) {
            HashMap<Integer, Integer> perHour = checkins.getOrDefault(key, new HashMap<>());
            ArrayList<Date> datesForPosition = timestamps.get(key);
            for (Date time : datesForPosition) {
                int timeKey = getKeyFromDate(time);
                int count = perHour.getOrDefault(timeKey, 0);
                perHour.put(timeKey, ++count);
            }
            checkins.put(key, perHour);
        }
    }

    public void detectLargeChanges() {
        HashMap<String, Change[]> perPosition = changesPerPosition();
        HashMap<String, Float> meanValues = getMeanPerPosition(perPosition);
        findOutliers(perPosition, meanValues);
    }

    private HashMap<String, Change[]> changesPerPosition() {
        HashMap<String, Change[]> changes = new HashMap<>();
        for (String key : checkins.keySet()) {
            HashMap<Integer, Integer> perTime = checkins.get(key);
            List<Change> positionChanges = new ArrayList<>();
            for (Integer timeKey : perTime.keySet()) {
                int now = perTime.get(timeKey);
                int previousKey = timeKey - 1;
                int then = perTime.getOrDefault(previousKey, 0);
                int change = now - then;
                Date d = new Date();
                d.setTime((long) timeKey * DELIMETER);
                positionChanges.add(new Change(change, d));
            }
            changes.put(key, positionChanges.toArray(new Change[positionChanges.size()]));
        }
        return changes;
    }

    private HashMap<String, Float> getMeanPerPosition(HashMap<String, Change[]> perPosition) {
        HashMap<String, Float> means = new HashMap<>(perPosition.size());
        for (String key : perPosition.keySet()) {
            Change[] changes = perPosition.get(key);
            int sum = 0, count = 0;
            for (Change v : changes) {
                int value = Math.abs(v.amount);
                if (value != 0) {
                    sum += value;
                    ++count;
                }
            }
            means.put(key, sum / (float) count);
        }
        return means;
    }

    private void findOutliers(HashMap<String, Change[]> perPosition, HashMap<String, Float> means) {
        for (String key : perPosition.keySet()) {
            Change[] changes = perPosition.get(key);
            ArrayList<Change> positionAnomalies = anomalies.getOrDefault(key, new ArrayList<>());
            Float mean = means.get(key);
            float min = mean * ANOMALY_FACTOR;
            for (Change v : changes) {
                if (Math.abs((float) v.amount) > min) {
                    positionAnomalies.add(v);
                }
            }
            anomalies.put(key, positionAnomalies);
        }
    }

    public void print() {
        System.out.println("Found anomalies:");
        for (String key : anomalies.keySet()) {
            print(key, anomalies.get(key));
        }
    }

    private void print(String key, ArrayList<Change> positionChanges) {
        // avoid printing irrelevant info
        if (positionChanges.size() != 0) {
            System.out.println(key + "(" + positionChanges.size() + ")");
            for (Change c : positionChanges) {
                System.out.println("\t" + c);
            }
        }

    }

    /**
     * print anomalies within given coordinates
     * @param fromX
     * @param toX
     * @param fromY
     * @param toY
     */
    public void printBox(int fromX, int toX, int fromY, int toY) {
        System.out.println("Found anomalies:");
        for (; fromX <= toX; ++fromX) {
            for (; fromY <= toY; ++fromY) {
                String key = fromX + "," + fromY;
                ArrayList<Change> positionChanges = anomalies.getOrDefault(key, new ArrayList<>());
                print(key, positionChanges);
            }
        }
    }

    private int getKeyFromDate(Date date) {
        int time = (int) (date.getTime() / DELIMETER);
        return time;
    }
}