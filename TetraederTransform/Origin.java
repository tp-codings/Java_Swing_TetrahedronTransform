package TetraederTransform;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


public class Origin {

		
	public Origin (float x, float y)
	{
		
	}
	
	public void draw(Graphics2D g2d, float[][] origin)
	{
		Ellipse2D.Float point = new Ellipse2D.Float(origin[0][3]-3, origin[1][3]-3, 6, 6);
		g2d.setColor(Color.BLACK);
		g2d.fill(point);
	}
}


