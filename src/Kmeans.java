import java.util.*;
public class Kmeans
{
	private final static int NUM_DIMENSIONS = 3;
	int k;
	private Visitor visitor;
	HashMap<Integer, DataPoint> randomCentroids;
	HashMap<Integer, DataPoint> timeAndPlace;
	int iterations;
	double quality;
	double newQuality;
	public Kmeans(Visitor theVisitor)
	{
		visitor = theVisitor;
		iterations = 0;
		quality = 0;
		newQuality = 0;
		k = 10;
		run();
	}
	private void run()
	{
		//centroid index as key, DataPoint (hour, coordinate and centroid index) as value.
		fillHashMap();
		createRandomCentroids();
		assignToCluster();	
		recalculateCentroids();
		checkQuality();
	}
	private void fillHashMap()
	{
		timeAndPlace = new HashMap<Integer, DataPoint>();
		for (int i = 0; i < visitor.size(); i++)
		{
			timeAndPlace.put(i, new DataPoint(visitor.getHour(i), visitor.getCoord(i)));
		}
	}
	private void createRandomCentroids()
	{
		randomCentroids = new HashMap<Integer, DataPoint>();
		for (int i = 0; i < k; i++)
		{
			randomCentroids.put(i, visitor.getRandomPoint());
		}
	}

	private void assignToCluster()
	{
		double distance = 0;
		double shortestDistance;
		List<Double> distances = new ArrayList<Double>();
		int centroidIndex = 0;
		for (int i = 0; i < visitor.size(); i++)
		{
			distance = 0;
			shortestDistance = 100000;
			centroidIndex = -1;
			for (int j = 0; j < randomCentroids.size(); j++)
			{
				distance = Math.sqrt(Math.pow((double)(timeAndPlace.get(i).hour - randomCentroids.get(j).hour), 2)
				+ Math.pow((double)(timeAndPlace.get(i).coord.x - randomCentroids.get(j).coord.x), 2)
				+ Math.pow((double)(timeAndPlace.get(i).coord.y - randomCentroids.get(j).coord.y), 2));
				if (distance < shortestDistance)
				{
					shortestDistance = distance;
					centroidIndex = j;
				}
			}
			timeAndPlace.get(i).centroidIndex = centroidIndex;
		}
	}

	private void recalculateCentroids()
	{
		double averageHour = 0;
		double averageX = 0;
		double averageY = 0;
		double counter = 0;
		for (int i = 0; i < randomCentroids.size(); i++)
		{
			for (int j = 0; j < timeAndPlace.size(); j++)
			{
				if (i == timeAndPlace.get(j).centroidIndex)
				{
					averageHour+= timeAndPlace.get(j).hour;
					averageX += timeAndPlace.get(j).coord.x;
					averageY += timeAndPlace.get(j).coord.y;
					counter++;
				}
			}
			if (counter != 0)
			{
				averageHour/=counter;
				averageX/=counter;
				averageY/=counter;
				randomCentroids.get(i).hour = averageHour;
				randomCentroids.get(i).coord.x = averageX;
				randomCentroids.get(i).coord.y = averageY;
			}
			averageHour = 0;
			averageX = 0;
			averageY = 0;
			counter = 0;
		}

	}

	private void checkQuality()
	{
		for (int i = 0; i < randomCentroids.size(); i++)
		{
			for (int j = 0; j < timeAndPlace.size(); j++)
			{
				if (i == timeAndPlace.get(j).centroidIndex)
				{
					if (iterations == 0)
					{
						quality += Math.pow(timeAndPlace.get(j).hour - randomCentroids.get(i).hour, 2) +
						Math.pow(timeAndPlace.get(j).coord.x - randomCentroids.get(i).coord.x, 2) + 
						Math.pow(timeAndPlace.get(j).coord.y - randomCentroids.get(i).coord.y, 2);

					}
					else
					{
						newQuality += Math.pow(timeAndPlace.get(j).hour - randomCentroids.get(i).hour, 2) +
						Math.pow(timeAndPlace.get(j).coord.x - randomCentroids.get(i).coord.x, 2) + 
						Math.pow(timeAndPlace.get(j).coord.y - randomCentroids.get(i).coord.y, 2);
					}
				}
			}
		}

		/*if (iterations == 0)
		{
			iterations++;
			
			assignToCluster();
			recalculateCentroids();

			checkQuality();
		}

		else
		{
			if (newQuality < quality)
			{
				iterations++;
				quality = newQuality;
				newQuality = 0;
				assignToCluster();
				recalculateCentroids();

				checkQuality();
			}
		}*/
		
	}

	private void print()
	{
		for (int i = 0; i < timeAndPlace.size(); i++)
		{
			System.out.println(timeAndPlace.get(i).centroidIndex);
		}
	}
}