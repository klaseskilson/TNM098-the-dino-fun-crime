public class DataPoint
{
	public Coordinate coord;
	public double hour;
	public int centroidIndex;
	public DataPoint(double theHour, Coordinate theCoord)
	{
		coord = new Coordinate(theCoord.x, theCoord.y);
		hour = theHour;
		centroidIndex = -1;
	}

}