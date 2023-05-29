package TetraederTransform;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import utils.Matrix;

//Objekt, hier jetzt Dreieck
public class Triangle
{
	//Punktvektoren
	private float[][] p_;
	private float[][] k_;
	//Farbvektor
	private Color[] colors_;
	
	public Triangle (float x, float y, float extend)
	{
		//extend sagt, wie weit Punkte auseinander sein sollen, 
		float xExtend = 0.866F * extend;	//sin(120)
		float yExtend = -0.5F * extend;		//cos(120)
		
		//Was macht die 1 dahinter?
		p_ = new float[][]{					//float[4][4] -> 4 Vektoren à 4 Einträge (x, y, z, 1)
			{0+x, extend+y, 0, 1},				//Vektor nach oben
			{xExtend+x, yExtend+y, 0, 1},		//Vektor nach unten rechts
			{-xExtend+x, yExtend+y, 0, 1},		//Vektor nach unten links
			{0+x, 0, extend+y, 1}				//Vektor nach vorn
		};
		k_ = new float[][] {
			{1, 0, 0, 1},
			{0, 1, 0, 1},
			{0, 0, 1, 1},
			{0, 0, 0, 1}
		};
		
		colors_ = new Color[] {
				Color.RED,
				Color.GREEN,
				Color.BLUE,
				Color.YELLOW
		};
	}
	
	public void draw(Graphics2D g2d, float[][] model)
	{
		//transformierte Punktvektoren
		float[][] transformed = new float[4][4];
		float[][] transformedKoord = new float[4][4];
		
		//Durch alle Punkvektoren durchiterieren und alle um Transfomationsmatrix verschieben
		for(int i = 0; i < 4; ++i)
		{
			transformed[i] = Matrix.matMult(model, p_[i]);
		}
		//Versuch, Koordinatensystem in Tetraeder zu plotten
		for(int i = 0; i < 4; ++i)
		{
			transformedKoord[i] = Matrix.matMult(model, k_[i]);
		}
		for(int i = 0; i<4; i++)
		{
			for(int j = 0; j<4; j++)
			{
				transformedKoord[i][j] = transformedKoord[i][j]/(float)Math.sqrt(Math.pow(transformedKoord[i][0], 2)+Math.pow(transformedKoord[i][1], 2)+Math.pow(transformedKoord[i][2], 2)+Math.pow(transformedKoord[i][3], 2));
			}
		}
		
		g2d.setColor(Color.BLACK);
		//System.out.println(transformedKoord[0][3]);
		//System.out.println(transformedKoord[1][3]);
		//Verbindungslinien zeichnen
		for (int i = 0; i < 4; ++i)
		{
			//Basis
			float[] p0 = transformed[i];
			float[] p1 = transformed[(i+1)%4];
			//Spitze
			float[] p2 = transformed[(i+2)%4];
			//Verbindungslinien
			Line2D.Float line1 = new Line2D.Float(p0[0],p0[1], p1[0], p1[1]);
			Line2D.Float line2 = new Line2D.Float(p0[0],p0[1], p2[0], p2[1]);
			//Zeichenstärke ändern
			g2d.setStroke(new BasicStroke(2.5F));
			g2d.draw(line1);
			g2d.draw(line2);
 		}
		
		g2d.setStroke(new BasicStroke(3.5F));
		for (int i = 0; i<3; i++)
		{
			Line2D.Float axis = new Line2D.Float(model[0][3],model[1][3],model[0][3]+transformedKoord[i][0]*100, model[1][3]+transformedKoord[i][1]*100);
			g2d.setColor(colors_[i]);
			//g2d.draw(axis);
		}
		
		
		//Punkte zeichnen
		for (int i= 0; i<4; ++i)
		{
			Ellipse2D.Float point = new Ellipse2D.Float(transformed[i][0] - 5, transformed[i][1]- 5, 10, 10);
			g2d.setColor(colors_[i]);
			g2d.fill(point);
		}
	}
}
