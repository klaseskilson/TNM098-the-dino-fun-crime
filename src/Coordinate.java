public class Coordinate
{
	public int x;
	public int y;
	public Coordinate(int theX, int theY)
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
	public Coordinate add(int theX, int theY)
	{
		return new Coordinate(x+theX, y+theY);
	}
}