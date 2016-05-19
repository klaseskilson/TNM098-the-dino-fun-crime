import java.util.*;
public class Visitor
{
	private List<Coordinate> coords;
	private List<String> activities;
	private List<Date> timestamps;
	private int id;
	public Visitor(int theID)
	{
		coords = new ArrayList<Coordinate>();
		activities = new ArrayList<String>();
		timestamps = new ArrayList<Date>();
		id = theID;
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
			System.out.println("id: " + id + " " + activities.get(i) +" " +  coords.get(i).x + ", " + coords.get(i).y + " " + timestamps.get(i));
		}
	}
	public Visitor getPeopleAtBusyCoords(Coordinate busyCoord)
	{
		for (int i = 0; i < coords.size(); i++)
		{
			if (coords.get(i).equals(busyCoord))
				return this;
		}
		return null;
	}
	public void printTime(Coordinate coord)
	{
		for (int i = 0; i < coords.size(); i++)
		{
			if (coords.get(i).equals(coord))
			{
				System.out.println(timestamps.get(i));
			}
		}
	}


}