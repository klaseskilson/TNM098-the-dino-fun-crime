import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovementAnalyser {
    private Visitor visitor;
    List<Movement> movement;

    public class Movement {
        int x, y, duration;
        Date timestamp;

        public Movement(int x, int y, int duration, Date timestamp) {
            this.x = x;
            this.y = y;
            this.duration = duration;
            this.timestamp = timestamp;
        }
    }

    public MovementAnalyser(Visitor v) {
        visitor = v;
    }

    public List<Movement> detectNonMoving() {
        List<Movement> movements = getDateDifferences();
        return getOutliers(movements);
    }

    public int sumWaiting() {
        int sum = 0;
        for (Movement m : movement) {
            sum += m.duration;
        }
        return sum;
    }

    private List<Movement> getDateDifferences() {
        List<Date> timestamps = visitor.getTimestamps();
        movement = new ArrayList<>(timestamps.size() - 1);
        for (int i = 1; i < timestamps.size(); ++i) {
            int k = i - 1;
            Coordinate coord = visitor.getCoord(k);
            Date next = timestamps.get(i),
                 current = timestamps.get(k);
            int diffInSeconds = (int) (next.getTime() - current.getTime()) / 1000;
            movement.add(new Movement((int) coord.x, (int) coord.y, diffInSeconds, current));
        }
        return movement;
    }

    private int meanDuration(List<Movement> movements) {
        int sum = 0;
        for (Movement m : movements) {
            sum += m.duration;
        }
        return sum / movements.size();
    }

    private List<Movement> getOutliers(List<Movement> movements) {
        int mean = meanDuration(movements);
        List<Movement> outliers = new ArrayList<>();
        for (Movement m : movements) {
            if (m.duration > mean) {
                outliers.add(m);
            }
        }
        return outliers;
    }
}