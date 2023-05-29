package TetraederTransform;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MatrixPanel extends JPanel implements PropertyChangeListener{
	//einzelne Labels (Matrizenplätze)
	JLabel[] labels_;
	//legt fest, auf wen (was vom Controller) genau die Matrix eigentlich hören soll
	String property_;
	
	public MatrixPanel(String name, String property)
	{
		property_ = property;
		//Border und Titel
		setBorder(BorderFactory.createTitledBorder(name));
		setLayout(new GridBagLayout());
		
		//Initialisierung der Labels (16, da 4x4)
		labels_ = new JLabel[16];
		
		//Initialisierung des GridBagLayouts und der einzelnen Label
		for(int i = 0; i<4; ++i)
		{
			for(int j = 0; j<4; ++j)
			{
				labels_[i*4+j] = new JLabel();
				add(labels_[i*4+j], new GridBagConstraints(
						i,j,
						1,1,
						0,0,
						GridBagConstraints.CENTER,
						GridBagConstraints.BOTH,
						new Insets(2,2,2,2),
						2, 2));
			}
		}
		//Anfangs alles Einheitsmatrix
		updateValues(TransformationController.identity());
		
		//soll feste, unveränderliche Ausmaße haben
		Dimension d = new Dimension(175, 125);
		setMinimumSize(d);
		setPreferredSize(d);
		setMaximumSize(d);
	}
	
	//Ändert die Werte der Labels nach Vorgabe der übergebenen Matrix
	private void updateValues(float[][] matrix) {
		for(int i = 0; i<4; ++i)
		{
			for(int j = 0; j<4; ++j)
			{
				labels_[j*4+i].setText(String.format("%.2f", matrix[i][j]));
			}
		}
	}
	
	//Eventhandler -> wenn gewünschter Slider sich ändert, update diese Werte
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(property_))
		{
			updateValues((float[][])evt.getNewValue());
		}
		revalidate();			//???
	}
}
