package TetraederTransform;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

//Eigentliche Zeichenfläche
public class DrawingPanel extends JPanel implements PropertyChangeListener{
	//zusammengebastelte Transformationsmatrix für das Objekt
	private float[][] model_;
	private float[][] origin_;
	//Das Objekt
	private Triangle t_;
	private Origin o_; 
	
	DrawingPanel()
	{
		//Initialisierung der Transformationsmatrix
		model_ = TransformationController.identity();
		origin_ = TransformationController.identity();
		setBackground(Color.WHITE);
		t_ = new Triangle(0,0,50);
		o_ = new Origin(0,0);
		
		//Damit sich das Fenster nicht in der Größe ändert
		Dimension d = new Dimension(800, 800);
		setMinimumSize(d);
		setPreferredSize(d);
		setMaximumSize(d);
	}
	
	
	@Override
	protected void paintComponent(Graphics g)
	{
		//Paintmethode von JPanel, da Drawingpanel ja davon erbt und diese Funktion hier erweitert wird, um mehr zu zeichnen
		super.paintComponent(g);
		
		//getSize() bezieht sich auf das Drawingpanel, bzw. angesprochenes JPanel
		int width = getSize().width;
		int height = getSize().height;
		
		//Graphics ist nur eine abstrakte Klasse -> Davon kann man keine Instanzen machen, Graphics2D ist nach diesem Blueprint abgeleitet
		Graphics2D g2d = (Graphics2D) g;
		//Referenzpunkt für alles gezeichnete in die Mitte und Drehen der Orientierung in y - Richtung um 180 Grad
		g2d.translate(width/2,  height/2);
		g2d.scale(1, -1);
		//Zeichnet Koordinatensystem
		g2d.setColor(Color.RED);
		g2d.drawLine(-width/2, 0, width/2, 0);
		g2d.setColor(Color.GREEN);
		g2d.drawLine(0, -height/2, 0, height/2);
		
		g2d.drawLine((int)origin_[0][3]-3, (int)origin_[1][3]-3, (int)model_[0][3], (int)model_[1][3]);
		g2d.setColor(Color.GREEN);
		
		//Objekt soll gezeichnet werden
		t_.draw(g2d, model_);
		o_.draw(g2d, origin_);
	}
	
	//Eventhandler -> Wenn was mit "model" gefeuert wird, hört Drawingpanel zu (in Ueb4 angemeldet) und aktualisiert das Objekt
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("model"))
			model_ = (float[][])evt.getNewValue();
		if(evt.getPropertyName().equals("backOrigin"))
			origin_ = (float[][])evt.getNewValue();
		//Ruft eigentlich nur noch mal die paintComponent Funktion oben auf:
		repaint();
	}

}
