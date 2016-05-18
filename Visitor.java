import java.util.*;
public class Visitor
{
	private List<Coordinate> coords;
	private List<String> activities;
	private List<String> timestamps;
	public Visitor(List<Coordinate> theCoords, List<String> theActivities, List<String> theTimestamps)
	{
		coords = new ArrayList<Coordinate>();
		for (int i = 0; i < theCoords.size(); i++)
		{
			coords.add(new Coordinate(theCoords.get(i).x, theCoords.get(i).y));
			activities.add(theActivities.get(i));
			timestamps.add(theTimestamps.get(i));
		}
	}
	public Coordinate getCoordByTime(String time)
	{
		for (int i = 0; i < timestamps.size(); i++)
		{
			if (timestamps.get(i).equals(time))
				return coords.get(i);
		}
		return null;
	}

	public String getTimeByCoord(Coordinate theCoord)
	{
		for (int i = 0; i < timestamps.size(); i++)
		{
			if (coords.get(i).equals(theCoord))
				return timestamps.get(i);
		}
		return null;
	}

	public String getActivityByTime(String time)
	{
		for (int i = 0; i < timestamps.size(); i++)
		{
			if (timestamps.get(i).equals(time))
				return activities.get(i);
		}
		return null;
	}

}