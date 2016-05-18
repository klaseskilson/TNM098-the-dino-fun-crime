import java.util.*;
public class Visitor
{
	private List<Coordinate> coords;
	private List<String> activities;
	private List<Date> timestamps;

	public Visitor()
	{
		coords = new ArrayList<Coordinate>();
		activities = new ArrayList<String>();
		timestamps = new ArrayList<Date>();
	}
	
	public Coordinate getCoordByTime(Date time)
	{
		for (int i = 0; i < timestamps.size(); i++)
		{
			if (timestamps.get(i).equals(time))
				return coords.get(i);
		}
		return null;
	}

	public Date getTimeByCoord(Coordinate theCoord)
	{
		for (int i = 0; i < timestamps.size(); i++)
		{
			if (coords.get(i).equals(theCoord))
				return timestamps.get(i);
		}
		return null;
	}

	public String getActivityByTime(Date time)
	{
		for (int i = 0; i < timestamps.size(); i++)
		{
			if (timestamps.get(i).equals(time))
				return activities.get(i);
		}
		return null;
	}

	public void update(Coordinate theCoord, String theActivity, Date theTimestamp)
	{
		coords.add(theCoord);
		activities.add(theActivity);
		timestamps.add(theTimestamp);
	}	

	public void print()
	{
		for (int i = 0; i < activities.size(); i++)
		{
			System.out.println(activities.get(i) +" " +  coords.get(i).x + ", " + coords.get(i).y + " " + timestamps.get(i));
		}
	}


}