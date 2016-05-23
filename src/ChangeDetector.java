import java.util.*;

public class ChangeDetector {
    HashMap<String, ArrayList<Date>> timestamps;
    HashMap<String, int[]> checkins;

    public ChangeDetector() {
        timestamps = new HashMap<>(1000);
    }

    /**
     * add data and store it in timestamps
     * @param data list containing DataLines
     */
    public void addData(ArrayList<DataLine> data) {
        // read data line by line and save the time stamps in hashmap based on the coordinates
        for (DataLine entry : data) {
            // group checkins to analyse larger areas
            double reductionFactor = 3.0;
            int x = (int) Math.round(entry.x / reductionFactor),
                y = (int) Math.round(entry.y / reductionFactor);
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
            int[] perHour = checkins.getOrDefault(key, new int[24]);
            Arrays.fill(perHour, 0);
            ArrayList<Date> datesForPosition = timestamps.get(key);
            for (Date time : datesForPosition) {
                int hour = getHour(time);
                perHour[hour]++;
            }
            checkins.put(key, perHour);
        }
    }

    public void detectLargeChanges() {
        HashMap<String, Integer[]> perPosition = changesPerPosition();
        HashMap<String, Float> meanValues = getMeanPerPosition(perPosition);
        findOutliers(perPosition, meanValues);
    }

    private HashMap<String, Integer[]> changesPerPosition() {
        HashMap<String, Integer[]> changes = new HashMap<>();
        for (String key : checkins.keySet()) {
            int[] perHour = checkins.get(key);
            List<Integer> positionChanges = new ArrayList<>();
            for (int i = 1; i < perHour.length; ++i) {
                int change = Math.abs(perHour[i] - perHour[i - 1]);
                positionChanges.add(change);
                if (perHour[i - 1] != 0 && change != 0) {
//                    System.out.println("Change: " + change + " at " + key);
                }
            }
            changes.put(key, positionChanges.toArray(new Integer[positionChanges.size()]));
        }
        return changes;
    }

    private HashMap<String, Float> getMeanPerPosition(HashMap<String, Integer[]> perPosition) {
        HashMap<String, Float> means = new HashMap<>(perPosition.size());
        for (String key : perPosition.keySet()) {
            Integer[] changes = perPosition.get(key);
            int sum = 0, count = 0;
            for (Integer v : changes) {
                int value = v;
                if (value != 0) {
                    sum += value;
                    ++count;
                }
            }
            means.put(key, sum / (float) count);
        }
        return means;
    }

    private void findOutliers(HashMap<String, Integer[]> perPosition, HashMap<String, Float> means) {
        double factor = 1.5;
        for (String key : perPosition.keySet()) {
            Integer[] changes = perPosition.get(key);
            Float mean = means.get(key);
            int hour = 0;
            for (Integer v : changes) {
                if ((float) v > mean * factor) {
                    System.out.println("Large change hr " + hour + "! (" + key + "): " + v + " > " + mean);
                }
                ++hour;
            }
        }
    }

    private int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}