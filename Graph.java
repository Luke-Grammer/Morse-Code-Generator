
public class Graph
{
	double prevX;
	double prevY;
	double maxY;
	
	public Graph()
	{		
		prevX = 0;
		prevY = 0;
		StdDraw.setXscale(0, 75);
		StdDraw.setYscale(-150, 150);
	}
	
	public void GraphLine(double x, double y)
	{
		StdDraw.line(prevX, prevY, x, y);
		prevX = x;
		prevY = y;
	}

}
