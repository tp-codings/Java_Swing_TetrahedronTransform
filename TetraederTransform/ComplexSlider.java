package TetraederTransform;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

//Slider als eigenes Panel
public class ComplexSlider extends JPanel {

	ComplexSlider(String text, int min, int max, int value, TransformationController controller, SliderChangefunc func)
	{
		//Borderlayout und Titel des Sliders
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(text+": " + value));		//Slider benennen
		
		//Eigentlicher Slider
		JSlider slider = new JSlider(min, max, value);						//wilder Shit
		
		//Folgendes Konstrukt soll dem Slider zuhören, wenn der Slider sich ändert (abgerufen werden)
		//Eigentlich nur dafür da, dass sich der Wert im Slidertitel ändert
		slider.addChangeListener(evt -> {
			int currValue = ((JSlider) evt.getSource()).getValue();
			setBorder(BorderFactory.createTitledBorder(text+": " + currValue));
			//controller.updateXRotation(currValue);
			//Bestimmte übergebene Funktion soll mit currVal geupdatet werden
			func.call(currValue);
		});
		add(slider, BorderLayout.CENTER);
	}
}
//Geiler shit
interface SliderChangefunc
{
	public void call(float value);
	
}