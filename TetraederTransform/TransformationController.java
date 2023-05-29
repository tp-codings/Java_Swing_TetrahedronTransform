package TetraederTransform;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.Matrix;

//Rotationcontroller wird ein Fenster
public class TransformationController extends JFrame{
	
	//Einzelne Rotationsmatrizen
	float[][] xRotation_;
	float[][] yRotation_;
	float[][] zRotation_;
	float[][] rotationMatrix_;
	
	float[][] xTranslation_;
	float[][] yTranslation_;
	float[][] zTranslation_;
	float[][] translationMatrix_;
	
	float[][] xOrigin_;
	float[][] yOrigin_;
	float[][] zOrigin_;
	
	float[][] xScale_;
	float[][] yScale_;
	float[][] zScale_;
	float[][] scaleMatrix_;
	
	float[][] model_;
	float[][] origin_;
	float[][] backOrigin_;
	
	TransformationController()
	{
		//Standardfenster mit Boylayout
		setResizable(false);
		setLocation(900, 0);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		setSize(500,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		//Setzt Matrizen untereinander
		JPanel transformationContainerX = new JPanel();
		JPanel transformationContainerY = new JPanel();
		JPanel transformationContainerZ = new JPanel();
		JPanel transformationContainerA = new JPanel();
		transformationContainerX.setLayout(new BoxLayout(transformationContainerX, BoxLayout.Y_AXIS));
		transformationContainerY.setLayout(new BoxLayout(transformationContainerY, BoxLayout.Y_AXIS));
		transformationContainerZ.setLayout(new BoxLayout(transformationContainerZ, BoxLayout.Y_AXIS));
		transformationContainerA.setLayout(new BoxLayout(transformationContainerA, BoxLayout.Y_AXIS));
		add(transformationContainerX);
		add(transformationContainerY);
		add(transformationContainerZ);
		add(transformationContainerA);
		
		//Initialisierung der Matrizen als Einheitsmatrix
		xRotation_ = identity();
		yRotation_ = identity();
		zRotation_ = identity();
		rotationMatrix_ = identity();
		
		xTranslation_ = identity();
		yTranslation_ = identity();
		zTranslation_ = identity();
		translationMatrix_ = identity();
		
		xOrigin_ = identity();
		yOrigin_ = identity();
		zOrigin_ = identity();
		
		xScale_ = identity();
		yScale_ = identity();
		zScale_ = identity();
		scaleMatrix_ = identity();
		
		model_ = identity();
		origin_ = identity();
		backOrigin_ = identity();
		
		//Hinzufügen der Slider -> Übergabe der Rotationsupdatesfunktionen als Funktionszeiger, damit diese nicht im Komplexslider explizit aufgerufen werden müssen
		transformationContainerX.add(new ComplexSlider("X Rotation", 0, 360, 0, this, this::updateXRotation));
		transformationContainerX.add(new ComplexSlider("Y Rotation", 0, 360, 0, this, this::updateYRotation));
		transformationContainerX.add(new ComplexSlider("Z Rotation", 0, 360, 0, this, this::updateZRotation));
		
		transformationContainerY.add(new ComplexSlider("X Translation", -200, 200, 0, this, this::updateXTranslation));
		transformationContainerY.add(new ComplexSlider("Y Translation", -200, 200, 0, this, this::updateYTranslation));
		transformationContainerY.add(new ComplexSlider("Z Translation", -200, 200, 0, this, this::updateZTranslation));
		
		transformationContainerZ.add(new ComplexSlider("X Origin", -200, 200, 0, this, this::updateXOrigin));
		transformationContainerZ.add(new ComplexSlider("Y Origin", -200, 200, 0, this, this::updateYOrigin));
		transformationContainerZ.add(new ComplexSlider("Z Origin", -200, 200, 0, this, this::updateZOrigin));
		
		transformationContainerA.add(new ComplexSlider("X Scale", -500, 500, 100, this, this::updateXScale));
		transformationContainerA.add(new ComplexSlider("Y Scale", -500, 500, 100, this, this::updateYScale));
		transformationContainerA.add(new ComplexSlider("Z Scale", -500, 500, 100, this, this::updateZScale));
		
		setVisible(true);
	}
	
//------------------------------------------------------------------------------------------------------
	//Updates der einzelnen Rotationsmatrizen
	public void updateXRotation(float alpha)
	{
		float [][] rotation = identity();
		
		float cos = (float) Math.cos(Math.toRadians(alpha));
		float sin = (float) Math.sin(Math.toRadians(alpha));
		
		//Rotation um x-Achse ist so definiert
		rotation[1][1] = cos;
		rotation[1][2] = -sin;
		rotation[2][2] = cos;
		rotation[2][1] = sin;
		
		xRotation_ = rotation;
		
		//Diese Änderung soll gefeuert werden
		//firePropertyChange("xRotation", null, xRotation_);
		//Modell dafür da, um Rotationen auf Objekt anzuwenden
		updateRotationMatrix();
		computeModel();
		firePropertyChange("model", null, model_);
	}
	
	public void updateYRotation(float gamma)
	{
		float [][] rotation = identity();
		
		float cos = (float) Math.cos(Math.toRadians(gamma));
		float sin = (float) Math.sin(Math.toRadians(gamma));
		
		rotation[0][0] = cos;
		rotation[2][0] = -sin;
		rotation[0][2] = sin;
		rotation[2][2] = cos;
		
		yRotation_ = rotation;
		//firePropertyChange("yRotation", null, yRotation_);
		updateRotationMatrix();
		computeModel();
		firePropertyChange("model", null, model_);
	}
	
	public void updateZRotation(float gamma)
	{
		float [][] rotation = identity();
		
		float cos = (float) Math.cos(Math.toRadians(gamma));
		float sin = (float) Math.sin(Math.toRadians(gamma));
		
		rotation[0][0] = cos;
		rotation[0][1] = -sin;
		rotation[1][0] = sin;
		rotation[1][1] = cos;
		
		zRotation_ = rotation;
		//firePropertyChange("zRotation", null, zRotation_);
		updateRotationMatrix();
		computeModel();
		firePropertyChange("model", null, model_);
	}
	public void updateRotationMatrix()
	{
		rotationMatrix_ = Matrix.matMult(xRotation_, yRotation_);
		rotationMatrix_ = Matrix.matMult(rotationMatrix_, zRotation_);
		firePropertyChange("rotation", null, rotationMatrix_);
	}
	
//------------------------------------------------------------------------------------------------------

	public void updateXTranslation(float value)
	{
		float [][] translation = identity();
		
		translation[0][3] = value;
		
		xTranslation_ = translation;
		//firePropertyChange("xTranslation", null, xTranslation_);
		updateTranslationMatrix();
		computeModel();
		firePropertyChange("model", null, model_);
	}
	public void updateYTranslation(float value)
	{
		float [][] translation = identity();
		
		translation[1][3] = value;
		
		yTranslation_ = translation;
		//firePropertyChange("yTranslation", null, yTranslation_);
		updateTranslationMatrix();
		computeModel();
		firePropertyChange("model", null, model_);
	}
	public void updateZTranslation(float value)
	{
		float [][] translation = identity();
		
		translation[2][3] = value;
		
		zTranslation_ = translation;
		//firePropertyChange("zTranslation", null, zTranslation_);
		updateTranslationMatrix();
		computeModel();
		firePropertyChange("model", null, model_);
	}
	
	public void updateTranslationMatrix()
	{
		translationMatrix_ = Matrix.matMult(xTranslation_, yTranslation_);
		translationMatrix_ = Matrix.matMult(translationMatrix_, zTranslation_);
		firePropertyChange("translation", null, translationMatrix_);
	}
	
//------------------------------------------------------------------------------------------------------

	public void updateXOrigin(float value)
	{
		float [][] origin = identity();
		
		origin[0][3] = value;
		
		xOrigin_ = origin;
		//firePropertyChange("xOrigin", null, xOrigin_);
		computeOrigin();
		computeModel();
		//firePropertyChange("origin", null, backOrigin_);
		firePropertyChange("model", null, model_);
		
	}
	public void updateYOrigin(float value)
	{
		float [][] origin = identity();
		
		origin[1][3] = value;
		
		yOrigin_ = origin;
		//firePropertyChange("yOrigin", null, yOrigin_);
		computeOrigin();
		computeModel();

		//firePropertyChange("origin", null, backOrigin_);
		firePropertyChange("model", null, model_);
	}
	public void updateZOrigin(float value)
	{
		float [][] origin = identity();
		
		origin[2][3] = value;
		
		zOrigin_ = origin;
		//firePropertyChange("zOrigin", null, zOrigin_);
		computeOrigin();
		computeModel();
		//firePropertyChange("origin", null, backOrigin_);
		firePropertyChange("model", null, model_);
	}

//------------------------------------------------------------------------------------------------------
	
	public void updateXScale(float value)
	{
		float [][] scale = identity();
		
		scale[0][0] = value/100;
		
		xScale_ = scale;
		//firePropertyChange("xScale", null, xScale_);
		updateScaleMatrix();
		computeModel();
		firePropertyChange("model", null, model_);
	}
	
	public void updateYScale(float value)
	{
		float [][] scale = identity();
		
		scale[1][1] = value/100;
		
		yScale_ = scale;
		//firePropertyChange("yScale", null, yScale_);
		updateScaleMatrix();
		computeModel();
		firePropertyChange("model", null, model_);
	}
	public void updateZScale(float value)
	{
		float [][] scale = identity();
		
		scale[2][2] = value/100;
		
		zScale_ = scale;
		//firePropertyChange("zScale", null, zScale_);
		updateScaleMatrix();
		computeModel();
		firePropertyChange("model", null, model_);
	}
	
	public void updateScaleMatrix()
	{
		scaleMatrix_ = Matrix.matMult(xScale_, yScale_);
		scaleMatrix_ = Matrix.matMult(scaleMatrix_, zScale_);
		firePropertyChange("scale", null, scaleMatrix_);
	}
//------------------------------------------------------------------------------------------------------

	//Matrixmultiplikation MODEL = TRANSLATION * BACKORIGIN * SCALE * ROTATION * ORIGIN 
	private void computeModel() 
	{
		//firePropertyChange("yScale", null, origin_);
		//firePropertyChange("zScale", null, backOrigin_);
		/*System.out.println(origin_[0][3]);
		System.out.println(origin_[1][3]);
		System.out.println(origin_[2][3]);
		System.out.println("-----------------------------");
		System.out.println(backOrigin_[0][3]);
		System.out.println(backOrigin_[1][3]);
		System.out.println(backOrigin_[2][3]);*/
		
		/*model_ = Matrix.matMult(zTranslation_, 
				Matrix.matMult(yTranslation_,
				Matrix.matMult(xTranslation_ ,
				Matrix.matMult(origin_, 
				Matrix.matMult(zScale_, 
				Matrix.matMult(yScale_, 
				Matrix.matMult(xScale_,
				Matrix.matMult(zRotation_, 
				Matrix.matMult(yRotation_, 
				Matrix.matMult(xRotation_, backOrigin_))))))))));*/
		
		//Warum macht das einen Unterschied???
		/*model_ = Matrix.matMult(origin_, xRotation_);
        model_ = Matrix.matMult(model_, yRotation_);
        model_ = Matrix.matMult(model_, zRotation_);
        model_ = Matrix.matMult(model_, xScale_);
        model_ = Matrix.matMult(model_, yScale_);
        model_ = Matrix.matMult(model_, zScale_);
        model_ = Matrix.matMult(model_, backOrigin_);
        model_ = Matrix.matMult(model_, xTranslation_);
        model_ = Matrix.matMult(model_, yTranslation_);
        model_ = Matrix.matMult(model_, zTranslation_);*/
		
		model_ = Matrix.matMult(translationMatrix_, backOrigin_);
        model_ = Matrix.matMult(model_, scaleMatrix_);
        model_ = Matrix.matMult(model_, rotationMatrix_);
        model_ = Matrix.matMult(model_, origin_);

		
		/*model_ = Matrix.matMult(origin_, 
				Matrix.matMult(xRotation_,
				Matrix.matMult(yRotation_ ,
				Matrix.matMult(zRotation_, 
				Matrix.matMult(xScale_, 
				Matrix.matMult(yScale_, 
				Matrix.matMult(zScale_,
				Matrix.matMult(backOrigin_, 
				Matrix.matMult(xTranslation_, 
				Matrix.matMult(yTranslation_, zTranslation_))))))))));*/
		
	}
	
	private void computeRotationModel()
	{
		model_ =Matrix.matMult(backOrigin_, 
				Matrix.matMult(zRotation_, 
				Matrix.matMult(yRotation_, 
				Matrix.matMult(xRotation_, origin_))));
	}
	private void computeOrigin()
	{
		backOrigin_ = Matrix.matMult(zOrigin_, Matrix.matMult(yOrigin_, xOrigin_));
		/*origin_[0][3] = xOrigin_[0][3]- model_[0][3];
		origin_[1][3] = yOrigin_[1][3]- model_[1][3];
		origin_[2][3] = zOrigin_[2][3]- model_[2][3];*/
		//System.out.println(origin_[1][3]);
		float[][] bO;				//Shit Pointer Shit
		bO = new float[backOrigin_.length][];
		
		for(int i = 0; i<backOrigin_.length; i++)
		{
			bO[i] = backOrigin_[i].clone();
		}
		
		bO[0][3] *= -1;
		bO[1][3] *= -1;
		bO[2][3] *= -1;
		
		origin_ = bO;
		System.out.println(origin_[1][3]);
		System.out.println(backOrigin_[1][3]);
		firePropertyChange("origin", null, origin_);
		firePropertyChange("backOrigin", null, backOrigin_);
	} 
	
	
	private float[][] computeBackOrigin(float[][] origin)
	{
		float[][] bO = origin;
		
		bO[0][3] *= -1;
		bO[1][3] *= -1;
		bO[2][3] *= -1;
		
		return bO;
	}
	

	//Funktion, die 4x4 Einheitsmatrix zurückgibt
	public static float[][] identity()
	{
		float [][] id = new float[4][4];
		for(int i = 0; i < 4; ++i)
		{
			for(int j = 0; j < 4; ++j)
			{
				if(i == j) {
					id[i][j] = 1;
				} else
				{
					id[i][j] = 0;
				}
			}
		}
		return id;
	}
}
