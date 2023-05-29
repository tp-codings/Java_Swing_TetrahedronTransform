package TetraederTransform;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TetraederTransform {

	public static void main(String[] args) {
		//Standardfenster
		JFrame frame = new JFrame("Homogene Koordinaten");
		frame.setResizable(false);
		frame.setSize(600, 600);;
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Setzt Matrizen nach links
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		//Setzt Matrizen untereinander
		JPanel transformationContainerX = new JPanel();
		JPanel transformationContainerY = new JPanel();
		transformationContainerX.setLayout(new BoxLayout(transformationContainerX, BoxLayout.Y_AXIS));
		transformationContainerY.setLayout(new BoxLayout(transformationContainerY, BoxLayout.Y_AXIS));
		frame.add(transformationContainerX);
		frame.add(transformationContainerY);
		
		//fügt einezlne Matrixpanels dem Container zu
		MatrixPanel modulPanel = new MatrixPanel("Modell", "model");
		transformationContainerX.add(modulPanel);
		
		/*MatrixPanel xOriginPanel = new MatrixPanel("X Origin", "xOrigin");
		transformationContainerX.add(xOriginPanel);
		MatrixPanel yOriginPanel = new MatrixPanel("Y Origin", "yOrigin");
		transformationContainerX.add(yOriginPanel);
		MatrixPanel zOriginPanel = new MatrixPanel("Z Origin", "zOrigin");
		transformationContainerX.add(zOriginPanel);*/
		MatrixPanel OriginPanel = new MatrixPanel("Origin", "origin");
		transformationContainerX.add(OriginPanel);
		MatrixPanel BackOriginPanel = new MatrixPanel("BackOrigin", "backOrigin");
		transformationContainerX.add(BackOriginPanel);
		
		/*MatrixPanel xScalePanel = new MatrixPanel("X Scale", "xScale");
		transformationContainerX.add(xScalePanel);
		MatrixPanel yScalePanel = new MatrixPanel("Y Scale", "yScale");
		transformationContainerX.add(yScalePanel);
		MatrixPanel zScalePanel = new MatrixPanel("Z Scale", "zScale");
		transformationContainerX.add(zScalePanel);*/
		MatrixPanel ScalePanel = new MatrixPanel("Scale", "scale");
		transformationContainerX.add(ScalePanel);
		
		/*MatrixPanel xRotationPanel = new MatrixPanel("X Rotation", "xRotation");
		transformationContainerY.add(xRotationPanel);
		MatrixPanel yRotationPanel = new MatrixPanel("Y Rotation", "yRotation");
		transformationContainerY.add(yRotationPanel);
		MatrixPanel zRotationPanel = new MatrixPanel("Z Rotation", "zRotation");
		transformationContainerY.add(zRotationPanel);*/
		MatrixPanel RotationPanel = new MatrixPanel("Rotation", "rotation");
		transformationContainerX.add(RotationPanel);
		
		/*MatrixPanel xTranslationPanel = new MatrixPanel("X Translation", "xTranslation");
		transformationContainerY.add(xTranslationPanel);
		MatrixPanel yTranslationPanel = new MatrixPanel("Y Translation", "yTranslation");
		transformationContainerY.add(yTranslationPanel);
		MatrixPanel zTranslationPanel = new MatrixPanel("Z Translation", "zTranslation");
		transformationContainerY.add(zTranslationPanel);*/
		MatrixPanel TranslationPanel = new MatrixPanel("Translation", "translation");
		transformationContainerX.add(TranslationPanel);
		
		
		//Dem Fenster wird das Drawingpanel zugefügt
		DrawingPanel drawingPanel = new DrawingPanel();
		frame.add(drawingPanel);
		
		//Rotationcontroller controlls Rotation (zweites Fenster mit Slidern)
		TransformationController controller = new TransformationController();
		
		//Drawingpanel und die Matrizenpanels sollen dem Controller zuhören -> Die werden beim Controller angemeldet (geaddet)
		controller.addPropertyChangeListener(drawingPanel);
		controller.addPropertyChangeListener(modulPanel);
		
		/*controller.addPropertyChangeListener(xRotationPanel);
		controller.addPropertyChangeListener(yRotationPanel);
		controller.addPropertyChangeListener(zRotationPanel);
		
		controller.addPropertyChangeListener(xScalePanel);
		controller.addPropertyChangeListener(yScalePanel);
		controller.addPropertyChangeListener(zScalePanel);
		
		controller.addPropertyChangeListener(xTranslationPanel);
		controller.addPropertyChangeListener(yTranslationPanel);
		controller.addPropertyChangeListener(zTranslationPanel);
		
		controller.addPropertyChangeListener(xOriginPanel);
		controller.addPropertyChangeListener(yOriginPanel);
		controller.addPropertyChangeListener(zOriginPanel);*/
		
		controller.addPropertyChangeListener(TranslationPanel);
		controller.addPropertyChangeListener(RotationPanel);
		controller.addPropertyChangeListener(ScalePanel);
		controller.addPropertyChangeListener(OriginPanel);
		controller.addPropertyChangeListener(BackOriginPanel);
		
		frame.pack();
		frame.setVisible(true);
	}

}
