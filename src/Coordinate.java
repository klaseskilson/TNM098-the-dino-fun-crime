public class Coordinate
{
	public double x;
	public double y;
	public Coordinate(double theX, double theY)
	{
		x = theX;
		y = theY;
	}
	public boolean equals(Coordinate theCoord)
	{
		if (theCoord.x == x && theCoord.y == y)
			return true;

		return false;
	}
	public Coordinate add(double theX, double theY)
	{
		return new Coordinate(x+theX, y+theY);
	}

	public String toString()
	{
		return (x+", "+y);
	}
}
