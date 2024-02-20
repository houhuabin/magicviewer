package Magic.Units.Main;

import java.util.Vector;

public class IntPoint2D
{
	public int x;
	public int y;
	public int z;
	
	public IntPoint2D()
	{
		x=y=z=0;
	}
	
	public IntPoint2D(int a,int b,int c)
	{
		x=a;
		y=b;
		z=c;
	}
	public IntPoint2D(IntPoint2D p)
	{
		x=p.x;
		y=p.y;
		z=p.z;
	}
	
	public void setLocation(int a,int b,int c)
	{
		x=a;
		y=b;
		z=c;
	}
	
	public void setLocation(IntPoint2D p)
	{
		x=p.x;
		y=p.y;
		z=p.z;
	}
	
	public boolean isSameLocation(IntPoint2D p)
	{
		if(x==p.x && y==p.y && z==p.z) return true;
		return false;
	}

      
}
